name := "SpoilerBot"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  "com.github.austinv11" % "Discord4J" % "2.9",
  "org.slf4j" % "slf4j-api" % "1.7.25",
  "org.slf4j" % "slf4j-simple" % "1.7.25"
)

resolvers ++= Seq(
  "jitpack" at "https://jitpack.io",
  "jcenter" at "https://jcenter.bintray.com"
)