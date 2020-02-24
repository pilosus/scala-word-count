package wordcount

import scala.io.StdIn

object WordCount extends App {
  val unknownOption = -1
  val usage =
    """
      | wordCount - print newline, word, and byte counts for standard input
      |
      | wordCount [OPTION] ... [stdin]
      |
      | Options
      |  -c, --bytes
      |         print the byte counts
      |  -m, --chars
      |         print the character counts
      |  -l, --lines
      |         print the newline counts
      |  -w, --words
      |         print the word counts
      |
      |""".stripMargin

  def parseOptions(
      result: List[Int],
      list: List[String],
      lines: Int,
      words: Int,
      chars: Int,
      bytes: Int
  ): List[Int] = {
    def isSwitch(s: String) = (s(0) == '-')
    list match {
      case Nil => result
      case ("-c" | "--bytes") :: tail =>
        parseOptions(result :+ bytes, tail, lines, words, chars, bytes)
      case ("-w" | "--words") :: tail =>
        parseOptions(result :+ words, tail, lines, words, chars, bytes)
      case ("-m" | "--chars") :: tail =>
        parseOptions(result :+ chars, tail, lines, words, chars, bytes)
      case ("-l" | "--lines") :: tail =>
        parseOptions(result :+ lines, tail, lines, words, chars, bytes)
      // case string :: optionMiddle :: tail if isSwitch(optionMiddle) => .. // filename as an argument before switch
      // case string :: Nil =>  ... // filename as the last argument
      case option :: tail => List(unknownOption)
    }
  }

  def printOptions(options: List[Int]): Unit = {
    options match {
      case usageFlag :: Nil => println(usage)
      case _                => println(options.mkString("\t"))
    }
  }

  var (linesCounter, wordsCounter, charsCounter, bytesCounter) = (0, 0, 0, 0)
  var line                                                     = ""

  while ({ line = StdIn.readLine(); line != null }) {
    linesCounter += 1
    wordsCounter += line.split("\\W+").length
    charsCounter += line.length
    bytesCounter += line.getBytes().length + 1 // add newline char
  }
  val options = Option(
    parseOptions(
      List(),
      args.toList,
      linesCounter,
      wordsCounter,
      charsCounter,
      bytesCounter
    )
  ).filter(_.nonEmpty)
    .getOrElse(List(linesCounter, wordsCounter, charsCounter, bytesCounter))
  printOptions(options)
}
