package org.bassethound.reader.impl

import org.bassethound.reader.Reader

/**
  * Reads a file and returns a stream with the content of the string
  * and the line number (starting from 0) by splitting by break line
  */
class RawTextReader extends Reader[String,String]{
  override def read(input: String) = {
    val byLine = "\\r\\n+|\\n".r.split(input)
    (input, byLine.zipWithIndex.toStream)
  }
}
