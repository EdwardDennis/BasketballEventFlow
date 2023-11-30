package controllers

import services.DataService
import utils.Logging

class DataController(dataService: DataService) extends Logging {
  def getData(filename: String): Option[Seq[Int]] = {
    val data = filename match {
      case "sample1" => dataService.getSampleOneData
      case "sample2" => dataService.getSampleTwoData
      case _ => throw new IllegalArgumentException("Invalid filename")
    }

    data match {
      case Right(values) => Some(values)
      case Left(ex) =>
        logger.error(s"Error reading sample data: ${ex.getMessage}")
        None
    }
  }
}
