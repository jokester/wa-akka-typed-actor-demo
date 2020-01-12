package com.wamazing.learning_akka.first_example
import akka.actor.typed.scaladsl.{Behaviors, LoggerOps}
import akka.actor.typed.{ ActorRef, ActorSystem, Behavior }

object HelloWorldMain {

  final case class Start(name: String)

  def apply(): Behavior[Start] =
    Behaviors.setup { context =>
      val greeter = context.spawn(HelloWorld(), "greeter")

      Behaviors.receiveMessage { message =>
        val replyTo = context.spawn(HelloWorldBot(max = 3), message.name)
        greeter ! HelloWorld.Greet(message.name, replyTo)
        Behaviors.same
      }
    }
}
