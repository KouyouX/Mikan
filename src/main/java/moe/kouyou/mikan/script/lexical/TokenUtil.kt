package moe.kouyou.mikan.script.lexical

inline fun Token.isSymbol() = this.type == TokenType.Symbol
inline fun Token.isString() = this.type == TokenType.String
inline fun Token.isInteger() = this.type == TokenType.Integer
inline fun Token.isFloat() = this.type == TokenType.Float
inline fun Token.isBoolean() = this.type == TokenType.Symbol
inline fun Token.isEdge() = this.type == TokenType.Symbol
inline fun Token.isOperator() = this.type == TokenType.Symbol
inline fun Token.isLiteral() = this.type == TokenType.String || this.type == TokenType.Integer ||
    this.type == TokenType.Float || this.type == TokenType.Boolean