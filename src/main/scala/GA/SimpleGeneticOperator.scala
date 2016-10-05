package GA

/**
  * Created by kimura-lab on 16/09/23.
  */
class SimpleGeneticOperator {
  //交叉確率
  val CROSSOVER_RATE = 0.5
  //個体数(偶数を設定する必要がある)
  val INDIVIDUAL_NUM = 8
  //個体長
  val INDIVIDUAL_LENGTH = 6
  //突然変異確率
  val MUTATION_RATE = 0.000001

  def execute(): Unit = {
    var total = 0.0
    var count = 1
    var population = init()
    var elite =new Individual(INDIVIDUAL_LENGTH)
    var pre =0.0
    while (true) {
      elite=population.getElite()
      var selected_population = Selection(population)
      var crossovered_population = Crossover(selected_population)
      var mutationed_population = Mutation(crossovered_population)
      population = mutationed_population
      if(pre!=population.getIndividualAverage()) {
        println(population.getElite().getValueAsInt())
        pre=population.getIndividualAverage()
      }
      //total=total+instance.getAverageOfIndividualValues(individuals)
      // println(total/count)
      //individuals.foreach(println)
      count = count + 1
      population.rewriteWorst(elite)
    }

  }

  //Individual_numに応じて集団を生成
  def init(): Population = {
    new Population(INDIVIDUAL_NUM, INDIVIDUAL_LENGTH)
  }

  def Selection(population: Population): Population = {

    var next_population = new Population(population.getSize(), INDIVIDUAL_LENGTH)
    //ルーレット選択
    //各個体の適応度が重複する場合、適応度がより大きい個体が生成された場合、どう処理されるのか
    def roulette_selection(): Population = {
      var total_fitness = population.getTotalFitness()
      var roulette = Math.random() * total_fitness//0~total_fitness
      var arrow = 0.0//
      var isEnded = false
      for (i <- 0 to INDIVIDUAL_NUM - 1) {
        roulette = Math.random() * total_fitness
        arrow = 0
        isEnded = false
        for (index <- 0 to INDIVIDUAL_NUM-1) {
          if (!isEnded) {
            arrow = arrow + population.getFitness(index)
            if (arrow > roulette) {
              next_population.setIndividualValue(i, population.getIndividual(index).getValue())
              isEnded = true
            }
          }
        }
      }
      next_population
    }
    def rank_selection(): Unit = {
    }
    def tournament_selection(): Unit = {
    }
    def saveElite(): Unit ={

    }
    roulette_selection()
  }

  def Crossover(population: Population): Population = {
    def one_point_crossover(): Population = {
      var index_pointer = 0
      while (index_pointer < INDIVIDUAL_NUM) {

        //交差点を設定
        var point = fanctions.random(INDIVIDUAL_LENGTH - 1) + 1

        //先頭から個体を選択
        var target1_index = index_pointer
        index_pointer = index_pointer + 1
        var target2_index = index_pointer
        index_pointer = index_pointer + 1
        var target_individual1 = population.getIndividual(target1_index).getValue()
        var target_individual2 = population.getIndividual(target2_index).getValue()
        //交差するかどうか
        var is_crossover = {
          if (CROSSOVER_RATE >= Math.random()) {
            true
          } else{
            false
          }
        }
        if (is_crossover) {
          //入れ替える(新しい個体の生成)
          var new_individual1 = target_individual1.substring(0, point) + target_individual2.substring(point, INDIVIDUAL_LENGTH)
          var new_individual2 = target_individual2.substring(0, point) + target_individual1.substring(point, INDIVIDUAL_LENGTH)
          population.setIndividualValue(target1_index,new_individual1)
          population.setIndividualValue(target2_index,new_individual2)
        }
      }
      population
    }
    def multi_porints_crossover(): Unit = {
    }
    def uniform_crossover(): Unit = {
    }

    one_point_crossover()
  }

  def Mutation(population: Population): Population= {
    var str_index = 1
    var begin_str = ""
    var end_str = ""
    var target_ch = ""
    var replased_ch = ""
    for (index <- 0 to INDIVIDUAL_NUM-1) {
      str_index = 1
      while (str_index <= INDIVIDUAL_LENGTH) {
        if (MUTATION_RATE >= Math.random()) {
          var target_str =population.getIndividual(index).getValue()
          begin_str = target_str.substring(0, str_index - 1)
          target_ch = String.valueOf(target_str.charAt(str_index - 1))
          end_str = target_str.substring(str_index, target_str.length)
          if (target_ch.equals("0")) replased_ch = "1"
          else replased_ch = "0"
          population.setIndividualValue(index,begin_str + replased_ch + end_str)
        }
        str_index = str_index + 1
      }
    }
    population
  }

  def getAverageOfIndividualValues(individuals: Array[String]): Double = {
    var total = 0.0
    individuals.foreach(individual => {
      total = total + Integer.parseInt(individual, 2).asInstanceOf[Double]
    })
    total / INDIVIDUAL_NUM.asInstanceOf[Double]
  }


  //forTest
  def fitness_function(p: Int): Unit = {

  }
}
