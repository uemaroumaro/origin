package SparkGA

/**
  * Created by kimura-lab on 16/09/30.
  */
object FitnessFunction extends Serializable {
  def getFanc(): (Int) => Double = {
    (p : Int) => {
      p-500*Math.cos(p)+500
      //(Math.abs(p) / 256.0) * (1.0 - (Math.abs(p) / 256.0))
    }
  }
}
