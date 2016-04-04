package org.bassethound.app

import java.io.File

import org.bassethound.sniffer.impl.{KeywordFileSniffer, NumericFileSniffer}
import scopt.OptionParser

import scala.annotation.tailrec
import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.language.postfixOps

object Application extends App{

  implicit val executionContext : ExecutionContext = scala.concurrent.ExecutionContext.global

  private val parser = new OptionParser[List[String]]("Basset Hound") {
    arg[Seq[String]]("List of files and directories to be scanned") action {
      (f,list) => list ++ f
    }
  }

  private lazy val output = parser.parse(args, List.empty) match {
    case Some(list : List[String]) if list.nonEmpty =>
      val files = listFiles(list.map(v=> new File(v)) , List.empty)

      println(s"I found ${files.size} files in total!")
      println(s"This are that will be scanned: \n\t${files mkString "\n\t"}")

      val numericAnalysis = files.map(new NumericFileSniffer().sniff)
      val keywordAnalysis = files.map(new KeywordFileSniffer().sniff)

      Some(numericAnalysis ++ keywordAnalysis)
    case _ => None
  }

  @tailrec
  private def listFiles(files:List[File], acc: List[File]) : List[File] = {
    if(files.isEmpty){
      acc
    }else{
      val all = files.partition(_.isDirectory)
      val next = all._1.flatMap(_.listFiles()).filterNot(_.getName.startsWith(".")) //Exclude private folders
      listFiles(next, acc ++ all._2)
    }
  }

  println(
    """Welcome to Basset-hound
      |I'm currently fetching all the files to be analyzed by me so this might take some time.
    """.stripMargin)

  val res = output match {
    case Some(f) => Await.result(Future.sequence(f) , 10 minute).flatten.mkString("\n")
    case _ => "No arguments received, please check you added a file / folder to scan"
  }
  println(res)
}

