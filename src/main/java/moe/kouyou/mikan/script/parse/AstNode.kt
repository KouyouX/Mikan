package moe.kouyou.mikan.script.parse

import moe.kouyou.mikan.script.lexical.Token

open class AstNode {
  
  class Root: AstNode() {
    val procedures = hashMapOf<String, Procedure>()
  }
  
  class Procedure(val name: Token, val code: Block): AstNode()
  
  abstract class Statement: AstNode()
  
  class Block(val statements: Array<Statement>)
  
  class SetVar(val name: Token, val value: Expression): Statement()
  
  class For(val condition: Expression): Statement()
  
  class If: AstNode()
  
  open class Expression: AstNode(){
    val operands = arrayListOf<Expression>()
    val operators = arrayListOf<Token>()
  }
  
  class Atom(val value: Token): Expression()
  
  abstract class Command: AstNode()
  
}
