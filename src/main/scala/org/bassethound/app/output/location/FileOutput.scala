package org.bassethound.app.output.location

import java.io.File
import java.nio.file.FileAlreadyExistsException

object FileOutput {

  def out(out: String, file: Option[File]) = file.map {
    case f: File if f.exists && !f.isDirectory =>
      throw new FileAlreadyExistsException(s"$f already exists") // If file already exists, avoid override by blowing up
    case f: File if !f.isDirectory => // If it's not a directory, save it
      org.bassethound.util.Files.write(out, f)
    case f: File if f.isDirectory =>
      val newFile = new File(s"${f.getAbsolutePath}/out") // If it's a directory, create the file out and save it
      org.bassethound.util.Files.write(out, newFile)
  }
}
