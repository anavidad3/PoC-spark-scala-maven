package org.crashstars.firststeps

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}
import org.crashstars.common.Logging
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfter, FlatSpec, Matchers}

/**
 * Created by navidad on 7/10/15.
 */
class FirstStepsTest extends FlatSpec with Matchers with BeforeAndAfter with BeforeAndAfterAll with Logging {

  val conf = new SparkConf(true)
    .setMaster("local[2]")
    .setAppName("CassandraManager")
  val sc = new SparkContext(conf)
  val sqlContext = new SQLContext(sc)

  override def beforeAll() = {
    //Nothing right now
  }

  override def afterAll() = {
    //Nothing right now
  }

  before {
    //Nothing right now
  }

  after {
    //Nothing right now
  }

  "Read file users.csv with Spark Context" should "read 6 users and 1 header row" in {
    logDebug("First test with Logging trait")
    val csvUserFile = getClass.getResource("/data/users.csv").getPath
    val fileRDD: RDD[String] = sc.textFile(csvUserFile)
    fileRDD.count() should be(7)

  }
  it should "throw NullPointerException if user dataframe is not initialized" in {
    a[NullPointerException] should be thrownBy {
      getClass.getResource("/dataWrongDirectory/users.csv").getPath
    }
  }



  "Read file users.csv with Spark CSV" should "read 6 users and discard header row" in {
    logDebug("Second test with Logging trait")
    val path = getClass.getResource("/data/users.csv").getPath
    val df = sqlContext.read.format("com.databricks.spark.csv")
      .option("header", "true")
      .option("delimiter", ";")
      .load(path)
    df.count() should be(6)
  }

}
