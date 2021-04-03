package moe.kouyou.mikan.global

import moe.kouyou.mikan.execute.MNull
import moe.kouyou.mikan.execute.MValue
import moe.kouyou.mikan.syntax.*
import moe.kouyou.mikan.syntax.Function

object Registry {
    private val stmtParsers = hashMapOf<String, StmtParser>()

    private val funcParsers = hashMapOf<String, FuncParser>()

    fun regStmtParser(prefix: String, parser: StmtParser) {
        stmtParsers[prefix] = parser
    }

    fun regFuncParser(prefix: String, parser: FuncParser) {
        funcParsers[prefix] = parser
    }

    fun getStmtParser(prefix: String) = stmtParsers.getOrElse(prefix) { throw RuntimeException() }

    fun getFuncParser(prefix: String) = funcParsers.getOrElse(prefix) { throw RuntimeException() }

    private val foreignFunctions = hashMapOf<String, (Array<MValue>) -> MValue>()

    fun regForeignFunction(name: String, code: (Array<MValue>) -> MValue) {
        foreignFunctions[name] = code
    }

    fun regNoReturnForeignFunction(name: String, code: (Array<MValue>) -> Unit) {
        foreignFunctions[name] = {code(it); MNull}
    }

    fun getForeignFunction(name: String) = foreignFunctions.getOrElse(name) { throw RuntimeException() }

    private val functions = hashMapOf<String, Function>()

    fun regFunction(code: Function) {
        functions[code.name] = code
    }

    fun getFunction(name: String) = functions.getOrElse(name) { throw RuntimeException() }
}