package org.bassethound.util

import org.bassethound.feeder.Feeder

/**
  * Implementation of a Feeder to be used for simple testing
  */
class MockFeeder extends Feeder[Boolean, Boolean]{

  override def digest(source: (_, Stream[(Boolean, Int)])): (_, Stream[(Boolean, Int)]) = (source._1, source._2)

}
