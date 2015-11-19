package org.crashstars.akka.primes

import akka.actor._
import akka.actor.Props
import akka.routing._
import akka.actor.Actor.Receive

/**
 * Created by navidad on 19/11/15.
 */
class PrimeMaster(listenerParam: ActorRef, numberOfWorkersParam: Int) extends Actor {

  private val numberOfWorkers = numberOfWorkersParam

  private val listener = listenerParam

  private var numberOfResults = 0

  private val finalResult = AggregateResult(scala.collection.mutable.Set.empty[Long])

  private val workerRouter = context.actorOf(Props[PrimeWorker].withRouter(
    RoundRobinRouter(nrOfInstances = numberOfWorkers)))


  override def receive = {
    case NumberRangeMessage(start, end) => {

      val segmentLength = (end - start) / numberOfWorkers
      for (i <- 0 to numberOfWorkers - 1) {
        val startPartial = start + (i * segmentLength)
        var endPartial = startPartial + segmentLength - 1
        if (i == numberOfWorkers - 1) endPartial = end
        workerRouter ! NumberRangeMessage(startPartial, endPartial)
      }

    }
    case Result(partialResultList) => {
      numberOfResults += 1
      finalResult.result = finalResult.result ++= partialResultList
      if(numberOfResults >= numberOfWorkers) {
        listener ! finalResult
        context stop self
      }
    }
  }
}
