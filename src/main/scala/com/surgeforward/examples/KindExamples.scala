package com.surgeforward.examples

/**
 * Created by mnelson on 8/21/14.
 *
 */

object KindExamples {

  /**
   * What if we have Ints or Floats, or MyCustomNumericClass?
   */
  object NotVeryUsefulStatistics {
    def median(xs: Vector[Double]): Double = xs(xs.size / 2)
    def quartiles(xs: Vector[Double]): (Double, Double, Double) =
      (xs(xs.size / 4), median(xs), xs(xs.size / 4 * 3))
    def iqr(xs: Vector[Double]): Double = quartiles(xs) match {
      case (lowerQuartile, _, upperQuartile) => upperQuartile - lowerQuartile
    }
    def mean(xs: Vector[Double]): Double = {
      xs.reduce(_ + _) / xs.size
    }
  }


  /**
   * Wouldn't it be nice if we had a common base class for Numbery things?
   */
  class Number
  object BetterButStillSuckyStatistics {
    def median(xs: Vector[Number]): Number = ???
    def quartiles(xs: Vector[Number]): (Number, Number, Number) = ???
    def iqr(xs: Vector[Number]): Number = ???
    def mean(xs: Vector[Number]): Number = ???
  }
  /**
   * Bad idea! why?
   *  - drop previously available type information
   *  - close our implementation to types whose sources we don't control
   */


  /**
   * Let's make a more generic implementation
   */
  object Math {
    trait NumberLike[T] {
      def plus(x: T, y: T): T
      def divide(x: T, y: Int): T
      def minus(x: T, y: T): T
    }
    object NumberLike {
      implicit object NumberLikeDouble extends NumberLike[Double] {
        def plus(x: Double, y: Double): Double = x + y
        def divide(x: Double, y: Int): Double = x / y
        def minus(x: Double, y: Double): Double = x - y
      }
      implicit object NumberLikeInt extends NumberLike[Int] {
        def plus(x: Int, y: Int): Int = x + y
        def divide(x: Int, y: Int): Int = x / y
        def minus(x: Int, y: Int): Int = x - y
      }
    }
  }

  object Statistics {
    import Math.NumberLike

    def median[T](xs: Vector[T]): T = xs(xs.size / 2)
    def quartiles[T : NumberLike](xs: Vector[T]): (T, T, T) =
      (xs(xs.size / 4), median(xs), xs(xs.size / 4 * 3))


    /**
     * A context bound T : NumberLike means that an implicit value of type NumberLike[T] must
     * be available, and so is really equivalent to having a second implicit parameter list with a NumberLike[T]
     */
    def iqr[T : NumberLike](xs: Vector[T]): T = quartiles(xs) match {
      case (lowerQuartile, _, upperQuartile) =>
        implicitly[NumberLike[T]].minus(upperQuartile,lowerQuartile)
    }
    def mean[T](xs: Vector[T])(implicit ev: NumberLike[T]): T =
      ev.divide(xs.reduce(ev.plus), xs.size)
  }


  def main(args: Array[String]): Unit = {
    val l1 = Vector[Int](1,2,3,4,5,6,7,8,9,10)
    val l2 = Vector[Double](0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9)

    println(Statistics.mean(l1))
    println(Statistics.mean(l2))
    println(Statistics.median(l1))
    println(Statistics.median(l2))
    println(Statistics.quartiles(l1))
    println(Statistics.quartiles(l2))
    println(Statistics.iqr(l1))
    println(Statistics.iqr(l2))
  }


}
