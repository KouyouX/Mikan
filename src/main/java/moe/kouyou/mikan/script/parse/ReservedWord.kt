package moe.kouyou.mikan.script.parse

object ReservedWord {
  const val Procedure = "procedure"
  const val Var = "var"
  const val While = "while"
  const val Repeat = "repeat"
  const val If = "if"
  const val True = "true"
  const val False = "false"
  
  @JvmStatic
  val allReservedWords = arrayOf(Var, While, Repeat, If)
}