package SparkGA

/**
  * Created by kimura-lab on 16/10/24.
  */
object GeneticOperator extends Serializable {
  //交叉確率
  val CROSSOVER_RATE = 0.70
  //個体数(偶数を設定する必要がある)
  val INDIVIDUAL_NUM = 100
  //個体長
  val INDIVIDUAL_LENGTH = 8
  //突然変異確率
  val MUTATION_RATE = 0.001
  //移住間隔
  val IMMIGRATION_INTERVAL = 1000
  //移住間隔
  val IMMIGRATION_TIMES = 2
  //島の数
  val ISLAND_NUM = 5
  //移住方法
  val IMMIGRATION_WAY = ""
  //エリートの数
  val ELITE_NUM = 1
  //トーナメントサイズ
  val TOURNAMENT_SIZE = 2

  def Selection(islandData: Seq[String]): Seq[String] = {

    def roulette_selection(): Seq[String] = {
      var selected_individuals = Seq.empty[String]
      var total_fitness = 0.0
      islandData.foreach(individual => {
        total_fitness = total_fitness + FitnessFunction.getFanc()(Integer.parseInt(individual, 2))
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
      selected_individuals
    }
    /*def tournament_selection(): Seq[String] = {
      var selected_individuals = Seq.empty[String]
      var picked_individuals = Seq.empty[String]
      for (i <- 0 to INDIVIDUAL_NUM) {
        for (j <- 0 to TOURNAMENT_SIZE) {
          picked_individuals = picked_individuals :+ islandData(fanctions.random(INDIVIDUAL_NUM))
        }
      }
    }*/
    roulette_selection()
  }

  def Crossover(islandData: Seq[String]): Seq[String] = {
    def one_point_crossover(): Seq[String] = {
      var crossovered_individuals = Seq.empty[String]
      var index_pointer = 0

      while (index_pointer < INDIVIDUAL_NUM) {
        //交差点を設定
        var point = fanctions.random(INDIVIDUAL_LENGTH - 1) + 1

        //先頭から個体を選択
        var target1_index = index_pointer
        index_pointer = index_pointer + 1
        var target2_index = index_pointer
        index_pointer = index_pointer + 1
        var target_individual1 = islandData(target1_index)
        var target_individual2 = islandData(target2_index)
        //交差するかどうか
        val is_crossover = {
          if (CROSSOVER_RATE >= Math.random()) {
            true
          } else {
            false
          }
        }
        if (is_crossover) {
          //入れ替える(新しい個体の生成)
          var new_individual1 = target_individual1.substring(0, point) + target_individual2.substring(point, INDIVIDUAL_LENGTH)
          var new_individual2 = target_individual2.substring(0, point) + target_individual1.substring(point, INDIVIDUAL_LENGTH)
          crossovered_individuals = crossovered_individuals :+ new_individual1 :+ new_individual2
        } else {
          crossovered_individuals = crossovered_individuals :+ target_individual1 :+ target_individual2
        }
      }
      crossovered_individuals
    }
    one_point_crossover()
  }

  def Mutation(islandData: Seq[String]): Seq[String] = {

    var mutationed_individuals = Seq.empty[String]
    var str_index = 1
    var begin_str = ""
    var end_str = ""
    var target_ch = ""
    var replased_ch = ""
    for (index <- 0 to INDIVIDUAL_NUM - 1) {
      str_index = 1
      var target_str = islandData(index)
      while (str_index <= INDIVIDUAL_LENGTH) {
        if (MUTATION_RATE >= Math.random()) {
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
    mutationed_individuals
  }
}
