logLevel := Level.Warn

resolvers ++= Seq(
  Resolver.jcenterRepo,
  Resolver.bintrayIvyRepo("sohoffice", "sbt-plugins")
)

// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.8.1")

addSbtPlugin("com.iheart" % "sbt-play-swagger" % "0.9.1-PLAY2.8")

