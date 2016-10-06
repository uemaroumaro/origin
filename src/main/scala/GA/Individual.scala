package GA

/**
  * Created by kimura-lab on 16/09/30.
  */
class Individual(length: Int){
  var value=init()


  def init(): String ={
    setValue()
  }
  def setValue(): String ={
    var i = 0
    var individual = ""
    while (i < length) {
      individual = individual + String.valueOf((fanctions.random(2)))
      i = i + 1
    }
    individual
  }
  def setValue(value:String): Unit ={
    this.value= value
  }
  def getValue(): String ={
    value
  }
  def getValueAsInt(): Int ={
    Integer.parseInt(value, 2)
  }
  def getFitness(): Double ={
    FitnessFunction.getFanc()(getValueAsInt())
  }
}
