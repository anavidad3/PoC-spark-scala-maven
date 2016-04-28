package formacion.example

import org.crashstars.common.Logging


trait AnimalAcuatico extends Logging {

  def nadaCon[T <: Animal](animal: T): Unit = logInfo(s"$this nadando $animal")

  def nadaConPeroNoCon[T <: Animal](querido: T)(odiado: T): Unit = {
    logInfo(s"$this nadando con $querido pero no con $odiado")
  }

  val someInt: Int = 2.3 // Error: type mismatch;

  implicit def double2Int(d: Double): Int = d.toInt

  val someIntAgain : Int = 2.3 // Ok



}
