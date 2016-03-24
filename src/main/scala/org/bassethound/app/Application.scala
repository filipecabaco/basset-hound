package org.bassethound.app

import java.io.File

import org.bassethound.feeder.impl.WordFeeder
import org.bassethound.heuristic.impl.NumericHeuristic
import org.bassethound.reader.impl.FileReader

import scala.concurrent.{Await, Future}
import scala.util.Try
import scala.concurrent.duration._
import scala.language.postfixOps

import scala.concurrent.ExecutionContext.Implicits.global

object Application extends App{
  println(
    """Welcome to Basset Hound
      |Given the reader type and the list of inputs I will try to sniff out secrets in your code based on my heuristics
      |This will return a list of candidates according to each heuristic and output you the files with candidates.
      |
      |Please bare in mind that I'm still young and foolish on my analysis
    """.stripMargin)

  val files : List[File] = args.map(v => new File(v)).toList

  val readers = files.flatMap(v => Try(FileReader.read(v)).toOption)
  val feeders = readers.map(WordFeeder.digest)
  val heuristics = feeders.map(v=> Future(new NumericHeuristic().apply(v)))

  val futures = Future.sequence(heuristics)
  val results = Await.result(futures, 5 minute)

  results.foreach(println)

}
