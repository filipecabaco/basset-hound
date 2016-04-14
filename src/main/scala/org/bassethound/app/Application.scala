package org.bassethound.app

import java.io.File

import com.typesafe.config.ConfigFactory
import org.bassethound.app.output.{OutputDisplay, OutputFormat}
import org.bassethound.util.Aggregators
import scopt.OptionParser

import scala.collection.JavaConversions._
import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.language.postfixOps
import scala.util.Try


object Application extends App{


  implicit val executionContext : ExecutionContext = scala.concurrent.ExecutionContext.global

  private var files: Option[Seq[File]] = None
  private var output: OutputFormat = OutputFormat.Pretty
  private var outputDisplay: OutputDisplay = OutputDisplay.Console
  private var outputTarget: Option[File] = None
  private var excluded: Option[Seq[File]] = None
  private var aggregateType: AggregateType = AggregateType.OnSource
  private var silent: Boolean = false

  private val parser = new OptionParser[Map[Arguments ,_]]("Basset Hound") {
    opt[Seq[File]]('f', "files") text "Specify the list of files and directories to be scanned" action {
      (args,map) => map.updated(Arguments.Files, args)
    }
    opt[Seq[File]]('e', "excluded") text "Specify the excluded files"  action {
      (args,map) => map.updated(Arguments.Excluded, args)
    }
    opt[String]('d', "display") text "Specify where the output will be displayed / saved"  action {
      (args,map) => map.updated(Arguments.OutputDisplay, args)
    }
    opt[String]('o', "output") text "Specify the output type"  action {
      (args,map) => map.updated(Arguments.OutputType, args)
    }
    opt[File]('t', "target") text "Specify the target location for the output"  action {
      (args,map) => map.updated(Arguments.OutputTarget, args)
    }
    opt[String]('a', "aggregate") text "Specify the aggregation type" action{
      (args,map) => map.updated(Arguments.Aggregate, args)
    }
    opt[File]('c', "conf") text "Specify configuration file" action {
      (args, map) => map.updated(Arguments.Config, args)
    }
    opt[Boolean]('s', "silent") text "Run in silent mode" action {
      (args, map) => map.updated(Arguments.Silent, args)
    }
    help("help")
  }

  parser.parse(args, Map.empty) match {
    case Some(options : Map[Arguments,_]) =>

      aggregateType = AggregateType.parse(options.getOrElse(Arguments.Aggregate, "").toString)

      outputDisplay = OutputDisplay.parse(options.getOrElse(Arguments.OutputDisplay, "").toString)

      outputTarget = options.get(Arguments.OutputTarget).map{case v : File => v}

      output = OutputFormat.parse(options.getOrElse(Arguments.OutputType , "").toString)

      files = options.get(Arguments.Files).map { case v: Seq[_] => v.map{case f: File => f} }

      excluded = options.get(Arguments.Excluded).map{ case v: Seq[_] => v.map{case f: File => f} }

      silent = options.get(Arguments.Silent).map{case b : Boolean => b}.getOrElse(false)

      //Override configuration based on config file
      options.get(Arguments.Config).foreach{
        case v: File =>
          val c = ConfigFactory.parseFile(v)

          files = Try(c.getStringList("files").toSeq.map{v=>new File(v)}).toOption
          excluded = Try(c.getStringList("excluded").toSeq.map{v=>new File(v)}).toOption
          outputDisplay = Try(OutputDisplay.parse(c.getString("display"))).getOrElse(outputDisplay)
          outputTarget = Try(new File(c.getString("target"))).toOption
          output = Try(OutputFormat.parse(c.getString("output"))).getOrElse(output)
          aggregateType = Try(AggregateType.parse(c.getString("aggregate"))).getOrElse(aggregateType)
          silent = Try(c.getBoolean("silent")).getOrElse(silent)
      }

      //Force the output to be of type JSON if we want a Report
      output = if(outputDisplay == OutputDisplay.Report) OutputFormat.Json else output

      execute(silent)
    case _ => None
  }

  def execute(silent: Boolean = false) = {
    if(!silent){
      println(
        s"""Welcome to Basset-hound
            |I'm currently fetching all the files on your directories / subdirectories to be analyzed by me so this might take some time...
            |
            |My configs are the following:
            |Files: $files
            |Excluded: $excluded
            |Output Display: $outputDisplay
            |Output: $output
            |Output Target: $outputTarget
            |AggregateType: $aggregateType""".stripMargin)
    }
    val all = files.map(org.bassethound.util.Files.getAll(_, excluded, Seq.empty)) // Get all files
    val analysis = all.map(new Analysis().run) // Run desired analysis
    val futures = analysis.map{f=> f}.getOrElse(Seq.empty) // Gather all the futures
    val res = Await.result(Future.sequence(futures) , 10 minute).flatten // Await for result
    val aggregated = Aggregators.aggregate(aggregateType, res.flatMap(v=>v)) // Aggregation of results

    val out = OutputFormat.out(output, aggregateType, aggregated) //Prepare output

    OutputDisplay.out(outputDisplay, aggregateType, out, outputTarget) // Handle output
  }
}