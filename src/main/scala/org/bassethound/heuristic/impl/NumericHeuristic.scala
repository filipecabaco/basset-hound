package org.bassethound.heuristic.impl

import org.bassethound.heuristic.Heuristic

/**
  * Checks the percentage of numbers in a given string
  */
class NumericHeuristic extends Heuristic[String,Double]{

  private final val Pattern = "[\\D]".r

  override def analyseFunc(elem: String): Double = {
    val count : Double = elem.length
    val cleanedCount : Double = Pattern.replaceAllIn(elem, "").length
    cleanedCount / count
  }

  override def filterFunc(result: (String, Double)): Boolean = result._2 > 0.4
}
