package moe.kouyou.mikan.script.parse

import moe.kouyou.mikan.script.lexical.*

// peeks
fun TokenStream.isNext(literal: String): Boolean {
  return this.peek().ctx == literal
}

fun TokenStream.isNextSymbol(): Boolean {
  return this.peek().type == TokenType.Symbol
}

fun TokenStream.isNextString(): Boolean {
  return this.peek().type == TokenType.String
}

fun TokenStream.isNextInteger(): Boolean {
  return this.peek().type == TokenType.Integer
}

fun TokenStream.isNextFloat(): Boolean {
  return this.peek().type == TokenType.Float
}

fun TokenStream.isNextBoolean(): Boolean {
  return this.peek().type == TokenType.Boolean
}

fun TokenStream.isNextAtom(): Boolean {
  return this.peek().type in arrayOf(TokenType.String, TokenType.Integer, TokenType.Float, TokenType.Boolean)
}

fun TokenStream.isNextEdge(): Boolean {
  return this.peek().type == TokenType.Edge
}

// ask
fun TokenStream.askFor(literal: String): Token? {
  val s = this.next()
  return if (s.ctx == literal) s else {this.push(s); null}
}

fun TokenStream.askForSymbol(): Token? {
  val s = this.next()
  return if (s.type == TokenType.Symbol) s else {this.push(s); null}
}

fun TokenStream.askForString(): Token? {
  val s = this.next()
  return if (s.type == TokenType.String) s else {this.push(s); null}
}

fun TokenStream.askForInteger(): Token? {
  val s = this.next()
  return if (s.type == TokenType.Integer) s else {this.push(s); null}
}

fun TokenStream.askForFloat(): Token? {
  val s = this.next()
  return if (s.type == TokenType.Float) s else {this.push(s); null}
}

fun TokenStream.askForBoolean(): Token? {
  val s = this.next()
  return if (s.type == TokenType.Boolean) s else {this.push(s); null}
}

fun TokenStream.askForAtom(): Token? {
  val s = this.next()
  return if (s.type in arrayOf(TokenType.String, TokenType.Integer, TokenType.Float, TokenType.Boolean)) s
  else {this.push(s); null}
}

fun TokenStream.askForEdge(): Token? {
  val s = this.next()
  return if (s.type == TokenType.Edge) s else {this.push(s); null}
}

// expects
fun TokenStream.expect(literal: String): Token {
  val s = this.next()
  return if (s.ctx == literal) s else throw TokenMismatchException()
}

fun TokenStream.expectSymbol(): Token {
  val s = this.next()
  return if (s.type == TokenType.Symbol) s else throw TokenMismatchException()
}

fun TokenStream.expectString(): Token {
  val s = this.next()
  return if (s.type == TokenType.String) s else throw TokenMismatchException()
}

fun TokenStream.expectInteger(): Token {
  val s = this.next()
  return if (s.type == TokenType.Integer) s else throw TokenMismatchException()
}

fun TokenStream.expectFloat(): Token {
  val s = this.next()
  return if (s.type == TokenType.Float) s else throw TokenMismatchException()
}

fun TokenStream.expectBoolean(): Token {
  val s = this.next()
  return if (s.type == TokenType.Boolean) s else throw TokenMismatchException()
}

fun TokenStream.expectAtom(): Token {
  val s = this.next()
  return if (s.type in arrayOf(TokenType.String, TokenType.Integer, TokenType.Float, TokenType.Boolean)) s
  else throw TokenMismatchException()
}

fun TokenStream.expectEdge(): Token {
  val s = this.next()
  return if (s.type == TokenType.Edge) s else throw TokenMismatchException()
}

