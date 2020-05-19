package services

import org.scalatest.{BeforeAndAfter, FunSuite}
import repositories.MorseRepository

class morseTest extends FunSuite with BeforeAndAfter {

  val repo = new MorseRepository
  val morseServiceInst = new MorseService(repo)

  test("Morse Service translateAlfaToMorse") {
    assert(morseServiceInst.translateAlfaToMorse("a") == ".-")
    assert(morseServiceInst.translateAlfaToMorse("hOla") == ".... --- .-.. .-")
    assert(morseServiceInst.translateAlfaToMorse("hola meli") == ".... --- .-.. .-  -- . .-.. ..")
  }

  test("Morse Service translateMorsetoAlfa") {
    assert(morseServiceInst.translateMorsetoAlfa(".-") == "a")
    assert(morseServiceInst.translateMorsetoAlfa(".... --- .-.. .-") == "hola")
    assert(morseServiceInst.translateMorsetoAlfa(".... --- .-.. .-  -- . .-.. ..") == "hola meli")

  }

  test("Morse Service translateBinaryToMorse") {
    assert(morseServiceInst.translateBinaryToMorse("01110100000") == "-. .-.-.-")
    assert(morseServiceInst.translateBinaryToMorse("011101000000") == "-. .-.-.-")
    assert(morseServiceInst.translateBinaryToMorse("00111101101101110001100000000000") == "-..-. .-.-.-")
    assert(morseServiceInst.translateBinaryToMorse("0111010000000") == "-. .-.-.-")
    assert(morseServiceInst.translateBinaryToMorse("00010000") == ". .-.-.-")
    assert(morseServiceInst.translateBinaryToMorse("01110101110001100000") == "-.- . .-.-.-")
    assert(morseServiceInst.translateBinaryToMorse("01110011100101110001101110000") == "--.- .- .-.-.-")
    assert(morseServiceInst.translateBinaryToMorse("000000001101101100111000001111110001111110011111100000001110111111110111011100000001100011111100000111111001111110000000110000110111111110111011100000011011100000000000") == ".... --- .-.. .- -- . .-.. .. .-.-.-")
  }

  test("Test calculos de promedios y maximos") {
    assert(morseServiceInst.getAnalysisValues("01111011011111011") == (3, 1, 1))
    assert(morseServiceInst.getAnalysisValues("011110110111110") == (3, 1, 1))
    assert(morseServiceInst.getAnalysisValues("111100011001110011000000") == (2, 3, 6))
    assert(morseServiceInst.getAnalysisValues("000000111100011001110011") == (2, 3, 6))
    assert(morseServiceInst.getAnalysisValues("00110001100111001100000") == (2, 2, 5))
  }

}
