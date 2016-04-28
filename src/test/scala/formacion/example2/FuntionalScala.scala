package formacion.example2

import org.crashstars.common.Logging
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, FlatSpec, Matchers}

import scala.annotation.tailrec

/**
  * No side effects: Mutating variables, Print, Saving DB.
  * A function should do one thing, and only one thing. Same inputs, same results.
  * Return function an take function as parameter.
  */
class FuntionalScala extends FlatSpec with Matchers with BeforeAndAfter with BeforeAndAfterAll with Logging {

  val numIterations = 1000


  "This method" should "execute some funtional code" in {
    val fibonacci1: () => List[Int] = fibonacci(7)
    printlnListWithLogger(fibonacci1)

  }

  def binarySearchIterative(list: Array[Int], target: Int): Int = {
    var left = 0
    var right = list.length - 1
    while (left <= right) {
      val mid = left + (right - left) / 2
      if (list(mid) == target)
        return mid
      else if (list(mid) > target)
        right = mid - 1
      else
        left = mid + 1
    }
    -1
  }

  def binarySearchRecursive(list: Array[Int], target: Int)
                           (start: Int = 0, end: Int = list.length - 1): Int = {
    if (start > end) return -1
    val mid = start + (end - start + 1) / 2
    if (list(mid) == target)
      return mid
    else if (list(mid) > target)
      return binarySearchRecursive(list, target)(start, mid - 1)
    else
      return binarySearchRecursive(list, target)(mid + 1, end)
  }

  // Tail recursive
  def binarySearchFunctional(list: Array[Int], target: Int): Int = {
    @tailrec
    def _bsf(list: Array[Int], target: Int, start: Int, end: Int): Int = {
      if (start > end) return -1
      val mid = start + (end - start + 1) / 2
      list match {
        case (arr: Array[Int]) if (arr(mid) == target) => mid
        case (arr: Array[Int]) if (arr(mid) > target) => _bsf(list, target, start, mid - 1)
        case (arr: Array[Int]) if (arr(mid) < target) => _bsf(list, target, mid + 1, end)
      }
    }
    _bsf(list, target, 0, list.length - 1)
  }


  def fibonacci(sizeSeq: Int): () => List[Int] = {

    def fib(n: Int): Int = n match {
      case 0 | 1 => n
      case _ => fib(n - 1) + fib(n - 2)
    }
    () => Stream range(1, sizeSeq) map(fib(_)) toList
  }

  def printlnListWithLogger(f: () => List[Int]): Unit = {
    logInfo(f() toString)
  }


}
