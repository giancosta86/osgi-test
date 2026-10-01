package info.gianlucacosta.osgitest.hotswap.gui

object RunnableConverter {
  implicit def functionToRunnable(action: () => Unit): Runnable = new Runnable {
    override def run(): Unit = {
      action()
    }
  }
}
