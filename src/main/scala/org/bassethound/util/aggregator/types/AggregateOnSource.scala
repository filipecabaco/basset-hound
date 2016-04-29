package org.bassethound.util.aggregator.types

import org.bassethound.heuristic.Heuristic
import org.bassethound.util.aggregator.Aggregators

object AggregateOnSource extends Aggregator{

  def aggregateFunc(res: Seq[(_, Heuristic[_, _], Seq[(_, Int, _)])]) = {
    val aggregated = res.foldLeft(emptyAggregationMap)(accumulateOnSource)
    countOccurrences(aggregated)
  }

  /**
    * With the given accumulator, creates an updated version with new aggregated information on Source
    * @param acc Accumulator being used
    * @param values Values to be aggregated
    * @return New updated map
    */
  private def accumulateOnSource(acc: Map[String, Map[String, Seq[(_, Int, _)]]],
                                 values : (_, Heuristic[_,_],Seq[(_, Int, _)])) = values match {
    case (source:Any, heuristic:Heuristic[_,_], result:Seq[(_, Int, _)]) =>
      val heuristicName = heuristic.getClass.getSimpleName
      val sourceName = source.toString
      val updated = acc.getOrElse(sourceName, Map.empty) + (heuristicName -> result)
      acc.updated(sourceName, updated)
  }
}
