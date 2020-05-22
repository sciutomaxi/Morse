lazy val root = (project in file("."))
  .enablePlugins(PlayScala, SwaggerPlugin)
  .settings(
    name := """Morse""",
    scalaVersion := "2.12.8",
    libraryDependencies ++= Seq(
      guice,
      "org.webjars" % "swagger-ui" % "3.25.0",
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
      "org.mockito" % "mockito-core" % "2.8.47" % "test"

    ),
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-Xfatal-warnings"
    )
  )

swaggerDomainNameSpaces := Seq("models")
swaggerPrettyJson := true

coverageExcludedPackages := Seq(
  "router\\.*",
  ".*Reverse.*"
).mkString(";")

coverageMinimum := 70

coverageFailOnMinimum := false

coverageHighlighting := true

coverageEnabled := false