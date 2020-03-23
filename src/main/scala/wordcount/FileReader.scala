package wordcount

import scala.collection.mutable
import scala.io.{BufferedSource, Source, StdIn}
import java.io.{FileNotFoundException, IOException}
import util.control.Breaks._

class FileReader() {
  var stats: FileStatsType = mutable.LinkedHashMap()

  def initCounter(): mutable.LinkedHashMap[String, Int] = mutable.LinkedHashMap(
    "lines" -> 0,
    "words" -> 0,
    "chars" -> 0,
    "bytes" -> 0
  )

  def getFileStats(fileName: String): CounterMapType = {
    val counter                        = initCounter()
    val bufferedSource: BufferedSource = null

    try {
      val bufferedSource = Source.fromFile(fileName)
      for (line <- bufferedSource.getLines) {
        counter("lines") += 1
        counter("words") += line.split("\\W+").length
        counter("chars") += line.length
        counter("bytes") += line.getBytes().length + 1 // add newline char
      }
    } catch {
      case e: FileNotFoundException => {
        Console.err.println(s"$fileName not found")
        return null
      }
      case e: IOException => {
        Console.err.println(s"Error occurred while reading $fileName")
        return null
      }
    } finally {
      if (bufferedSource != null) bufferedSource.close()
    }
    counter
  }

  def getStdinStats: CounterMapType = {
    val counter = initCounter()
    var line    = ""

    while ({ line = StdIn.readLine(); line != null }) {
      counter("lines") += 1
      counter("words") += line.split("\\W+").length
      counter("chars") += line.length
      counter("bytes") += line.getBytes().length + 1
    }
    counter
  }

  def getStats(files: FileListType): FileStatsType = {
    val totals = initCounter()

    // TODO rewrite the Scala-way
    for (file <- files) {
      breakable {
        if (file == StdInFileName) {
          stats(file) = getStdinStats
        } else {
          val fileStats = getFileStats(file)
          if (fileStats == null) {
            break
          } else {
            stats(file) = fileStats
          }
        }

        totals("lines") += stats(file)("lines")
        totals("words") += stats(file)("words")
        totals("chars") += stats(file)("chars")
        totals("bytes") += stats(file)("bytes")
      }
    }

    stats(TotalsKey) = totals
    stats
  }
}
