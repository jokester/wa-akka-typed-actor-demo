package com.wamazing.learning_akka

import akka.actor.typed.ActorSystem

import scala.concurrent.duration._
import com.wamazing.learning_akka.first_example.HelloWorldMain
import com.wamazing.learning_akka.more_complex_example.ChatRoomMain

object Main extends App {

  def runFirstExample(): Unit = {
    val system = ActorSystem(HelloWorldMain(), "hello-world-main")

    system ! HelloWorldMain.Start("World")
    system ! HelloWorldMain.Start("Akka")

    system.scheduler.scheduleOnce(5.seconds, () => system.terminate())(system.executionContext)
  }

  def runMoreComplicatedExample(): Unit = {
    val system = ActorSystem(ChatRoomMain(), "ChatRoomDemo")

    system.scheduler.scheduleOnce(5.seconds, () => system.terminate())(system.executionContext)
  }

//  runMoreComplicatedExample()
  runFirstExample()
}
