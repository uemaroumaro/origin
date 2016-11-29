package SparkGA

import GA.SimpleGeneticOperator
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by kimura-lab on 16/09/23.
  */

//テスト用
object SparkGA_main {
  def main(args: Array[String]): Unit = {
/*    val conf = new SparkConf()
    val sc = new SparkContext("local", "partition-example")
    try {
      val testRDD = sc.makeRDD(Seq(1, 2, 3, 4, 5, 6, 7, 8, 12345789))
      testRDD.collect().foreach(println)
    }finally {
      sc.stop()
    }*/
    var instance = new SparkGAExecuter()
    instance.execute()
  }
}
