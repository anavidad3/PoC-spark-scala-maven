package formacion

import org.crashstars.common.Logging
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, FlatSpec, Matchers}

import scala.collection.immutable.IndexedSeq
import scala.util.Random


class Formacion1Scala extends FlatSpec with Matchers with BeforeAndAfter with BeforeAndAfterAll with Logging {

  val numIterations = 1000


  "This method" should "compute avg with for loop" in {
    499.0 shouldBe avg(numIterations)
  }

  def avg(numIterations: Integer): Double = {
    var result = 0L
    var count = 0
    for (i <- 0 until numIterations) {
      result += i
      count += 1
    }
    result / count
  }

  "This method" should "compute avg with while loop" in {
    499.0 shouldBe avgWithWhile(numIterations)
  }

  def avgWithWhile(numIterations: Integer): Double = {
    var result = 0L
    var count = 0
    while (count < numIterations) {
      result += count
      count += 1
    }
    result / count
  }

  "Pretty method" should "calculate avg" in {
    499.0 shouldBe prettyAvg(numIterations)
  }

  def prettyAvg(numIterations: Integer): Double = {
    val list = (0 until numIterations).toList
    list.sum / list.size
  }

  "This test" should "remove duplicate names and convert names to uppercase" in {
    val toSet = "A,Q,B,R,C,T,E,U,W,Y,I,O".split(",").toSet
    toSet should be(toUpperCaseAndRemoveDuplicate)
  }

  def toUpperCaseAndRemoveDuplicate: Set[String] = {
    val namesLowerCase = "a,b,c,a,b,c,q,w,e,r,t,y,u,i,o".split(",").toList
    val collect = namesLowerCase
      .map(name => name.toUpperCase)
      .toSet
    collect
  }

  "fill matrix" should "fill a matrix" in {
    val matrix = fillMatrix(2, 2)
    -1155099828 shouldBe matrix(0)(0)
    -836442134 shouldBe matrix(1)(1)
  }

  def generateArrayOfRandom(size: Int): Array[Int] = {
    val rand = new Random(3)
    val arrayRand: IndexedSeq[Int] = for( i <- 0 to size ) yield rand.nextInt()
    arrayRand.toArray
  }


  def fillMatrix(dimX: Int, dimY: Int): Array[Array[Int]] = {
    val rand = new Random(3)
    Array.fill(dimX, dimY)(rand.nextInt)
  }

  "fill cube" should "fill a cube" in {
    val cube = fillCube(3, 3, 3)
    -1155099828 shouldBe cube(0)(0)(0)
    285587229 shouldBe cube(1)(1)(1)
    -1975716582 shouldBe cube(2)(2)(2)
  }

  def fillCube(dimX: Int, dimY: Int, dimZ: Int = 10): Array[Array[Array[Int]]] = {
    val rand = new Random(3)
    val cube = Array.ofDim[Int](dimX, dimY, dimZ)
    for (i <- 0 until dimX; j <- 0 until dimY; k <- 0 until dimZ) {
      cube(i)(j)(k) = rand.nextInt
    }
    cube
  }

  "This test" should "not do anything" in {
    someOperationWithMap("o")
  }

  def someOperationWithMap(letterFilter: String): Unit = {
    val map = Map(("d", "Dog"), ("b", "Bird"), ("f", "Frog"), ("c", "Cat"))
    val mapFilter = map.filter(entry => entry._2.contains(letterFilter)) + ("h" -> "horse")
    logDebug(s"$mapFilter")
    val zebra: String = mapFilter.getOrElse("z", "ZZZZZebra")
    logWarning(s"¡¡¡¡A Zebra: $zebra!!!!")
  }

  /* PATTERN MACHING EXAMPLES */

  def toYesOrNo(choice: Int): String = choice match {
    case 1 | 2 | 3 => "yes"
    case 0 => "no"
    case _ => "error"
  }

  def isInstanceOf(obj: Any): Unit = {
    obj match {
      case "AQ_QA" => logDebug("Magic Word")
      case d: Double => logDebug(s"This is a double number:$d")
      case _: String => logDebug("This is a String")
      case list: List[_] =>
        logDebug("This is a List")
        logDebug(s"Print list:$list")
      case _ => logDebug("Not matching found")
    }
  }

  /**
    * This method is just one example.
    * A simple way to calculate factorial may be:
    * def fact(n:Int) = (1 to n).foldLeft(1) { (x,y) => x * y }
    */
  def fact(n: Int): Int = n match {
    case 0 => 1
    case n => n * fact(n - 1)
  }

  def length[A](list: List[A]): Int = list match {
    case _ :: tail => 1 + length(tail)
    case Nil => 0
  }


}
