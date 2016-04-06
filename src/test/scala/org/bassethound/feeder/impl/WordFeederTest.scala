package org.bassethound.feeder.impl

import org.scalatest.FunSuite
import org.scalatest.Matchers._
class WordFeederTest extends FunSuite {

  test("test digest with a source with words") {
    val source = ("source" , Stream(("one two",0), ("three",1)))
    val res = new WordFeeder().digest(source)
    res shouldBe ("source", Stream(("one",0), ("two",0), ("three",1)))
  }

  test("test digest with a source with no words on it") {
    val source = ("source" , Stream(("+",0), ("!",1)))
    val res = new WordFeeder().digest(source)
    res shouldBe ("source", Stream.empty)
  }
}
