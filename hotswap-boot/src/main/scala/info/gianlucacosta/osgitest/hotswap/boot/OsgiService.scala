package info.gianlucacosta.osgitest.hotswap.boot

import java.util.ServiceLoader

import org.osgi.framework.Constants
import org.osgi.framework.launch.FrameworkFactory

import scala.collection.JavaConversions._

object OsgiService {
  def startOSGi(bundleNames: Seq[String]) {
    val frameworkFactory = ServiceLoader
      .load(classOf[FrameworkFactory])
      .iterator()
      .next()

    val configurationMap = Map(
      Constants.FRAMEWORK_STORAGE_CLEAN ->
        Constants.FRAMEWORK_STORAGE_CLEAN_ONFIRSTINIT,

      "org.osgi.framework.system.packages.extra" ->
        List(
          "javafx.application",
          "javafx.collections",
          "javafx.event",
          "javafx.geometry",
          "javafx.scene",
          "javafx.scene.control",
          "javafx.scene.layout",
          "javafx.stage").mkString(",")
    )

    val framework = frameworkFactory.newFramework(configurationMap)
    framework.start()


    val bundleContext = framework.getBundleContext

    bundleNames.foreach(bundleName => {
      val bundlePath = s"/info/gianlucacosta/osgitest/bundles/${bundleName}"

      val bundleUrl = getClass.getResource(bundlePath).toString

      bundleContext.installBundle(
        bundleUrl
      )
    })


    bundleContext.getBundles.foreach(_.start())

    framework.waitForStop(0)
  }
}
