package services

import models.Event

import java.util.concurrent.atomic.AtomicInteger
import scala.collection.concurrent
import scala.collection.concurrent.TrieMap

trait GameService {
  val currentId: AtomicInteger
  val eventStore: concurrent.Map[Int, Event]

  def addEvent(event: Event): Unit = {
    val nextId = currentId.getAndIncrement()
    eventStore.addOne(nextId -> event)
  }

  def getAllEvents: Seq[(Int, Event)] = eventStore.toSeq.sortBy { case (id, _) => id }

  def getLastEvent: Option[Event] = eventStore.get(currentId.get() - 1)

  def getLastNEvents(numberOfEvents: Int): Option[Seq[(Int, Event)]] = {
    if (numberOfEvents < 0) {
      None
    } else {
      val events = eventStore.toSeq.sortBy { case (id, _) => id }.takeRight(numberOfEvents)
      Some(events)
    }
  }
}

class GameServiceImpl extends GameService {
  val currentId = new AtomicInteger(0)
  val eventStore: concurrent.Map[Int, Event] = concurrent.TrieMap.empty[Int, Event]
}

