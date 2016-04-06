package org.bassethound.heuristic.impl

import org.bassethound.heuristic.Heuristic

/**
  * Checks the percentage of numbers in a given string and if it's bigger than a certain threshold and
  * as a certain length , it's considered a possible candidate.
  */
class NumericHeuristic extends Heuristic[String,Double]{

  override def analyseFunc(candidate: String): Double = {
    val count : Double = candidate.length
    if(count < 8) return -1 //Check if the string is too small to be considered

    val cleanedCount : Double = "[\\D]".r.replaceAllIn(candidate, "").length

    if(cleanedCount == count) -1 else cleanedCount / count //Check the percentage and if all chars are numbers
  }

  override def filterFunc(score: Double): Boolean = score > 0.4
}
