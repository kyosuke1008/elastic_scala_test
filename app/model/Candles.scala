package model

import java.text.SimpleDateFormat
import java.util.{Calendar, Date}

import anorm.SQL
import javax.inject.{Inject, Singleton}
import play.api.db.Database

@Singleton
class Candles @Inject()(db: Database) {
  val sdfId = new SimpleDateFormat("yyyyMMddHHmm")
  val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

  def bulkInsert(bulk: String, instrument: String): Boolean =
    db.withConnection { implicit c =>
      SQL(
        s"""
          INSERT INTO CANDLES (`ID`, `INSTRUMENT`, `GRANULARITY`, `PRICE`, `TIME`)
          VALUES $bulk
          ON DUPLICATE KEY UPDATE INSTRUMENT = '$instrument'
        """.stripMargin).execute()
    }

  def insert(instrument: String, price: Double, granularity: String): Boolean =
    db.withConnection { implicit c =>
      val calendar: Calendar = Calendar.getInstance
      calendar.setTime(new Date())
      println(sdf.format(calendar.getTime))
      SQL(
        """
          INSERT INTO CANDLES (`ID`, `INSTRUMENT`, `GRANULARITY`, `PRICE`, `TIME`)
          VALUES ({id},{instrument},{granularity},{price}, {time})
        """.stripMargin)
        .on("id" -> sdfId.format(calendar.getTime),
          "instrument" -> instrument,
          "granularity" -> granularity,
          "price" -> price,
          "time" -> sdf.format(calendar.getTime)).execute()
    }
}