package repositories

import play.api.libs.json._
import models.Formatters._
import models.Languages
import play.api.Logger

class MorseRepository {

  private val logger: Logger = Logger(this.getClass())
  private val dictionary = loadData()

  private def loadData() = {
    logger.info("Loading data")
    val data = scala.io.Source.fromFile("conf/data.json").mkString
    Json.parse(s"""$data""").as[Languages]
  }

  /**
    * Find morse
    *
    * @param morse e.g.  .-.
    * @return Alfa Option[String]
    */
  def findMorseCode(morse: String) = {
    dictionary.languages("morse").data.get(morse.toLowerCase)
  }

  /**
    * Find morse
    *
    * @param alfa e.g. f
    * @return Alfa Option[String]
    */
  def findAlfaCode(alfa: String) = {
    dictionary.languages("alfa").data.get(alfa.toLowerCase)
  }

}
