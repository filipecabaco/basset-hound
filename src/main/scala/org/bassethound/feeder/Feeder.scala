package org.bassethound.feeder

import org.bassethound.model.Source

/**
  * This should handle the incoming stream and extract / handle
  * important information to be used by heuristic analyser
  *
  */

trait Feeder[A,B] {
  /**
    * Receives Stream and should return a stream with the relevant information to be analysed (e.g. Lines , Words, etc)
    *
    * @param source Incoming String with all the contents from a Reader
    * @return Stream with relevant information
    */
  def digest(source: Source[A,B]) : Stream[String]
}
