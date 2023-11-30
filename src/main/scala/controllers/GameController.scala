package controllers

import models.*
import services.{DataService, GameService}
import utils.Logging
import services.GameServiceImpl

class GameController(dataController: DataController, eventController: EventController, gameService: GameService) extends Logging {
  def decodeEvent(hexValue: Int): Unit = {
    eventController.getDecodedEvent(hexValue) match {
      case Some(event) => handleDecodedEvent(event)
      case None => logger.warn(s"[GameController][decodeEvent]: Event failed decoding.")
    }
  }

  private def handleDecodedEvent(event: Event): Unit = {
    if (validEvent(event)) {
      addValidEventToGame(event)
    } else {
      logger.warn(s"[GameController][handleDecodedEvent]: Event failed validation - $event.")
    }
  }

  private def validEvent(newEvent: Event): Boolean = {
    gameService.getLastEvent.forall(lastEvent => validateOrderOfEvents(lastEvent, newEvent) && validateTeamScores(lastEvent, newEvent))
  }

  private def validateOrderOfEvents(lastEvent: Event, newEvent: Event): Boolean = {
    val lastEventTime = convertToSeconds(lastEvent.elapsedMatchTime.minutes, lastEvent.elapsedMatchTime.seconds)
    val newEventTime = convertToSeconds(newEvent.elapsedMatchTime.minutes, newEvent.elapsedMatchTime.seconds)

    newEventTime > lastEventTime
  }

  private def convertToSeconds(minutes: Int, seconds: Int): Int = minutes * 60 + seconds

  private def validateTeamScores(lastEvent: Event, newEvent: Event): Boolean = {
    val team1PointsDifference = calculatePointsDifference(Team1, lastEvent.totalPoints, newEvent.totalPoints)
    val team2PointsDifference = calculatePointsDifference(Team2, lastEvent.totalPoints, newEvent.totalPoints)

    isValidPointsDifference(team1PointsDifference) && isValidPointsDifference(team2PointsDifference)
  }

  private def calculatePointsDifference(team: Team, lastPoints: Map[Team, Int], newPoints: Map[Team, Int]): Int =
    newPoints(team) - lastPoints(team)

  private def isValidPointsDifference(pointsDifference: Int): Boolean = pointsDifference >= 0 && pointsDifference <= 3

  private def addValidEventToGame(event: Event): Unit = {
    logger.info(s"[GameController][addValidEventToGame]: New event added to game - $event.")
    gameService.addEvent(event)
  }
}

object GameController extends Logging {
  @main def main(args: String*): Unit = {
    val dataService = new DataService
    val dataController = new DataController(dataService)
    val eventController = new EventController
    val gameService = new GameServiceImpl()
    val gameController = new GameController(dataController, eventController, gameService)

    args.headOption match {
      case Some("sample1") => dataController.getData("sample1").foreach(_.foreach(hexValue => gameController.decodeEvent(hexValue)))
      case Some("sample2") => dataController.getData("sample2").foreach(_.foreach(hexValue => gameController.decodeEvent(hexValue)))
      case _ => logger.error("Invalid argument. Please provide either 'sample1' or 'sample2'.")
    }
  }
}

