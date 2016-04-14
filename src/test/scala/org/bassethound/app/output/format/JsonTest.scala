package org.bassethound.app.output.format

import org.scalatest.FunSuite
import org.scalatest.Matchers._

class JsonTest extends FunSuite {
  private val example = (1,Map( "source" -> (1,Map("heuristic" -> Seq(("candidate", 0 , "score"))))))
  test("test render") {
    println(Json.render(example))
    Json.render(example) shouldBe
      """{"_1":1,"_2":{"source":{"_1":1,"_2":{"heuristic":[{"_1":"candidate","_2":0,"_3":"score"}]}}}}"""
  }

  test("test render pretty") {
    println(Json.renderPretty(example))
    Json.renderPretty(example) shouldBe
      """{
        |  "_1":1,
        |  "_2":{
        |    "source":{
        |      "_1":1,
        |      "_2":{
        |        "heuristic":[
        |          {
        |            "_1":"candidate",
        |            "_2":0,
        |            "_3":"score"
        |          }
        |        ]
        |      }
        |    }
        |  }
        |}""".stripMargin
  }

}
