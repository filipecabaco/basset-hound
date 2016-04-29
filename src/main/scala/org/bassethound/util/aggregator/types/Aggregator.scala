package org.bassethound.util.aggregator.types

import org.bassethound.heuristic.Heuristic

/**
  * Trait with that defines the way to aggregate results and with some auxiliary functions
  */
trait Aggregator {
  /**
    * Aggregate results of various Sniffer's into a Map with the Source as key
    *
    * Note: The Result is always tuple composed by (Candidate, Line, Score)
    *
    * @param res Seq of (Source, Heuristic , Result )
    * @return Map with normalized and organized values to be printed
    */
  def aggregateFunc(res: Seq[(_, Heuristic[_, _], Seq[(_, Int, _)])]): (Int, Map[String,(Int,Map[String,Seq[(_,Int,_)]])])

  /**
    * Base type to be used by all aggregations
    *
    * @return Empty type to be used
    */
  def emptyAggregationMap = Map.empty[String, Map[String, Seq[(_, Int, _)]]]

  /**
    * Counts the number of occurrences for a given result
    * (this can be used to sum up the occurrences of an Heuristic or Source)
    *
    * @param m Aggregated map
    * @return Aggregated map with number of occurrences per element and the total
    */
  def countOccurrences(m:Map[String, Map[String, Seq[(_, Int, _)]]]) ={
    val withOccurrences = m.map(v=> v._1 -> (v._2.foldLeft(0)(_ + _._2.size) , v._2))
    val totalOccurrences = withOccurrences.mapValues(_._1).foldLeft(0)(_ + _._2)
    (totalOccurrences, withOccurrences)
  }
}
