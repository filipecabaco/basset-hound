package org.bassethound.feeder.impl

import org.bassethound.feeder.Feeder

/**
  * Provides a Stream of String composed of all the lines in the given source with the line number (starting with 0)
  */
class LineFeeder extends Feeder[String,String] {

  override def digest(source: (_, Stream[(String,Int)])) = (source._1, source._2)

}
