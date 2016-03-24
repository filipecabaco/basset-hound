package org.bassethound.reader.impl

import org.scalatest.FunSuite
import org.scalatest.Matchers._

class RawTextReaderTest extends FunSuite {

  test("test raw reader") {
    val res = RawTextReader.read("a")
    res._1 shouldBe "a"
    res._2 shouldBe Stream("a")
  }

}
