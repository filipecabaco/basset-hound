package org.bassethound.heuristic.impl

import org.bassethound.heuristic.Heuristic

class KeywordHeuristic extends Heuristic[String,Boolean]{
  private final val Keywords = Set("secret" , "token")
  private final val Separators = Set("=" , ":")

  /*
    Group 0 - Full match
    Group 1 - Keyword found
    Group 2 - Separator found
    Group 3 - Word found (until next whitespace)
   */
  private final val ImportantRegex = s"""(${Keywords.mkString("|")})\\s*(${Separators.mkString("|")})\\s*(\\S+)""".r

  /**
    * Function responsible for asserting a score
    *
    * @param elem Element to be analysed
    * @return Score asserted
    */
  override def analyseFunc(elem: String): Boolean = ImportantRegex.findAllMatchIn(elem).nonEmpty

  /**
    * Filter function to be applied by the heuristics filter
    *
    * @param result A tuple of Element / Score retrieved from analysis
    * @return Is it a candidate?
    */
  override def filterFunc(result: (String, Boolean)): Boolean = result._2
}
