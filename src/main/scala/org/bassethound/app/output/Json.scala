package org.bassethound.app.output

import org.json4s.NoTypeHints
import org.json4s.native.Serialization
import org.json4s.native.Serialization._

object Json {
  implicit val formats = Serialization.formats(NoTypeHints)

  def render(m: Map[String,Map[String,Seq[(_,Int,_)]]]) : String = {
    write(m)
  }

  def renderPretty(m: Map[String,Map[String,Seq[(_,Int,_)]]]) : String = {
    writePretty(m)
  }

}




