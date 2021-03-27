package moe.kouyou.mikan.core.parse

import moe.kouyou.mikan.core.lexical.*


object Parser {
  fun parse(stream: TokenStream): Array<ProcedureNode> {
    val result = arrayListOf<ProcedureNode>()
    while (stream.hasMore()) result.add(ProcedureParser.parse(stream))
    return result.toTypedArray()
  }
}
