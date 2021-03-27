package moe.kouyou.mikan.core.lexical

inline fun Token.isSymbol() = this.type == TokenType.Symbol
inline fun Token.isString() = this.type == TokenType.String
//inline fun Token.isInteger() = this.type == TokenType.Integer
//inline fun Token.isFloat() = this.type == TokenType.Float
//inline fun Token.isBoolean() = this.type == TokenType.Boolean
inline fun Token.isNumber() = this.type == TokenType.Number
inline fun Token.isEdge() = this.type == TokenType.Symbol
inline fun Token.isOperator() = this.type == TokenType.Operator
inline fun Token.isLiteral() = this.type == TokenType.String || this.type == TokenType.Number
    //this.type == TokenType.Integer || this.type == TokenType.Float|| this.type == TokenType.Boolean
inline fun Token.isEOS() = this.type == TokenType.EOS