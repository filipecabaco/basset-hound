package org.bassethound.sniffer

import org.bassethound.feeder.Feeder
import org.bassethound.heuristic.Heuristic
import org.bassethound.reader.Reader
import org.bassethound.util.{MockFeeder, MockHeuristic, MockReader}
import org.scalatest.FunSuite
import org.scalatest.Matchers._

class SnifferTest extends FunSuite{

  test("sniffer works as expected with given reader, feeder, heuristic"){
    val fakeReader = new MockReader
    val fakeFeeder = new MockFeeder
    val fakeHeuristic = new MockHeuristic
    val fakeSniffer = new Sniffer[Boolean,Boolean,Boolean,Boolean] {
      override val reader: Reader[Boolean, Boolean] = fakeReader
      override val heuristic: Heuristic[Boolean, Boolean] = fakeHeuristic
      override val feeder: Feeder[Boolean, Boolean] = fakeFeeder
    }

    fakeSniffer.sniff(true) shouldBe Some((true, fakeHeuristic, List((true,0,true))))
  }

  test("handle reader failure"){
    val fakeReader = new Reader[Boolean, Boolean] {
      override def read(input: Boolean): (Boolean, Stream[(Boolean, Int)]) = ???
    }
    val fakeFeeder = new MockFeeder
    val fakeHeuristic = new MockHeuristic
    val fakeSniffer = new Sniffer[Boolean,Boolean,Boolean,Boolean] {
      override val reader: Reader[Boolean, Boolean] = fakeReader
      override val heuristic: Heuristic[Boolean, Boolean] = fakeHeuristic
      override val feeder: Feeder[Boolean, Boolean] = fakeFeeder
    }

    fakeSniffer.sniff(true) shouldBe None
  }
  test("handle feeder failure"){
    val fakeReader = new MockReader
    val fakeFeeder = new Feeder[Boolean,Boolean] {
      override def digest(source: (_, Stream[(Boolean, Int)])): (_, Stream[(Boolean, Int)]) = ???
    }
    val fakeHeuristic = new MockHeuristic
    val fakeSniffer = new Sniffer[Boolean,Boolean,Boolean,Boolean] {
      override val reader: Reader[Boolean, Boolean] = fakeReader
      override val heuristic: Heuristic[Boolean, Boolean] = fakeHeuristic
      override val feeder: Feeder[Boolean, Boolean] = fakeFeeder
    }

    fakeSniffer.sniff(true) shouldBe None
  }
  test("handle heuristic failure"){
    val fakeReader = new MockReader
    val fakeFeeder = new MockFeeder
    val fakeHeuristic = new Heuristic[Boolean, Boolean] {
      override def analyseFunc(candidate: Boolean): Boolean = ???
      override def filterFunc(score: Boolean): Boolean = ???
    }
    val fakeSniffer = new Sniffer[Boolean,Boolean,Boolean,Boolean] {
      override val reader: Reader[Boolean, Boolean] = fakeReader
      override val heuristic: Heuristic[Boolean, Boolean] = fakeHeuristic
      override val feeder: Feeder[Boolean, Boolean] = fakeFeeder
    }

    fakeSniffer.sniff(true) shouldBe None
  }
}
