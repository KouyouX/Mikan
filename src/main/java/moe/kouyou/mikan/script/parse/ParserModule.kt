package moe.kouyou.mikan.script.parse

import moe.kouyou.mikan.script.lexical.*

abstract class ParserModule {
  
  abstract fun parse(stream: TokenStream): AstNode
  
}

object ProcedureParser: ParserModule() {
  
  override fun parse(stream: TokenStream): AstNode.Procedure {
    TODO()
  }
  
}

object SetVarParser: ParserModule() {
  
  override fun parse(s: TokenStream): AstNode.SetVar{
    s.expect("set")
    val name = s.expectSymbol()
    s.expect("to")
    val expr = ExpressionParser.parse(s)
    return AstNode.SetVar(name, expr)
  }
  
}

object ExpressionParser: ParserModule() {
  
  override fun parse(s: TokenStream): AstNode.Expression{
    return AstNode.Expression()
  }
  
  private fun levelAdd(s: TokenStream): AstNode.Expression{
    val result = AstNode.Expression()
    result.operands.add(levelMul(s))
    var op: Token?
    while(s.askFor("x").also {op = it} != null){
      result.operators.add(op!!)
      result.operands.add(levelMul(s))
    }
    return result
  }
  
  private fun levelMul(s: TokenStream): AstNode.Expression{
    val result = AstNode.Expression()
    result.operands.add(AstNode.Atom(s.expectAtom()))
    var op: Token?
    while(s.askForAtom().also {op = it} != null){
      result.operators.add(op!!)
      result.operands.add(AstNode.Atom(s.expectAtom()))
    }
    return result
  }
  
}

abstract class CommandParser: ParserModule() {
  abstract val name: String
  abstract override fun parse(stream: TokenStream): AstNode.Command
}