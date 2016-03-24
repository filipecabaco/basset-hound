package org.bassethound.feeder.impl

import org.bassethound.feeder.Feeder

/**
  * Provides a Stream of String composed of all the words in the given source
  */
class WordFeeder extends Feeder[String,String] {

  final val Pattern = "\\w*".r

  /**
    * Receives Stream and should return a stream with the relevant information to be analysed (e.g. Lines , Words, etc)
    *
    * @param source Incoming String with all the contents from a Reader
    * @return Stream with relevant information
    */
  override def digest(source: (_, Stream[String])): Stream[String] = {
    source._2.flatMap(v => Pattern.findAllIn(v)).filterNot(_.isEmpty())
  }
}
