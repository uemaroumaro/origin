package SparkGA

/**
  * Created by kimura-lab on 16/09/30.
  */
object FitnessFunction extends Serializable {
  def getFanc(): (Int) => Double = {
    (p : Int) => {
      (Math.abs(p) / 256.0) * (1.0 - (Math.abs(p) / 256.0))
    }
  }
}
