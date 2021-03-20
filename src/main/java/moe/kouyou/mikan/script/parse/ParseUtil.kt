package moe.kouyou.mikan.script.parse

import moe.kouyou.mikan.script.lexical.*

// peeks
inline fun TokenStream.isNext(literal: String) = this.peek().ctx == literal
inline fun TokenStream.isNextSymbol() = this.peek().isSymbol()
inline fun TokenStream.isNextString() = this.peek().isString()
inline fun TokenStream.isNextInteger() = this.peek().isInteger()
inline fun TokenStream.isNextFloat() = this.peek().isFloat()
inline fun TokenStream.isNextBoolean() = this.peek().isBoolean()
inline fun TokenStream.isNextEdge() = this.peek().isEdge()
inline fun TokenStream.isOperator() = this.peek().isOperator()
inline fun TokenStream.isNextLiteral() = this.peek().isLiteral()

// ask
fun TokenStream.askFor(literal: String): Token? {
  val s = this.next()
  return if (s.ctx == literal) s else {
    this.push(s); null
  }
}

fun TokenStream.askForSymbol(): Token? {
  val s = this.next()
  return if (s.type == TokenType.Symbol) s else {
    this.push(s); null
  }
}

fun TokenStream.askForString(): Token? {
  val s = this.next()
  return if (s.type == TokenType.String) s else {
    this.push(s); null
  }
}

fun TokenStream.askForInteger(): Token? {
  val s = this.next()
  return if (s.type == TokenType.Integer) s else {
    this.push(s); null
  }
}

fun TokenStream.askForFloat(): Token? {
  val s = this.next()
  return if (s.type == TokenType.Float) s else {
    this.push(s); null
  }
}

fun TokenStream.askForBoolean(): Token? {
  val s = this.next()
  return if (s.isBoolean()) s else {
    this.push(s); null
  }
}

fun TokenStream.askForLiteral(): Token? {
  val s = this.next()
  return if (s.type in arrayOf(TokenType.String, TokenType.Integer, TokenType.Float, TokenType.Boolean)) s
  else {
    this.push(s); null
  }
}

fun TokenStream.askForEdge(): Token? {
  val s = this.next()
  return if (s.type == TokenType.Edge) s else {
    this.push(s); null
  }
}

fun TokenStream.askForOperator(): Token? {
  val s = this.next()
  return if (s.type == TokenType.Operator) s else {
    this.push(s); null
  }
}

// expects
fun TokenStream.expect(literal: String): Token {
  val s = this.next()
  return if (s.ctx == literal) s else throw RuntimeException()
}

fun TokenStream.expectSymbol(): Token {
  val s = this.next()
  return if (s.type == TokenType.Symbol) s else throw RuntimeException()
}

fun TokenStream.expectString(): Token {
  val s = this.next()
  return if (s.type == TokenType.String) s else throw RuntimeException()
}

fun TokenStream.expectInteger(): Token {
  val s = this.next()
  return if (s.type == TokenType.Integer) s else throw RuntimeException()
}

fun TokenStream.expectFloat(): Token {
  val s = this.next()
  return if (s.type == TokenType.Float) s else throw RuntimeException()
}

fun TokenStream.expectBoolean(): Token {
  val s = this.next()
  return if (s.type == TokenType.Boolean) s else throw RuntimeException()
}

fun TokenStream.expectLiteral(): Token {
  val s = this.next()
  return if (s.type in arrayOf(TokenType.String, TokenType.Integer, TokenType.Float, TokenType.Boolean)) s
  else throw RuntimeException()
}

fun TokenStream.expectEdge(): Token {
  val s = this.next()
  return if (s.type == TokenType.Edge) s else throw RuntimeException()
}

fun TokenStream.expectOperator(): Token {
  val s = this.next()
  return if (s.type == TokenType.Operator) s else throw RuntimeException()
}
