package wordcount

class StatsPrinter {

  def outputLine(counterStats: CounterMapType, flags: ArgFlags, fileName: String): Unit = {
    var result: String = "\t"
    for ((key, value) <- counterStats) {
      if (flags(key)) result += s"$value\t"
    }
    result += fileName
    Console.println(result)
  }

  def output(stats: FileStatsType, flags: ArgFlags): Unit = {
    val fileStats = stats.filterKeys(_ != TotalsKey)
    val totals    = stats(TotalsKey)

    for ((key, value) <- fileStats) outputLine(counterStats = value, flags = flags, fileName = key)

    if (fileStats.size > 1) {
      outputLine(counterStats = totals, flags = flags, fileName = "totals")
    }
  }
}
