package org.bassethound.app


sealed trait AggregateType

object AggregateType {
  case object OnSource extends AggregateType
  case object OnHeuristic extends AggregateType
  case object OnScore extends AggregateType

  /**
    * String to Outputs
    *
    * @param s String
    * @return The Outputs associated (defaults to Pretty)
    */
  def parse(s:String): AggregateType = s match {
    case v : String if v.equalsIgnoreCase("heuristic") => AggregateType.OnHeuristic
    case v : String if v.equalsIgnoreCase("score") => AggregateType.OnScore
    case _ => AggregateType.OnSource
  }
}

