package formacion.example

import org.crashstars.common.Logging
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, FlatSpec, Matchers}


class AnimalTest extends FlatSpec with Matchers with BeforeAndAfter with BeforeAndAfterAll with Logging {

  "This test" should "show some actions from a crocodile" in {
    val coco = Cocodrilo("coco", 2)
    val coco2 = new Cocodrilo("coco2")
    val coco3 = new Cocodrilo("coco3")

    val asesina: String = coco.asesina()
    logDebug(s"Cocodrilo asesina así: $asesina")
    coco.anda(coco2)
    coco.nadaCon[Cocodrilo](coco2)
    coco.nadaCon(coco2)

    //Partially funtion and currying.
    coco.nadaConPeroNoCon(coco2)(coco3)
  }

}
