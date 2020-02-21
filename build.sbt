name := "elastic_generator"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

evictionWarningOptions in update := EvictionWarningOptions.default.withWarnTransitiveEvictions(false).withWarnDirectEvictions(false).withWarnScalaVersionEviction(false)


scalaVersion := "2.11.7"
resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test,
  evolutions,
  "rome" % "rome" % "1.0",
  "org.jdom" % "jdom" % "2.0.2",
  "org.scalatestplus" %% "play" % "1.4.0-M4" % "test",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  // https://mvnrepository.com/artifact/mysql/mysql-connector-java
  "mysql" % "mysql-connector-java" % "8.0.15",
  "com.typesafe.play" %% "anorm" % "2.5.1",
  // https://mvnrepository.com/artifact/com.ullink.slack/simpleslackapi
  "com.ullink.slack" % "simpleslackapi" % "1.2.0",
  "org.json4s" %% "json4s-xml" % "3.7.0-M2"


)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its statically.

PlayKeys.playOmnidoc := false
routesGenerator := InjectedRoutesGenerator
