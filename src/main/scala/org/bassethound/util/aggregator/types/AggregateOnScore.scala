package org.bassethound.util.aggregator.types

import org.bassethound.heuristic.Heuristic

object AggregateOnScore extends Aggregator {

  def aggregateFunc(res: Seq[(_, Heuristic[_,_], Seq[(_,Int,_)])]) = {
    val onSource = AggregateOnSource.aggregateFunc(res)
    val totalOccurrences = onSource._1
    val results = onSource._2
    val scores = results.map{
      case (source, result) => source -> (result._1,result._2.values.flatMap(v=>v).groupBy(_._2))
    }

    val scoreByLine = scores.map{
      case(source, result) => source -> (result._1 ,result._2.flatMap{mergeResults})
    }

    (totalOccurrences, scoreByLine)
  }


  /**
    * Merge the results into a unique tuple that will represent a line
    * @param values Results to be handled
    * @return Merged tuples of results with normalized scores
    */
  private def mergeResults(values: (Int, Iterable[(_,Int,_)])) = {
    val normalizedScore = values match {
      case (_, res) => normalizeScores(res.toSeq)
    }

    normalizedScore.foldLeft(Map.empty[String, Seq[(_, Int, Double)]]){
      case (acc, (candidate,line:Int,score)) =>
        val merged = acc.get(line.toString) match {
          case Some(value)=> merge((candidate,line,score))(value.head)
          case _ => (candidate,line,score)
        }
        acc.updated(line.toString, Seq(merged))
    }
  }

  /**
    * Merges values from two result tuples
    * @param v1 First tuple
    * @param v2 Second tuple
    * @return New tuple with the largest candidate string, line from the first tuple and summed score
    */
  private def merge(v1: (_, Int, Double))(v2: (_, Int, Double)) = {
    val candidate1 = v1._1.toString
    val candidate2 = v2._1.toString

    (if(candidate1.length > candidate2.length) candidate1 else candidate2, v1._2, v1._3 + v2._3)
  }

  /**
    * Handles the normalization of scores to be used on aggregation
    * @param res List of results which is a tuple of (candidate, line, score :Any)
    * @return A list of results which is a tuple of (candidate, line, score :Double)
    */
  private def normalizeScores(res : Seq[(_,Int,_)]) = res map {
    case (candidate,line,score) => (candidate, line, normalize(score))
  }
  /**
    * Normalize the results to our own criteria
    *
    * @param result Score received
    * @return Normalized score
    */
  private def normalize(result: Any): Double = result match {
    case score : Double => score
    case score : Boolean if score => 0.3
    case v => throw new UnsupportedOperationException
  }
}
