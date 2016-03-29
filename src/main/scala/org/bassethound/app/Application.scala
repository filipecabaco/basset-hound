package org.bassethound.app

import java.io.File

import org.bassethound.sniffer.impl._

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._
import scala.language.postfixOps

object Application extends App{
  println(
    """Welcome to Basset Hound
      |Given the list of inputs I will try to sniff out secrets in your code based on my readers and heuristics
      |This will return a list of candidates according to each heuristic and output you the files with candidates.
      |
      |Please bare in mind that I'm still young and foolish on my analysis
    """.stripMargin)

  implicit val executionContext : ExecutionContext = scala.concurrent.ExecutionContext.global

  val files : List[File] = args.map(v => new File(v)).toList // File Example
  val strings : List[String] = args.map(v =>v).toList //Raw text Example

  val numericAnalysisFilesResults = files.map(new NumericFileSniffer().sniff)
  val numericAnalysisStringsResults = strings.map(new NumericStringSniffer().sniff)
  val keywordAnalysisFilesResults = files.map(new KeywordFileSniffer().sniff)
  val keywordAnalysisStringsResults = strings.map(new KeywordStringSniffer().sniff)


  val output = Await.result(Future.sequence(numericAnalysisFilesResults ++
    numericAnalysisStringsResults ++
    keywordAnalysisFilesResults ++
    keywordAnalysisStringsResults) , 5 minute)

  output.flatten.filter(_._2.nonEmpty).foreach(println)

}
