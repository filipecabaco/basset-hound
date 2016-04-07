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
  def aggregateOnSource(res : Seq[(_,Heuristic[_,_],Seq[(_,Int,_)])]) = res.foldLeft(emptyAggregationMap)(
    (acc, v : (_, Heuristic[_,_],Seq[(_,Int,_)])) => {
      val heuristic = v._2.getClass.getSimpleName
      val source = v._1.toString
      val updated = acc.getOrElse(source , Map.empty) + (heuristic -> v._3)
      acc.updated(source, updated)})



  /**
    * Aggregate results of various Sniffer's into a Map with the Source as key
    *
    * Note: The Result is always tuple composed by (Candidate, Line, Score)
    *
    * @param res Seq of (Source, Heuristic , Result)
    * @return Map with Heuristic as a Key with the values of Map [Source, Seq of Result]
    */
  def aggregateOnHeuristic(res: Seq[(_ , Heuristic[_,_] , Seq[(_,Int,_)])]) = res.foldLeft(emptyAggregationMap)(
    (acc, v: (_, Heuristic[_,_], Seq[(_,Int,_)])) => {
      val heuristic = v._2.getClass.getSimpleName
      val source = v._1.toString
      val updated = acc.getOrElse(heuristic , Map.empty) + (source -> v._3)
      acc.updated(heuristic, updated)})

  private def emptyAggregationMap = Map.empty[String,Map[String , Seq[(_,Int,_)]]]

}
