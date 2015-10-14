package org.crashstars.spark.streaming

import java.io.File

import org.apache.commons.io.FileUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.{ClockWrapper, SparkConf, SparkContext}
import org.crashstars.common.{Logging, ProjectProperties}
import org.scalatest.concurrent.Eventually
import org.scalatest.time.{Millis, Span}
import org.scalatest.{BeforeAndAfter, FlatSpec, GivenWhenThen, Matchers}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
 * Created by anavidad on 8/10/15.
 */
class SparkStreamingSuiteTest extends FlatSpec with Matchers with BeforeAndAfter with GivenWhenThen with
Eventually
with Logging {

  implicit override val patienceConfig = PatienceConfig(timeout = scaled(Span(3000, Millis)))

  private val master = ProjectProperties.getProperty("spark.master", "local[*]")
  private val appName = "SparkStreamingSuiteTest"
  private val batchDuration = Seconds(1)
  private val windowDuration = Seconds(4)
  private val slideDuration = Seconds(2)

  private val checkPointDir = new File(ProjectProperties.getProperty("working.directory") + "/test1" + "/checkpoint")

  private val numRddMock = 10
  private val conf = new SparkConf()
    .setMaster(master)
    .setAppName(appName)
    .set("spark.streaming.clock", "org.apache.spark.streaming.util.ManualClock")

  private val streamingUtils = new SparkStreamingUtils(conf, batchDuration, checkPointDir.getAbsolutePath)

  before {
    if (checkPointDir.exists()) FileUtils.forceDelete(checkPointDir)
    FileUtils.forceMkdir(checkPointDir)
  }

  after {
    //Nothing right now
  }

  "Sample set of words" should "be counted by letter" in {

    streamingUtils.withSparkStreamingContext { (sc, ssc) =>
      val clock = new ClockWrapper(ssc)
      val lines = mutable.Queue[RDD[String]]()
      var results = ListBuffer.empty[Array[(Char, Int)]]

      val queueDStream: InputDStream[String] = ssc.queueStream(lines)
      LetterCount.countLetter(queueDStream) {
        (rdd: RDD[(Char, Int)]) =>
          results += rdd.collect()
      }

      ssc.start()

      When(s"$numRddMock RDDs have been added")
      for (i <- 0 to numRddMock - 1) lines += generateMockRDD(sc)

      Then("letters counted after first batch")
      clock.advance(batchDuration.milliseconds)
      eventually {
        Array(
          Tuple2('a', 2),
          Tuple2('b', 2),
          Tuple2('c', 2)
        ).forall(results.last contains) should be(true)

        /* Another way to test if a set of elements is a subset from one concrete set*/
        List(
          Tuple2('a', 2),
          Tuple2('b', 2),
          Tuple2('c', 2)
        ).foldLeft(results.last.toSet)((acc, num) => acc - num).isEmpty should be(true)
      }

      Then("letters counted after second batch")
      clock.advance(batchDuration.milliseconds)
      eventually {
        Array(
          Tuple2('a', 4),
          Tuple2('b', 4),
          Tuple2('c', 4)
        ).forall(results.last contains) should be(true)
      }

      Then("letters counted after third batch")
      clock.advance(batchDuration.milliseconds)
      eventually {
        Array(
          Tuple2('a', 6),
          Tuple2('b', 6),
          Tuple2('c', 6)
        ).forall(results.last contains) should be(true)
      }
    }
  }



  s"Sample set of words with windows=$windowDuration, slice=$slideDuration time " +
    s"operations" should "be counted by letters" in {

    streamingUtils.withSparkStreamingContext { (sc, ssc) =>
      val clock = new ClockWrapper(ssc)
      val lines = mutable.Queue[RDD[String]]()
      var results = ListBuffer.empty[Array[(Char, Int)]]

      val queueDStream = ssc.queueStream(lines)
      LetterCount.countLetterByWindowsAndSlice(queueDStream, windowDuration, slideDuration) {
        (rdd: RDD[(Char, Int)]) =>
          results += rdd.collect()
      }

      ssc.start()

      Given(s"A window duration=$windowDuration and slideDuration=$slideDuration")

      When(s"$numRddMock RDDs have been added")
      for (i <- 0 to numRddMock - 1) lines += generateMockRDD(sc)

      Then("1 second -> Exception must be launch before first slide time match")
      clock.advance(batchDuration.milliseconds) //1 sec
      eventually {
        an[NoSuchElementException] should be thrownBy results.last
      }

      Then("2 seconds -> slide match with timeline")
      clock.advance(batchDuration.milliseconds) //2 sec
      eventually {
        Array(
          Tuple2('a', 4),
          Tuple2('b', 4),
          Tuple2('c', 4)
        ).forall(results.last contains) should be(true)
        List(
          Tuple2('a', 4),
          Tuple2('b', 4),
          Tuple2('c', 4)).foldLeft(results.last.toSet)((acc, num) => acc - num).isEmpty should be(true)
      }

      Then("3 seconds -> slide not match with timeline")
      clock.advance(batchDuration.milliseconds) //3 sec
      eventually {
        Array(
          Tuple2('a', 4),
          Tuple2('b', 4),
          Tuple2('c', 4)
        ).forall(results.last contains) should be(true)
      }

      Then("4 seconds -> slide match with timeline")
      clock.advance(batchDuration.milliseconds) //4 sec
      eventually {
        Array(
          Tuple2('a', 8),
          Tuple2('b', 8),
          Tuple2('c', 8)
        ).forall(results.last contains) should be(true)
      }

      Then("5 seconds -> slide not match with timeline")
      clock.advance(batchDuration.milliseconds) //5 sec
      eventually {
        Array(
          Tuple2('a', 8),
          Tuple2('b', 8),
          Tuple2('c', 8)
        ).forall(results.last contains) should be(true)
      }

      Then("6 seconds -> slide match with timeline")
      clock.advance(batchDuration.milliseconds) //6 sec
      eventually {
        Array(
          Tuple2('a', 8),
          Tuple2('b', 8),
          Tuple2('c', 8)
        ).forall(results.last contains) should be(true)
      }

      Then("10 seconds -> slide match with timeline")
      clock.advance(windowDuration.milliseconds) //10 sec
      eventually {
        Array(
          Tuple2('a', 8),
          Tuple2('b', 8),
          Tuple2('c', 8)
        ).forall(results.last contains) should be(true)
      }

      Then("11 seconds -> slide not match with timeline. Return same value that 10 seconds timeline")
      clock.advance(batchDuration.milliseconds) //11 sec
      eventually {
        Array(
          Tuple2('a', 8),
          Tuple2('b', 8),
          Tuple2('c', 8)
        ).forall(results.last contains) should be(true)
      }

      Then("12 seconds -> slide match with timeline. In last two seconds haven't have any input")
      clock.advance(batchDuration.milliseconds) // 12 sec
      eventually {
        Array(
          Tuple2('a', 4),
          Tuple2('b', 4),
          Tuple2('c', 4)
        ).forall(results.last contains) should be(true)
      }
    }
  }

  private def generateMockRDD(sc: SparkContext): RDD[String] = {
    sc.makeRDD(Seq("abc", "abc"))
  }


}
