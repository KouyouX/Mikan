package moe.kouyou.mikan.script.parse

import moe.kouyou.mikan.script.exec.MValue
import moe.kouyou.mikan.script.lexical.*

sealed class AstNode {
  
  class Root: AstNode() {
    val procedures = hashMapOf<String, Procedure>()
  }
  
  class Procedure(val name: Token, val code: Block): AstNode()
  
  abstract class Statement: AstNode()
  
  class Block(val statements: Array<Statement>)
  
  class SetVar(val name: Token, val value: Expression): Statement()
  
  class For(val condition: Expression, val code: Block): Statement()
  
  class If(val condition: Expression, val code: Block): Statement()
  
  sealed class Expression: AstNode() {
    abstract fun addOperand(operand: Expression)
    abstract fun addOperator(operator: Token)
    abstract fun getOperands(): List<Expression>
    abstract fun getOperators(): List<Token>
  }
  
  class AddLvlExpression: Expression() {
    private val operands = arrayListOf<MulLvlExpression>()
    private val operators = arrayListOf<Token>()
    override fun getOperands(): List<MulLvlExpression> = operands
    override fun getOperators(): List<Token> = operators
    
    override fun addOperand(operand: Expression) {
      if (operand is MulLvlExpression) operands.add(operand)
      else throw RuntimeException()
    }
    
    override fun addOperator(operator: Token) {
      if (operator.type != TokenType.Operator) throw RuntimeException()
      if (operator.ctx == "+" || operator.ctx == "-") operators.add(operator)
      else throw RuntimeException()
    }
  }
  
  class MulLvlExpression: Expression() {
    private val operands = arrayListOf<Atom>()
    private val operators = arrayListOf<Token>()
    override fun getOperands(): List<Atom> = operands
    override fun getOperators(): List<Token> = operators
    
    override fun addOperand(operand: Expression) {
      if (operand is Atom) operands.add(operand)
      else throw RuntimeException()
    }
    
    override fun addOperator(operator: Token) {
      if (operator.type != TokenType.Operator) throw RuntimeException()
      if (operator.ctx == "*" || operator.ctx == "/" || operator.ctx == "%") operators.add(operator)
      else throw RuntimeException()
    }
  }
  
  class Atom(val value: Token): Expression() {
    val type = value.type
    
    init {
      if (!(value.isSymbol() || value.isLiteral())) throw RuntimeException()
    }
    
    override fun addOperand(operands: Expression) = throw RuntimeException("not implemented")
    override fun addOperator(operator: Token) = throw RuntimeException("not implemented")
    override fun getOperands(): List<Expression> = throw RuntimeException("not implemented")
    override fun getOperators(): List<Token> = throw RuntimeException("not implemented")
  }
  
  abstract class Command: AstNode() {
    abstract val target: (Array<MValue>) -> MValue
    abstract val args: Array<Expression>
  }
  
}
