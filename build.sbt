name := "bassethound"

version := "0.1"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-log4j12" % "1.7.12",
  "com.github.scopt" %% "scopt" % "3.4.0",
  "org.json4s" %% "json4s-native" % "3.3.0",
  "org.scalatest" %% "scalatest" % "2.2.6" % "test")
