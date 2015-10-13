package org.crashstars.firststeps

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.crashstars.common.spark.SparkUtils
import org.crashstars.common.{Logging, ProjectProperties}
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, FlatSpec, Matchers}

/**
 * Created by navidad on 7/10/15.
 */
class FirstStepsTest extends FlatSpec with Matchers with BeforeAndAfter with BeforeAndAfterAll with Logging {

  val conf = new SparkConf(true)
    .setMaster(ProjectProperties.getProperty("spark.master", "local[2]"))
    .setAppName("FirstStepsTest")
  val sparkUtils = new SparkUtils(conf)

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

  "Read file users.csv with Spark Context" should "read 7 users and 1 header row" in {
    sparkUtils.withSparkContext { (sc) =>
      logDebug("First test with Logging trait")
      val csvUserFile = getClass.getResource("/data/users.csv").getPath
      val fileRDD: RDD[String] = sc.textFile(csvUserFile)
      fileRDD.count() should be(8)

    }
  }
  it should "throw NullPointerException if user dataframe is not initialized" in {
    a[NullPointerException] should be thrownBy {
      getClass.getResource("/dataWrongDirectory/users.csv").getPath
    }
  }


  "Whitspace front and behind of semicolon separator in user.csv" should "be removed" in {
    sparkUtils.withSparkContext { (sc) =>
      val csvUserFile = getClass.getResource("/data/users.csv").getPath
      val rddWithoutWhitespaces = sc.textFile(csvUserFile)
        .map(_.split(";"))
        .map(x => x.map(_.trim))
        .map(_.mkString(";"))
      val collect: Array[String] = rddWithoutWhitespaces.collect()
      logDebug(s"USERS WITHOUT WHITESPACE")
      collect.foreach(logDebug(_))
      val withOutSpaces: Boolean = collect forall (x => !(x.contains(" ;") || x.contains("; ")))
      withOutSpaces should be(true)
    }
  }


  "Read file users.csv with Spark CSV" should "read 7 users and discard header row" in {
    sparkUtils.withSparkSQLContext { (sc, sqlContext) =>
      val path = getClass.getResource("/data/users.csv").getPath
      val df = sqlContext.read.format("com.databricks.spark.csv")
        .option("header", "true")
        .option("delimiter", ";")
        .load(path)
      df.count should be(7)
    }
  }

  "Read file users.csv with Spark CSV" should "read 7 users, discard header row and remove duplicate" in {
    sparkUtils.withSparkSQLContext { (sc, sqlContext) =>
      val path = getClass.getResource("/data/users.csv").getPath
      val df = sqlContext.read.format("com.databricks.spark.csv")
        .option("header", "true")
        .option("delimiter", ";")
        .load(path)
      val dfUsersDistinct: Long = df.distinct().count()
      dfUsersDistinct should be(6)
    }
  }

}
