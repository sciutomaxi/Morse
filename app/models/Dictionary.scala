package models

import play.api.libs.json._

case class Dictionary(data: Map[String, String])

case class Languages(languages: Map[String, Dictionary])

object Formatters {
  implicit val dataFormat = Json.format[Dictionary]
  implicit val languagesFormat = Json.format[Languages]
}