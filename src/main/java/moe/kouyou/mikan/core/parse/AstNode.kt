package moe.kouyou.mikan.core.parse

import moe.kouyou.mikan.core.api.AstNode
import moe.kouyou.mikan.core.lexical.*

class ProcedureNode(val name: String, val code: BlockNode): AstNode

abstract class StmtNode: AstNode

class BlockNode(val stmts: Array<StmtNode>): StmtNode()

class VarNode(val name: String, val value: ExprNode): StmtNode()

class WhileNode(val condition: ExprNode, val code: BlockNode): StmtNode()

class RepeatNode(val count: ExprNode, val code: BlockNode): StmtNode()

class IfNode(val condition: ExprNode, val code: BlockNode): StmtNode()

class ExprNode(val operands: Array<AstNode>, val operators: Array<String>): AstNode

class AtomNode(val value: Token): AstNode

class CommandNode(val target: String, val args: Array<ExprNode>): StmtNode()
