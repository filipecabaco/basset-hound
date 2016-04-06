package org.bassethound.reader.impl

import org.scalatest.FunSuite
import org.scalatest.Matchers._

class RawTextReaderTest extends FunSuite {

  test("test raw reader with one lines") {
    val res = new RawTextReader().read("a")
    res._1 shouldBe "a"
    res._2 shouldBe Stream(("a",0))
  }
  test("test raw reader with two lines") {
    val res = new RawTextReader().read("a\nb")
    res._1 shouldBe "a\nb"
    res._2 shouldBe Stream(("a",0),("b",1))
  }
}
