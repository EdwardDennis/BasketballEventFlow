package controllers

import models.*
import utils.Logging

class EventController() extends Logging {
  def getDecodedEvent(hexValue: Int): Option[Event] = {
    if (!validBitLength(hexValue)) {
      logger.warn("[EventController][decodeNewEvent]: The given hexadecimal value is invalid.")
      None
    } else {
      val pointsScored = getPointsScored(hexValue)
      val totalPoints = getTotalPoints(hexValue)
      val elapsedMatchTime = getElapsedMatchTime(hexValue)
      val scoringTeam = getScoringTeam(hexValue)
      (scoringTeam, pointsScored, totalPoints, elapsedMatchTime) match {
        case (Some(scoringTeam), Some(pointsScored), Some(totalPoints), Some(elapsedMatchTime)) =>
          val event = Event(scoringTeam, pointsScored, totalPoints, elapsedMatchTime)
          Some(event)
        case (_, _, _, _) =>
          None
      }
    }
  }


  private def validBitLength(hex: Int): Boolean = {
    val hexString = hex.toHexString.toUpperCase
    val binary = BigInt(hexString, 16).toString(2)
    if (binary.length >= 19 && binary.length <= 31) {
      true
    } else {
      false
    }
  }

  private def getPointsScored(hexValue: Int): Option[Int] = {
    val points = hexValue & 0x3
    points match {
      case 1 | 2 | 3 => Some(points)
      case _ =>
        logger.warn("[EventController][getPointsScored]: Invalid points scored.")
        None
    }
  }

  private def getTotalPoints(hexValue: Int): Option[Map[Team, Int]] = {
    val MAX_TEAM_POINTS = 0xFF
    val team1PointsTotal = (hexValue >> 11) & MAX_TEAM_POINTS
    val team2PointsTotal = (hexValue >> 3) & MAX_TEAM_POINTS
    if (team1PointsTotal >= 0 && team2PointsTotal >= 0) {
      Some(Map(Team1 -> team1PointsTotal, Team2 -> team2PointsTotal))
    } else {
      logger.warn("[EventController][getTotalPoints]: Invalid total points.")
      None
    }
  }

  private def getElapsedMatchTime(hexValue: Int): Option[Time] = {
    val MAX_SECONDS = 4800
    val elapsedSeconds = hexValue >> 19 & 0xFFF
    if (elapsedSeconds > 0 && elapsedSeconds < MAX_SECONDS) {
      val minutes = elapsedSeconds / 60
      val seconds = elapsedSeconds % 60
      Some(Time(minutes, seconds))
    } else {
      logger.warn("[EventController][getElapsedMatchTime]: Invalid match time.")
      None
    }
  }

  private def getScoringTeam(hexValue: Int): Option[Team] = {
    val teamIndex = (hexValue >> 2) & 0x1
    teamIndex match {
      case 0 => Some(Team1)
      case 1 => Some(Team2)
      case _ =>
        logger.warn("[EventController][getScoringTeam]: Invalid scoring team.")
        None
    }
  }
}
