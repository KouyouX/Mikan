package moe.kouyou.mikan.syntax

import moe.kouyou.mikan.lexical.TokenStream

interface Parser<out T: AstNode>: (TokenStream) -> T

typealias StmtParser = Parser<Stmt>
typealias ExprParser = Parser<Expr>
typealias FuncParser = Parser<Func>

class LambdaParser<out T: AstNode>(val impl: (TokenStream) -> T): Parser<T>{
    override fun invoke(p1: TokenStream): T = impl(p1)
}

val BlockParser = LambdaParser<BlockStmt> {
    TODO()
}

