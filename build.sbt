ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.1"

lazy val root = (project in file("."))
  .settings(
    name := "basketballScoring",
    coverageEnabled := true
  )


libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-simple" % "2.0.9",
  "org.slf4j" % "slf4j-api" % "2.0.9",
  "org.scalatest" %% "scalatest" % "3.2.17" % "test",
  "org.scalatestplus" %% "mockito-4-11" % "3.2.17.0" % "test"
)

