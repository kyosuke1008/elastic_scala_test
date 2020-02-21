package schedule

/**
 @author ishizukyousuke on 2015/10/03.
 */
import akka.actor.Actor

trait ActorBase extends Actor {

  /** Akkaから自動的にCallされる */
  def receive = {
    case message: String => {

    }
  }

  /** 処理本体 */
  def execute()
}