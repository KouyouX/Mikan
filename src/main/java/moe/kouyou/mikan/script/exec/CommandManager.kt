package moe.kouyou.mikan.script.exec

import moe.kouyou.mikan.script.api.Executable

object CommandManager {
  @JvmStatic
  private val commands = hashMapOf<String, Executable>()
  
  @JvmStatic
  fun registerCommand(name: String, command: Executable) = commands.putIfAbsent(name ,command)
  
  @JvmStatic
  fun getCommand(name: String) = commands.getOrElse(name) {throw RuntimeException()}
  
  init{
    registerCommand("set") {}
    registerCommand("if") {}
    registerCommand("repeat") {}
  }
}