package wordcount

import scala.io.StdIn
import scala.collection.mutable

object WordCount extends App {
  val usage =
    """
      | NAME
      |        wc - print newline, word, and byte counts for each file
      |
      | SYNOPSIS
      |        wordCount [OPTION] ... [FILE]
      |
      | DESCRIPTION
      |        wordCount - print newline, word, and byte counts for each FILE.
      |
      |        With no FILE, or when FILE is -, read standard input.
      |
      |        Options
      |         -c, --bytes
      |                print the byte counts
      |         -m, --chars
      |                print the character counts
      |         -l, --lines
      |                print the newline counts
      |         -w, --words
      |                print the word counts
      |
      |""".stripMargin

  def checkForHelp(): Unit = {
    if (input.flags("help")) {
      if (input.wrongArg != "") {
        Console.err.println(s"Wrong argument: ${input.wrongArg}")
        Console.err.println(usage)
        sys.exit(1)
      } else {
        Console.println(usage)
        sys.exit(0)
      }
    }
  }

  val input: ArgParser = new ArgParser().parse(args.toList)
  checkForHelp()

  var counter: mutable.LinkedHashMap[String, Int] = mutable.LinkedHashMap(
    "bytes" -> 0,
    "chars" -> 0,
    "words" -> 0,
    "lines" -> 0
  )

  var line = ""

  while ({ line = StdIn.readLine(); line != null }) {
    counter("bytes") += line.getBytes().length + 1 // add newline char
    counter("chars") += line.length
    counter("words") += line.split("\\W+").length
    counter("lines") += 1
  }

  val optedStats = counter.filter((e) => input.flags(e._1))
  Console.println(input)
  Console.println((optedStats.mkString("\t")))
}
