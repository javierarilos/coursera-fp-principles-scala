def factorial(x: Int): Int = x match {
  case 0 => 1
  case _ => x * factorial(x - 1)
}

val x = List.tabulate(10)(x => x)

x map factorial



