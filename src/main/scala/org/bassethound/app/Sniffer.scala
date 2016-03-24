package org.bassethound.app

import java.io.File

import org.bassethound.exceptions.UnsupportedEntryType
import org.bassethound.feeder.Feeder
import org.bassethound.heuristic.Heuristic
import org.bassethound.reader.impl.{FileReader, RawTextReader}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

class Sniffer[A,B,C](feeder:Feeder[A,B])(heuristic: Heuristic[B,C])(implicit val executionContext : ExecutionContext) {
/**
  private val partial : PartialFunction[Any, Future[Option[(A , List[(B,C) ] )]]] = {
    case f : File => fileSniffOut(f)
    case s : String => stringSniffOut(s)
    case _ => throw new UnsupportedEntryType
  }

  def sniff(entry : Any) = partial(entry)

  private def fileSniffOut(file : File) = {
    val reader = Try(FileReader.read(file)).toOption
    val feed = reader.map(v=> (v._1, feeder.digest(v)))
    Future(feed.map(v => (v._1 , heuristic.apply(v._2))))
  }

  private def stringSniffOut(string : String) = {
    val reader = Some(RawTextReader.read(string))
    val feed = reader.map(v=> (v._1, feeder.digest(v)))
    Future(feed.map(v => (v._1 , heuristic.apply(v._2))))
  }
  */
}
