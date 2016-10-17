package SparkGA

/**
  * Created by kimura-lab on 16/09/30.
  */
object fanctions {
  def random(max: Int): Int = {
    (Math.random() * max).asInstanceOf[Int]
  }
}
