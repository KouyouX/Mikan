package moe.kouyou.mikan.lexical

/*
fun main(){
    LexerImpl("""
        while(true) {
            lis( kinda fucking)
            var a = saf == 1
            var b = s >= 0
        }
    """.trimIndent()).tokens.forEach {
        println(it.type.toString() + " : " + it.text)
    }
}
*/

typealias Lexer = (String) -> TokenStream

inline fun defaultLexer() = LexerImpl

val symbolFirstChar = ("ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz" + "0123456789" + "\$" + "_")
        .toCharArray()

val symbolChar = symbolFirstChar + "0123456789".toCharArray()

val keywords = arrayOf("true", "false", "null", "func", "var", "if", "while", "break", "continue", "return")

val LexerImpl: Lexer = { source: String ->
    val result = arrayListOf<Token>()
    val src = source.toCharArray()
    var offset = 0

    while (offset < source.length) {
        when (src[offset]) {
            in symbolFirstChar -> {
                val sb: StringBuilder = StringBuilder(16)
                while (source[offset] in symbolChar) {
                    sb.append(source[offset])
                    ++offset
                }
                val res = sb.toString()
                if (res == "true" || res == "false") result.add(Token(TokenType.Boolean, res))
                else if (res == "null") result.add(Token(TokenType.Null, res))
                else if (res in keywords) result.add(Token(TokenType.Keyword, res))
                else result.add(Token(TokenType.Symbol, res))
            }

            in '0'..'9' -> {
                val sb: StringBuilder = StringBuilder(10)
                while (source[offset] in '0'..'9') {
                    sb.append(source[offset])
                    ++offset
                }
                if (source[offset] == '.') {
                    sb.append('.')
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
                    if (source[offset] == '\\' && source[offset + 1] == '\'') {
                        sb.append('\'')
                        offset += 2
                        continue
                    }
                    sb.append(source[offset])
                    ++offset
                }
                ++offset
                result.add(Token(TokenType.String, sb.toString()))
            }

            '+', '-', '*', '/', '%', '&', '|' -> {
                result.add(Token(TokenType.Operator, src[offset].toString()))
                ++offset
            }

            '>', '<', '!', '=' -> {
                if(source[offset+1] == '=') {
                    result.add(Token(TokenType.Operator, String(charArrayOf(source[offset], source[offset + 1]))))
                    offset += 2
                    continue
                }
                result.add(Token(TokenType.Operator, src[offset].toString()))
                ++offset
            }

            '(', ')', '{', '}' -> {
                result.add(Token(TokenType.Edge, src[offset].toString()))
                ++offset
            }

            ';', '\r', '\n' -> {
                if (result[result.size - 1].text != ";" &&
                        result[result.size - 1].text != "{" &&
                        result[result.size - 1].text != "(")
                            result.add(Token(TokenType.Edge, ";"))
                ++offset
            }

            ' ' -> ++offset

            else -> throw RuntimeException()
        }
    }

    TokenStream(result.toTypedArray())
}
