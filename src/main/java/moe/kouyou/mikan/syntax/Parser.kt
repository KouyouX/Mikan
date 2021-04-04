package moe.kouyou.mikan.syntax

import moe.kouyou.mikan.global.Registry
import moe.kouyou.mikan.lexical.Token
import moe.kouyou.mikan.lexical.TokenStream
import moe.kouyou.mikan.lexical.TokenType

interface Parser<out T: AstNode>: (TokenStream) -> T

typealias StmtParser = Parser<Stmt>
typealias ExprParser = Parser<Expr>
typealias FuncParser = Parser<Func>

class LambdaParser<out T: AstNode>(val impl: (TokenStream) -> T): Parser<T>{
    override fun invoke(p1: TokenStream): T = impl(p1)
}

val FunctionParser: Parser<Func> = LambdaParser {
    it.expectConstant("func")
    val name = it.expectSymbol().text
    val code = BlockParser(it)
    Func(name, code)
}

val BlockParser: Parser<BlockStmt> = LambdaParser {
    it.expectConstant("{")
    val result: ArrayList<Stmt> = arrayListOf()
    while(!it.isNextConstant("}")) result.add(StatementParser(it))
    it.next()
    BlockStmt(result.toTypedArray())
}

val StatementParser: Parser<Stmt> = LambdaParser {
    val next = it.peek()
    when (next.text) {
        ";" -> {
            it.next()
            NopeStmt
        }
        "var" -> VarParser(it)
        "while" -> WhileParser(it)
        "if" -> IfParser(it)
        "return" -> ReturnParser(it)
        "break" -> {
            it.next()
            BreakStmt
        }
        "continue" -> {
            it.next()
            ContinueStmt
        }
        else -> {
            (Registry.getStmtParser(next.text) ?: CallParser)(it)
        }
    }
    TODO()
}

val VarParser: Parser<VarStmt> = LambdaParser {
    it.expectConstant("var")
    val name = it.expectSymbol().text
    it.expectConstant("=")
    val value = ExpressionParser(it)
    it.expectEOS()
    VarStmt(name, value)
}

val WhileParser: Parser<WhileStmt> = LambdaParser {
    it.expectConstant("while")
    WhileStmt(ExpressionParser(it), StatementParser(it))
}

val IfParser: Parser<IfStmt> = LambdaParser {
    it.expectConstant("if")
    val cond = arrayListOf<Expr>()
    val code = arrayListOf<Stmt>()
    cond.add(ExpressionParser(it))
    code.add(StatementParser(it))
    while(it.isNextConstant("elif")){
        cond.add(ExpressionParser(it))
        code.add(StatementParser(it))
    }
    if(it.isNextConstant("else")){
        cond.add(LiteralExpr(Token(TokenType.Boolean, "true")))
        code.add(StatementParser(it))
    }
    IfStmt(cond.toTypedArray(), code.toTypedArray())
}

val ReturnParser: Parser<ReturnStmt> = LambdaParser {
    it.expectConstant("return")
    val value = ExpressionParser(it)
    s.expectEOS()
    ReturnStmt(value)
}

val CallParser: Parser<CallStmt> = LambdaParser {
    val target = it.expectSymbol().text
    val args = arrayListOf<Expr>()
    while(!it.isNextEOS()) args.add(ExpressionParser(it))
    it.expectEOS()
    CallStmt(target, args.toTypedArray())
}
