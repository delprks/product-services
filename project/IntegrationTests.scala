import sbt.Keys._
import sbt._

object IntegrationTests {

  lazy val ItTest = config("it") extend Test

  lazy val settings = Defaults.itSettings ++ Seq(
    javaOptions in ItTest ++= Seq(
      "-Dconfig.file=src/main/resources/application.it.conf",
      "-Dlogback.configurationFile=logback.it.xml"),
    fork in ItTest := true
  )
}
