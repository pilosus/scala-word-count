package wordcount

import scala.collection.mutable

class ArgParser {
  var files            = new mutable.ListBuffer[String]()
  var wrongArg: String = ""
  var flags: mutable.LinkedHashMap[String, Boolean] = mutable.LinkedHashMap(
    "bytes" -> false,
    "words" -> false,
    "chars" -> false,
    "lines" -> false,
    "help"  -> false
  )

  override def toString: String = s"files: $files | flags: $flags"

  def isSwitch(s: String): Boolean = (s(0) == '-') && (s.length != 1)

  def parse(args: List[String]): ArgParser = {
    args match {
      case Nil => this
      case ("-c" | "--bytes") :: tail =>
        flags("bytes") = true
        parse(tail)
      case ("-w" | "--words") :: tail =>
        flags("words") = true
        parse(tail)
      case ("-m" | "--chars") :: tail =>
        flags("chars") = true
        parse(tail)
      case ("-l" | "--lines") :: tail =>
        flags("lines") = true
        parse(tail)
      case ("-h" | "--help") :: _ =>
        flags("help") = true
        this
      case option :: tail if isSwitch(option) =>
        flags("help") = true
        wrongArg = option
        parse(tail)
      case fileName :: tail =>
        files += fileName
        parse(tail)
      case _ =>
        this
    }
  }
}
