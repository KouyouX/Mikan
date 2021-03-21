package moe.kouyou.mikan

import moe.kouyou.mikan.script.exec.Executor
import moe.kouyou.mikan.script.lexical.Lexer
import moe.kouyou.mikan.script.parse.Parser
import org.bukkit.plugin.java.JavaPlugin

fun main() {
  Executor(Parser.parse(Lexer.analyze("""
    procedure main{
      set a = 1
      print '红叶'
      if (a) {
        print a
      }
      repeat(3) {
        print a
      }
    }
  """.trimIndent()))[0]).exec()
}


/**
 * Main class of Mikan.
 * @author Kouyou
 */
class Main: JavaPlugin(){
  
  override fun onEnable(){
  
  }
  
  override fun onDisable() {
  
  }
  
}