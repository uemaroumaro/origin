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

  /* def FitnessFunction(p:Int): Int = {
       31*p-p*p
   }*/
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

  //個体数INDIVIDUAL_NUMの個体の集団を返す
  def init(): Seq[String] = {
    var individuals = Seq(getIndividual())
    for (i <- 1 to INDIVIDUAL_NUM - 1) {
      individuals = getIndividual() +: individuals
    }
    individuals
  }

  def makeIslands(): Seq[Seq[String]] = {
    var islands = Seq(init())
    for (i <- 1 to ISLAND_NUM - 1) {
      islands = init() +: islands
    }
    islands
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

  def execute(): Unit = {

    val IslandsRDD = sc.makeRDD(makeIslands(), ISLAND_NUM)
    val IslandsRDD2 = IslandsRDD.map(island => {
      var islandData = island
      for (i <- 0 to 100) {
        var elite_candidates = islandData.sortBy(value => FitnessFunction.getFanc()(Integer.parseInt(value, 2))).reverse


        var selected_individuals = Seq.empty[String]
        var total_fitness = 0.0
        islandData.foreach(individual => {
          total_fitness = total_fitness + Integer.parseInt(individual, 2)
        })
        var roulette = Math.random() * total_fitness //0~total_fitness
        var roulette_val = 0.0 //
        var isEnded = false
        islandData.foreach(individual => {
          //矢を射抜く処理
          roulette = Math.random() * total_fitness
          //矢がどこに当たったか割り出す変数
          roulette_val = 0.0
          isEnded = false
          islandData.foreach(individual => {
            if (!isEnded) {
              roulette_val = roulette_val + FitnessFunction.getFanc()(Integer.parseInt(individual, 2))
              //roulette_val = roulette_val + (Integer.parseInt(individual, 2)*31-Integer.parseInt(individual, 2)*Integer.parseInt(individual, 2))
              if (roulette_val > roulette) {
                selected_individuals = selected_individuals :+ individual
                isEnded = true
              }
            }
          })
        })
        islandData = selected_individuals
        var crossovered_individuals = Seq.empty[String]
        var index_pointer = 0

        while (index_pointer < GA_conf.INDIVIDUAL_NUM) {
          //交差点を設定
          var point = fanctions.random(GA_conf.INDIVIDUAL_LENGTH - 1) + 1

          //先頭から個体を選択
          var target1_index = index_pointer
          index_pointer = index_pointer + 1
          var target2_index = index_pointer
          index_pointer = index_pointer + 1
          var target_individual1 = islandData(target1_index)
          var target_individual2 = islandData(target2_index)
          //交差するかどうか
          val is_crossover = {
            if (GA_conf.CROSSOVER_RATE >= Math.random()) {
              true
            } else {
              false
            }
          }
          if (is_crossover) {
            //入れ替える(新しい個体の生成)
            var new_individual1 = target_individual1.substring(0, point) + target_individual2.substring(point, GA_conf.INDIVIDUAL_LENGTH)
            var new_individual2 = target_individual2.substring(0, point) + target_individual1.substring(point, GA_conf.INDIVIDUAL_LENGTH)
            crossovered_individuals = crossovered_individuals :+ new_individual1 :+ new_individual2
          } else {
            crossovered_individuals = crossovered_individuals :+ target_individual1 :+ target_individual2
          }
        }

        islandData = crossovered_individuals

        var mutationed_individuals = Seq.empty[String]
        var str_index = 1
        var begin_str = ""
        var end_str = ""
        var target_ch = ""
        var replased_ch = ""
        for (index <- 0 to GA_conf.INDIVIDUAL_NUM - 1) {
          str_index = 1
          var target_str = islandData(index)
          while (str_index <= GA_conf.INDIVIDUAL_LENGTH) {
            if (GA_conf.MUTATION_RATE >= Math.random()) {
              begin_str = target_str.substring(0, str_index - 1)
              target_ch = String.valueOf(target_str.charAt(str_index - 1))
              end_str = target_str.substring(str_index, target_str.length)
              if (target_ch.equals("0")) replased_ch = "1"
              else replased_ch = "0"
              target_str = begin_str + replased_ch + end_str
            }
            str_index = str_index + 1
          }
          mutationed_individuals = mutationed_individuals :+ target_str
        }
        islandData = mutationed_individuals
        //eri-tomodosi
        islandData = islandData.sortBy(value => FitnessFunction.getFanc()(Integer.parseInt(value, 2)))
        for (i <- 0 to GA_conf.ELITE_NUM) {
          islandData = islandData.updated(i, elite_candidates((elite_candidates.length - 1) - i))
        }
      }

    })
    IslandsRDD.collect().foreach(println)
    IslandsRDD2.collect().foreach(println)
  }
}
