package moe.kouyou.mikan.lexical

data class Token(val type: TokenType, val text: String) {
    inline fun isKeyword() = this.type == TokenType.Keyword
    inline fun isSymbol() = this.type == TokenType.Symbol
    inline fun isString() = this.type == TokenType.String
    inline fun isInteger() = this.type == TokenType.Integer
    inline fun isFloat() = this.type == TokenType.Float
    inline fun isBoolean() = this.type == TokenType.Boolean
    inline fun isEdge() = this.type == TokenType.Symbol
    inline fun isOperator() = this.type == TokenType.Operator
    inline fun isLiteral() = this.type == TokenType.String || this.type == TokenType.Integer
            || this.type == TokenType.Float || this.type == TokenType.Boolean
}

enum class TokenType {
    Keyword,
    Symbol,
    Operator,
    String,
    Integer,
    Float,
    Boolean,
    Edge,
}
