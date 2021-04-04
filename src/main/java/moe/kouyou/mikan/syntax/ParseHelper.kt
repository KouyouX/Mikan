package moe.kouyou.mikan.syntax

import moe.kouyou.mikan.lexical.*

inline fun TokenStream.expectConstant(str: String) = this.next().also { if (it.text != str) throw RuntimeException() }

inline fun TokenStream.expectKeyword() = this.next().also { if (it.type != TokenType.Keyword) throw RuntimeException() }

inline fun TokenStream.expectSymbol() = this.next().also { if (it.type != TokenType.Symbol) throw RuntimeException() }

inline fun TokenStream.expectOperator() = this.next().also { if (it.type != TokenType.Operator) throw RuntimeException() }

inline fun TokenStream.expectString() = this.next().also { if (it.type != TokenType.String) throw RuntimeException() }

inline fun TokenStream.expectInteger() = this.next().also { if (it.type != TokenType.Integer) throw RuntimeException() }

inline fun TokenStream.expectFloat() = this.next().also { if (it.type != TokenType.Float) throw RuntimeException() }

inline fun TokenStream.expectNumber() = this.next().also { val s = it.type
    if(s != TokenType.Integer && s != TokenType.Float)
        throw RuntimeException() }

inline fun TokenStream.expectBoolean() = this.next().also { if (it.type != TokenType.Boolean) throw RuntimeException() }

inline fun TokenStream.expectNull() = this.next().also { if (it.type != TokenType.Null) throw RuntimeException() }

inline fun TokenStream.expectLiteral() = this.next().also { val s = it.type
    if (s != TokenType.String && s != TokenType.Integer && s != TokenType.Float && s != TokenType.Boolean && s != TokenType.Null)
            throw RuntimeException() }

inline fun TokenStream.expectEdge() = this.next().also { if (it.type != TokenType.Edge) throw RuntimeException() }

inline fun TokenStream.expectEOS() = this.next().also { if (it.type != TokenType.EOS) throw RuntimeException() }

inline fun TokenStream.isNextConstant(str: String) = this.peek().text == str

inline fun TokenStream.isNextKeyword() = this.peek().type == TokenType.Keyword

inline fun TokenStream.isNextSymbol() = this.peek().type == TokenType.Symbol

inline fun TokenStream.isNextOperator() = this.peek().type == TokenType.Operator

inline fun TokenStream.isNextString() = this.peek().type == TokenType.String

inline fun TokenStream.isNextInteger() = this.peek().type == TokenType.Integer

inline fun TokenStream.isNextFloat() = this.peek().type == TokenType.Float

inline fun TokenStream.isNextNumber(): Boolean {
    val s = this.peek().type
    return s == TokenType.Integer || s == TokenType.Float
}

inline fun TokenStream.isNextBoolean() = this.peek().type == TokenType.Boolean

inline fun TokenStream.isNextNull() = this.peek().type == TokenType.Null

inline fun TokenStream.isNextLiteral(): Boolean {
    val s = this.peek().type
    return s == TokenType.Integer || s == TokenType.Float || s == TokenType.String ||
            s == TokenType.Boolean || s == TokenType.Null
}

inline fun TokenStream.isNextEOS() = this.peek().type == TokenType.EOS