package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  def isOdd = (x: Int) => x % 2 == 1

  test("contains: isOdd contains 1 is true") {
    assert(contains(isOdd, 1))
  }

  test("contains: isOdd contains 0 is true") {
    assert(! contains(isOdd, 0))
  }


  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s1to10 = (x: Int) => x > 0 && x < 11
    val isOdd = (x: Int) => x % 2 == 1

  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("intersect contains elements in both sets") {
    new TestSets {
      val s = intersect(s1to10, isOdd)
      assert(contains(s, 1), "1 should be an odd number between 1 and 10")
      assert(contains(s, 1), "7 should be an odd number between 1 and 10")
      assert(! contains(s, 6), "6 should NOT be an odd number between 1 and 10")
      assert(! contains(s, 11), "11 should NOT be an odd number between 1 and 10")
    }
  }

  test("diff contains elements in first set that are NOT in second set") {
    new TestSets {
      val evens1to10 = diff(s1to10, isOdd)
      assert(contains(evens1to10, 2), "2 should belong to evens 1 to 10")
      assert(contains(evens1to10, 8), "8 should belong to evens 1 to 10")
      assert(! contains(evens1to10, 11), "11 should not belong to evens 1 to 10")
      assert(! contains(evens1to10, 3), "3 should not belong to evens 1 to 10")
      assert(! contains(evens1to10, 12), "12 should not belong to evens 1 to 10")
    }
  }

  test("filter returns the subset of `s` for which `p` holds.") {
    new TestSets {
      val odds1to10 = filter(s1to10, isOdd)
      assert(! contains(odds1to10, 2), "2 is filtered from 1 to 10 by isOdd")
      assert(! contains(odds1to10, 8), "8 is filtered from 1 to 10 by isOdd")
      assert(! contains(odds1to10, 11), "11 is filtered from 1 to 10 by isOdd")
      assert(contains(odds1to10, 3), "3 is NOT filtered from 1 to 10 by isOdd")
      assert(! contains(odds1to10, 12), "12 is filtered from 1 to 10 by isOdd")
    }
  }

  test("forall Returns whether all bounded integers within `s` satisfy `p`.") {
    new TestSets {
      assert( ! forall(s1to10, isOdd))
      assert(forall(isOdd, isOdd))
      assert(forall(s1to10, s1to10))
    }
  }

  test("exists Returns whether there exists a bounded integer within `s` that satisfies `p`.") {
    new TestSets {
      assert(exists(s1to10, isOdd))
      assert(exists(isOdd, isOdd))
      assert( ! exists(s1to10, singletonSet(67)))
    }
  }


  test("map Returns a set transformed by applying `f` to each element of `s`.") {
    new TestSets {
      val plusOne = (x: Int) => x + 1

      val s2to11 = map(s1to10, plusOne)
      assert(contains(s2to11, 2))
      assert(contains(s2to11, 3))
      assert(contains(s2to11, 11))
      assert(! contains(s2to11, 1))
      assert(! contains(s2to11, 12))
      assert(! contains(s2to11, 128765))

    }
  }





}
