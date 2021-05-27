package location

object Test {
  def main(args: Array[String]): Unit = {
    println(s"We are on line ${Macros.location.line}")
  }
}
