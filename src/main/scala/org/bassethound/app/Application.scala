package org.bassethound.app

import java.io.File

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration._
import scala.language.postfixOps

object Application extends App{
  println(
    """Welcome to Basset Hound
      |Given the reader type and the list of inputs I will try to sniff out secrets in your code based on my heuristics
      |This will return a list of candidates according to each heuristic and output you the files with candidates.
      |
      |Please bare in mind that I'm still young and foolish on my analysis
    """.stripMargin)

  implicit val executionContext : ExecutionContext = scala.concurrent.ExecutionContext.global

  val files : List[File] = args.map(v => new File(v)).toList

  val results = Await.result(new Sniffer().sniffOut(files), 5 minute)

  results.foreach(println)
}
