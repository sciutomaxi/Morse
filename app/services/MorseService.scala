package services

import javax.inject.Inject
import play.api.Logger
import repositories.MorseRepository

class MorseService @Inject()(repository: MorseRepository, binaryTranslate: BinaryTranslateService) {

  private val logger: Logger = Logger(this.getClass())

  /**
    * Traducimos de code morse a alfanumerico
    *
    * @param morse .... --- .-.. .-
    * @return string e.g "hola"
    */
  def translateMorsetoAlfa(morse: String) = {
    morse.split(" ").foldLeft("") { (a, b) => {
      repository.findAlfaCode(b) match {
        case Some(caracter) => a + caracter
        case None => {
          logger.warn(s"Not found $morse $b")
          a + ""
        }
      }
    }
    }.trim
  }

  /**
    * Traducimos de alfa a code morse
    *
    * @param alfa hola
    * @return string .... --- .-.. .-
    */
  def translateAlfaToMorse(alfa: String) = {
    alfa.foldLeft("") { (a, b) => {
      a + repository.findMorseCode(b.toString).getOrElse("") + " "
    }
    }.trim
  }

  /**
    * Traducimos de binario a codigo morse
    *
    * @param binary 010101000110000
    * @return code morse
    */
  def translateBinaryToMorse(binary: String) = {
    binaryTranslate.translateBinaryToMorse(binary)
  }

}
