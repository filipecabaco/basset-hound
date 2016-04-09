package org.bassethound.app.output

/**
  * Handles the output types Basset Hound supports
  */
sealed trait Outputs
object Outputs {
  case object Pretty extends Outputs
  case object Json extends Outputs
  case object PrettyJson extends Outputs

  /**
    * String to Outputs
    * @param s String
    * @return The Outputs associated (defaults to Pretty)
    */
  def parse(s:String) : Outputs = s match {
    case v : String if v.equalsIgnoreCase("json") => Outputs.Json
    case v : String if v.equalsIgnoreCase("pretty-json") => Outputs.PrettyJson
    case _ => Outputs.Pretty
  }

  /**
    * Runs the expected output and returns the String
    * @param outputs Outputs type
    * @param content Content to be printed
    * @return String content associated with selected Outputs
    */
  def out(outputs: Outputs , content : Map[String,Map[String,Seq[(_,Int,_)]]]) : String = outputs match {
    case Outputs.Pretty => org.bassethound.app.output.Pretty.onSource(content)
    case Outputs.Json => org.bassethound.app.output.Json.render(content)
    case Outputs.PrettyJson => org.bassethound.app.output.Json.renderPretty(content)
  }

}
