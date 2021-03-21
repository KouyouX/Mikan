package moe.kouyou.mikan.script.parse

import moe.kouyou.mikan.script.lexical.*

sealed class AstNode

class ProcedureNode(val name: Token, val code: BlockNode): AstNode()

abstract class StatementNode: AstNode()

class BlockNode(val statements: Array<StatementNode>): StatementNode()

class VarNode(val name: Token, val value: ExprNode): StatementNode()

class WhileNode(val condition: ExprNode, val code: BlockNode): StatementNode()

class RepeatNode(val count: ExprNode, val code: BlockNode): StatementNode()

class IfNode(val condition: ExprNode, val code: BlockNode): StatementNode()

class ExprNode(val operands: Array<AstNode>, val operators: Array<String>): AstNode()

class AtomNode(val value: Token): AstNode()

class CommandNode(val target: String, val args: Array<ExprNode>): StatementNode()