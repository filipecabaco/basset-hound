package org.bassethound.util

import org.bassethound.heuristic.Heuristic

/**
  * Implementation of a Heuristic to be used for simple testing
  */
class MockStringHeuristic extends Heuristic[String, Double]{

  override def analyseFunc(candidate: String): Double = 0.7

  override def filterFunc(score: Double): Boolean = true
}
