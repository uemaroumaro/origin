package IslandModelGA

import GA.SimpleGeneticOperator
import GA.Population
import IslandModelGA.Island

/**
  * Created by kimura-lab on 16/09/30.
  */
class IslandModelGenericOperator extends SimpleGeneticOperator {

  //交叉確率
  override val CROSSOVER_RATE = 0.59
  //個体数(偶数を設定する必要がある)
  override val INDIVIDUAL_NUM = 8
  //個体長
  override val INDIVIDUAL_LENGTH = 5
  //突然変異確率
  override val MUTATION_RATE = 0.0001
  //移住間隔
  val IMMIGRATION_INTERVAL = 5
  //島の数
  val ISLAND_NUM = 5
  //移住方法
  val IMMIGRATION_WAY = ""

  override def execute(): Unit = {
    var island = new Island(ISLAND_NUM, INDIVIDUAL_NUM, INDIVIDUAL_LENGTH)

  }

  def Immigration(): Unit = {

  }

  def map_Selection(island: Island): Island = {
    var i =0
    var next_island= new Island(ISLAND_NUM, INDIVIDUAL_NUM, INDIVIDUAL_LENGTH)
    while(i<IMMIGRATION_INTERVAL){
     // next_island_setPopulation(i)=Selection(island.getIsland(i))
    i=i+1
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
