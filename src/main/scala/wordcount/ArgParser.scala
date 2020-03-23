package wordcount

import scala.collection.mutable

class ArgParser {
  val SwitchPrefix      = '-'
  var files             = new FileListType()
  var wrongArg: String  = _
  var showHelp: Boolean = false
  var flags: ArgFlags = mutable.LinkedHashMap(
    "lines" -> false,
    "words" -> false,
    "chars" -> false,
    "bytes" -> false
  )

  override def toString: String = s"files: $files | flags: $flags"

  def isSwitch(s: String): Boolean = (s(0) == SwitchPrefix) && (s.length != 1)

  def appendStdinIfNoFiles(): Unit = if (files.isEmpty) files += StdInFileName

  def setDefaultFlagsIfOmitted(): Unit = {
    if (flags.forall(e => !e._2)) {
      for ((k, v) <- flags.filter(e => e._1 != "chars")) {
        flags(k) = true
      }
    }
  }

  def parse(args: List[String]): ArgParser = {
    args match {
      case Nil =>
        appendStdinIfNoFiles()
        setDefaultFlagsIfOmitted()
        this
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
        showHelp = true
        this
      case option :: tail if isSwitch(option) =>
        showHelp = true
        wrongArg = option
        parse(tail)
      case fileName :: tail =>
        files += fileName
        parse(tail)
    }
  }
}
