package org.bassethound.feeder.impl

import org.bassethound.feeder.Feeder
import org.bassethound.model.Source

/**
  * Provides a Stream of String composed of all the lines in the given source
  */

object LineFeeder extends Feeder[String] {

  final val Pattern = "\\n".r

  /**
    * Receives Stream and should return a stream with the relevant information to be analysed (e.g. Lines , Words, etc)
    *
    * @param source Incoming String with all the contents from a Reader
    * @return Stream with relevant information
    */
  override def digest(source: Source[_, String]): Stream[String] = {
     Pattern.split(source.content.mkString("\n")).toStream
  }
}
