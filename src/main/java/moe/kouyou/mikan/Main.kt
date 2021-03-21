package moe.kouyou.mikan

import moe.kouyou.mikan.script.exec.Executor
import moe.kouyou.mikan.script.lexical.Lexer
import moe.kouyou.mikan.script.parse.Parser
import org.bukkit.plugin.java.JavaPlugin

fun main() {
  Executor(Parser.parse(Lexer.analyze("""
    procedure main{
      var a = 2 *124 *-1 + 1
      print a
      var asiofn89h2huifbasiuobf9dsbvuo2o3htf9asbgfbas897gh83b = 1
      print -3 *-3
      print '红叶'
      print 3 * 3 3 + 3 3 3+3 3*3
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