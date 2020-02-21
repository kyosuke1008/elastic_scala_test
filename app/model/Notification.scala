package model

import com.ullink.slack.simpleslackapi.{SlackAttachment, SlackChannel, SlackSession}
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory


class Notification {
  val session: SlackSession = SlackSessionFactory
    .createWebSocketSlackSession("xoxb-387993829538-607615704661-sLcy8tMStLZqL1rg39p4hnal")

  def exec(msg: String, slackAttachment: SlackAttachment): AnyRef = {
    // BotのAPI Tokenを設定// BotのAPI Tokenを設定
    session.connect()

    val channel: SlackChannel = session.findChannelByName("random")
    session.sendMessage(channel, msg, slackAttachment)
  }

  def win(price: Int): AnyRef = {
    val msg =
      s" *${price.toString}円* 利益確定しました(^ ェ ^)".stripMargin

    val slackAttachment = new SlackAttachment()
    slackAttachment.setColor("#00cc00")
    slackAttachment.setTitle("Success!")
    slackAttachment.setText(msg)
    exec("", slackAttachment)
  }

  def lose(price: Int): AnyRef = {
    val msg = s" *${price.toString}円* 損益確定しました(′ ェ ｀)".stripMargin
    val slackAttachment = new SlackAttachment()
    slackAttachment.setColor("#ff0000")
    slackAttachment.setTitle("Failure!")
    slackAttachment.setText(msg)

    exec("", slackAttachment)
  }

  def orders(instrument: String, price: Int): AnyRef = {
    val msg =
      s" *$instrument* を *${price.toString}units* オーダーしました(・ ェ ・)".stripMargin
    val slackAttachment = new SlackAttachment()
    slackAttachment.setTitle("Order!")
    slackAttachment.setText(msg)
    exec("", slackAttachment)
  }
}
