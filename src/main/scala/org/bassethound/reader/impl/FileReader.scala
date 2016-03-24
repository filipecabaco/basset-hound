package org.bassethound.reader.impl

import java.io.File

import org.bassethound.model.Source
import org.bassethound.reader.Reader

object FileReader extends Reader[File,String]{
  /**
    * Read from the given input and returns the required information
    *
    * @param input Input type of the reader
    * @return A @Source type with the information to be used by the feeder
    */
  override def read(input: File): Source[File, String] = Source(
    source = input,
    content = scala.io.Source.fromFile(input).getLines().toStream)
}