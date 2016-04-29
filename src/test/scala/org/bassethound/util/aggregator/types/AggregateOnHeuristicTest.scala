package org.bassethound.util.aggregator.types

import org.bassethound.util.MockBooleanHeuristic
import org.scalatest.FunSuite
import org.scalatest.Matchers._
class AggregateOnHeuristicTest extends FunSuite {

  test("test heuristic aggregation result") {
    val mockHeuristic = new MockBooleanHeuristic
    val mock = Seq((true, mockHeuristic, Seq((true,0,true),(true,1,true))))
    AggregateOnHeuristic.aggregateFunc(mock) shouldEqual (2, Map(mockHeuristic.getClass.getSimpleName -> (2, Map("true" -> List((true,0,true),(true,1,true))))))
  }
}