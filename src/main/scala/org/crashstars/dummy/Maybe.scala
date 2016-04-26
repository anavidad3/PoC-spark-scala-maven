package org.crashstars.dummy

/**
 * Created by navidad on 9/11/15.
 */
sealed abstract class Maybe[+A] {
  def isEmpty: Boolean
  def get: A
  def getOrElse[B >: A](default: B): B = {
    if(isEmpty) default else get
  }

  def flatMap[A, B](xs: List[A])(f: A => List[B]) : List[B] = {
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
}


final case class Just[A](value: A) extends Maybe[A] {
  def isEmpty = false
  def get = value
}

//case object Nil extends Maybe[scala.Nothing] {
//  def isEmpty = true
//  def get = throw new NoSuchElementException("Nil.get")
//}