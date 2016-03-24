package org.bassethound.heuristic.impl

import org.scalatest.FunSuite
import org.scalatest.Matchers._

class NumericHeuristicTest extends FunSuite {

  test("test only numbers in String") {
    val res = new NumericHeuristic().analyseFunc("0000")
    res shouldBe 1
  }

  test("test 50% numbers in String") {
    val res = new NumericHeuristic().analyseFunc("AA00")
    res shouldBe 0.5
  }

  test("test 0% numbers in String") {
    val res = new NumericHeuristic().analyseFunc("AAAA")
    res shouldBe 0
  }

  test("test filter function with candidate") {
    val res = new NumericHeuristic().filterFunc(("0",1))
    res shouldBe true
  }

  test("test filter function with non candidate") {
    val res = new NumericHeuristic().filterFunc(("A",0))
    res shouldBe false
  }

}
