package org.crashstars.common

import org.scalatest.{BeforeAndAfterAll, BeforeAndAfter, Matchers, FlatSpec}

/**
 * Created by anavidad on 8/10/15.
 */
class PropertiesTest extends FlatSpec with Matchers with BeforeAndAfter with BeforeAndAfterAll with Logging  {

  "Loading properties from classpath properties directory" should "read 1 file" in {
    val property: String = ProjectProperties.getProperty("working.directory")
    val propertyXY: String = ProjectProperties.getProperty("spark.master")
    property should  be ("/tmp/PoC-spark-scala")
    propertyXY should be ("local[*]")
  }

}
