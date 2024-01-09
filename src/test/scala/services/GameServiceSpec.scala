package services

import models.{Event, Team1, Team2, Time}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GameServiceSpec extends AnyWordSpec with Matchers with BeforeAndAfterEach {

  val testEvent: Event = Event(Team1, 1, Map(Team1 -> 1, Team2 -> 0), Time(1, 1))
  val testEvent2: Event = Event(Team1, 2, Map(Team1 -> 2, Team2 -> 0), Time(1, 45))

  "GameService.addEvent()" should {
    "add an event and increment the id" in {
      val gameService = new GameServiceImpl()
      gameService.addEvent(testEvent)
      gameService.getAllEvents should have size 1
      gameService.getAllEvents.head shouldBe testEvent
    }
  }
  "GameService.getAllEvents()" should {
    "return all events previously added" in {
      val gameService = new GameServiceImpl()
      gameService.addEvent(testEvent)
      gameService.addEvent(testEvent2)
      gameService.getAllEvents shouldBe List(testEvent, testEvent2)
    }
  }

  "GameService.getLastEvent" should {
    "return the last event when it exists" in {
      val gameService = new GameServiceImpl()
      gameService.addEvent(testEvent)
      gameService.getLastEvent shouldBe Some(testEvent)
    }
    "return None when no events are saved" in {
      val gameService = new GameServiceImpl()
      gameService.getLastEvent shouldBe None
    }
  }

  "GameService.getLastNEvents()" should {
    "return the last n events" when {
      "called with a number less than events length" in {
        val gameService = new GameServiceImpl()
        gameService.addEvent(testEvent)
        gameService.addEvent(testEvent2)
        gameService.getLastNEvents(2) shouldBe List(testEvent, testEvent2)
      }
    }
    "return all events" when {
      "called with a higher number than events length" in {
        val gameService = new GameServiceImpl()
        gameService.addEvent(testEvent)
        gameService.addEvent(testEvent2)
        gameService.getLastNEvents(3) shouldBe List(testEvent, testEvent2)
      }
    }
    "return an empty list" when {
      "called with 0" in {
        val gameService = new GameServiceImpl()
        gameService.addEvent(testEvent)
        gameService.getLastNEvents(0) shouldBe List.empty
      }
    }
    "return None" when {
      "called with a negative number" in {
        val gameService = new GameServiceImpl()
        gameService.addEvent(testEvent)
        gameService.getLastNEvents(-1) shouldBe List.empty
      }
    }
  }
}
