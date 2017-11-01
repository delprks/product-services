import org.scalastyle.sbt.ScalastylePlugin

scalaVersion := "2.11.11"

scalacOptions := Seq(
  "-unchecked",
  "-deprecation",
  "-encoding",
  "utf8"
)

val applicationDependencies = {
  val akkaVersion               = "2.5.4"
  val akkaHttpVersion           = "10.0.10"
  val akkaHttpJson4sVersion     = "1.17.0"
  val json4sVersion             = "3.5.0"
  val jodaTimeVersion           = "2.9.4"
  val jodaConvertVersion        = "1.8"
  val logbackVersion            = "1.2.1"
  val slf4sVersion              = "1.7.12"
  val specs2Version             = "3.8.9"
  val slickVersion              = "3.1.1"

  Seq(
    "com.typesafe.akka"      %% "akka-actor"        % akkaVersion,
    "com.typesafe.akka"      %% "akka-slf4j"        % akkaVersion,
    "com.typesafe.akka"      %% "akka-stream"       % akkaVersion,
    "com.typesafe.akka"      %% "akka-http"         % akkaHttpVersion,
    "com.typesafe.slick"     %% "slick"             % slickVersion,
    "com.typesafe.slick"     %% "slick-hikaricp"    % slickVersion,
    "de.heikoseeberger"      %% "akka-http-json4s"  % akkaHttpJson4sVersion,
    "org.json4s"             %% "json4s-jackson"    % json4sVersion,
    "org.json4s"             %% "json4s-ext"        % json4sVersion,
    "joda-time"              %  "joda-time"         % jodaTimeVersion,
    "org.joda"               %  "joda-convert"      % jodaConvertVersion,
    "ch.qos.logback"         %  "logback-classic"   % logbackVersion,
    "org.slf4s"              %% "slf4s-api"         % slf4sVersion,
    "com.h2database"         %  "h2"                % "1.4.193",
    "com.typesafe.akka"      %% "akka-http-testkit" % akkaHttpVersion % "test",
    "org.specs2"             %% "specs2-core"       % specs2Version   % "it,test",
    "org.specs2"             %% "specs2-mock"       % specs2Version   % "it,test"
  )
}

val `test-all` = taskKey[Unit]("Run Unit tests, integration tests and scalastyle.")
val testSettings = IntegrationTests.settings ++ Seq(
  `test-all` in Compile := Def.sequential(
    test in Test,
    test in IntegrationTests.ItTest,
    (ScalastylePlugin.scalastyle in Compile).toTask("")
  ).value
)

lazy val `product-services-prototype` = (project in file("."))
  .configs(IntegrationTests.ItTest)
  .settings(
    organization := "delprks.apis",
    name := "product-services-prototype",
    libraryDependencies ++= applicationDependencies,
    javaOptions in run ++= Seq(
      "-Dconfig.resource=application.dev.conf",
      "-Dlogback.configurationFile=logback.dev.xml"),
    fork in run := true
  )
  .settings(testSettings)
