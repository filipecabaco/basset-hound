package org.bassethound.feeder.impl

import org.scalatest.FunSuite
import org.scalatest.Matchers._

class LineFeederTest extends FunSuite {

  test("test line digest if source content considered lines on stream creation") {
    val source = ("source" , Stream(("one",0),("two",1),("three",2)))
    val res =  new LineFeeder().digest(source)
    res shouldBe ("source", Stream(("one",0),("two",1),("three",2)))
  }

}
