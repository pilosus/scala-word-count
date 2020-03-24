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

object WordCount extends App {
  def checkForHelp(parsedArgs: ArgParser): Unit = {
    if (parsedArgs.showHelp) {
      if (parsedArgs.wrongArg != null) {
        Console.err.println(s"Wrong argument: ${input.wrongArg}")
        Console.err.println(HelpMessage)
        sys.exit(1)
      } else {
        Console.println(HelpMessage)
        sys.exit(0)
      }
    }
  }

  val input: ArgParser = new ArgParser().parse(args.toList)
  checkForHelp(input)

  val printer   = new StatsPrinter()
  val fileStats = new FileReader().getStats(input.files)
  printer.output(fileStats, input.flags)
}
