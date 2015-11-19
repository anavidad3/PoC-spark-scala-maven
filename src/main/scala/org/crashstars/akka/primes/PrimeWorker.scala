package org.crashstars.akka.primes

import akka.actor.Actor
import org.crashstars.common.Logging

/**
 * Created by navidad on 19/11/15.
 */
class PrimeWorker extends Actor with Logging {

  override def receive = {

    case NumberRangeMessage(start, end) => {
      //logDebug(s"Number Range ${start} to ${end}")
      logDebug(s"Number Range ${start} to ${end}")
      val result = for (i <- start to end
                        if isPrime(i) ) yield i
      sender().!(Result(result.toSet))(context.parent)
    }
  }

  private def isPrime(n: Long) : Boolean = {
    if (n == 1 || n == 2 || n == 3) return true
    // Is n an even number?
    if (n % 2 == 0) return false

    //if not, then just check the odds
    var i = 1L
    do {
      i +=2
      if (n % i == 0) return false
    } while (i*i <= n)
    true
  }

}
