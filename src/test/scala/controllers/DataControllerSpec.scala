package controllers

import data.{sampleOne, sampleTwo}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class DataControllerSpec extends AnyWordSpec with Matchers {
  val dataController = new DataController

  "DataController.getData()" should {
    "return the correct sampleOne data" when {
      "called with sampleOne" in {
        val data = dataController.getData("sample1")
        data shouldBe Some(sampleOne)
      }
    }
    "return the correct sampleTwo data" when {
      "called with sampleTwo" in {
        val data = dataController.getData("sample2")
        data shouldBe Some(sampleTwo)
      }
    }
    "return None for incorrect data selections" in {
      val data = dataController.getData("invalidSample")
      data should be(None)
    }
  }
}

