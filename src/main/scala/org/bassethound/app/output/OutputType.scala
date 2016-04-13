package org.bassethound.app.output

import org.bassethound.app.AggregateType

/**
  * Handles the output types Basset Hound supports
  */
sealed trait OutputType
object OutputType {
  case object Pretty extends OutputType
  case object Json extends OutputType
  case object PrettyJson extends OutputType

  /**
    * String to Outputs
    *
    * @param s String
    * @return The Outputs associated (defaults to Pretty)
    */
  def parse(s:String) : OutputType = s match {
    case v : String if v.equalsIgnoreCase("json") => OutputType.Json
    case v : String if v.equalsIgnoreCase("pretty-json") => OutputType.PrettyJson
    case _ => OutputType.Pretty
  }

  /**
    * Runs the expected output and returns the String
    *
    * @param outputs Outputs type
    * @param content Content to be printed
    * @return String content associated with selected Outputs
    */
  def out(outputs: OutputType, aggregate: AggregateType, content : Map[String,Map[String,Seq[(_,Int,_)]]]) : String = outputs match {
    case OutputType.Pretty => aggregate match {
      case AggregateType.OnSource => org.bassethound.app.output.Pretty.onSource(content)
      case AggregateType.OnHeuristic => org.bassethound.app.output.Pretty.onHeuristic(content)
      case AggregateType.OnScore => org.bassethound.app.output.Pretty.onScore(content)
    }
    case OutputType.Json => org.bassethound.app.output.Json.render(content)
    case OutputType.PrettyJson => org.bassethound.app.output.Json.renderPretty(content)
  }

}
