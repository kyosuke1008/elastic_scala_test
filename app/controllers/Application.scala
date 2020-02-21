package controllers

import com.google.inject.Inject
import model._
import play.Configuration
import play.api.libs.json.Json
import play.api.libs.ws.WSClient
import play.api.mvc.{AnyContent, _}
import zef.ZwfBuildGlobalDtoService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class Application @Inject()(wSClient: WSClient)(config: Configuration)
  extends Controller with ElasticWS {

  override protected val ws: WSClient = wSClient

  /**
    *
    *
    * @return ステータス
    */
  def getXML: Action[AnyContent] =
    Action.async {

      val service = new ZwfBuildGlobalDtoService();
      val o = new Order()
      o.createMaster().map(j => {
        registry(Json.toJson(j))
      })
      Future {
        Ok(Json.obj("average" -> o.createMaster().toString))
      }
    }
}