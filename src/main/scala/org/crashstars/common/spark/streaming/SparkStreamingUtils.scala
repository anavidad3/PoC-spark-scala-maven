package org.crashstars.common.spark.streaming

import org.apache.spark.streaming.StreamingContextState._
import org.apache.spark.streaming.{Duration, Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}
import org.crashstars.common.Logging

/**
 * Created by anavidad on 13/10/15.
 */
class SparkStreamingUtils(conf: SparkConf, batchDuration: Duration = Seconds(1),
                          checkPointDir: String = "/tmp/D1F1CULT_R3P3T") extends Logging {

  def withSparkStreamingContext(testCode: (SparkContext, StreamingContext) => Any): Unit = {
    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(sc, batchDuration)
    ssc.checkpoint(checkPointDir)
    testCode(sc, ssc)
    if (ssc != null) {
      ssc.getState() match {
        case INITIALIZED =>
          logWarning("StreamingContext has not been started yet")
        case STOPPED =>
          logWarning("StreamingContext has already been stopped")
        case ACTIVE =>
          ssc.stop()
          logInfo("StreamingContext stopped successfully")
      }
    }
  }


}
