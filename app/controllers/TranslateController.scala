package controllers

import javax.inject._
import models.{RequestMorse, RequestTranslate}
import play.api.Logger
import play.api.libs.json.{Format, JsError, JsSuccess, Json}
import play.api.mvc._
import services.{BinaryTranslateService, MorseService}

@Singleton
class TranslateController @Inject()(morse: MorseService, binaryTranslate: BinaryTranslateService, cc: ControllerComponents) extends AbstractController(cc) {

  val logger: Logger = Logger(this.getClass())
  implicit val requestMorseFormat: Format[RequestMorse] = Json.format[RequestMorse]
  implicit val requestTranslateFormat: Format[RequestTranslate] = Json.format[RequestTranslate]

  def toAlfaToMorse = Action(parse.json) { implicit request =>
    request.body.validate[RequestMorse] match {
      case r: JsSuccess[RequestMorse] => {
        if (r.get.text.matches("[[A-Za-z0-9 ]+]+"))
          Ok(morse.translateAlfaToMorse(r.get.text))
        else {
          logger.error(s"Incorrect Input  ${r.get.text}")
          BadRequest("Invalid characters")
        }
      }
      case validationErr: JsError =>
        BadRequest("Incorrect Json")
    }
  }

  def translate2Human = Action(parse.json) { implicit request =>
    request.body.validate[RequestMorse] match {
      case r: JsSuccess[RequestMorse] => {
        if (r.get.text.matches("[. -]+"))
          Ok(morse.translateMorsetoAlfa(r.get.text))
        else {
          logger.error(s"Incorrect Input ${r.get.text}")
          BadRequest("Invalid characters")
        }
      }
      case validationErr: JsError => BadRequest("Incorrect Json")
    }
  }

  def decodeBits2Morse = Action(parse.json) { implicit request =>
    request.body.validate[RequestMorse] match {
      case r: JsSuccess[RequestMorse] => {
        if (r.get.text.matches("[0-1]+"))
          Ok(binaryTranslate.translateBinaryToMorse(r.get.text))
        else {
          logger.error(s"Incorrect Input ${r.get.text}")
          BadRequest("Invalid characters")
        }
      }
      case validationErr: JsError => {
        logger.error(s"Incorrect json")
        BadRequest("Incorrect Json")
      }
    }
  }

  def translateSmart = Action(parse.json) { implicit request =>
    request.body.validate[RequestTranslate] match {
      case r: JsSuccess[RequestTranslate] => {
        r.get.source match {
          case "morse" => {
            r.get.target match {
              case "bits" => NotImplemented("morse to bits is not implemented")
              case "alfa" => Ok(morse.translateMorsetoAlfa(r.get.text))
              case _ => BadRequest(s"Invalid target ${r.get.target}. Only valid bits, morse or alfa")
            }
          }
          case "bits" => {
            r.get.target match {
              case "morse" => Ok(binaryTranslate.translateBinaryToMorse(r.get.text))
              case "alfa" => NotImplemented("bits to alfa is not implemented")
              case _ => BadRequest(s"Invalid target ${r.get.target}. Only valid bits, morse or alfa")
            }
          }
          case "alfa" => {
            r.get.target match {
              case "bits" => NotImplemented("alfa to bits is not implemented")
              case "morse" => Ok(morse.translateAlfaToMorse(r.get.text))
              case _ => BadRequest(s"Invalid target ${r.get.target}. Only valid bits, morse or alfa")
            }
          }
          case _ => {
            logger.error(s"Invalid source ${r.get.source}")
            BadRequest(s"Invalid source ${r.get.source}. Only valid is bits, morse or alfa")
          }
        }
      }
      case validationErr: JsError => {
        logger.error(s"Incorrect Json")
        BadRequest("Incorrect Json")
      }
    }
  }

}