package org.bassethound.model

/**
  * Saves the information required by all elements of the hound
  * @param source The source of the content
  * @param content A @Stream with the given content
  * @tparam A Type of the source
  * @tparam B Type of the content
  */
case class Source[A,B](source :A, content :Stream[B])
