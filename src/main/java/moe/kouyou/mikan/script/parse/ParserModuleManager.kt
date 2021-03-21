package moe.kouyou.mikan.script.parse

object ParserModuleManager {
  @JvmStatic
  private val parsers = hashMapOf<String, ParserModule>()
  
  @JvmStatic
  fun registerParser(name: String, parser: CommandParser) {
    if (name in ReservedWord.allReservedWords) throw RuntimeException()
    parsers.putIfAbsent(name, parser)
  }
  
  @JvmStatic
  fun getParser(name: String) {
    if (name in ReservedWord.allReservedWords) throw RuntimeException()
    parsers.getOrElse(name) {throw RuntimeException()}
  }
  
}