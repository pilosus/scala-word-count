import org.scalatest._
import wordcount._

class ArgParserSpec extends FlatSpec with Matchers {
  "An ArgParser" should "parse filenames" in {
    val args: Array[String]   = Array("file1", "file2", "file3")
    val parsedArgs: ArgParser = new ArgParser().parse(args.toList)

    parsedArgs.files(0) should be("file1")
    parsedArgs.files(1) should be("file2")
    parsedArgs.files(2) should be("file3")
  }

  it should "set stdin as default file denoted as `-` if no filenames given" in {
    val args: Array[String]   = Array()
    val parsedArgs: ArgParser = new ArgParser().parse(args.toList)
    parsedArgs.files(0) should be("-")
    parsedArgs.files.size should be(1)
  }

  it should "parse `-` as filename" in {
    val args: Array[String]   = Array("--option1", "-o2", "file1", "-", "file2")
    val parsedArgs: ArgParser = new ArgParser().parse(args.toList)
    parsedArgs.files(0) should be("file1")
    parsedArgs.files(1) should be("-")
    parsedArgs.files(2) should be("file2")
    parsedArgs.files.size should be(3)
  }

  it should "parse valid options in short and full formats" in {
    val args: Array[String]   = Array("--bytes", "-w", "file")
    val parsedArgs: ArgParser = new ArgParser().parse(args.toList)
    parsedArgs.files.size should be(1)
    parsedArgs.flags { "bytes" } should be(true)
    parsedArgs.flags { "words" } should be(true)
    parsedArgs.flags { "lines" } should be(false)
    parsedArgs.flags { "chars" } should be(false)
    parsedArgs.showHelp should be(false)
    parsedArgs.wrongArg should be(null)
  }

  it should "set lines, words, bytes flags true by default" in {
    val args: Array[String]   = Array()
    val parsedArgs: ArgParser = new ArgParser().parse(args.toList)
    parsedArgs.flags { "bytes" } should be(true)
    parsedArgs.flags { "words" } should be(true)
    parsedArgs.flags { "lines" } should be(true)
    parsedArgs.flags { "chars" } should be(false)
  }

  it should "set help flag and save wrong arg if invalid option given" in {
    val args: Array[String]   = Array("--wrongOption")
    val parsedArgs: ArgParser = new ArgParser().parse(args.toList)
    parsedArgs.showHelp should be(true)
    parsedArgs.wrongArg should be("--wrongOption")
  }

  it should "set help flag if the option is given, ignore other options after it" in {
    val args: Array[String]   = Array("--bytes", "--help", "file1", "file2")
    val parsedArgs: ArgParser = new ArgParser().parse(args.toList)
    parsedArgs.flags { "bytes" } should be(true)
    parsedArgs.showHelp should be(true)
    parsedArgs.wrongArg should be(null)
    parsedArgs.files.size should be(0)
  }
}
