package org.bassethound.reader.impl

import org.bassethound.model.Source
import org.scalatest.FunSuite
import org.scalatest.Matchers._
class RawTextReaderTest extends FunSuite {

  test("test raw reader") {
    val res = RawTextReader.read("a")
    res shouldBe Source("a" , Stream("a"))
  }

}
