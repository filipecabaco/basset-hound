package org.bassethound.app

import java.io.File

import org.bassethound.feeder.impl.WordFeeder
import org.bassethound.heuristic.impl.NumericHeuristic
import org.bassethound.reader.impl.FileReader

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

class Sniffer(implicit val executionContext : ExecutionContext) {

  def sniffOut(files : List[File]) = {
    val readers = files.flatMap(v => Try(FileReader.read(v)).toOption)
    val feeders = readers.map(v=> (v.source, WordFeeder.digest(v)))
    val heuristics = feeders.map(v=> Future((v._1 , new NumericHeuristic().apply(v._2))))

    Future.sequence(heuristics)
  }
}
