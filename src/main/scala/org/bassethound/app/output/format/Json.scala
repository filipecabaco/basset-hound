package org.bassethound.app.output.format

import org.json4s.NoTypeHints
import org.json4s.native.Serialization
import org.json4s.native.Serialization._

object Json {
  implicit val formats = Serialization.formats(NoTypeHints)

  def render(m: (Int, Map[String,(Int, Map[String,Seq[(_,Int,_)]])])) : String = {
    write(m)
  }

  def renderPretty(m: (Int, Map[String,(Int, Map[String,Seq[(_,Int,_)]])])) : String = {
    writePretty(m)
  }
}




