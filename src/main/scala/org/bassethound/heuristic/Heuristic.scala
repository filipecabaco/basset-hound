package org.bassethound.heuristic

/**
  * Responsible for analysing the data provided by feeders and filter possible candidates from non candidates
  * @tparam A Type of input
  * @tparam B Type of the score
  */
trait Heuristic[A,B] {

  /**
    * Will assert a score from the given data and return a tuple of Element / Score
    * @param elem Element to be analysed
    * @return Tuple of Element / Score
    */
  def analyse(elem:A) : (A,B) = (elem , analyseFunc(elem))

  /**
    * Function responsible for asserting a score
    * @param elem Element to be analysed
    * @return Score asserted
    */
  def analyseFunc(elem:A) : B

  /**
    * Filter function to be applied by the heuristics filter
    * @param result A tuple of Element / Score retrieved from analysis
    * @return Is it a candidate?
    */
  def filterFunc(result: (A,B)) : Boolean

  def apply(content:(Any, Stream[A])) : (Any, List[(A,B)]) = (content._1 , content._2.par.map(analyse).filter(filterFunc).toList)
}
