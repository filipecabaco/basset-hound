package org.bassethound.app

import java.io.File

import org.bassethound.sniffer.impl.{KeywordFileSniffer, NumericFileSniffer}
import org.bassethound.util.{Aggregators, Files}
import scopt.OptionParser

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.language.postfixOps


object Application extends App{

  private lazy val output = parser.parse(args, Map.empty) match {
    case Some(options : Map[String,_]) =>
      options.get("files").map {
        case v: Seq[_] =>
          val files = v.map{case f: File => f}
          processFiles(files)
      }
    case _ => None
  }
  implicit val executionContext : ExecutionContext = scala.concurrent.ExecutionContext.global

  private val parser = new OptionParser[Map[String,Any]]("Basset Hound") {
    opt[Seq[File]]('f', "files") text "List of files and directories to be scanned" action {
      (args,map) => map.updated("files", args)
    }
  }

  private def processFiles(files: Seq[File])={
    val all = Files.getAll(files , Seq.empty)

    println(
      s"""This are the files to be scanned: \n\t${all mkString "\n\t"}
         |
         |A total of ${all.size} files will be analysed.""".stripMargin)
    val numericFileSniffer = new NumericFileSniffer()
    val keywordFileSniffer = new KeywordFileSniffer()

    all.map{ f=>
      Future sequence Seq(
        Future(numericFileSniffer.sniff(f)),
        Future(keywordFileSniffer.sniff(f)))
    }
  }

  println(
    """Welcome to Basset-hound
      |I'm currently fetching all the files to be analyzed by me so this might take some time.
    """.stripMargin)

  output match {
    case Some(f) =>
      val res = f.flatMap(Await.result(_ , 10 minute)).flatten
      val aggregate = Aggregators.aggregateOnSource(res)
      println(s"\n\nResults of the analysis: \n${prettyPrint(aggregate)} \n\n")
      //println(s"\n\n Results of the analysis: \n ${aggregate mkString "\n\n"}")
    case _ => "No arguments received, please check you added a file / folder to scan"
  }

  private def prettyPrint(m : Map[_,Map[_,Seq[(_,Int,_)]]]) : String = {
    m.map{ v =>
      val content = v._2.map{ w =>
        val content = w._2.map{z=> s"\t\tCandidate: ${z._1} \n\t\tLine: ${z._2 + 1} \n\t\tScore: ${z._3}"} mkString "\n"
        s"\tHeuristic: ${w._1} \n$content"
      } mkString "\n"
      s"Source: ${v._1} \n $content"
    } mkString "\n"
  }

}


