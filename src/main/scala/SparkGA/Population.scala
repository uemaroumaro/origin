package SparkGA

/**
  * Created by kimura-lab on 16/09/30.
  */
class Population(size: Int, individual_length: Int){
  var population = init()

  def init(): Array[Individual] = {
    setPopulation()
  }

  def setPopulation(): Array[Individual] = {
    var population = new Array[Individual](size)
    var i = 0
    while (i < size) {
      population(i) = new Individual(individual_length)
      i = i + 1
    }
    population
  }
  def getSize(): Int = {
    size
  }

  def getIndividual(index: Int): Individual = {
    population(index)
  }

  def setIndividual(index: Int, individual: Individual): Unit = {
    population(index).setValue(individual.getValue())
  }

  def setIndividualValue(index: Int, str: String): Unit = {
    population(index).setValue(str)
  }


  def getFitness(index: Int): Double = {
    population(index).getFitness()
  }

  def getTotalFitness(): Double = {
    var total_fitness = 0.0
    //適応度の合計を求める
    population.foreach(individual => {
      total_fitness = total_fitness + individual.getFitness()
    })
    total_fitness
  }

  def getIndividualTotal(): Double = {
    var total = 0.0
    //適応度の合計を求める
    population.foreach(individual => {
      total = total + individual.getValueAsInt()
    })
    total
  }

  def getIndividualAverage(): Double = {
    getIndividualTotal() / size.asInstanceOf[Double]
  }

  def getAverage(): Double = {
    getTotalFitness() / size.asInstanceOf[Double]
  }

  def getElite(): Individual = {
    var max = 0.0
    var elite = new Individual(individual_length)
    population.foreach(individual => {
      if (max < individual.getFitness()) {
        max=individual.getFitness()
        elite = individual
      }
    })
    elite
  }

  def getWorst(): Double = {
    var min = 0.0
    population.foreach(individual => {
      if (min > individual.getFitness()) {
        min = individual.getFitness()
      }
    })
    min
  }

  def getWorstIndex(): Int = {
    var min = 999999999.9
    var i = 0
    var index = 0
    population.foreach(individual => {
      if (min > individual.getFitness()) {
        min =individual.getFitness()
        index=i
      }
      i=i+1
    })
    index
  }

  def rewriteWorst(individual: Individual) = {
    setIndividual(getWorstIndex(),individual)
  }

}
