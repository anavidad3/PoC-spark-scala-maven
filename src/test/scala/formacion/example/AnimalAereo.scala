package formacion.example

import org.crashstars.common.Logging


trait AnimalAereo extends Logging{

  def vuela(animal:Animal) :Unit = {
    logInfo(s"Volando $animal")
  }

}
