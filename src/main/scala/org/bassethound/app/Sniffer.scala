package org.bassethound.app

import java.io.File

import org.bassethound.exceptions.UnsupportedEntryType
import org.bassethound.feeder.impl.WordFeeder
import org.bassethound.heuristic.impl.NumericHeuristic
import org.bassethound.reader.impl.{FileReader, RawTextReader}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

class Sniffer(implicit val executionContext : ExecutionContext) {

  private val partial : PartialFunction[Any, Future[Option[(Any , List[(Any,Any) ] )]]] = {
    case f : File => fileSniffOut(f)
    case s : String => stringSniffOut(s)
    case _ => throw new UnsupportedEntryType
  }

  def sniff(entry : Any) = partial(entry)

  private def fileSniffOut(file : File) = {
    val reader = Try(FileReader.read(file)).toOption
    val feeder = reader.map(v=> (v._1, WordFeeder.digest(v)))
    Future(feeder.map(v => (v._1 , new NumericHeuristic().apply(v._2))))
  }

  private def stringSniffOut(string : String) = {
    val reader = Some(RawTextReader.read(string))
    val feeder = reader.map(v=> (v._1, WordFeeder.digest(v)))
    Future(feeder.map(v => (v._1 , new NumericHeuristic().apply(v._2))))
  }
}
