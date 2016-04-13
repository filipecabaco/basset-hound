package org.bassethound.app.output

/**
  * Handles Pretty print option
  */
object Pretty {
  /**
    * Pretty print when result is aggregated on Source
    * @param m Aggregated on Source result
    * @return Pretty String
    */
  def onSource(m : Map[String,Map[String,Seq[(_,Int,_)]]]): String = m.map{ v =>
    val content = v._2.map{ w =>
      val content = w._2.map(candidate) mkString "\n" // Creates the output for each Candidate
      s"\tHeuristic: ${w._1} \n$content" // Shows the Heuristic associated
    } mkString "\n"
    s"Source: ${v._1} \n $content" // Shows the Source associated
  } mkString "\n"

  /**
    * Pretty print when result is aggregated on Heuristic
    * @param m Aggregated on Heuristic result
    * @return Pretty String
    */
  def onHeuristic(m : Map[String,Map[_,Seq[(_,Int,_)]]]): String = m.map{ v =>
    val content = v._2.map{ w =>
      val content = w._2.map(candidate) mkString "\n" // Creates the output for each Candidate
      s"\tSource: ${w._1} \n$content" // Shows the Source associated
    } mkString "\n"
    s"Heuristic: ${v._1} \n $content" // Shows the Heuristic associated
  } mkString "\n"

  /**
    * Pretty print when result is aggregated on Source with the sum of scores
    * @param m Aggregated on Score result
    * @return Pretty String
    */
  def onScore(m : Map[String,Map[_,Seq[(_,Int,_)]]]): String = m.map{ v =>
    val content = v._2.map{ w =>
      val content = w._2.map(candidateNoLine) mkString "\n" // Creates the output for each Candidate
      s"\tLine: ${w._1} \n$content" // Shows the Heuristic associated
    } mkString "\n"
    s"Source: ${v._1} \n $content" // Shows the Source associated
  } mkString "\n"


  private def candidate(t : (_,Int,_)) ={
    s"\t\tCandidate: ${t._1}" +
      s"\n\t\tLine: ${t._2 + 1}" +
      s"\n\t\tScore: ${t._3}"
  }

  private def candidateNoLine(t : (_,Int,_)) ={
    s"\t\tCandidate: ${t._1}" +
      s"\n\t\tScore: ${t._3}"
  }
}
