package org.bassethound.heuristic.impl

import org.bassethound.heuristic.Heuristic

/**
  * Checks for the existence of certain keywords and respective separators,
  * tries to assert if there's some critical information in the candidate
  */
class KeywordHeuristic extends Heuristic[String,Boolean]{
  private final val Keywords = Set("secret" , "token" ,"api_key")
  private final val Separators = Set("=" , ":")

  /**
    Group 0 - Full match
    Group 1 - Keyword found
    Group 2 - Separator found
    Group 3 - Word found (until next whitespace)
    */
  private final val ImportantRegex = s"""(${Keywords.mkString("|")})\\s*(${Separators.mkString("|")})\\s*(\\S+)""".r

  override def analyseFunc(candidate: String): Boolean = ImportantRegex.findAllMatchIn(candidate).nonEmpty

  override def filterFunc(score: Boolean): Boolean = score
}
