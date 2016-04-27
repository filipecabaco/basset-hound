package org.bassethound.app.output

import java.io.File

/**
  * Handles the output types Basset Hound supports
  */
sealed trait OutputDisplay
object OutputDisplay {
  case object File extends OutputDisplay
  case object Report extends OutputDisplay
  case object Console extends OutputDisplay

  /**
    * String to OutputLocation
    *
    * @param s String
    * @return The Outputs associated (defaults to Pretty)
    */
  def parse(s:String) : OutputDisplay = s match {
    case v : String if v.equalsIgnoreCase("file") => OutputDisplay.File
    case v : String if v.equalsIgnoreCase("report") => OutputDisplay.Report
    case _ => OutputDisplay.Console
  }

  /**
    * Runs the expected output and returns the String
    *
    * @param outputs Outputs type
    * @param content Content to be printed
    * @return String content associated with selected Outputs
    */
  def out(outputs: OutputDisplay, aggregateType: AggregateType, content: String, file: Option[File]) = outputs match {
    case OutputDisplay.File if file.isEmpty=>
      println(s"Please specify the output path")
    case OutputDisplay.File =>
      val res = org.bassethound.app.output.location.FileOutput.out(content,file)
      println(s"Created file with content of output: $res")
    case OutputDisplay.Report =>
      val res = org.bassethound.app.output.location.HtmlReport.report(content,aggregateType)
      println(s"Created HTML report: $res")
    case OutputDisplay.Console =>
      println(content)
  }

}
