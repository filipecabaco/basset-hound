package org.bassethound.app

object Application extends App{
  println(
    """Welcome to Basset Hound
      |Given the reader type and the list of inputs I will try to sniff out secrets in your code based on my heuristics
      |This will return a list of candidates according to each heuristic and output you the files with candidates.
      |
      |Please bare in mind that I'm still young and foolish on my analysis
    """.stripMargin)
}
