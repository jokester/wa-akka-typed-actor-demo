package com.wamazing.learning_akka

import akka.actor.typed.ActorSystem
import scala.concurrent.duration._
import com.wamazing.learning_akka.first_example.HelloWorldMain

object Main extends App {

  def runFirstExample(): Unit = {
    val system = ActorSystem(HelloWorldMain(), "hello-world-main")

    system ! HelloWorldMain.Start("World")
    system ! HelloWorldMain.Start("Akka")

    system.scheduler.scheduleOnce(5.seconds, () => system.terminate())(system.executionContext)
  }

  runFirstExample()
}
