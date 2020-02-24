import org.scalatest._

class HelloSpec extends FunSuite with DiagrammedAssertions {
  test("Test should start with t") {
    assert("Test".startsWith("T"))
  }
}
