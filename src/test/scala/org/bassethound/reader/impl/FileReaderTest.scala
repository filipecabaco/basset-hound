package org.bassethound.reader.impl

import java.io.File

import org.scalatest.FunSuite
import org.scalatest.Matchers._

class FileReaderTest extends FunSuite {

  private val BasicInput = """/file_reader/basic_input"""

  test("test file reader") {
    val file = new File(getClass.getResource(BasicInput).getPath)
    val content = FileReader.read(file)

    content._1 shouldBe file
    content._2.map(v=>v) shouldBe Stream("test 1","test 2")
  }

}
