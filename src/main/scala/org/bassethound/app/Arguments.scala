package org.bassethound.app

sealed trait Arguments
object Arguments {
  case object Files extends Arguments
  case object OutputType extends Arguments
  case object OutputDisplay extends Arguments
  case object OutputTarget extends Arguments
  case object Excluded extends Arguments
  case object Config extends Arguments
  case object Aggregate extends Arguments
  case object Silent extends Arguments


}
