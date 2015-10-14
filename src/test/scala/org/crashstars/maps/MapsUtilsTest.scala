package org.crashstars.maps

import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

/**
 * Created by anavidad on 13/10/15.
 */
class MapsUtilsTest extends FlatSpec with Matchers with BeforeAndAfter {

  before {
    //Nothing right now
  }

  after {
    //Nothing right now
  }

  "Calculate distance between two coordinates [LONGITUDE, LATITUDE]" should "give the distance in Kilometers" in {
    val distance: Double = MapsUtils.calculateDistance(Seq(
      Coordinate(-8.570196, 41.159484),
      Coordinate(-8.570187, 41.158962)), MapsUtils.kilometers)
    distance should be(0.058045930626297544d) //Google maps show 57 meters

    val distance2: Double = MapsUtils.calculateDistance(Seq(
      Coordinate(-8.630739, 41.15825100000001),
      Coordinate(-8.632971000000001, 41.158575)), MapsUtils.kilometers)
    distance2 should be(0.19029049045190824d) //Google maps show 190 meters

  }

  "Calculate distance between two coordinates [LONGITUDE, LATITUDE]" should "give the distance in miles" in {
    val distance: Double = MapsUtils.calculateDistance(Seq(
      Coordinate(-8.570196, 41.159484),
      Coordinate(-8.570187, 41.158962)))
    distance should be(0.03606806911778808d)

    val distance2: Double = MapsUtils.calculateDistance(Seq(
      Coordinate(-8.630739, 41.15825100000001),
      Coordinate(-8.632971000000001, 41.158575)))
    distance2 should be(0.11824102892352924d)

  }
  it should "throw AssertionError if some coordinate in set is null" in {
    a[AssertionError] should be thrownBy {
      MapsUtils.calculateDistance(Seq(null, Coordinate(-8.570187, 41.158962)))
    }
    an[AssertionError] should be thrownBy {
      MapsUtils.calculateDistance(Seq(Coordinate(-8.570187, 41.158962), null))
    }
    an[AssertionError] should be thrownBy {
      MapsUtils.calculateDistance(Seq(null, null))
    }
  }

  "Calculate distance between three coordinates [LONGITUDE, LATITUDE] round trip" should "give the distance in " +
    "Kilometers" in {
    val distanceGoAndBack: Double = MapsUtils.calculateDistance(Seq(
      Coordinate(-8.570196, 41.159484),
      Coordinate(-8.570187, 41.158962),
      Coordinate(-8.570196, 41.159484)), MapsUtils.kilometers)
    distanceGoAndBack should be(2 * 0.058045930626297544d)

    val distanceGoAndBack2: Double = MapsUtils.calculateDistance(Seq(
      Coordinate(-8.630739, 41.15825100000001),
      Coordinate(-8.632971000000001, 41.158575),
      Coordinate(-8.630739, 41.15825100000001)), MapsUtils.kilometers)
    distanceGoAndBack2 should be(2 * 0.19029049045190824d)
  }

  "Calculate distance between three coordinates [LONGITUDE, LATITUDE] round trip" should "give the distance in " +
    "miles" in {
    val distanceGoAndBack: Double = MapsUtils.calculateDistance(Seq(
      Coordinate(-8.570196, 41.159484),
      Coordinate(-8.570187, 41.158962),
      Coordinate(-8.570196, 41.159484)))
    distanceGoAndBack should be(2 * 0.03606806911778808d)

    val distanceGoAndBack2: Double = MapsUtils.calculateDistance(Seq(
      Coordinate(-8.630739, 41.15825100000001),
      Coordinate(-8.632971000000001, 41.158575),
      Coordinate(-8.630739, 41.15825100000001)))
    distanceGoAndBack2 should be(2 * 0.11824102892352924d)
  }

}
