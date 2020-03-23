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
