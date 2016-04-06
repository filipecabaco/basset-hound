package org.bassethound.util

import java.io.File

import scala.annotation.tailrec

/**
  * Utilities related with Files
  */
object Files {

  /**
    * Returns all the files within a given list of File
    * @param files Seq of File to explore
    * @param acc Accumulator with the final result
    * @return Accumulator with all files in directories and subdirectories
    */
  @tailrec
  def getAll(files: Seq[File], acc: Seq[File]): Seq[File] = {
    if (files.isEmpty) {
      acc
    } else {
      val all = files.partition(_.isDirectory)
      val next = all._1.flatMap(_.listFiles()).filterNot(_.getName.startsWith(".")) //Exclude private folders
      getAll(next, acc ++ all._2)
    }

  }
}
