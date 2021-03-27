package moe.kouyou.mikan.core.lexical

enum class TokenType {
  Symbol,
  Number,
  String,
  Edge,
  Operator,
  EOS,
}

class Token(val type: TokenType, val ctx: String)
