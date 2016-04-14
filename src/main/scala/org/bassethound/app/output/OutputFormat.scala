package org.bassethound.app.output

import org.bassethound.app.AggregateType
/**
  * Handles the output types Basset Hound supports
  */
sealed trait OutputFormat
object OutputFormat {
  case object Pretty extends OutputFormat
  case object Json extends OutputFormat
  case object PrettyJson extends OutputFormat

  /**
    * String to Outputs
    *
    * @param s String
    * @return The Outputs associated (defaults to Pretty)
    */
  def parse(s:String) : OutputFormat = s match {
    case v : String if v.equalsIgnoreCase("json") => OutputFormat.Json
    case v : String if v.equalsIgnoreCase("pretty-json") => OutputFormat.PrettyJson
    case _ => OutputFormat.Pretty
  }

  /**
    * Runs the expected output and returns the String
    *
    * @param outputs Outputs type
    * @param content Content to be printed
    * @return String content associated with selected Outputs
    */
  def out(outputs: OutputFormat, aggregate: AggregateType, content: (Int, Map[String,(Int,Map[String,Seq[(_,Int,_)]])])) : String = outputs match {
    case OutputFormat.Pretty => aggregate match {
      case AggregateType.OnSource => org.bassethound.app.output.format.Pretty.onSource(content)
      case AggregateType.OnHeuristic => org.bassethound.app.output.format.Pretty.onHeuristic(content)
      case AggregateType.OnScore => org.bassethound.app.output.format.Pretty.onScore(content)
    }
    case OutputFormat.Json => org.bassethound.app.output.format.Json.render(content)
    case OutputFormat.PrettyJson => org.bassethound.app.output.format.Json.renderPretty(content)
  }

}
