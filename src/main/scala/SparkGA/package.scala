package SparkGA

import GA.SimpleGeneticOperator

/**
  * Created by kimura-lab on 16/09/23.
  */

//テスト用
object SparkGA_main {
  def main(args: Array[String]): Unit = {
    var instance = new SparkGAExecuter()
    instance.execute()
  }
}
