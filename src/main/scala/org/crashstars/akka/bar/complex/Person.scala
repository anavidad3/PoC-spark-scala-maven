import akka.actor.{ActorLogging, Actor}

class Person extends Actor with ActorLogging {
  def receive = {
    case FullPint(number) =>
      log.info(s"I'll make short work of pint $number")

      Thread.sleep(1000)

      log.info(s"Done, here is the empty glass for pint $number")

      sender ! EmptyPint(number)
  }
}