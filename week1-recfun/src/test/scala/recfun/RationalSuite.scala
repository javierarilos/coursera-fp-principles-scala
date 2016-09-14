package recfun

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class RationalSuite extends FunSuite {

  import recfun.Rational

  test("Rational.add 1/2 + 1/2 = 2/2") {
    val oneHalf = new Rational(1, 2)
    val twoHalfs = new Rational(4, 4)
    println(oneHalf + "+" + oneHalf + "=" + twoHalfs)
    assert(oneHalf.add(oneHalf).equals(twoHalfs))
  }

}
