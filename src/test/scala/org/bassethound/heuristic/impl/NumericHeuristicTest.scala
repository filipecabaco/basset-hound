package org.bassethound.heuristic.impl

import org.scalatest.FunSuite
import org.scalatest.Matchers._

class NumericHeuristicTest extends FunSuite {
  private final val heuristic = new NumericHeuristic()

  test("test only numbers in String") {
    heuristic.analyseFunc("00000000") shouldBe -1
  }

  test("test 50% numbers in String") {
    heuristic.analyseFunc("AAAA0000") shouldBe 0.5
  }

  test("test 0% numbers in String") {
    heuristic.analyseFunc("AAAAAAAA") shouldBe 0
  }

  test("test that strings with low character count are excluded"){
    heuristic.analyseFunc("00AA") shouldBe -1
  }
  
  test("test that strings with high character count are normally analysed"){
    heuristic.analyseFunc("0000AAAA") shouldBe 0.5
  }

  test("test filter function with candidate") {
    heuristic.filterFunc(0.5) shouldBe true
  }

  test("test filter function with non candidate") {
    heuristic.filterFunc(0) shouldBe false
  }



}
