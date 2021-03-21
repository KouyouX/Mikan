package moe.kouyou.mikan.script.lexical

enum class TokenType {
  Symbol,
  Number,
  String,
  Edge,
  Operator,
  EOS,
}

class Token(val type: TokenType, val ctx: String)
