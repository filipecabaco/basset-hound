package org.bassethound.heuristic

import org.scalatest.FunSuite
import org.scalatest.Matchers._

class HeuristicTest extends FunSuite {

  test("test that filter will filter out the proper candidates from a stream") {
    val fakeHeuristic = new Heuristic[String]{

      override def analyse(content: Stream[String]): List[String] = ???

      override def filterFunc(elem: String): Boolean = !elem.isEmpty
    }

    fakeHeuristic.filter(Stream("","","a","","b")) shouldBe List("a","b")
  }

}
