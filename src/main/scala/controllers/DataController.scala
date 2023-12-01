package controllers

import data.{sampleOne, sampleTwo}
import utils.Logging

class DataController extends Logging {
  def getData(sampleData: String): Option[Seq[Int]] = {
    sampleData match {
      case "sample1" => Some(sampleOne)
      case "sample2" => Some(sampleTwo)
      case _ => None
    }
  }
}
