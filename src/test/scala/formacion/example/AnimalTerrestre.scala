package formacion.example

import org.crashstars.common.Logging


trait AnimalTerrestre extends Logging {

  def anda(animal: Animal) = logDebug(s"Anda $animal")
}
