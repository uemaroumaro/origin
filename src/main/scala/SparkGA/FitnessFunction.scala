package SparkGA

/**
  * Created by kimura-lab on 16/09/30.
  */
object FitnessFunction extends Serializable{
  def getFanc(): (Int) => Int = {
    (p: Int) => {
      31*p-p*p
    }
  }
}
