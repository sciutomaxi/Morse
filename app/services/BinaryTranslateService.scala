package services

import javax.inject.Inject

import scala.util.Try

class BinaryTranslateService @Inject()() {

  /**
    * Translate from binary to morse code
    *
    * @param binary 010101000110000
    * @return code morse
    */
  def translateBinaryToMorse(binay: String) = {
    val values = getAvgOnesAndZeros(binay)
    val binaryChars = binay.toCharArray
    var i = 0
    var countOne = 0
    var countZero = 0
    var morse = ""
    while (i < binaryChars.length) {
      if (binaryChars(i) == '1') {
        countOne += 1
        if (i != 0 && binaryChars(i - 1) == '0') {
          morse += getCharLettersOrWord(countZero, values._2)
          countZero = 0
        }
        //si es el fin
        if (i == binaryChars.size - 1) {
          morse += getDotOrDash(countOne, values._1)
        }
      } else {
        countZero += 1
        if (i != 0 && binaryChars(i - 1) == '1') {
          morse += getDotOrDash(countOne, values._1)
          countOne = 0
        }
        //si es el fin
        if (i == binaryChars.size - 1) {
          morse += getCharLettersOrWord(countZero, values._2)
        }
      }
      i += 1
    }
    morse.trim
  }

  /**
    * Get a dot or dash depending on the number of counted 1s and the average
    *
    * @param countOne int
    * @param avg      int
    * @return String . or -
    */
  private def getDotOrDash(countOne: Int, avg: Int): String = {
    if (countOne <= avg)
      "."
    else
      "-"
  }

  /**
    * Get a letter, word or character
    *
    * @param countZero
    * @param avgZero
    * @return
    */
  private def getCharLettersOrWord(countZero: Int, avgZero: Int): String = {
    if (countZero <= avgZero) {
      ""
    } else {
      //debemos ver si es un espacio entre letras o palabras
      if (countZero <= ((avgZero * 2) + 1)) //es una letra
        " "
      else // es una palabra
        "  "
    }
  }

  /**
    * Get averages of 1s and 0s
    * @param binay
    * @return (avg ones, avg zeros)
    */
  def getAvgOnesAndZeros(binay: String) = {
    val binaryChars = binay.toCharArray
    var i = 0
    var countOne = 0
    var ocurrenciasOne = 0
    var countZero = 0
    var countBitsZero = 0
    var ocurrenciasZero = 0

    while (i < binaryChars.length) {
      if (binaryChars(i) == '1') {
        countOne += 1

        //si no es la primera pos y el anterior bit era un 0 contamos la ocurrencia
        if (i != 0 && binaryChars(i - 1) == '0') {
          ocurrenciasZero += 1
          countBitsZero = 0
        } else if (i == (binaryChars.size - 1)) { //analizamos la ultima posicion
          ocurrenciasOne += 1
        }
      } else {
        //si no es la primera pos y el anterior bit era un 1 contamos la ocurrencia
        if (i != 0 && binaryChars(i - 1) == '1')
          ocurrenciasOne += 1
        countZero += 1
        countBitsZero += 1

        if (i == (binaryChars.size - 1)) //analizamos la ultima posicion
          ocurrenciasZero += 1
      }
      i += 1
    }

    (Try(countOne / ocurrenciasOne).getOrElse(0), Try(countZero / ocurrenciasZero).getOrElse(0))
  }


}
