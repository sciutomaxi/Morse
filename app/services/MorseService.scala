package services

import javax.inject.Inject
import play.api.Logger
import repositories.MorseRepository

class MorseService @Inject()(repository: MorseRepository) {

  private val logger: Logger = Logger(this.getClass())

  /**
    * Translate code morse into alphanumeric
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
    * Translate alphanumeric into code morse
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

}
