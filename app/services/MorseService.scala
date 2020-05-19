package services

import javax.inject.Inject
import repositories.MorseRepository

class MorseService @Inject()(repo: MorseRepository) {

  def translateMorsetoAlfa(morse: String) = {
    morse.split(" ").foldLeft("") { (a, b) => {
      a + repo.findAlfaCode(b).getOrElse("")
    }
    }.trim
  }

  def translateAlfaToMorse(morse: String) = {
    morse.foldLeft("") { (a, b) => {
      a + repo.findMorseCode(b.toString).getOrElse("") + " "
    }
    }.trim
  }

  def translateBinaryToMorse(binay: String) = {
    val values = getAnalysisValues(binay)
    val binaryChars = binay.toCharArray
    var i = 0
    var countOne = 0
    var countZero = 0
    var morse = ""
    while (i < binaryChars.length) {
      if (binaryChars(i) == '1') {
        countOne += 1
        if (i != 0 && binaryChars(i - 1) == '0') {
          morse += getEspacioOrFin(countZero, values._2, values._3)
          countZero = 0
        }
        //si es el fin
        if (i == binaryChars.size - 1) {
          morse += getPuntoOrGuion(countOne, values._1)
        }
      } else {
        countZero += 1
        if (i != 0 && binaryChars(i - 1) == '1') {
          morse += getPuntoOrGuion(countOne, values._1)
          countOne = 0
        }
        //si es el fin
        if (i == binaryChars.size - 1) {
          morse += getEspacioOrFin(countZero, values._2, values._3)
        }
      }
      i += 1
    }
    morse.trim
  }

  private def getPuntoOrGuion(countOne: Int, promedio: Int): String = {
    if (countOne <= promedio)
      "."
    else
      "-"
  }

  /*letra:punto o guion <= promedio
    letra  > promedio y < que el mensaje de fin (maximo)
    fin de mensaje = maximo
  */
  private def getEspacioOrFin(countZero: Int, avgZero: Int, max: Int): String = {
    if (countZero <= avgZero) {
      ""
    } else if (countZero == max) {//fin del mensaje
      " .-.-.-"
    } else {
      " "
    }
  }

  def getAnalysisValues(binay: String) = {
    val binaryChars = binay.toCharArray
    var i = 0
    var countOne = 0
    var ocurrenciasOne = 0
    var countZero = 0
    var countBitsZero = 0
    var ocurrenciasZero = 0
    var countZeroMax = -1

    while (i < binaryChars.length) {
      if (binaryChars(i) == '1') {
        countOne += 1

        //si no es la primera pos y el anterior bit era un 0 contamos la ocurrencia
        if (i != 0 && binaryChars(i - 1) == '0') {
          ocurrenciasZero += 1
          if (countBitsZero >= countZeroMax) {
            countZeroMax = countBitsZero
          }
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

        if (i == (binaryChars.size - 1)) { //analizamos la ultima posicion
          ocurrenciasZero += 1
          if (countBitsZero >= countZeroMax)
            countZeroMax = countBitsZero

        }
      }
      i += 1
    }

    (countOne / ocurrenciasOne, countZero / ocurrenciasZero, countZeroMax)
  }


}
