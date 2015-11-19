package org.crashstars.akka.primes

import akka.actor.Actor
import org.crashstars.common.Logging

/**
 * Created by navidad on 19/11/15.
 */
class PrimeListener extends Actor with Logging {

  override def receive = {
    case AggregateResult(finalResult) => {
      logInfo(s"Distinct primes numbers found : ${finalResult.size}")
      logInfo(s"Sample take : ${finalResult.take(10)}")
      context.system.shutdown()
    }
  }
}
