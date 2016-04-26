package org.crashstars.dummy

import org.crashstars.common.Logging
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfter, Matchers, FlatSpec}

import scala.annotation.tailrec

/**
 * Created by navidad on 6/11/15.
 */
class DummyTest extends FlatSpec with Matchers with BeforeAndAfter with BeforeAndAfterAll with Logging {

  "Dummy examples" should "not do nothing" in {
    val path: String = getClass.getClassLoader.getResource("data/users.csv").getPath
    val file = scala.io.Source.fromFile(path)
    file.getLines().flatMap(_.split(";")).foreach(println)
    println(sumX2(1, 2))

    val list: Array[Int] = Array(1, 2, 3, 4, 5, 6)
    for (number <- list; result = number * 10; if (result % 2 == 0)) println(result)
    val listMultiplyByTen = for (number <- list; result = number * 10; if (result % 2 == 0)) yield result
    println(listMultiplyByTen.mkString(","))
    listMultiplyByTen foreach println
    println(listMultiplyByTen.sortWith(_ > _).mkString(","))
    println(listMultiplyByTen.sorted.reverse.mkString(","))
    val hack = None
    println(sumAsDef(hack, hack))
    println(sumAsDef(Option(3), hack))
    val list1: List[String] = List("asd", "fgh", "jkl")

    flatMap(list1)(_.toList)
    flatMap(list1) {
      _.toList
    }

    singleExpression

  }

  val sumX2 = (a: Int, b: Int) => {
    val c = a + b
    c * 2
  }

  def sumAsDef(a: Option[Int], b: Option[Int]) = {
    a.getOrElse(0) + b.getOrElse(0)
  }

  def flatMap[A, B](xs: List[A])(f: A => List[B]): List[B] = {
    flatten(map(xs, f))
  }

  def flatten[B](xss: List[List[B]]): List[B] = {
    xss match {
      case List() => Nil
      case head :: tail => head ::: flatten(tail)
    }
  }

  def map[A, B](xs: List[A], f: A => B): List[B] = {
    xs match {
      case List() => Nil
      case head :: tail => f(head) :: map(tail, f)
    }
  }

  def flatten3[B](xss: List[List[B]]): List[B] = {
    @tailrec
    def _flatten3(oldList: List[List[B]], newList: List[B]): List[B] = oldList match {
      case List() => newList
      case head :: tail => _flatten3(tail, newList ::: head)
    }
    _flatten3(xss, Nil)
  }

  def exists[A](xs: List[A], e: A) =
    xs.foldLeft(false)((exist, ele) => exist || (ele == e))

  def singleExpression: List[String] => (List[Int], List[Int]) = {
    a => a map (_.toInt) partition (_ < 30)
  }

}
