# wa-typed-actor-demo

## Reading

### what is an actor?

https://doc.akka.io/docs/akka/current/general/actors.html

### akka actor (scala API)

- `Behavior[T]`
- `ActorRef[T]`
- `ActorSystem[T]`

T: メッセージタイプ。ActorSystemにおいて、Tは「ルートActorのメッセージタイプ」

## First Example

https://doc.akka.io/docs/akka/current/typed/actors.html#first-example

- HelloWorldMain
- HelloWorld
- HelloWorldActor

## A More Complicated Example

- https://doc.akka.io/docs/akka/current/typed/actors.html#a-more-complex-example

## Compared to akka-(nontyped)-actor

### Actor definition

```
class SomeActor(props) extends akka.actor.Actor {
    override def receive(message: Any): Unit = {
        ...
    }
}

=>

abstract class akka.typed.Behavior[T]

1. "functional style"

constructed by Behaviors.* (

2. "OOP style"

class SomeBehavior(context: ActorContext[T]) extends AbstractBehavior[T](context) {
    def onMessage(message: T): Behavior[T] = {
        ???
    }
}
```

```
type Actor.Receive = PartialFunction[Any, Unit]

context.become(nextReceive: Receive)
=>
return a different Behavior[T] object
```

### Actor creation

```
akka.actor.ActorContext#actorOf(props): akka.ActorRef

=>

TypedActorContext#spawn(behavior: Behavior[T]): akka.typed.ActorRef[T]
```

### ActorSystem

```
akka.ActorSystem(): akka.ActorSystem

=>

akka.typed.ActorSystem(behavior: Behavior[T]): akka.typed.ActorSystem[T]

- explicit root actor
- `class typed.ActorSystem[T] extends typed.ActorRef[T]`
```
