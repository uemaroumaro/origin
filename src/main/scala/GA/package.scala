import IslandModelGA.IslandModelGenericOperator
import GA.{Individual, Population, SimpleGeneticOperator}

/**
  * Created by kimura-lab on 16/09/23.
  */

//テスト用
object GA_main {
  def main(args: Array[String]): Unit = {
    var instance = new IslandModelGenericOperator()
    instance.execute()
  }
}
