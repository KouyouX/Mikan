package moe.kouyou.mikan.script.exec

import moe.kouyou.mikan.script.lexical.TokenType
import moe.kouyou.mikan.script.lexical.TokenType.*
import moe.kouyou.mikan.script.parse.AstNode
import moe.kouyou.mikan.script.exec.*

class Executor(val procedure: AstNode.Procedure) {
  val vars = hashMapOf<String, MValue>()
  
  fun exec() {
    execBlock(procedure.code)
  }
  
  private fun execBlock(node: AstNode.Block) {
    for (statement in node.statements) {
      when (statement) {
        is AstNode.If -> execIf(statement)
        is AstNode.While -> execWhile(statement)
        is AstNode.Repeat -> execRepeat(statement)
        is AstNode.SetVar -> execSetVar(statement)
        is AstNode.Command -> execCommand(statement)
        else -> throw RuntimeException()
      }
    }
  }
  
  private fun execIf(node: AstNode.If) {
    if (evalExpression(node.condition) == MNumber.True) execBlock(node.code)
  }
  
  private fun execWhile(node: AstNode.While) {
    while (evalExpression(node.condition) == MBoolean.True) execBlock(node.code)
  }
  
  private fun execRepeat(node: AstNode.Repeat) {
    var count = (evalExpression(node.count) as MNumber).value
    while (count-- > 0) execBlock(node.code)
  }
  
  private fun execSetVar(node: AstNode.SetVar) {
    vars[node.name.ctx] = evalExpression(node.value)
  }
  
  private fun execCommand(node: AstNode.Command) {
    val args = node.args.map(this::evalExpression).toTypedArray()
    node.target.execute(args)
  }
  
  private fun evalExpression(node: AstNode.Expression): MValue {
    if (node.operands.size == 1) return evalAtom(node.operands[0] as AstNode.Atom)
    var operand1 = evalExpression(node.operands[0] as AstNode.Expression) as MNumber
    for (i in 0..node.operators.size) {
      val operand2 = operand1 + evalExpression(node.operands[i] as AstNode.Expression) as MNumber
      operand1 = when(node.operators[i].ctx){
        "+" -> operand1 + operand2
        "-" -> operand1 - operand2
        "*" -> operand1 * operand2
        "/" -> operand1 / operand2
        "%" -> operand1 % operand2
        else -> throw RuntimeException()
      }
    }
    return operand1
  }
  
  private fun evalAtom(node: AstNode.Atom): MValue {
    when (node.value.type) {
      TokenType.Symbol -> vars.getOrElse(node.value.ctx) {throw RuntimeException()}
      //TokenType.Integer -> MNumber(node.value.ctx.toDouble())
      //TokenType.Float -> MFloat(node.value.ctx.toFloat())
      //TokenType.Boolean -> MBoolean.valueOf(node.value.ctx.toBoolean())
      TokenType.Number -> MNumber(node.value.ctx.toDouble())
      TokenType.String -> MString(node.value.ctx)
      else -> throw RuntimeException()
    }
  }
}