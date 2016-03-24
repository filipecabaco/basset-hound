package org.bassethound.heuristic.impl

import org.bassethound.heuristic.Heuristic

/**
  * Checks the percentage of numbers in a given string
  */
class NumericHeuristic extends Heuristic[String,Double]{
  /**
    * Function responsible for asserting a score
    *
    * @param elem Element to be analysed
    * @return Score asserted
    */
  override def analyseFunc(elem: String): Double = ???

  /**
    * Filter function to be applied by the heuristics filter
    *
    * @param result A tuple of Element / Score retrieved from analysis
    * @return Is it a candidate?
    */
  override def filterFunc(result: (String, Double)): Boolean = ???
}
