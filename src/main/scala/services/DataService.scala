package services

import scala.io.Source
import scala.util.Try

class DataService {
  def getSampleOneData: Either[Throwable, Seq[Int]] = {
    val sampleOneFileName = "sample1.txt"
    readHexadecimalValuesFromFile(sampleOneFileName)
  }

  def getSampleTwoData: Either[Throwable, Seq[Int]] = {
    val sampleTwoFileName = "sample2.txt"
    readHexadecimalValuesFromFile(sampleTwoFileName)
  }

  private def readHexadecimalValuesFromFile(filename: String): Either[Throwable, Seq[Int]] = {
    val bufferedSource = Source.fromResource(filename)

    val hexValues = Try(
      bufferedSource.getLines().map(hex => Integer.parseInt(hex.stripPrefix("0x"), 16)).toList
    ).toEither

    bufferedSource.close()

    hexValues
  }
}
