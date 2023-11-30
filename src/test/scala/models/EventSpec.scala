package models

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class EventSpec extends AnyFlatSpec with Matchers {
  "toString method" should "return a correct string representation of the event" in {
    val event = Event(Team1, 2, Map(Team1 -> 10, Team2 -> 8), Time(5, 30))
    val expected = "[05:30][Team1: 10 | Team2: 8]: Team1 scored 2 points"
    event.toString shouldEqual expected
  }
}
