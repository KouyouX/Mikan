package moe.kouyou.mikan.core.parse

import moe.kouyou.mikan.core.api.ParserModule

object ParserModuleManager {
  @JvmStatic
  private val parsers = hashMapOf<String, ParserModule<*>>()
  
  @JvmStatic
  fun registerParser(name: String, parser: ParserModule<*>) {
    if (name in ReservedWord.allReservedWords) throw RuntimeException()
    parsers.putIfAbsent(name, parser)
  }
  
  @JvmStatic
  fun getParser(name: String) {
    if (name in ReservedWord.allReservedWords) throw RuntimeException()
    parsers.getOrElse(name) {throw RuntimeException()}
  }
  
}