package models

final case class Event(team: Team,
                       pointsScored: Int,
                       totalPoints: Map[Team, Int],
                       elapsedMatchTime: Time) {

  override def toString: String = {
    val pointsString = totalPoints.map { case (team, points) => s"$team: $points" }.mkString(" | ")
    s"[$elapsedMatchTime][$pointsString]: $team scored $pointsScored points"
  }
}





