package org.bassethound.util.aggregator.types

import org.bassethound.util.MockBooleanHeuristic
import org.scalatest.FunSuite
import org.scalatest.Matchers._

class AggregateOnSourceTest extends FunSuite {

  test("test source aggregation result") {
    val mockHeuristic = new MockBooleanHeuristic
    val mock = Seq((true, mockHeuristic, Seq((true,0,true),(true,1,true))))
    AggregateOnSource.aggregateFunc(mock) shouldEqual (2, Map("true" -> (2, Map(mockHeuristic.getClass.getSimpleName -> List((true,0,true),(true,1,true))))))
  }

}
