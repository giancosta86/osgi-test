package info.gianlucacosta.osgitest.hotswap.boot

import javafx.application.Application
import javafx.stage.{Stage, StageStyle}


object App {
  def main(args: Array[String]) {
    Application.launch(classOf[App], args: _*)
  }
}

class App extends Application {

  private val startupBundleNames = List(
    "hotswap-gui-1.0.jar",
    "hotswap-message-1.0.jar",
    "scala-library-2.11.7.jar"
  )

  override def start(primaryStage: Stage) {
    //It seems that the primary stage *must* be shown, even if totally
    // invisible, to prevent bugs when hiding secondary windows
    primaryStage.initStyle(StageStyle.UTILITY)
    primaryStage.setOpacity(0)
    primaryStage.show()

    new Thread(new Runnable {
      override def run(): Unit = {
        OsgiService.startOSGi(startupBundleNames)
      }
    }).start()
  }
}
