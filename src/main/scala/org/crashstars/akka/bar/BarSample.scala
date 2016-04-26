package org.crashstars.akka.bar

import akka.actor.{ActorSystem, ActorLogging, Actor, Props}

/**
 * Created by navidad on 20/11/15.
 */
case object Ticket
case object FullPint
case object EmptyPint

class BarTender extends Actor with ActorLogging {
  def receive = {
    case Ticket =>
      log.info("1 pint coming right up")
      Thread.sleep(1000)
      log.info("Your pint is ready, here you go")
      sender ! FullPint
    case EmptyPint =>
      log.info("I think you're done for the day")
      context.system.shutdown()
  }
}

class Person extends Actor with ActorLogging {
  def receive = {
    case FullPint =>
      log.info("I'll make short work of this")
      Thread.sleep(1000)
      log.info("I'm ready for the next")
      sender ! EmptyPint
  }
}

object BarSample extends App {

  val system = ActorSystem("BarSample-akka")
  val michael = system.actorOf(Props[BarTender], "michael")
  val sarah = system.actorOf(Props[Person], "sarah")

  michael.tell(Ticket, sarah)

  system.awaitTermination()
}
