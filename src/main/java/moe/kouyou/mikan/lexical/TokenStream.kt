package moe.kouyou.mikan.lexical

class TokenStream(val tokens: Array<Token>) {
    var offset: Int = 0

    inline fun peek() = tokens[offset]
    inline fun peek(index: Int) = tokens[index]
    inline fun next() = tokens[offset++]
    inline fun hasMore() = tokens.size == offset + 1
}





