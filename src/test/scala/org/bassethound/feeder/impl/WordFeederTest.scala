package org.bassethound.feeder.impl

import org.scalatest.FunSuite
import org.scalatest.Matchers._
class WordFeederTest extends FunSuite {

  test("test digest with a source with words") {
    val source = ("source" , Stream("one two", "three"))
    val res = new WordFeeder().digest(source)
    res shouldBe ("source", Stream("one", "two", "three"))
  }

  test("test digest with a source with no words on it") {
    val source = ("source" , Stream("+", "!"))
    val res = new WordFeeder().digest(source)
    res shouldBe ("source", Stream.empty)
  }
}
