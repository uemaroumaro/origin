package SparkGA

import IslandModelGA.{Island, IslandModelGenericOperator}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by kimura-lab on 16/09/23.
  */
class SparkGAExecuter {


  val conf = new SparkConf()
  val sc = new SparkContext("yarn-cluster", "SparkGA")

  /* def FitnessFunction(p:Int): Int = {
       31*p-p*p
   }*/
  //個体長INDIVIDUAL_NUの個体を一つ生成
  def getIndividual(): String = {
    var i = 0
    var individual = ""
    while (i < GeneticOperator.INDIVIDUAL_LENGTH) {
      individual = individual + String.valueOf((fanctions.random(2)))
      i = i + 1
    }
    individual
  }

  //個体数INDIVIDUAL_NUMの個体の集団を返す
  def init(): Seq[String] = {
    var individuals = Seq(getIndividual())
    for (i <- 1 to GeneticOperator.INDIVIDUAL_NUM - 1) {
      individuals = getIndividual() +: individuals
    }
    individuals
  }

  def makeIslands(): Seq[Seq[String]] = {
    var islands = Seq(init())
    for (i <- 1 to GeneticOperator.ISLAND_NUM - 1) {
      islands = init() +: islands
    }
    islands
  }


  def Immigration(): Unit = {

  }

  def map_Selection(island: Island): Island = {
    var i = 0
    var next_island = new Island(GeneticOperator.ISLAND_NUM, GeneticOperator.INDIVIDUAL_NUM, GeneticOperator.INDIVIDUAL_LENGTH)
    while (i < GeneticOperator.IMMIGRATION_INTERVAL) {
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

  def execute(): Unit = {

      val start = System.currentTimeMillis()
    try {
      var IslandsRDD = sc.makeRDD(makeIslands(), GeneticOperator.ISLAND_NUM)
      for (k <- 1 to GeneticOperator.IMMIGRATION_TIMES) {
        val IslandsRDD2 = IslandsRDD.map(island => {
          var islandData = island
          for (i <- 0 to GeneticOperator.IMMIGRATION_INTERVAL) {
            //エリート保存
            var elite_candidates = islandData.sortBy(value => FitnessFunction.getFanc()(Integer.parseInt(value, 2)))
            //選択
            islandData = GeneticOperator.Selection(islandData)
            //交叉
            islandData = GeneticOperator.Crossover(islandData)
            //突然変異
            islandData = GeneticOperator.Mutation(islandData)
            //エリート戻し
            islandData = islandData.sortBy(value => FitnessFunction.getFanc()(Integer.parseInt(value, 2)))
            for (i <- 0 to GeneticOperator.ELITE_NUM - 1) {
              islandData = islandData.updated(i, elite_candidates((elite_candidates.length - 1) - i))
            }
          }
          islandData
        })
        //移住(移住割合の実装方法は考えるべき)

        val IslandsRDD3 = IslandsRDD2.mapPartitionsWithIndex((pno, partition) => partition.map(island => {
          pno -> island.take(GeneticOperator.INDIVIDUAL_NUM * 2 / 3)
        }))
        val IslandsRDD4 = IslandsRDD2.mapPartitionsWithIndex((pno, partition) => partition.map(island => {
          if ((pno + 1) < GeneticOperator.ISLAND_NUM) {
            (pno + 1) -> island.drop(GeneticOperator.INDIVIDUAL_NUM * 2 / 3)
          } else {
            0 -> island.drop(GeneticOperator.INDIVIDUAL_NUM * 2 / 3)
          }
        }))
        val islandsRDD5 = IslandsRDD3.cogroup(IslandsRDD4).mapPartitionsWithIndex((n, i) => i.map(s => {
          s._2._1.head
        } ++ {
          s._2._2.head
        }))
        IslandsRDD = islandsRDD5
      }

      //集計
      IslandsRDD = IslandsRDD.map(s => {
        s.map(value => Integer.parseInt(value, 2).toString)
      })
      IslandsRDD.glom().mapPartitionsWithIndex((n, i) => i.map(a => "pno[%d]: %s".format(n, a.mkString(", "))), true).collect.foreach(println)
    } finally {
      sc.stop()
    }
    println("実行時間 : " + (System.currentTimeMillis() - start) + "msec")
  }
}
