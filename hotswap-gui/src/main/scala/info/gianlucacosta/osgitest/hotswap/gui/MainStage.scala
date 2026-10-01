package info.gianlucacosta.osgitest.hotswap.gui

import java.io.FileInputStream
import javafx.event.ActionEvent
import javafx.geometry.{Insets, Pos}
import javafx.scene.Scene
import javafx.scene.control.{Alert, Button}
import javafx.scene.layout.VBox
import javafx.stage.{FileChooser, Stage, WindowEvent}

import info.gianlucacosta.osgitest.hotswap.gui.EventHandlerConverter._
import info.gianlucacosta.osgitest.hotswap.message.MessageProvider
import org.osgi.framework.wiring.FrameworkWiring
import org.osgi.framework.{BundleContext, FrameworkUtil}


class MainStage(bundleContext: BundleContext) extends Stage {
  setScene(createScene())

  setWidth(500)
  setHeight(300)
  setTitle("OSGi test - HotSwap")
  setResizable(false)
  centerOnScreen()
  show()


  private def createScene(): Scene = {
    val buttonWidth = 200
    val buttonHeight = 32

    val mainBox = new VBox()
    mainBox.setPadding(new Insets(40))
    mainBox.setSpacing(25)
    mainBox.setAlignment(Pos.CENTER)

    val showMessageButton = new Button("Show message")
    showMessageButton.setPrefSize(buttonWidth, buttonHeight)
    showMessageButton.setOnAction((event: ActionEvent) => {
      showUserMessage()
      ()
    })

    mainBox.getChildren.add(showMessageButton)


    val updateButton = new Button("Update message bundle...")
    updateButton.setPrefSize(buttonWidth, buttonHeight)
    updateButton.setOnAction((event: ActionEvent) => {
      updateMessageBundle(bundleContext)
      ()
    })
    mainBox.getChildren.add(updateButton)

    setOnCloseRequest((event: WindowEvent) => {
      System.exit(0)
    })

    new Scene(mainBox)
  }


  def showUserMessage(): Unit = {
    val alert = new Alert(Alert.AlertType.INFORMATION)
    alert.setTitle(MainStage.this.getTitle)
    alert.setHeaderText("OSGI Test")
    alert.setContentText(MessageProvider.getMessage)
    alert.showAndWait()
  }


  def updateMessageBundle(bundleContext: BundleContext): Unit = {
    val fileChooser = new FileChooser()
    fileChooser.getExtensionFilters.add(
      new FileChooser.ExtensionFilter(
        "OSGi bundle",
        "*.jar"))

    fileChooser.setTitle("Open updated OSGi bundle...")

    val bundleFile = fileChooser.showOpenDialog(this)
    if (bundleFile != null) {
      val sourceFileStream = new FileInputStream(bundleFile)

      FrameworkUtil
        .getBundle(MessageProvider.getClass)
        .update(sourceFileStream)


      val systemBundle = bundleContext.getBundle(0)
      val frameworkWiring = systemBundle.adapt(classOf[FrameworkWiring])
      frameworkWiring.refreshBundles(null)
    }
  }
}
