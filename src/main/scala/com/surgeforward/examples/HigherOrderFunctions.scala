package com.surgeforward.examples

import scala.collection.mutable.ListBuffer

/**
 * Created by mnelson on 8/21/14.
 *
 */
object HigherOrderFunctions {

  sealed trait Animal {
    def name: String
    override def toString = name
    def fuzzy = false
  }

  case class Lion(name: String) extends Animal
  case class Tiger(name: String) extends Animal
  case class Bear(name: String) extends Animal {
    override def fuzzy = true
  }


  def main(args:Array[String]): Unit = {
    val animals = List(
      Lion("Leo"), Tiger("Tommy"), Bear("Phillip")
    )

    val names = animals.filter(_.fuzzy).map(_.name)
    println(names)

    val names2 = animals.filter(a => a.fuzzy == true)
    println(names2)


    val byFuzzyness = animals
      .groupBy(a => a.fuzzy)
      .foreach { pair =>
        pair._1 match {
          case true => println("Fuzzies: " + pair._2 )
          case false => println("Not Fuzzies: " + pair._2 )
        }
      }
  }

}
