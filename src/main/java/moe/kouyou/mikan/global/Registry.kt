package moe.kouyou.mikan.global

import moe.kouyou.mikan.execute.MValue
import moe.kouyou.mikan.syntax.*

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

    //

    private val foreignFunctions = hashMapOf<String, (Array<MValue>) -> MValue>()

    fun regForeignFunction(name: String, code: (Array<MValue>) -> MValue) {
        foreignFunctions[name] = code
    }

    fun getForeignFunction(name: String) = foreignFunctions.getOrElse(name) { throw RuntimeException() }
}