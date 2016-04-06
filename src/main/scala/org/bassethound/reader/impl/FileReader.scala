package org.bassethound.reader.impl

import java.io.File

import org.bassethound.reader.Reader

/**
  * Reads a file and returns a stream with the content of a line and the line number (starting from 0)
  */
class FileReader extends Reader[File,String]{

  override def read(input: File) = (input, scala.io.Source.fromFile(input,"utf-8").getLines.zipWithIndex.toStream)
}