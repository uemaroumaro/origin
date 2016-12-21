package SparkGA

/**
  * Created by kimura-lab on 16/09/30.
  */
object FitnessFunction extends Serializable {

  def summaryFitness(individual:String , fanc: String = "Rastrigin", mode: String= "indiv", val_length : Int = 10):  Double = {

    val values = parseList(individual,val_length)
      fanc match {
        case "Rastrigin" => rastriginFitness(values)
        case "Rosenbrock" => rosenbrockFitness(values)
        case _ => rastriginFitness(values)
      }
    }

  def rastriginFitness(values: List[Double]):Double = {
    val p =values.map(n => (n - 512) / 100)
      val d = p.length
      10 * d + p.map(n => n * n - 10 * Math.cos(2 * Math.PI * n)).sum
  }

  def rosenbrockFitness(values : List[Double]):Double = {
    val p =values.map(n => (n - 2048) / 1000)
      val d = p.length
      val x1 = p.apply(0)
      p.tail.map(n => 100 * Math.pow(x1 - Math.pow(n, 2), 2) + Math.pow(n - 1, 2)).sum
  }
  def parseList(individual: String, val_length: Int): List[Double] = {
    val div_num = individual.length / val_length
    var list = List.empty[Double]
    for (i <- 0 to div_num - 1) {
      list = list :+ Integer.parseInt(individual.substring(i * val_length, i * val_length + val_length), 2).toDouble
    }
    list
  }
}
