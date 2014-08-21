package com.surgeforward.examples

/**
 * Created by mnelson on 8/21/14.
 *
 */

trait Happy {
  def smile()
  def laugh() {
    println("Ha ha")
  }
}

trait Sad {
  var timesCried = 0
  def cry(): Unit = {
    println("Boo hoo")
    timesCried += 1
  }
}

class Person extends Happy {
  override def smile(): Unit = {
    println("Not sure how to translate a smile to text")
  }
}

object TraitExamples {

  def main(args: Array[String]): Unit = {
    val me = new Person
    val you = new Person with Sad

    val us = List(me, you, 8.2)

    us.foreach {
      case s: Sad => s.cry()
      case h: Happy => h.smile()
      case x:Any => println("You are anything! %s".format(x))
      case _ => println("Uh, what are you?")
    }
  }

}
