package org.bassethound.feeder.impl

import org.bassethound.feeder.Feeder

/**
  * Provides a Stream of String composed of all the words in the given source with the line number (starting with 0)
  */
class WordFeeder extends Feeder[String,String] {

  final val Pattern = "\\w*".r

  override def digest(source: (_, Stream[(String,Int)])) = {
    val words = source._2.flatMap{v=>
      Pattern.findAllIn(v._1).map((_, v._2))
    }
    val filtered = words.filter(_._1.nonEmpty)
    (source._1, filtered)
  }
}
