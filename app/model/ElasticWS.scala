package model

import play.api.libs.json._
import play.api.libs.ws.{WSAuthScheme, WSClient, WSRequest}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait ElasticWS {

  val user = "elastic"
  val password = "iiDN8hJBGOktOJC2gVyul0p6"
  val elasticUrl = "https://aeed164b82e14c938f9de6e5ec7d1197.ap-northeast-1.aws.found.io:9243"


  protected val ws: WSClient


  private def access(path: String): WSRequest =
    ws.url(s"$elasticUrl$path")
      .withAuth(user, password, WSAuthScheme.BASIC)
      .withHeaders("Content-type" -> "application/json")


  /**
    * 現在のオーダー
    *
    * @param instrument 通貨ペア
    * @return 現在のオーダー
    */
  def registry(body: JsValue): Future[JsResult[InOrders]] =
    access("/master/zef")
      .post(body).map { response =>
      println(response.json)
      response.json.validate[InOrders]
    }
}
