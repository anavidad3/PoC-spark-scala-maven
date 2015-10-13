package org.crashstars.common.spark

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}
import org.crashstars.common.Logging

/**
 * Created by anavidad on 13/10/15.
 */
class SparkUtils(conf: SparkConf) extends Logging {

  def withSparkContext(testCode: (SparkContext) => Any): Unit = {
    val sc = new SparkContext(conf)
    testCode(sc)
    if (sc != null) sc.stop()
  }

  def withSparkSQLContext(testCode: (SparkContext, SQLContext) => Any): Unit = {
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    testCode(sc, sqlContext)
    if (sc != null) sc.stop()
  }


}
