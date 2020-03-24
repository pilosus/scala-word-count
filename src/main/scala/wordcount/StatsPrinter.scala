// Copyright (C) 2020 the original author or authors.
// See the LICENCE.txt file distributed with this work for additional
// information regarding copyright ownership.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

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
