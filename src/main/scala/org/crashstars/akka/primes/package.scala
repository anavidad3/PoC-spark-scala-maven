package org.crashstars.akka

/**
  * Created by navidad on 19/11/15.
  */
package object primes {

  case class NumberRangeMessage(startNumber: Long, endNumber: Long)

  case class Result(val result: Set[Long])

  case class AggregateResult(var result: scala.collection.mutable.Set[Long])

}
