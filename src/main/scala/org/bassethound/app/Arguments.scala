package org.bassethound.app

sealed trait Arguments
object Arguments {
  case object Files extends Arguments
  case object Output extends Arguments
  case object Target extends Arguments
}
