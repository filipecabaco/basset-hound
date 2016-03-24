package org.bassethound.feeder.impl

import org.scalatest.FunSuite
import org.scalatest.Matchers._
class WordFeederTest extends FunSuite {

  test("test digest with a source with words") {
    val source = ("" , Stream("one two", "three"))
    val res = WordFeeder.digest(source)
    res shouldBe Stream("one", "two", "three")
  }

  test("test digest with a source with no words on it") {
    val source = ("" , Stream("+", "!"))
    val res = WordFeeder.digest(source)
    res shouldBe Stream.empty
  }
}
