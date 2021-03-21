package moe.kouyou.mikan.script.exec

import moe.kouyou.mikan.script.lexical.TokenType
import moe.kouyou.mikan.script.parse.*

class Executor(private val procedure: ProcedureNode) {
  private val vars = hashMapOf<String, MValue>()
  
  fun exec() {
    execBlock(procedure.code)
  }
  
  private fun execBlock(node: BlockNode) {
    for (statement in node.statements) {
      when (statement) {
        is IfNode -> execIf(statement)
        is WhileNode -> execWhile(statement)
        is RepeatNode -> execRepeat(statement)
        is VarNode -> execVar(statement)
        is CommandNode -> execCommand(statement)
        else -> throw RuntimeException()
      }
    }
  }
  
  private fun execIf(node: IfNode) {
    if (evalExpression(node.condition) == MNumber.True) execBlock(node.code)
  }
  
  private fun execWhile(node: WhileNode) {
    while (evalExpression(node.condition) == MNumber.True) execBlock(node.code)
  }
  
  private fun execRepeat(node: RepeatNode) {
    var count = ((evalExpression(node.count) as MNumber).value as Double).toInt()
    while (count-- > 0) execBlock(node.code)
  }
  
  private fun execVar(node: VarNode) {
    vars[node.name.ctx] = evalExpression(node.value)
  }
  
  private fun execCommand(node: CommandNode) {
    val args = node.args.map(this::evalExpression).toTypedArray()
    CommandManager.getCommand(node.target).execute(args)
  }
  
  private fun evalExpression(node: ExprNode): MValue {
    var operand1 = if(node.operands[0] is AtomNode) evalAtom(node.operands[0] as AtomNode)
      else evalExpression(node.operands[0] as ExprNode)
    for (i in node.operators.indices) {
      val operand2 = if(node.operands[i + 1] is AtomNode) evalAtom(node.operands[i + 1] as AtomNode) as MNumber
      else evalExpression(node.operands[i + 1] as ExprNode) as MNumber
      operand1 = when(node.operators[i]){
        "+" -> operand1 as MNumber + operand2
        "-" -> operand1 as MNumber - operand2
        "*" -> operand1 as MNumber * operand2
        "/" -> operand1 as MNumber / operand2
        "%" -> operand1 as MNumber % operand2
        else -> throw RuntimeException()
      }
    }
    return operand1
  }
  
  private fun evalAtom(node: AtomNode): MValue {
    return when (node.value.type) {
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