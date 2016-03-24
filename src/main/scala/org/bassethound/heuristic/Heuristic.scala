package org.bassethound.heuristic

/**
  * Responsible for analysing the data provided by feeders and filter possible candidates from non candidates
  */
trait Heuristic[A] {
  /**
    * Will be responsible for analysing the content and assert a score for each element
    * @param content
    * @return
    */
  def analyse(content:Stream[A]) : List[A]

  /**
    * Filter function to be applied by the heuristics filter
    * @param elem  Element that will be analysed
    * @return Is it a candidate?
    */
  def filterFunc(elem : A) : Boolean

  /**
    * According to the specifics of the heuristic this will filter out the possible candidates from the content.
    *
    * It uses to the function specified in filterFunc
    *
    * @param content Content to be analyzed and filter the candidates out of it
    * @return The candidates
    */
  def filter(content:Stream[A]) : List[A] = content.filter(filterFunc).toList

}
