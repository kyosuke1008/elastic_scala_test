package model

import anorm._
import javax.inject._
import play.api.db._

case class CurrentStat(status: String, isOrder: Boolean, orderPrice: BigDecimal, trade: String)

//STATUS      varchar(100) default '1,2,3' not null,
//INSTRUMENT  varchar(20)                  not null,
//IS_ORDER    tinyint(1) default '0'       not null,
//ORDER_PRICE double,
//TRADE       varchar(10),

@Singleton
class CurrentStatus @Inject()(db: Database) {
  val parser: RowParser[CurrentStat] = for {
    status <- SqlParser.str("STATUS")
    isOrder <- SqlParser.bool("IS_ORDER")
    orderPrice <- SqlParser.double("ORDER_PRICE")
    trade <- SqlParser.str("TRADE")
  } yield CurrentStat(status, isOrder, orderPrice, trade)

  val isOrderParser: RowParser[Boolean] = for {
    isOrder <- SqlParser.bool("IS_ORDER")
  } yield isOrder

  def lose(instrument: String) {
    val c = fetch(instrument).status.split(",")
    val num1: Int = c.head.toInt + c.last.toInt
    //配列に追加
    val addC = c :+ num1.toString
    if (c.length < 3) {
      update("1,2,3", isOrder = false, 0, "none", instrument)
    } else {
      update(addC.mkString(",") , isOrder = false, 0, "none", instrument)
    }
  }

  def win(instrument: String) {
    val c = fetch(instrument).status.split(",")
    val num1 = c.filter(n => n != c.head).filter(n => n != c.last)
    //    val num2 = num1.filter(n => n != num1.head).filter(n => n != num1.last)
    val deleteC = num1.mkString(",")
    print(c.head)
    if (num1.length < 2) {
      update("1,2,3", isOrder = false, 0, "none", instrument)
    } else {
      update(deleteC, isOrder = false, 0, "none", instrument)
    }
  }

  def fetch(instrument: String): CurrentStat = db.withConnection { implicit c =>
    SQL("SELECT * FROM CURRENT_STATUS WHERE INSTRUMENT = {instrument}")
      .on("instrument" -> instrument).as(parser.singleOpt)
      .getOrElse(CurrentStat("1,2,3", isOrder = false, 0, null))
  }

  def isOrder(instrument: String): Option[Boolean] = db.withConnection { implicit c =>
    SQL("SELECT IS_ORDER FROM CURRENT_STATUS WHERE INSTRUMENT = {instrument}")
      .on("instrument" -> instrument).as(isOrderParser.singleOpt)
  }

  def update(status: String, isOrder: Boolean, orderPrice: Double, trade: String, instrument: String): Boolean =
    db.withConnection { implicit c =>
      SQL(
        """
        UPDATE CURRENT_STATUS
        SET STATUS = {status},
            IS_ORDER = {isOrder},
            ORDER_PRICE = {orderPrice},
            TRADE = {trade}
        WHERE INSTRUMENT = {instrument}
      """.stripMargin)
        .on("status" -> status, "isOrder" -> isOrder, "orderPrice" -> orderPrice,
          "trade" -> trade, "instrument" -> instrument).execute()
    }

  /**
    * 現在の倍率
    *
    * @return 倍率
    */
  def magnification(instrument: String): Int = {
    val c = fetch(instrument).status.split(",")
    c.head.toInt + c.last.toInt
  }
}