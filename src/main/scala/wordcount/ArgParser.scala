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
