package moe.kouyou.mikan.script.lexical

class TokenStream(ctx: Array<Token>) {
  
  private val ctx: MutableList<Token> = ctx.toMutableList()
  
  fun peek(): Token {
    return ctx[0]
  }
  
  fun next(): Token {
    return ctx.removeAt(0)
  }
  
  fun nexts(count: Int): Array<Token> {
    return ctx.subList(0, count).toTypedArray()
  }
  
  fun push(tk: Token) {
    ctx.add(0, tk)
  }
  
}