package org.bassethound.reader.impl

import org.bassethound.model.Source
import org.bassethound.reader.Reader

object RawTextReader extends Reader[String,String]{
  /**
    * Read from the given input and returns the required information
    *
    * @param input Input type of the reader
    * @return A @Source type with the information to be used by the feeder
    */
  override def read(input: String): Source[String, String] = Source(source = input , content = Stream(input))
}
