package org.bassethound.app

import java.io.File

import org.bassethound.exceptions.UnsupportedEntryType
import org.bassethound.feeder.impl.WordFeeder
import org.bassethound.heuristic.impl.NumericHeuristic
import org.bassethound.reader.impl.FileReader

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

class Sniffer(implicit val executionContext : ExecutionContext) {

  private val partial : PartialFunction[Any, Future[Option[(Any , List[(Any,Any) ] )]]] = {
    case f : File => fileSniffOut(f)
    case _ => throw new UnsupportedEntryType
  }

  def sniff(entry : Any) = partial(entry)

  private def fileSniffOut(file : File) = {
    val readers = Try(FileReader.read(file)).toOption
    val feeders = readers.map(v=> (v.source, WordFeeder.digest(v)))
    Future(feeders.map(v => (v._1 , new NumericHeuristic().apply(v._2))))
  }
}
