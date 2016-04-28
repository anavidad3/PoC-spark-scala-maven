package formacion.example

import formacion.example.Cocodrilo._


class Cocodrilo(name: String, numLegs: Int) extends Animal(name) with AnimalAcuatico
  with AnimalTerrestre with Asesino {

  def this() {
    this(Cocodrilo.defaultName, Cocodrilo.defaultNumLegs)
  }

  def this(name: String) = this(name, defaultNumLegs)

  override def asesina() : String = {
    _asesina
  }

  //Type inference
  private def _asesina = "Ahoga a su presa"
}

/**
  * Object is like a singleton.
  */
object Cocodrilo {
  val color: String = "Green"
  val defaultName = "Cocodrilo"
  val defaultNumLegs = 4

  // Factory pattern in Scala
  def apply(name: String, numLegs: Int): Cocodrilo = new Cocodrilo(name, numLegs)
}
