package org.bassethound.util

import org.bassethound.heuristic.Heuristic

/**
  * Handles the aggregation of results
  */
object Aggregators {

  /**
    * Aggregate results of various Sniffer's into a Map with the Source as key
    *
    * Note: The Result is always tuple composed by (Candidate, Line, Score)
    *
    * @param res Seq of (Source, Heuristic , Result )
    * @return Map with Source as a Key with the values of Map[Heuristic, Seq of Result]
    */
  def aggregateOnSource(res : Seq[(_,Heuristic[_,_],Seq[(_,Int,_)])]) = {
    res.foldLeft(emptySourceAggregationMap)(
      (acc, v : (_, Heuristic[_,_],Seq[(_,Int,_)])) => {
        val updated = acc.getOrElse(v._1 , Map.empty) + (v._2 -> v._3)
        acc.updated(v._1, updated)
      })
  }

  private def emptySourceAggregationMap = Map.empty[Any,Map[Heuristic[_,_] , Seq[(_,Int,_)]]]

  /**
    * Aggregate results of various Sniffer's into a Map with the Source as key
    *
    * Note: The Result is always tuple composed by (Candidate, Line, Score)
    *
    * @param res Seq of (Source, Heuristic , Result)
    * @return Map with Heuristic as a Key with the values of Map [Source, Seq of Result]
    */
  def aggregateOnHeuristic(res: Seq[(_ , Heuristic[_,_] , Seq[(_,Int,_)])]) = {
    res.foldLeft(emptyHeuristicAggregationMap)(
      (acc, v: (_, Heuristic[_,_], Seq[(_,Int,_)])) => {
        val updated = acc.getOrElse(v._2 , Map.empty) + (v._1 -> v._3)
        acc.updated(v._2, updated)
      })
  }

  private def emptyHeuristicAggregationMap = Map.empty[Heuristic[_,_],Map[Any , Seq[(_,Int,_)]]]
}
