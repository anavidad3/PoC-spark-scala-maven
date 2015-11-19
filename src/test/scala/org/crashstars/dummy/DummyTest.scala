package org.crashstars.dummy

import org.crashstars.common.Logging
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfter, Matchers, FlatSpec}

/**
 * Created by navidad on 6/11/15.
 */
class DummyTest extends FlatSpec with Matchers with BeforeAndAfter with BeforeAndAfterAll with Logging {

  "Dummy examples" should "not do nothing" in {
    val path: String = getClass.getClassLoader.getResource("data/users.csv").getPath
    val file = scala.io.Source.fromFile(path)
    file.getLines().flatMap(_.split(";")).foreach(println)
    println(sum(1, 2))

    Array(1,2)

    val list = Array(1,2,3,4,5,6)
    for(number <- list; result = number*10; if(result % 2 == 0)) println(result)
    val listMultiplyByTen = for(number <- list; result = number*10; if(result % 2 == 0)) yield result

    println(listMultiplyByTen.mkString(","))
    println(listMultiplyByTen.sortWith(_ > _).mkString(","))
    println(listMultiplyByTen.sorted.reverse.mkString(","))
    val hack = None
    println(sumAsDef(hack , hack))
    println(sumAsDef(Option(3), hack))
  }

  val sum = (a: Int, b: Int) => a + b
  def sumAsDef(a: Option[Int], b: Option[Int]) = {
    a.getOrElse(0) + b.getOrElse(0)
  }


}
