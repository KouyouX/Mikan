package moe.kouyou.mikan.script.lexical

class TokenStream(ctx: Array<Token>) {
  private val ctx: MutableList<Token> = ctx.toMutableList()
  
  fun peek() = ctx[0]
  fun peek(index: Int) = ctx[index]
  fun next() = ctx.removeAt(0)
  fun push(tk: Token) = ctx.add(0, tk)
  fun hasMore() = ctx.isNotEmpty()
}