package org.crashstars.common

import java.util.Properties

import org.springframework.core.io.support.PathMatchingResourcePatternResolver
/**
 * Created by anavidad on 8/10/15.
 */

object ProjectProperties extends Properties with Logging {

  private val patternResolver = new PathMatchingResourcePatternResolver()
  private val mappingLocations = patternResolver.getResources("classpath*:properties/**/*.properties")

  private def fillProperties(): Unit = {
    logDebug("Initializing properties...")
    mappingLocations.foreach(x => {
      logDebug(s"File $x")
      load(x.getInputStream)
    })
  }

  fillProperties()

  def main (args: Array[String]) {
    println("hola")
  }

}
