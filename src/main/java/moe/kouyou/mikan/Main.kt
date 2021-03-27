package moe.kouyou.mikan

import moe.kouyou.mikan.core.exec.Executer
import moe.kouyou.mikan.core.lexical.Lexer
import moe.kouyou.mikan.core.parse.Parser
import org.bukkit.plugin.java.JavaPlugin

fun main() {
  Executer(Parser.parse(Lexer.analyze("""
    procedure main{
      while(1) {
        print '红叶'
      }
    }
  """.trimIndent()))[0]).exec()
}


/**
 * Main class of Mikan.
 * @author Kouyou
 */
class Main: JavaPlugin() {
  
  override fun onEnable() {
  
  }
  
  override fun onDisable() {
  
  }
  
}