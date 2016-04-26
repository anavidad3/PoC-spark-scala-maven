import akka.actor.{ActorLogging, Actor}

class BarTender extends Actor with ActorLogging {
  var total = 0

  def receive = {
    case Ticket(quantity) =>
      total = total + quantity

      log.info(s"I'll get $quantity pints for [${sender.path}]")

      for (number <- 1 to quantity) {
        log.info(s"Pint $number is coming right up for [${sender.path}]")

        Thread.sleep(1000)

        log.info(s"Pint $number is ready, here you go [${sender.path}]")

        sender ! FullPint(number)
      }

    case EmptyPint(number) =>
      total match {
        case 1 =>
          log.info("Ya'll drank those pints quick, time to close up shop")

          context.system.shutdown()

        case n =>
          total = total - 1

          log.info(s"You drank pint $number quick, but there are still $total pints left")
      }
  }
}