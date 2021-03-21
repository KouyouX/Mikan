package moe.kouyou.mikan.script.parse

import moe.kouyou.mikan.script.api.Executable
import moe.kouyou.mikan.script.lexical.*

sealed class AstNode {
  
  class Procedure(val name: Token, val code: Block): AstNode()
  
  abstract class Statement: AstNode()
  
  class Block(val statements: Array<Statement>): Statement()
  
  class SetVar(val name: Token, val value: Expression): Statement()
  
  class While(val condition: Expression, val code: Block): Statement()
  
  class Repeat(val count: Expression, val code: Block): Statement()
  
  class If(val condition: Expression, val code: Block): Statement()
  
  class Expression(val operands: Array<AstNode>, val operators: Array<Token>): AstNode()
  
  class Atom(val value: Token): AstNode()
  
  class Command(val target: Executable, val args: Array<Expression>): Statement()
  
}
