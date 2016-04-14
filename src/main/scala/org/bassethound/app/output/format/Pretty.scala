package org.bassethound.app.output.format

/**
  * Handles Pretty print option
  */
object Pretty {
  /**
    * Pretty print when result is aggregated on Source
    * @param m Aggregated on Source result
    * @return Pretty String
    */
  def onSource(m : (Int, Map[String,(Int,Map[String,Seq[(_,Int,_)]])])): String ={
    val total = m._1
    val main = m._2.map{ prepareContent("Source", "Heuristic", _)} mkString "\n"
    s"Total occurrences: $total \n$main"
  }

  /**
    * Pretty print when result is aggregated on Heuristic
    * @param m Aggregated on Heuristic result
    * @return Pretty String
    */
  def onHeuristic(m : (Int, Map[String,(Int,Map[String,Seq[(_,Int,_)]])])): String = {
    val total = m._1
    val main = m._2.map{ prepareContent("Heuristic", "Score",_)} mkString "\n"
    s"Total occurrences: $total \n$main"
  }

  /**
    * Pretty print when result is aggregated on Source with the sum of scores
    * @param m Aggregated on Score result
    * @return Pretty String
    */
  def onScore(m : (Int, Map[String,(Int,Map[String,Seq[(_,Int,_)]])])): String = {
    val total = m._1
    val main = m._2.map{ prepareContent("Source", "Line", _, showLine= false)} mkString "\n"
    s"Total occurrences: $total \n$main"
  }

  /**
    * Creates the main content of the  Pretty Print String
    * @param firstLevel String to be shown on the first level of the output
    * @param secondLevel String to be shown on the second level of the output
    * @param values The tuple with the information required to build the output
    * @return List of strings constructed based on information received
    */
  private def prepareContent(firstLevel:String,
                             secondLevel:String,
                             values:(String,(Int,Map[String,Seq[(_,Int,_)]])),
                             showLine: Boolean= true) = {
    val firstLevelContent = values._2._2.map{ w =>
      val secondLevelContent = w._2.map(candidate(_,showLine)) mkString "\n" // Creates the output for each Candidate
      s"\t$secondLevel: ${w._1} \n$secondLevelContent" // Creates the Second Level
    } mkString "\n"

    s"$firstLevel: ${values._1} \nOccurrences: ${values._2._1} \n $firstLevelContent" // Creates the First associated
  }

  /**
    * Creates the String that will represent the candidate
    * @param t Tuple with all the information required
    * @param showLine Should we show the line?
    * @return String ready to be used
    */
  private def candidate(t : (_,Int,_), showLine: Boolean= true) ={
    val line = s"\n\t\tLine: ${t._2 + 1}"
    s"\t\tCandidate: ${t._1}" +
      s"${if(showLine) line else ""}" +
      s"\n\t\tScore: ${t._3}"
  }
}
