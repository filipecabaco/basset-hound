package org.bassethound.heuristic

/**
  * Responsible for analysing the data provided by feeders and filter possible candidates from non candidates
  * @tparam A Type of input
  * @tparam B Type of the score
  */
trait Heuristic[A,B] {

  /**
    * Function responsible for asserting a score
    * @param candidate Element to be analysed
    * @return Score asserted
    */
  def analyseFunc(candidate:A) : B

  /**
    * Filter function to be applied by the heuristics filter
    * @param score Score retrieved from analysis
    * @return Is it a candidate?
    */
  def filterFunc(score: B) : Boolean

  /**
    * Will run the analysisFunc filter using filterFunc and return a tuple with Candidate, Line and Score
    * @param content The content to be analysed
    * @return A tuple with (Candidate, Line, Score)
    */
  def apply(content:(_, Stream[(A,Int)])) : (_, Heuristic[A,B], Seq[(A,Int,B)]) = {
    val analysis = content._2.map(v=> (v._1, v._2 , analyseFunc(v._1)))
    val filtered = analysis.filter(v=> filterFunc(v._3)).toList
    (content._1, this, filtered)
  }
}
