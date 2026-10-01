package info.gianlucacosta.osgitest.hotswap.gui

import javafx.event.{Event, EventHandler}

object EventHandlerConverter {
  implicit def functionToEventHandler[T <: Event](action: T => Unit): EventHandler[T] = {
    new EventHandler[T] {
      override def handle(event: T): Unit = {
        action(event)
      }
    }
  }
}
