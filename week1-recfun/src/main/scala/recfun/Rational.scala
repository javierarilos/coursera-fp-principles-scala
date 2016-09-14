package recfun

/**
  * Created by jarias on 08/09/16.
  */
class Rational(x: Int, y: Int) {
  def numer = x
  def denom = y

  def add(that: Rational): Rational =
    new Rational(
      numer * that.denom + that.numer * denom,
      denom * that.denom
    )

  override def toString: String = numer + "/" + denom

  def neg: Rational = new Rational(-numer, denom)

  def sub(that: Rational): Rational = add(that.neg)

  override def equals(obj: scala.Any): Boolean = {
    println("equals: " + this + "to:"+ obj)
    if (! obj.isInstanceOf[Rational]) false
    else {
      def aRational = obj.asInstanceOf[Rational]
      numer == aRational.numer && denom == aRational.denom
    }
  }

}
