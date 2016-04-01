package org.bassethound.sniffer.impl


import org.bassethound.feeder.Feeder
import org.bassethound.feeder.impl.LineFeeder
import org.bassethound.heuristic.Heuristic
import org.bassethound.heuristic.impl.KeywordHeuristic
import org.bassethound.reader.Reader
import org.bassethound.reader.impl.RawTextReader
import org.bassethound.sniffer.Sniffer

import scala.concurrent.ExecutionContext

/**
  * Handles the following flow
  *
  *   Reader - Read a String
  *   Feeder - Output a Stream of lines
  *   Heuristic - Check if keywords were found in the content of the line
  *
  * @param executionContext Execution context used by the future
  */
class KeywordStringSniffer(implicit val executionContext: ExecutionContext) extends Sniffer[String,String,String,Boolean]{

  override val heuristic: Heuristic[String, Boolean] = new KeywordHeuristic
  override val feeder: Feeder[String, String] = new LineFeeder
  override val reader: Reader[String, String] = new RawTextReader
}
