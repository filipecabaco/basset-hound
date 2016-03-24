package org.bassethound.sniffer

import org.bassethound.feeder.Feeder
import org.bassethound.heuristic.Heuristic
import org.bassethound.reader.Reader

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

trait  Sniffer[A,B,C,D]{
  implicit val executionContext:ExecutionContext

  val reader : Reader[A,B]
  val feeder:Feeder[B,C]
  val heuristic: Heuristic[C,D]

  def sniff(input : A) = {
    val read = Try(reader.read(input)).toOption
    val feed = read.map(feeder.digest)
    Future(feed.map(heuristic.apply))
  }
}

