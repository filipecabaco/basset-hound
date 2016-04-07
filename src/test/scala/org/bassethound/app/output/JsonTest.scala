package org.bassethound.app.output

import org.scalatest.FunSuite
import org.scalatest.Matchers._

class JsonTest extends FunSuite {
  private val example = Map( "source" -> Map("heuristic" -> Seq(("candidate", 0 , "score"))))
  test("test render") {
    Json.render(example) shouldBe
      """{"source":{"heuristic":[{"_1":"candidate","_2":0,"_3":"score"}]}}"""
  }

  test("test render pretty") {
    Json.renderPretty(example) shouldBe
      """{
        |  "source":{
        |    "heuristic":[
        |      {
        |        "_1":"candidate",
        |        "_2":0,
        |        "_3":"score"
        |      }
        |    ]
        |  }
        |}""".stripMargin
  }

}
