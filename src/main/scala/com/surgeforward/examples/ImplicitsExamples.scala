package com.surgeforward.examples

import scala.collection.mutable.ListBuffer


object ImplicitsExamples {

  case class Animal(name:String, fierceness:Int)

  implicit object fierceOrdering extends Ordering[Animal] {
    def compare(a:Animal, b:Animal) = a.fierceness - b.fierceness
  }

//  implicit object nameOrdering extends Ordering[Animal] {
//    def compare(a:Animal, b:Animal) = a.name.compareTo(b.name)
//  }


  /**
   * implicitly add mikeMap to list
   * mikeMap takes "converter" function from T=>R
   */
  implicit def MyImplicits[T](x:List[T]) = new {
    def mikeMap[R](conv: T=>R) : List[R] = {
      val buffer = new ListBuffer[R]
      for (item <- x) {
        buffer += conv(item)
      }
      buffer.toList
    }
  }


  def sortAListOfAnimals() {
    val animals = List(
      Animal("mouse", 1),
      Animal("tiger", 10),
      Animal("bobcat", 6),
      Animal("matt mackay", Int.MaxValue)
    )

    val fearsome = animals.sorted
    println(fearsome)

    val names3 = animals.mikeMap(x => {
      x.name
    })
    println(names3)

  }

  def main(args:Array[String]) {
    sortAListOfAnimals()
  }
}
