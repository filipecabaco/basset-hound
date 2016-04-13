package org.bassethound.app

sealed trait Arguments
object Arguments {
  case object Files extends Arguments
  case object Output extends Arguments
  case object Target extends Arguments
  case object Excluded extends Arguments
  case object Config extends Arguments
  case object Aggregate extends Arguments


}
