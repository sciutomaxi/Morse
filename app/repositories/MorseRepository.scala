package repositories

import play.api.libs.json._
import models.Formatters._
import models.Languages
import play.api.Logger

class MorseRepository {

  private val logger: Logger = Logger(this.getClass())
  private val dictionary = loadData()

  /**
    * Cargamos los dicionarios en un map
    * @return
    */
  private def loadData() = {
    logger.info("Loading data")
    val data = scala.io.Source.fromFile("conf/data.json").mkString
    Json.parse(s"""$data""").as[Languages]
  }

  /**
    * Find morse
    *
    * @param alfa e.g.  hola
    * @return Morse Option[String]
    */
  def findMorseCode(alfa: String) = {
    dictionary.languages("morse").data.get(alfa.toLowerCase)
  }

  /**
    * Find morse
    *
    * @param morse e.g. .-.
    * @return Alfa Option[String]
    */
  def findAlfaCode(morse: String) = {
    dictionary.languages("alfa").data.get(morse)
  }

}
