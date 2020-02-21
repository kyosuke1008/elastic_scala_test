package actors

import akka.actor._

object HelloActor {
  def props: Props = Props[HelloActor]

  case class SayHello(name: String)
}

class HelloActor extends Actor {

  import HelloActor._

  def receive: PartialFunction[Any, Unit] = {
    case SayHello(name: String) =>
      sender() ! "Hello, " + name
  }
}