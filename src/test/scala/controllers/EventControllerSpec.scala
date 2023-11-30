package controllers

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import models.{Event, Team1, Team2, Time}

class EventControllerSpec extends AnyWordSpec with Matchers {
  val eventController = new EventController

  "EventController.getDecodedEvent()" should {
    "return Some(event)" when {
      "called with a valid hexadecimal value - Team 1 scores 2 points after 15 seconds of play" in {
        eventController.getDecodedEvent(0x781002) shouldBe Some(Event(Team1, 2, Map(Team1 -> 2, Team2 -> 0), Time(0, 15)))
      }
      "called with a valid hexadecimal value - Team 2 replies with 3 points 15 seconds later" in {
        eventController.getDecodedEvent(0xf0101f) shouldBe Some(Event(Team2, 3, Map(Team1 -> 2, Team2 -> 3), Time(0, 30)))
      }
      "called with a valid hexadecimal value - at 10:10, a single point for Team 1 gives them a 5 point lead – 25-20" in {
        eventController.getDecodedEvent(0x1310c8a1) shouldBe Some(Event(Team1, 1, Map(Team1 -> 25, Team2 -> 20), Time(10, 10)))
      }
      "called with a valid hexadecimal value - at 22:23, a 2-point shot for Team 1 leaves them 4 points behind at 48-52" in {
        eventController.getDecodedEvent(0x29f981a2) shouldBe Some(Event(Team1, 2, Map(Team1 -> 48, Team2 -> 52), Time(22, 23)))
      }
      "called with a valid hexadecimal value - at 38:30, a 3-point shot levels the game for Team 2 at 100 points each" in {
        eventController.getDecodedEvent(0x48332327) shouldBe Some(Event(Team2, 3, Map(Team1 -> 100, Team2 -> 100), Time(38, 30)))
      }
      
    }
    "return None" when {
      "called with an invalid bit length" in {
        eventController.getDecodedEvent(0x105) shouldBe None
      }
      "called with invalid points scored" in {
        eventController.getDecodedEvent(0x964FA00) shouldBe None
      }
      "called with an invalid elapsed match time" in {
        eventController.getDecodedEvent(0x00060B) shouldBe None
      }
    }
  }
}
