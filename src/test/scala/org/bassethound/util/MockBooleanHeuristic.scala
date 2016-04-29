package org.bassethound.util

import org.bassethound.heuristic.Heuristic

/**
  * Implementation of a Heuristic to be used for simple testing
  */
class MockBooleanHeuristic extends Heuristic[Boolean, Boolean]{

  override def analyseFunc(candidate: Boolean): Boolean = candidate

  override def filterFunc(score: Boolean): Boolean = score
}
