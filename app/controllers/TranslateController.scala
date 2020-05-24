package controllers

import javax.inject._
import models.RequestMorse
import play.api.Logger
import play.api.libs.json.{Format, JsError, JsSuccess, Json}
import play.api.mvc._
import services.{BinaryTranslateService, MorseService}

@Singleton
class TranslateController @Inject()(morse: MorseService, binaryTranslate: BinaryTranslateService, cc: ControllerComponents) extends AbstractController(cc) {

  val logger: Logger = Logger(this.getClass())
  implicit val requestMorseFormat: Format[RequestMorse] = Json.format[RequestMorse]

  def toAlfaToMorse = Action(parse.json) { implicit request =>
    request.body.validate[RequestMorse] match {
      case r: JsSuccess[RequestMorse] => {
        if (r.get.text.matches("[[A-Za-z0-9 ]+]+"))
          Ok(morse.translateAlfaToMorse(r.get.text))
        else {
          logger.error(s"Input incorrecto ${r.get.text}")
          BadRequest("Caracteres invalidos")
        }
      }
      case validationErr: JsError =>
        BadRequest("Json incorrecto")
    }
  }

  def translate2Human = Action(parse.json) { implicit request =>
    request.body.validate[RequestMorse] match {
      case r: JsSuccess[RequestMorse] => {
        if (r.get.text.matches("[. -]+"))
          Ok(morse.translateMorsetoAlfa(r.get.text))
        else {
          logger.error(s"Input incorrecto ${r.get.text}")
          BadRequest("Caracteres invalidos")
        }
      }
      case validationErr: JsError => BadRequest("Json incorrecto")
    }
  }

  def decodeBits2Morse = Action(parse.json) { implicit request =>
    request.body.validate[RequestMorse] match {
      case r: JsSuccess[RequestMorse] => {
        if (r.get.text.matches("[0-1]+"))
          Ok(binaryTranslate.translateBinaryToMorse(r.get.text))
        else {
          logger.error(s"Input incorrecto ${r.get.text}")
          BadRequest("Caracteres invalidos")
        }
      }
      case validationErr: JsError => {
        logger.error(s"Json incorrecto")
        BadRequest("Json incorrecto")
      }
    }
  }

}