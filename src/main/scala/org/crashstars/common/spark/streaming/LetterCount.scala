package org.crashstars.common.spark.streaming

import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.Duration
import org.apache.spark.streaming.dstream.DStream

/**
 * Created by anavidad on 13/10/15.
 */
object LetterCount {

  val updateFunc = (values: Seq[Int], state: Option[Int]) => {
    val currentCount = values.sum
    val previousCount = state.getOrElse(0)
    Some(currentCount + previousCount)
  }

  def countLetter(queueDS: DStream[String])(handler: (RDD[(Char, Int)] => Unit)): Unit = {
    val key: DStream[(Char, Int)] = queueDS
      .flatMap(x => x.toCharArray)
      .map(letter => (letter, 1))
      .reduceByKey(_ + _)
      .updateStateByKey(updateFunc)
    key.foreachRDD(handler)
  }

  def countLetterByWindowsAndSlice(queueDS: DStream[String],
                                   windowDuration: Duration,
                                   slideDuration: Duration)
                                  (handler: (RDD[(Char, Int)] => Unit)): Unit = {
    val key: DStream[(Char, Int)] = queueDS
      .flatMap(x => x.toCharArray)
      .map(letter => (letter, 1))
      .reduceByKeyAndWindow(_ + _, _ - _, windowDuration, slideDuration)
    key.foreachRDD(handler)
  }

}
