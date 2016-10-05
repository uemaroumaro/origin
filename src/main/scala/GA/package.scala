import GA.{Individual, IslandModelGenericOperator, SimpleGeneticOperator,Population}

/**
  * Created by kimura-lab on 16/09/23.
  */

//テスト用
object GA_main {
  def main(args: Array[String]): Unit = {
    var instance = new SimpleGeneticOperator()
    var pp="aaa"
    val insta = new Population(8,8)
    insta.setIndividualValue(0,pp)

    insta.setIndividualValue(1,pp)
    insta.setIndividualValue(2,pp)
    insta.setIndividualValue(3,pp)
    print()
    instance.execute()
  }
}
