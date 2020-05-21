package services

import javax.inject.Inject
import play.api.Logger
import repositories.MorseRepository

class MorseService @Inject()(repository: MorseRepository, binaryTranslate: BinaryTranslateService) {

  private val logger: Logger = Logger(this.getClass())

  def translateMorsetoAlfa(morse: String) = {
    morse.split(" ").foldLeft("") { (a, b) => {
      repository.findAlfaCode(b) match {
        case Some(caracter) => a + caracter
        case None => {
          logger.warn(s"Not found $morse")
          ""
        }
      }
    }
    }.trim
  }

  def translateAlfaToMorse(alfa: String) = {
    alfa.foldLeft("") { (a, b) => {
      a + repository.findMorseCode(b.toString).getOrElse("") + " "
    }
    }.trim
  }

  def translateBinaryToMorse(binay: String) = {
    binaryTranslate.translateBinaryToMorse(binay)
  }

}
