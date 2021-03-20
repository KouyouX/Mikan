package moe.kouyou.mikan.script.lexical

object TokenPattern {
  val Symbol = "[A-Za-z\$_][A-za-z0-9\$_]*".toRegex()
  val Integer = "[1-9][0-9]*".toRegex()
  val Float = "[1-9][0-9]*\\.[0-9]+".toRegex()
  val Boolean = "true|false".toRegex()
  val String = "'[^']*'".toRegex()
  val Edge = "[(){}]".toRegex()
  val Operator = "\\+-\\*/%".toRegex()
}

enum class TokenType {
  Symbol,
  Integer,
  Float,
  Boolean,
  String,
  Edge,
  Operator,
}

class Token(val type: TokenType, val ctx: String)
