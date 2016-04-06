package org.bassethound.sniffer

import org.bassethound.feeder.Feeder
import org.bassethound.heuristic.Heuristic
import org.bassethound.reader.Reader

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

/**
  * This is the element that connects Reader -> Feeder -> Heuristic
  * @tparam A Type of the source (e.g. File, API, Raw text)
  * @tparam B Type of the output of the Reader
  * @tparam C Type of the output of the Feeder
  * @tparam D Type of the output of the score from the Heuristic
  */
trait  Sniffer[A,B,C,D]{
  implicit val executionContext:ExecutionContext

  val reader : Reader[A,B]
  val feeder:Feeder[B,C]
  val heuristic: Heuristic[C,D]

  /**
    * Calls all elements by order and returns a future with the result
    * @param input The initial input to be processed by the Reader
    * @return Future with the result of the heuristic applied to it
    */
  def sniff(input : A) = Try{
      val read = reader.read(input)
      val feed = feeder.digest(read)
      heuristic.apply(feed)
    }.toOption.filter(_._3.nonEmpty)
}