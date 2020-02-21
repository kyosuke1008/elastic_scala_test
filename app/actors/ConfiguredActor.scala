package actors

import akka.actor._
import javax.inject._
import play.api.Configuration

object ConfiguredActor {
  case object GetConfig
}

class ConfiguredActor @Inject() (configuration: Configuration) extends Actor {
  import ConfiguredActor._

  val config: String = configuration.getString("my.config").getOrElse("none")

  def receive: PartialFunction[Any, Unit] = {
    case GetConfig =>
      sender() ! config
  }
}