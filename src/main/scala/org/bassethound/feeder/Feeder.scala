package org.bassethound.feeder

/**
  * This should handle the incoming stream and extract / handle
  * important information to be used by heuristic analyser
  * @tparam A Type of the output from a Source object
  * @tparam B Type of the output from a Feeder object
  */

trait Feeder[A,B] {
  /**
    * Receives Stream and should return a stream with the relevant information to be analysed (e.g. Lines , Words, etc)
    *
    * @param source Incoming String with all the contents from a Reader
    * @return Stream with relevant information
    */
  def digest(source: (_,Stream[(A,Int)])) : (_, Stream[(B,Int)])
}
