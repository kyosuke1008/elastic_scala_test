package model


import play.api.libs.json.{Json, Reads}


case class PriceResponse(bid: BigDecimal, ask: BigDecimal)

case class PriceResponses(prices: Seq[PriceResponse])

object PriceResponses {
  implicit def read: Reads[PriceResponses] = Json.reads[PriceResponses]
}
object PriceResponse {
  implicit def read: Reads[PriceResponse] = Json.reads[PriceResponse]
}