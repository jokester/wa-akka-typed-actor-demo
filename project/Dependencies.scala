import sbt._

object Dependencies {

  private lazy val akkaHttpVersion = "10.1.11"
  private lazy val akkaVersion = "2.6.1"

  lazy val runtimeDeps: Seq[ModuleID] = Seq(
    // akka
    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "com.typesafe.akka" %% "akka-discovery" % akkaVersion,
    // "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
    // "com.typesafe.akka" %% "akka-http-xml" % akkaHttpVersion,

    // redis
    "net.debasishg" %% "redisclient" % "3.20",

    // logging
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
    "ch.qos.logback" % "logback-classic" % "1.2.3", // this provides SLJ4J backend

    // util
    "com.typesafe" % "config" % "1.3.1",
    "com.aventrix.jnanoid" % "jnanoid" % "2.0.0",
  )

  lazy val testDeps: Seq[ModuleID] = Seq(
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
    "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion,

    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion,
    "org.scalatest" %% "scalatest" % "3.0.8"
  ).map(_ % Test)

  private lazy val silencerVersion = "1.4.4"

  lazy val buildDeps: Seq[ModuleID] = Seq(
    compilerPlugin("com.github.ghik" % "silencer-plugin" % silencerVersion cross CrossVersion.full),
    "com.github.ghik" % "silencer-lib" % silencerVersion % Provided cross CrossVersion.full
  )

}
