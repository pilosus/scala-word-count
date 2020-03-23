import org.scalatest._

class TestSpec extends FunSuite with DiagrammedAssertions {
  test("Test should start with t") {
    assert("Test".startsWith("T"))
  }
}
