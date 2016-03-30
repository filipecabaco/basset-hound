package org.bassethound.heuristic.impl

import org.bassethound.heuristic.Heuristic

/**
  * Checks the percentage of numbers in a given string
  */
class NumericHeuristic extends Heuristic[String,Double]{

  override def analyseFunc(elem: String): Double = {
    val count : Double = elem.length
    if(count < 8) return -1 //Check if the string is too small to be considered

    val cleanedCount : Double = "[\\D]".r.replaceAllIn(elem, "").length

    if(cleanedCount == count) -1 else cleanedCount / count //Check the percentage and if all chars are numbers
  }

  override def filterFunc(result: (String, Double)): Boolean = result._2 > 0.4
}
