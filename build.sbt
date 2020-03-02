name := "scala-test"

version := "0.1"

scalaVersion := "2.12.7"


libraryDependencies ++= Seq(
  "org.apache.poi" % "poi" % "3.17",
  "org.apache.poi" % "poi-ooxml-schemas" % "3.17",
  "org.apache.poi" % "poi-ooxml" % "3.17",
  "com.chuusai" %% "shapeless" % "2.3.3"
)
