package org.bassethound.feeder

import org.bassethound.model.Source

/**
  * This should handle the incoming stream and extract / handle
  * important information to be used by heuristic analyser
  * @tparam B Type of the output from a Source object
  */

trait Feeder[B] {
  /**
    * Receives Stream and should return a stream with the relevant information to be analysed (e.g. Lines , Words, etc)
    *
    * @param source Incoming String with all the contents from a Reader
    * @return Stream with relevant information
    */
  def digest(source: Source[_,B]) : Stream[String]
}
