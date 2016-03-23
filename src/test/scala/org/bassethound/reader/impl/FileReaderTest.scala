package org.bassethound.reader.impl

import org.scalatest.FunSuite
import org.scalatest.Matchers._
class FileReaderTest extends FunSuite {

  private val BasicInput = """/file_reader/basic_input"""

  test("test file reader") {
    val content = FileReader.read(getClass.getResource(BasicInput).getPath)

    content.source shouldBe getClass.getResource(BasicInput).getPath
    content.content.map(v=>v) shouldBe Stream("test 1","test 2")
  }

}
