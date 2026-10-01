package info.gianlucacosta.osgitest.hotswap.gui

import javafx.application.Platform

import info.gianlucacosta.osgitest.hotswap.gui.RunnableConverter._
import org.osgi.framework.{BundleActivator, BundleContext}

class GuiActivator extends BundleActivator {
  private var mainStage: MainStage = _

  override def start(context: BundleContext) {
    System.out.println(s"GuiActivator.start() ==> Activator id: ${System.identityHashCode(this)}")

    printBundles(context)

    Platform.runLater(() =>
      mainStage = new MainStage(context)
    )
  }


  override def stop(context: BundleContext) {
    System.out.println(s"GuiActivator.stop() ==> Activator id: ${System.identityHashCode(this)}")

    Platform.runLater(() =>
      mainStage.hide()
    )
  }


  private def printBundles(context: BundleContext) {
    println("-------------")

    context.getBundles.foreach(bundle =>
      println(s"${bundle.getBundleId} - ${bundle.getSymbolicName} (${bundle.getVersion})")
    )

    println("-------------")
  }
}
