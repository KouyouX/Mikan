package moe.kouyou.mikan.lexical

typealias Lexer = (String) -> TokenStream

inline fun defaultLexer() = LexerImpl

val symbolChar = ("ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz" + "0123456789" + "\$" + "_")
        .toCharArray()

val LexerImpl: Lexer = { source: String ->
    val result = arrayListOf<Token>()
    val src = source.toCharArray()
    var offset = 0

    while (offset < source.length) {
        when (src[offset]) {
            in 'A'..'Z', in 'a'..'z', '$', '_' -> {
                val sb: StringBuilder = StringBuilder(16)
                while (source[offset] in symbolChar) {
                    sb.append(source[offset])
                    ++offset
                }
                result.add(Token(TokenType.Symbol, sb.toString()))
            }

            in '0'..'9' -> {
                val sb: StringBuilder = StringBuilder(10)
                while (source[offset] in '0'..'9') {
                    sb.append(source[offset])
                    ++offset
                }
                if (source[offset] == '.') {
                    sb.append(source[offset])
                    ++offset
                } else {
                    result.add(Token(TokenType.Integer, sb.toString()))
                    continue
                }
                while (source[offset] in '0'..'9') {
                    sb.append(source[offset])
                    ++offset
                }
                result.add(Token(TokenType.Float, sb.toString()))
            }

            '\'' -> {
                ++offset
                val sb: StringBuilder = StringBuilder(32)
                while (source[offset] != '\'') {
                    if (source[offset] == '\\') {
                        ++offset
                        if (source[offset] == '\'') {
                            sb.append('\'')
                            ++offset
                        } else if (source[offset] == '\\') {
                            sb.append('\\')
                            ++offset
                        } else throw RuntimeException()
                        continue
                    }
                    sb.append(source[offset])
                    ++offset
                }
                ++offset
                result.add(Token(TokenType.String, sb.toString()))
            }

            '+', '-', '*', '/', '%', '=' -> {
                result.add(Token(TokenType.Operator, src[offset].toString()))
                ++offset
            }

            '(', ')', '{', '}' -> {
                result.add(Token(TokenType.Edge, src[offset].toString()))
                ++offset
            }

            ';', '\r', '\n' -> {
                if (result[result.size - 1].text != ";") result.add(Token(TokenType.Edge, ";"))
                ++offset
            }

            ' ' -> ++offset

            else -> throw RuntimeException()
        }
    }

    result.toTypedArray()
}
