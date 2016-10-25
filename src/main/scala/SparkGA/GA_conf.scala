package SparkGA

/**
  * Created by kimura-lab on 16/10/24.
  */
object GA_conf extends Serializable{
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

}
