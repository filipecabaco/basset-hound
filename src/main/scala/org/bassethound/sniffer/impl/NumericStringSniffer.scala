package org.bassethound.sniffer.impl


import org.bassethound.feeder.Feeder
import org.bassethound.feeder.impl.WordFeeder
import org.bassethound.heuristic.Heuristic
import org.bassethound.heuristic.impl.NumericHeuristic
import org.bassethound.reader.Reader
import org.bassethound.reader.impl.RawTextReader
import org.bassethound.sniffer.Sniffer

import scala.concurrent.ExecutionContext
/**
  * Handles the following flow
  *
  *   Reader - Read Raw String
  *   Feeder - Output a Stream of words
  *   Heuristic - Calculate the percentage of numbers in each word
  *
  * @param executionContext Execution context used by the future
  */
class NumericStringSniffer(implicit val executionContext: ExecutionContext) extends Sniffer[String,String,String,Double]{

  override val heuristic: Heuristic[String, Double] = new NumericHeuristic
  override val feeder: Feeder[String, String] = new WordFeeder
  override val reader: Reader[String, String] = new RawTextReader
}
