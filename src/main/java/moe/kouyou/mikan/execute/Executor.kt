package moe.kouyou.mikan.execute

import moe.kouyou.mikan.global.Registry
import moe.kouyou.mikan.lexical.TokenType
import moe.kouyou.mikan.syntax.Function
import moe.kouyou.mikan.syntax.*

typealias Executor = (Environment, Function) -> Unit

object ExecutorImpl : Executor {
    private var returned = false

    override fun invoke(p1: Environment, p2: Function) {
        execFunc(p1, p2, emptyArray())
    }

    private inline fun execFunc(env: Environment, func: Function, args: Array<MValue>) {
        execBlock(env, func.code)
        if(!env.isVariableExists("%return%")) env.setVariable("%return%", MNull)
        returned = false
    }

    private inline fun execBlock(env: Environment, block: BlockStmt) {
        if (returned) return
        for (stmt in block.code) {
            execStmt(env, stmt)
        }
    }

    private fun execStmt(env: Environment, stmt: Statement) {
        if (returned) return
        when (stmt) {
            is BlockStmt -> execBlock(env, stmt)
            is VarStmt -> execVar(env, stmt)
            is WhileStmt -> execWhile(env, stmt)
            is IfStmt -> execIf(env, stmt)
            is ReturnStmt -> execReturn(env, stmt)
            is CallStmt -> execCall(env, stmt)
            else -> throw RuntimeException()
        }
    }

    private inline fun execVar(env: Environment, stmt: VarStmt) {
        env.setVariable(stmt.name, evalExpr(env, stmt.value))
    }

    private inline fun execReturn(env: Environment, stmt: ReturnStmt) {
        env.setVariable("%return%", evalExpr(env, stmt.value))
        returned = true
    }

    private inline fun execWhile(env: Environment, stmt: WhileStmt) {
        while (evalExpr(env, stmt.condition) == MBoolean.True) execStmt(env, stmt.code)
    }

    private inline fun execIf(env: Environment, stmt: IfStmt) {
        var i = 0
        while (i < stmt.conditions.size) {
            if (evalExpr(env, stmt.conditions[i]) == MBoolean.True) {
                execStmt(env, stmt.codes[i])
            }
            ++i
        }
    }

    private inline fun execCall(env: Environment, stmt: CallStmt) {
        env.enterStackFrame()
        execFunc(env, Registry.getFunction(stmt.target), stmt.args.map { evalExpr(env, it) }.toTypedArray())
        env.leaveStackFrame()
    }

    private fun evalExpr(env: Environment, expr: Expr): MValue {
        return when(expr){
            is BinaryExpr -> evalBinaryExpr(env, expr)
            is UnaryExpr -> evalUnaryExpr(env, expr)
            is LiteralExpr -> evalLiteralExpr(env, expr)
            is GetVarExpr -> evalGetVarExpr(env, expr)
            is CallExpr -> evalCallExpr(env, expr)
        }
    }

    private inline fun evalBinaryExpr(env: Environment, expr: BinaryExpr): MValue {
        var operand = evalExpr(env, expr.operands[0])
        var i = 0
        while(i < expr.operators.size){
            operand = when (expr.operators[i]){
                "+" -> ValueCalc.handlePlus(operand, evalExpr(env, expr.operands[i]))
                "-" -> ValueCalc.handleMinus(operand, evalExpr(env, expr.operands[i]))
                "*" -> ValueCalc.handleTimes(operand, evalExpr(env, expr.operands[i]))
                "/" -> ValueCalc.handleDiv(operand, evalExpr(env, expr.operands[i]))
                "%" -> ValueCalc.handleRem(operand, evalExpr(env, expr.operands[i]))
                "&" -> ValueCalc.handleAnd(operand, evalExpr(env, expr.operands[i]))
                "|" -> ValueCalc.handleOr(operand, evalExpr(env, expr.operands[i]))
                "=" -> ValueCalc.handleEqual(operand, evalExpr(env, expr.operands[i]))
                "!=" -> ValueCalc.handleNotEqual(operand, evalExpr(env, expr.operands[i]))
                else -> throw RuntimeException()
            }
            ++i
        }
        return operand
    }

    private inline fun evalUnaryExpr(env: Environment, expr: UnaryExpr): MValue {
        var operand = evalExpr(env, expr.operand)
        var i = 0
        while(i < expr.operators.size){
            operand = when (expr.operators[i]){
                "-" -> ValueCalc.handleNegate(operand)
                "!" -> ValueCalc.handleNot(operand)
                else -> throw RuntimeException()
            }
            ++i
        }
        return operand
    }

    private inline fun evalLiteralExpr(env: Environment, expr: LiteralExpr): MValue {
        return when(expr.value.type) {
            TokenType.Null -> MNull
            TokenType.Boolean -> MBoolean.of(expr.value.text == "true")
            TokenType.Integer -> MInteger(expr.value.text.toInt())
            TokenType.Float -> MFloat(expr.value.text.toFloat())
            TokenType.String -> MString(expr.value.text)
            else -> throw RuntimeException()
        }
    }

    private inline fun evalGetVarExpr(env: Environment, expr: GetVarExpr): MValue = env.getVariable(expr.name)

    private inline fun evalCallExpr(env: Environment, expr: CallExpr): MValue{
        env.enterStackFrame()
        execFunc(env, Registry.getFunction(expr.target), expr.args.map { evalExpr(env, it) }.toTypedArray())
        val ret = env.getVariable("%return%")
        env.leaveStackFrame()
        return ret
    }
}

