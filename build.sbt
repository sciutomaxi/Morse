import sbt.Keys._

lazy val root = (project in file("."))
  .enablePlugins(PlayService, PlayLayoutPlugin)
  .settings(
    name := "morse",
    scalaVersion := "2.12.8",
    libraryDependencies ++= Seq(
      guice,
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
      "org.mockito" % "mockito-core" % "2.8.47" % "test"
    )
  )
