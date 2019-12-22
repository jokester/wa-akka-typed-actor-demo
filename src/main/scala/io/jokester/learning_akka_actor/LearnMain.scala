package io.jokester.learning_akka_actor

import akka.actor.ActorSystem
import akka.stream.Materializer

object LearnMain extends App {

  //  implicit lazy val actorSys = ActorSystem("main-system")
  implicit lazy val m = Materializer

  implicit lazy val sys = ActorSystem("main-system")

  val app = new MainApp
  app.startServer("127.0.0.1", 9090, sys)

  println("Server is down... terminating")
  sys.terminate()
}
