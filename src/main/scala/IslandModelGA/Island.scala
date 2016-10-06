package IslandModelGA

import GA.{Individual, Population}

/**
  * Created by kimura-lab on 16/10/06.
  */
class Island(island_size: Int, population_size: Int, individual_length: Int) {
  var island = init()

  def init(): Array[Population] = {
    var island = new Array[Population](island_size)
    var i = 0
    while (i < island_size) {
      island(i) = new Population(population_size,individual_length)
      i = i + 1
    }
    island
  }
}
