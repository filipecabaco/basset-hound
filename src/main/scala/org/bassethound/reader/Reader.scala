package org.bassethound.reader

/**
  * Represents the readers that will feed the analysis
 *
  * @tparam A The input type
  * @tparam B The output type
  */
trait Reader[A,B] {
  /**
    * Read from the given input and returns the required information
    *
    * @param input Input type of the reader
    * @return A @Source type with the information to be used by the feeder
    */
  def read(input:A):(A,Stream[B])
}
