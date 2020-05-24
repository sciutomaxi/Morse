package controllers

import akka.stream.Materializer
import org.scalatest.FunSuite
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test._
import repositories.MorseRepository
import services.{BinaryTranslateService, MorseService}

class TranslateControllerTest extends FunSuite with GuiceOneAppPerSuite {

  implicit lazy val materializer: Materializer = app.materializer

  val repo = new MorseRepository
  val binaryTranslate = new BinaryTranslateService()
  val morseServiceInst = new MorseService(repo)
  val controller = new TranslateController(morseServiceInst, binaryTranslate, Helpers.stubControllerComponents())

  test("Json valid OK /translate/2morse") {
    val request = FakeRequest(POST, s"/translate/2morse").withJsonBody(Json.parse("""{"text":"hola"}"""))
    val response = call(controller.toAlfaToMorse, request)
    assert(status(response) == OK)
  }

  test("Caracteres invalidos BadRequest /translate/2morse") {
    val requestBad = FakeRequest(POST, s"/translate/2morse").withJsonBody(Json.parse("""{"text":"ho@-{} la"}"""))
    val responseBad = call(controller.toAlfaToMorse, requestBad)
    assert(status(responseBad) == BAD_REQUEST)
  }

  test("Json invalido BadRequest /translate/2morse") {
    val requestJsonBad = FakeRequest(POST, s"/translate/2morse").withJsonBody(Json.parse("""{"mensaje":"hola"}"""))
    val responseJsonBad = call(controller.toAlfaToMorse, requestJsonBad)
    assert(status(responseJsonBad) == BAD_REQUEST)
  }

  test("Json valid OK /translate/2text") {
    val request = FakeRequest(POST, s"/translate/2text").withJsonBody(Json.parse("""{"text":".... --- .-.. .-"}"""))
    val response = call(controller.translate2Human, request)
    assert(status(response) == OK)
  }

  test("Caracteres invalidos BadRequest /translate/2text") {
    val requestBad = FakeRequest(POST, s"/translate/2text").withJsonBody(Json.parse("""{"text":"hola"}"""))
    val responseBad = call(controller.translate2Human, requestBad)
    assert(status(responseBad) == BAD_REQUEST)
  }

  test("Json invalido BadRequest /translate/2text") {
    val requestJsonBad = FakeRequest(POST, s"/translate/2text").withJsonBody(Json.parse("""{"mensaje":"hola"}"""))
    val responseJsonBad = call(controller.translate2Human, requestJsonBad)
    assert(status(responseJsonBad) == BAD_REQUEST)
  }

  test("Json valid OK /translate/binaryToMorse") {
    val request = FakeRequest(POST, s"/translate/binaryToMorse").withJsonBody(Json.parse("""{"text":"0010101000100000"}"""))
    val response = call(controller.decodeBits2Morse, request)
    assert(status(response) == OK)
  }

  test("Caracteres invalidos BadRequest /translate/binaryToMorse") {
    val requestBad = FakeRequest(POST, s"/translate/binaryToMorse").withJsonBody(Json.parse("""{"text":"hola"}"""))
    val responseBad = call(controller.decodeBits2Morse, requestBad)
    assert(status(responseBad) == BAD_REQUEST)
  }

  test("Json invalido BadRequest /translate/binaryToMorse") {
    val requestJsonBad = FakeRequest(POST, s"/translate/binaryToMorse").withJsonBody(Json.parse("""{"mensaje":"hola"}"""))
    val responseJsonBad = call(controller.decodeBits2Morse, requestJsonBad)
    assert(status(responseJsonBad) == BAD_REQUEST)
  }

}