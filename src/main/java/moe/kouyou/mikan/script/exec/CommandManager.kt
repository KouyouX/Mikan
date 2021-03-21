package moe.kouyou.mikan.script.exec

import moe.kouyou.mikan.script.api.Executable
import moe.kouyou.mikan.script.parse.ReservedWord

object CommandManager {
  @JvmStatic
  private val commands = hashMapOf<String, Executable>()
  
  const val commandNamePattern = "[a-zA-Z\$_][a-zA-Z0-9\$_]*"
  
  @JvmStatic
  fun registerCommand(name: String, command: Executable) {
    if (name in ReservedWord.allReservedWords) throw RuntimeException()
    if (!commandNamePattern.toRegex().matches(name)) throw RuntimeException()
    commands.putIfAbsent(name, command)
  }
  
  @JvmStatic
  fun getCommand(name: String): Executable {
    if (name in ReservedWord.allReservedWords) throw RuntimeException()
    return commands.getOrElse(name) {throw RuntimeException()}
  }
  
  init {
    registerCommand("void") {}
    registerCommand("print") {it.forEach {y -> println(y!!.value)}}
  }
}