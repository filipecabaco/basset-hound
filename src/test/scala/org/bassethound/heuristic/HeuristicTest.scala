package org.bassethound.heuristic

import org.scalatest.FunSuite
import org.scalatest.Matchers._
class HeuristicTest extends FunSuite {

  test("test that apply will filter out the proper candidates from a stream") {
    val content = ("source",Stream(("",0),("",1),("a",2),("",3),("b",4)))
    val fakeHeuristic = new Heuristic[String,Boolean]{
      override def analyseFunc(candidate: String): Boolean = !candidate.isEmpty

      override def filterFunc(score: Boolean): Boolean = score
    }

    fakeHeuristic.apply(content) shouldBe ("source",fakeHeuristic, Seq(("a",2,true),("b",4,true)))
  }
}
