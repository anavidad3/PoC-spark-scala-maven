package org.crashstars.maps

/**
 * Created by anavidad on 13/10/15.
 */
object MapsUtils {

  val miles = "M"
  val kilometers = "K"

  /**
   * Distance between set of coordinates {@see org.crashstars.maps.Coordinate}
   * @param coordinates Set of {@see org.crashstars.maps.Coordinate}
   * @param unit {@see MapsUtils.miles, MapsUtils.kilometers}
   * @return
   */
  def calculateDistance(coordinates: Seq[Coordinate], unit: String = MapsUtils.miles): Double = {

    coordinates match {
      case head :: tail => {
        tail.foldLeft(head, 0.0d)((accum, point) => (point, accum._2 + distance(accum._1, point, unit)))._2
      }
      case Nil => 0.0
    }
  }

  /**
   * Distance between two {@see org.crashstars.maps.Coordinate}
   * @param point1
   * @param point2
   * @param unit
   */
  private def distance(point1: Coordinate, point2: Coordinate, unit: String = MapsUtils.miles): Double = {

    assert(point1 != null)
    assert(point2 != null)

    val theta = point1.longitude - point2.longitude;
    val distMiles = math.toDegrees(Math.acos(
      Math.sin(math.toRadians(point1.latitude)) *
        Math.sin(math.toRadians(point2.latitude)) +
        Math.cos(math.toRadians(point1.latitude)) *
          Math.cos(math.toRadians(point2.latitude)) *
          Math.cos(math.toRadians(theta)))) * 60 * 1.1515

    if (unit == MapsUtils.kilometers) {
      distMiles * 1.609344
    } else distMiles

  }

}
