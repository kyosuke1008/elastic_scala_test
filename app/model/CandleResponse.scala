package model

import java.time.LocalDateTime

import play.api.libs.json.{Json, Reads}


case class CandleResponse(mid: Mid, time: LocalDateTime)

case class CandleResponses(candles: Seq[CandleResponse])

case class Mid(l:BigDecimal,h:BigDecimal)

object Mid {
  implicit def read: Reads[Mid] = Json.reads[Mid]
}

object CandleResponses {
  implicit def read: Reads[CandleResponses] = Json.reads[CandleResponses]
}
object CandleResponse {
  implicit def read: Reads[CandleResponse] = Json.reads[CandleResponse]
}