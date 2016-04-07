package org.bassethound.app

import java.io.File

import org.bassethound.sniffer.impl.{KeywordFileSniffer, NumericFileSniffer}

import scala.concurrent.{ExecutionContext, Future}

class Analysis(implicit val executionContext : ExecutionContext){
  def run(files: Seq[File]) = {
    val numericFileSniffer = new NumericFileSniffer()
    val keywordFileSniffer = new KeywordFileSniffer()

    files.map{ f=>
      Future sequence Seq(
        Future(numericFileSniffer.sniff(f)),
        Future(keywordFileSniffer.sniff(f)))
    }
  }
}
