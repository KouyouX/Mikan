package moe.kouyou.mikan.syntax

import moe.kouyou.mikan.lexical.Token

sealed class AstNode

open class Function(val name: String, val code: Array<Statement>): AstNode()
typealias Func = Function

open class Statement: AstNode()
typealias Stmt = Statement

open class Expression: AstNode()
typealias Expr = Expression

/**
 * EOS token
 */
object NopeStmt: Stmt()

class BlockStmt(val code: Array<Stmt>): Stmt()

class CallStmt(val target: String, val args: Array<Expr>): Stmt()

class ReturnStmt(val value: Expr): Stmt()

class VarStmt(val name: String, val value: Expr): Stmt()

class WhileStmt(val condition: Expr, val code: Stmt): Stmt()

object BreakStmt: Stmt()

object ContinueStmt: Stmt()

/**
 * if(cond) code elif(cond) code else code
 * else = elif(true)
 */
class IfStmt(val conditions: Array<Expr>, val codes: Array<Stmt>): Stmt()

class BinaryExpr(val operands: Array<Expr>, val operators: Array<String>): Expr()

class UnaryExpr(val operand: Expr, val operators: Array<String>): Expr()

class CallExpr(val target: String, val args: Array<Expr>): Expr()

class GetVarExpr(val name: String): Expr()

class LiteralExpr(val value: Token): Expr()
