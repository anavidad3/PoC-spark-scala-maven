package org.crashstars.akka.primes

import akka.actor.{ActorRef, Props, ActorSystem}

/**
 * Created by navidad on 19/11/15.
 */
class PrimeCalculator {

  def calculate(start: Long, end: Long) = {
    val system = ActorSystem("PrimeCalculator")
    val listener = system.actorOf(Props[PrimeListener], "primeListener")
    val master = system.actorOf(Props(new PrimeMaster(listener, 20)), "primeMaster")
    master ! NumberRangeMessage(start, end)
  }

}

object PrimeCalculator {

  def main(args: Array[String]) {
    val primeCalculator = new PrimeCalculator
    primeCalculator.calculate(0,90000000)
  }
}
