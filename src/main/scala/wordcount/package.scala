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
