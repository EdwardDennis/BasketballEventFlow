package services

import models.Event

protected trait GameService {
  protected def addEvent(event: Event): Unit

  protected def getAllEvents: List[Event]

  protected def getLastEvent: Option[Event]

  protected def getLastNEvents(n: Int): List[Event]

}

class GameServiceImpl extends GameService {
  private var eventStore: List[Event] = List.empty

  override def addEvent(event: Event): Unit = eventStore ::= event

  override def getAllEvents: List[Event] = eventStore.reverse

  override def getLastEvent: Option[Event] = eventStore.headOption

  override def getLastNEvents(n: Int): List[Event] = eventStore.take(n).reverse
}
