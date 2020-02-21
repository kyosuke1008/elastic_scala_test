package model

import java.time.LocalDateTime

import play.api.libs.json._

case class InOrder(id: String, `type`: String, createTime: LocalDateTime, tradeID: String, price: BigDecimal)

case class InOrders(orders: Seq[InOrder])

object InOrders {
  implicit def read: Reads[InOrders] = Json.reads[InOrders]
}

object InOrder {
  implicit def read: Reads[InOrder] = Json.reads[InOrder]
}