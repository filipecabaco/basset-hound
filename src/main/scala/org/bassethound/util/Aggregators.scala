package org.bassethound.util

import java.io.File

import org.bassethound.app.AggregateType
import org.bassethound.heuristic.Heuristic

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
  def aggregate(aggregateType: AggregateType , res: Seq[(_, Heuristic[_, _], Seq[(_, Int, _)])])= aggregateType match {
    case AggregateType.OnSource => aggregateOnScore(res)
    case AggregateType.OnHeuristic => aggregateOnHeuristic(res)
    case AggregateType.OnScore => aggregateOnScore(res)
  }

  /**
    * Aggregate results of various Sniffer's into a Map with the Source as key
    *
    * Note: The Result is always tuple composed by (Candidate, Line, Score)
    *
    * @param res Seq of (Source, Heuristic , Result)
    * @return Map with Heuristic as a Key with the values of Map [Source, Seq of Result]
    */
  def aggregateOnHeuristic(res: Seq[(_, Heuristic[_, _], Seq[(_, Int, _)])]) = res.foldLeft(emptyAggregationMap)(
    (acc, v: (_, Heuristic[_, _], Seq[(_, Int, _)])) => {
      val heuristic = v._2.getClass.getSimpleName
      val source = v._1.toString
      val updated = acc.getOrElse(heuristic, Map.empty) + (source -> v._3)
      acc.updated(heuristic, updated)})

  private def emptyAggregationMap = Map.empty[String, Map[String, Seq[(_, Int, _)]]]

  /**
    * Aggregate results of various Sniffer's into a Map with the Source as key and a sum of all scores
    * @param res Seq of (Source, Heuristic , Result)
    * @return Map with Source as a Key with the values of Map[Line, Seq of Result]
    */
  def aggregateOnScore(res: Seq[(_, Heuristic[_,_], Seq[(_,Int,_)])]) = {
    val scores = aggregateOnSource(res).map(v=> v._1 -> v._2.values.flatMap(v=>v).groupBy(_._2))
    val scoreByLine = scores.map(v=> v._1 -> v._2.map(v=> extractScores(v._1, v._2.toSeq)))
    scoreByLine.map(v=> v._1 -> v._2.map{z=>
      z._1.toString -> Seq((Files.getLine(new File(v._1),z._1),z._1 , z._2))
    })
  }

  /**
    * Aggregate results of various Sniffer's into a Map with the Source as key
    *
    * Note: The Result is always tuple composed by (Candidate, Line, Score)
    *
    * @param res Seq of (Source, Heuristic , Result )
    * @return Map with Source as a Key with the values of Map[Heuristic, Seq of Result]
    */
  def aggregateOnSource(res : Seq[(_, Heuristic[_, _],Seq[(_, Int, _)])]) = res.foldLeft(emptyAggregationMap)(
    (acc, v : (_, Heuristic[_, _],Seq[(_, Int, _)])) => {
      val heuristic = v._2.getClass.getSimpleName
      val source = v._1.toString
      val updated = acc.getOrElse(source, Map.empty) + (heuristic -> v._3)
      acc.updated(source, updated)})

  /**
    * Extracts the scores
    * @param line Line of the candidates
    * @param res Result tuple
    * @return Line -> Double with summed scores
    */
  private def extractScores(line: Int, res: Seq[(_,Int,_)]) = {
    line -> res.foldLeft(0.0)(sumScores)
  }

  /**
    * Sum scores
    * @param acc Accumulator
    * @param v Value from where we should extract the score
    * @return
    */
  private def sumScores(acc: Double, v: (_,Int,_)): Double = acc + normalize(v._3)

  /**
    * Normalize the results to our own criteria
    * @param score Score received
    * @return Normalized score
    */
  private def normalize(score: Any): Double = score match {
    case v : Double => v
    case v : Boolean if v => 0.3
    case v => throw new UnsupportedOperationException
  }
}
