package org.bassethound.heuristic

import org.scalatest.FunSuite
import org.scalatest.Matchers._

class HeuristicTest extends FunSuite {

  test("test that filter will filter out the proper candidates from a stream") {
    val content = Stream("","","a","","b")
    val fakeHeuristic = new Heuristic[String,Boolean]{
      override def analyseFunc(elem: String): Boolean = !elem.isEmpty

      override def filterFunc(result: (String, Boolean)): Boolean = result._2
    }

    fakeHeuristic.apply(content) shouldBe List(("a",true),("b",true))
  }

}
