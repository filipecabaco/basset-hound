package org.bassethound.feeder.impl

import org.bassethound.model.Source
import org.scalatest.FunSuite
import org.scalatest.Matchers._
class WordFeederTest extends FunSuite {

  test("test digest with a source with words") {
    val source = new Source(source = "" , content = Stream("one two", "three"))
    val res = WordFeeder.digest(source)
    res shouldBe Stream("one", "two", "three")
  }

  test("test digest with a source with no words on it") {
    val source = new Source(source = "" , content = Stream("+", "!"))
    val res = WordFeeder.digest(source)
    res shouldBe Stream.empty
  }
}
