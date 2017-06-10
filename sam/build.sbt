name := "sam"

version := "1.0"

lazy val `sam` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"



libraryDependencies ++= Seq(
  "org.mongodb" %% "casbah" % "2.8.1",
  "org.slf4j" % "slf4j-simple" % "1.6.4",
  "com.roundeights" %% "hasher" % "1.2.0"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

libraryDependencies ++= Seq(
  ws
)