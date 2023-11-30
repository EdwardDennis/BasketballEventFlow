package mocks

import models.Event
import org.scalatestplus.mockito.MockitoSugar
import services.GameService

import java.util.concurrent.atomic.AtomicInteger
import scala.collection.concurrent
import scala.collection.concurrent.TrieMap

class MockGameService extends GameService with MockitoSugar {
  override val currentId: AtomicInteger = new AtomicInteger(0)
  override val eventStore: TrieMap[Int, Event] = concurrent.TrieMap.empty[Int, Event]

  def reset(): Unit = {
    currentId.set(0)
    eventStore.clear()
  }
}
