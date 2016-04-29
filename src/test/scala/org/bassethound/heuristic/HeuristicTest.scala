package org.bassethound.heuristic

import org.bassethound.util.MockBooleanHeuristic
import org.scalatest.FunSuite
import org.scalatest.Matchers._
class HeuristicTest extends FunSuite {

  test("test that apply will filter out the proper candidates from a stream") {
    val content = (true,Stream((false,0),(false,1),(true,2),(false,3),(true,4)))
    val fakeHeuristic = new MockBooleanHeuristic

    fakeHeuristic.apply(content) shouldBe (true,fakeHeuristic, Seq((true,2,true),(true,4,true)))
  }
}
