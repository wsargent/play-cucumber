name := """play-cucumber"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.10.6"

resolvers += "Sonatype-Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies ++= Seq(
  cache,
  "org.scalatestplus" %% "play" % "1.4.0" % "test",
  "org.scalatest" %% "scalatest" % "2.2.6" % "test",
  "info.cukes" %% "cucumber-scala" % "1.2.4" % "test"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

routesGenerator := InjectedRoutesGenerator

cucumberSettings

cucumberFeaturesLocation := "./test/features"

cucumberStepsBasePackage := "features.steps"
