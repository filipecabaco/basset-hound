package org.bassethound.feeder.impl

import org.scalatest.FunSuite
import org.scalatest.Matchers._

class LineFeederTest extends FunSuite {

  test("test line digest if source content considered lines on stream creation") {
    val source = ("" , Stream("one","two","three"))
    val res =  LineFeeder.digest(source)
    res shouldBe Stream("one", "two", "three")
  }

  test("test line digest if source content hasn't considered lines on stream creation") {
    val source = ("" , Stream("one\ntwo", "three"))
    val res =  LineFeeder.digest(source)
    res shouldBe Stream("one", "two", "three")
  }

}
