package services

import mocks.MockGameService
import models.{Event, Team1, Team2, Time}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.BeforeAndAfterEach

import scala.collection.concurrent.TrieMap

class GameServiceSpec extends AnyWordSpec with Matchers with BeforeAndAfterEach {
  val gameService = new MockGameService()

  override def beforeEach(): Unit = {
    gameService.reset()
  }

  val testEvent: Event = Event(Team1, 1, Map(Team1 -> 1, Team2 -> 0), Time(1, 1))
  val testEvent2: Event = Event(Team1, 2, Map(Team1 -> 2, Team2 -> 0), Time(1, 45))

  "GameService.addEvent()" should {
    "add an event and increment the id" in {
      gameService.addEvent(testEvent)
      gameService.getAllEvents should have size 1
      gameService.getAllEvents.head._1 shouldBe 0
    }
  }
  "GameService.getAllEvents()" should {
    "return all events previously added" in {
      gameService.addEvent(testEvent)
      gameService.addEvent(testEvent2)
      gameService.getAllEvents shouldBe gameService.eventStore.toSeq
      gameService.getAllEvents shouldBe List((0, testEvent), (1, testEvent2))
    }
  }

  "GameService.getLastEvent" should {
    "return the last event when it exists" in {
      gameService.addEvent(testEvent)
      gameService.getLastEvent shouldBe Some(testEvent)
    }
    "return None when no events are saved" in {
      gameService.getLastEvent shouldBe None
    }
  }

  "GameService.getLastNEvents()" should {
    "return the last n events" when {
      "called with a number less than events length" in {
        gameService.addEvent(testEvent)
        gameService.addEvent(testEvent2)
        gameService.getLastNEvents(2) shouldBe Some(List((0, testEvent), (1, testEvent2)))
      }
    }
    "return all events" when {
      "called with a higher number than events length" in {
        gameService.addEvent(testEvent)
        gameService.addEvent(testEvent2)
        gameService.getLastNEvents(3) shouldBe Some(List((0, testEvent), (1, testEvent2)))
      }
    }
    "return None" when {
      "called with a negative number" in {
        gameService.addEvent(testEvent)
        gameService.getLastNEvents(-1) shouldBe None
      }
    }
  }

  "GameServiceImpl" should {
    "start with an index of 0" in {
      GameServiceImpl().currentId.intValue() shouldBe 0
    }
    "start with an empty TrieMap" in {
      GameServiceImpl().eventStore shouldBe TrieMap.empty[Int, Event]
    }
  }
}
