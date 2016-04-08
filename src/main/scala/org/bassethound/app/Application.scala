package org.bassethound.app

import java.io.File
import java.nio.file.FileAlreadyExistsException

import org.bassethound.app.output.{Json, Outputs, Pretty}
import org.bassethound.util.Aggregators
import scopt.OptionParser

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.language.postfixOps

object Application extends App{


  implicit val executionContext : ExecutionContext = scala.concurrent.ExecutionContext.global

  private var files: Option[Seq[File]] = None
  private var outputType: Outputs = Outputs.Pretty
  private var output: Option[File] = None
  private var excluded: Option[Seq[File]] = None

  private val parser = new OptionParser[Map[Arguments ,_]]("Basset Hound") {
    opt[Seq[File]]('f', "files") text "List of files and directories to be scanned" action {
      (args,map) => map.updated(Arguments.Files, args)
    }
    opt[String]('t', "type") text "Output type (defaults to pretty print)"  action {
      (args,map) => map.updated(Arguments.Output, args)
    }
    opt[File]('o', "output") text "Output target path"  action {
      (args,map) => map.updated(Arguments.Target, args)
    }
    opt[Seq[File]]('e', "excluded") text "Excluded files"  action {
      (args,map) => map.updated(Arguments.Excluded, args)
    }
  }

  parser.parse(args, Map.empty) match {
    case Some(options : Map[Arguments,_]) =>
      outputType = options.get(Arguments.Output).map{
        case v : String if v.equalsIgnoreCase("json") => Outputs.Json
        case v : String if v.equalsIgnoreCase("pretty-json") => Outputs.PrettyJson

      }.getOrElse(Outputs.Pretty)

      output = options.get(Arguments.Target).map{case v : File => v}

      files = options.get(Arguments.Files).map {
        case v: Seq[_] => v.map{case f: File => f}
      }

      excluded = options.get(Arguments.Excluded).map{
        case v: Seq[_] => v.map{case f: File => f}
      }
    case _ => None
  }


  println(
    s"""Welcome to Basset-hound
      |I'm currently fetching all the files on your directories / subdirectories to be analyzed by me so this might take some time...
    """.stripMargin)

  val all = files.map(org.bassethound.util.Files.getAll(_, excluded, Seq.empty)) // Get all files

  val analysis = all.map(new Analysis().run) // Run desired analysis
  val futures = analysis.map{f=> f}.getOrElse(Seq.empty) // Gather all the futures
  val res = Await.result(Future.sequence(futures) , 10 minute).flatten // Await for result
  val aggregate = Aggregators.aggregateOnSource(res.flatMap(v=>v)) // Aggregate results to easily worked on

  val out = outputType match {
    case Outputs.Pretty => Pretty.onSource(aggregate)
    case Outputs.Json => Json.render(aggregate)
    case Outputs.PrettyJson => Json.renderPretty(aggregate)
    case _ => "Unsupported format, please check available formats on documentation"
  }

  // Check if we expect to save it in a file, if we do save it otherwise print it to the console
  if(output.isEmpty){
    println(out)
  }else {
    val target = output.map{
      case f: File if f.exists && !f.isDirectory=>
        throw new FileAlreadyExistsException(s"$f already exists")  // If file already exists, avoid override by blowing up
      case f: File if !f.isDirectory =>                             // If it's not a directory, save it
        org.bassethound.util.Files.write(out,f)
      case f: File if f.isDirectory =>
        val newFile = new File(s"${f.getAbsolutePath}/out")         // If it's a directory, create the file out and save it
        org.bassethound.util.Files.write(out,newFile)
    }

    println(s"Saved output to file ${target.get}")
  }
}