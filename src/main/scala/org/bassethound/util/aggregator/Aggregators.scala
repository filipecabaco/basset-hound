package org.bassethound.util.aggregator

import org.bassethound.app.output.AggregateType
import org.bassethound.heuristic.Heuristic
import org.bassethound.util.aggregator.types.{AggregateOnHeuristic, AggregateOnScore, AggregateOnSource}

/**
  * Handles the aggregation of results
  */
object Aggregators {

  /**
    * Aggregate results of various Sniffer's into a Map according to the AggregateType
    *
    * Note: The Result is always tuple composed by (Candidate, Line, Score)
    *
    * @param res Seq of (Source, Heuristic , Result)
    * @return Map organized based on Aggregate Type
    */
  def aggregate(aggregateType: AggregateType , res: Seq[(_, Heuristic[_, _], Seq[(_, Int, _)])]) = aggregateType match {
    case AggregateType.OnSource => AggregateOnSource.aggregateFunc(res)
    case AggregateType.OnHeuristic => AggregateOnHeuristic.aggregateFunc(res)
    case AggregateType.OnScore => AggregateOnScore.aggregateFunc(res)
  }

}
