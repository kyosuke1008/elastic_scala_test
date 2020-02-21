package model

import java.io.File

import javax.inject._
import org.json4s.JValue
import org.json4s.Xml.toJson
import play.api.libs.json._

import scala.xml.parsing.ConstructingParser
import scala.xml.{Comment, Elem, ProcInstr}


case class Master(zid: String,
                  id: String,
                  name: String,
                  t: String,
                  notes: String,
                  scopeZid: String,
                  heirZif_list: String,
                  deficiencyZifZid_list: String,
                  tag_list: String)

object Master {
  implicit val jsonWrites: Writes[Master] = Json.writes[Master]
  implicit val jsonReads: Reads[Master] = Json.reads[Master]
}

@Singleton
class Order {
  //master
  def createMaster(): Seq[Master] = {

    //Fileクラスのオブジェクトを生成する//Fileクラスのオブジェクトを生成する
    val dir = new File("./masters/")

    //listFilesメソッドを使用して一覧を取得する
    val list = dir.listFiles
    list.map(file => {
      val doc = ConstructingParser.fromFile(file, preserveWS = false).document()
      val seq = doc.children //ノード一覧。処理命令やルート要素など
      seq.filter(_.getClass.getSimpleName equals "Elem").map { e =>
        val v: JValue = toJson(e)
        val zenInfo = v \ "zwfRoot" \ "zwfInfo" \ "master"
        Master((zenInfo \ "zid").values.toString,
          (zenInfo \ "id").values.toString,
          (zenInfo \ "name").values.toString,
          (zenInfo \ "type").values.toString,
          (zenInfo \ "notes").values.toString,
          (zenInfo \ "scopeZid").values.toString,
          (zenInfo \ "heirZif_list").values.toString,
          (zenInfo \ "deficiencyZifZid_list").values.toString,
          (zenInfo \ "tag_list").values.toString)
      }.head
    })
  }

}