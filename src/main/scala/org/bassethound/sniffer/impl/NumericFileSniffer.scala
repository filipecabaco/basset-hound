package org.bassethound.sniffer.impl

import java.io.File

import org.bassethound.feeder.Feeder
import org.bassethound.feeder.impl.WordFeeder
import org.bassethound.heuristic.Heuristic
import org.bassethound.heuristic.impl.NumericHeuristic
import org.bassethound.reader.Reader
import org.bassethound.reader.impl.FileReader
import org.bassethound.sniffer.Sniffer

import scala.concurrent.ExecutionContext

class NumericFileSniffer(implicit val executionContext: ExecutionContext) extends Sniffer[File,String,String,Double]{

  override val reader: Reader[File, String] = new FileReader
  override val heuristic: Heuristic[String, Double] = new NumericHeuristic()
  override val feeder: Feeder[String, String] = new WordFeeder
}
