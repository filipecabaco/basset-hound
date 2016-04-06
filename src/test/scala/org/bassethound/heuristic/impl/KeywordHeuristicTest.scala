package org.bassethound.heuristic.impl

import org.scalatest.FunSuite
import org.scalatest.Matchers._

class KeywordHeuristicTest extends FunSuite {
  private final val heuristic = new KeywordHeuristic()
  test("test when keyword was found and contains relevant candidate") {
    heuristic.analyseFunc("secret:pass") shouldBe true
    heuristic.analyseFunc("secret = pass") shouldBe true
    heuristic.analyseFunc("token =pass") shouldBe true
    heuristic.analyseFunc("token: pass") shouldBe true
    heuristic.analyseFunc("token: pass //and then some comments") shouldBe true
  }

  test("test when keyword exists and does not contains relevant candidate") {
    heuristic.analyseFunc("token - not a secret") shouldBe false
    heuristic.analyseFunc("token :") shouldBe false
    heuristic.analyseFunc("token") shouldBe false
  }

  test("test that keyword was not found") {
    heuristic.analyseFunc("this aren't the droids you're looking for") shouldBe false
  }

  test("test filter function for valid candidate") {
    heuristic.filterFunc(true) shouldBe true
  }

  test("test filter function for non-candidate") {
    heuristic.filterFunc(false) shouldBe false
  }

}
