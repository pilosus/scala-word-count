import scala.collection.mutable

package object wordcount {
  val HelpMessage: String =
    """
      | NAME
      |       wc - print newline, word, and byte counts for each file
      |
      |SYNOPSIS
      |       wc [OPTION]... [FILE]...
      |
      |DESCRIPTION
      |       Print newline, word, and byte counts for each FILE, and a total line if more than one FILE is specified.  
      |       A word is a non-zero-length sequence of characters delimited by white space.
      |
      |       With no FILE, or when FILE is -, read standard input.
      |
      |       The options below may be used to select which counts are printed, always in the following order: 
      |       newline, word, character, byte.
      |
      |       -c, --bytes
      |              print the byte counts
      |
      |       -m, --chars
      |              print the character counts
      |
      |       -l, --lines
      |              print the newline counts
      |
      |       -w, --words
      |              print the word counts
      |
      |       -h, --help display this help and exit
      |
      |""".stripMargin
  val StdInFileName: String = "-"
  val TotalsKey: String     = "__totals__"
  type CounterMapType = mutable.LinkedHashMap[String, Int]
  type FileStatsType  = mutable.LinkedHashMap[String, CounterMapType]
  type FileListType   = mutable.ListBuffer[String]
  type ArgFlags       = mutable.LinkedHashMap[String, Boolean]
}
