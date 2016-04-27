package org.bassethound.util

import org.bassethound.reader.Reader

/**
  * Implementation of a Reader to be used for simple testing
  */
class MockReader extends Reader[Boolean, Boolean]{

  override def read(input: Boolean): (Boolean, Stream[(Boolean, Int)]) = (input, Stream((input,0)))

}
