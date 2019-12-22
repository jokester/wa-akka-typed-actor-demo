scalaVersion := "2.13.1"
import Dependencies._
import com.typesafe.config.ConfigFactory

lazy val root = (project in file("."))
  .settings(
    name := "akka-quickstart-scala",
    /* version = "1.0", */
    libraryDependencies ++= runtimeDeps ++ testDeps ++ buildDeps,
    scalacOptions ++= Seq("-Xlint", "-P:silencer:pathFilters=src_managed"),
  )

val configFile = new File(sys.env.getOrElse("CONFIG_FILE", "src/main/resources/application.conf"))

/*
 * a task to copy generated code
sourceGenerators in Compile += Def.task {
  val src  = (baseDirectory in root).value / "../api-codegen/hanhuazu.gen/scala-finch/src/main/scala" / "io/jokester/hanhuazu"
  val dest = (sourceManaged in Compile).value / "io/jokester/hanhuazu"
  Utils.copyDir(src, dest)
}.taskValue
*/

// "sbt stage" to compile and create executable in target/universal/
enablePlugins(JavaServerAppPackaging)

enablePlugins(AkkaGrpcPlugin)

// make "press return to stop" work in sbt run
connectInput in run := true
