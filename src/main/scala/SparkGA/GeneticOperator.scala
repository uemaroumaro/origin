package SparkGA

import IslandModelGA.{Island, IslandModelGenericOperator}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by kimura-lab on 16/09/23.
  */
class GeneticOperator {

  //交叉確率
  val CROSSOVER_RATE = 0.59
  //個体数(偶数を設定する必要がある)
  val INDIVIDUAL_NUM = 8
  //個体長
  val INDIVIDUAL_LENGTH = 5
  //突然変異確率
  val MUTATION_RATE = 0.0001
  //移住間隔
  val IMMIGRATION_INTERVAL = 5
  //島の数
  val ISLAND_NUM = 5
  //移住方法
  val IMMIGRATION_WAY = ""

  val conf = new SparkConf()
  val sc = new SparkContext("local", "partition-example")

  def execute(): Unit = {
    var individual = init()
    val populationRDD = sc.makeRDD(individual)
    populationRDD.collect.foreach(println)
  }

  //個体数INDIVIDUAL_NUMの個体の集団を返す
  def init(): Seq[String] = {
    var individuals = Seq(getIndividual())
    for (i <- 1 to INDIVIDUAL_NUM - 1) {
      individuals = individuals :+ getIndividual()
    }
    individuals
  }
//個体長INDIVIDUAL_NUの個体を一つ生成
  def getIndividual(): String = {
    var i = 0
    var individual = ""
    while (i < INDIVIDUAL_LENGTH) {
      individual = individual + String.valueOf((fanctions.random(2)))
      i = i + 1
    }
    individual
  }

  def Immigration(): Unit = {

  }

  def map_Selection(island: Island): Island = {
    var i = 0
    var next_island = new Island(ISLAND_NUM, INDIVIDUAL_NUM, INDIVIDUAL_LENGTH)
    while (i < IMMIGRATION_INTERVAL) {
      // next_island_setPopulation(i)=Selection(island.getIsland(i))
      i = i + 1
    }
    island

  }

  def map_Crossover(): Unit = {

  }

  def map_Mutation(): Unit = {

  }

  def getCurrentIndivisuals(): Unit = {

  }
}
