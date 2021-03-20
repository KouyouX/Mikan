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
  
  override fun parse(s: TokenStream): AstNode.SetVar {
    s.expect("set")
    val name = s.expectSymbol()
    s.expect("to")
    val expr = ExpressionParser.parse(s)
    return AstNode.SetVar(name, expr)
  }
  
}

object ExpressionParser: ParserModule() {
  
  override fun parse(s: TokenStream): AstNode.Expression {
    return levelAdd(s)
  }
  
  private fun levelAdd(s: TokenStream): AstNode.AddLvlExpression {
    val result = AstNode.AddLvlExpression()
    result.addOperand(levelMul(s))
    var op: Token?
    while (s.askForOperator().also {op = it} != null) {
      result.addOperator(op!!)
      result.addOperand(levelMul(s))
    }
    return result
  }
  
  private fun levelMul(s: TokenStream): AstNode.MulLvlExpression {
    val result = AstNode.MulLvlExpression()
    result.addOperand(Atom(s.next()))
    var op: Token?
    while (s.askForOperator().also {op = it} != null) {
      result.addOperator(op!!)
      result.addOperand(AstNode.Atom(s.next()))
    }
    return result
  }
  
}

abstract class CommandParser: ParserModule() {
  abstract val prefix: String
  abstract override fun parse(stream: TokenStream): AstNode.Command
}