package org.bassethound.util.aggregator.types

import org.bassethound.util.{MockBooleanHeuristic, MockStringHeuristic}
import org.scalatest.FunSuite
import org.scalatest.Matchers._

class AggregateOnScoreTest extends FunSuite {

  test("test score aggregation result") {
    val mockBooleanHeuristic = new MockBooleanHeuristic
    val mockStringHeuristic = new MockStringHeuristic

    val mock = Seq(
      (true, mockBooleanHeuristic, Seq((true,0,true),(true,1,true))),
      (true, mockStringHeuristic, Seq(("testing",0,0.7),("testing2",1,0.7))))

    AggregateOnScore.aggregateFunc(mock) shouldBe (4,Map("true" -> (4,Map("1" -> List(("testing2",1,1.0)), "0" -> List(("testing",0,1.0))))))
  }

}
