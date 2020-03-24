ThisBuild / version := sys.env.get("APP_VERSION").getOrElse("0.1.0")
ThisBuild / scalaVersion := "2.12.6"
ThisBuild / organization := "org.pilosus"

lazy val wordCount = (project in file("."))
  .enablePlugins(JavaAppPackaging)
  .settings(
    name := "WordCount",
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.1.1",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.1" % "test"
  )
