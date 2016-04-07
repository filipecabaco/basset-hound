package org.bassethound.app.output

sealed trait Outputs
object Outputs {
  case object Pretty extends Outputs
  case object Json extends Outputs
  case object PrettyJson extends Outputs
}
