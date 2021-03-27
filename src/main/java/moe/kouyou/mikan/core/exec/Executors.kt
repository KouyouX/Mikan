package moe.kouyou.mikan.core.exec

import moe.kouyou.mikan.core.api.*
import moe.kouyou.mikan.core.parse.*

object ProcedureExecutor: Executor<ProcedureNode> {
  override fun execute(env: Environment, node: ProcedureNode) {
    BlockExecutor.execute(env, node.code)
  }
}

object BlockExecutor: Executor<BlockNode> {
  override fun execute(env: Environment, node: BlockNode) {
    node.stmts.forEach {StmtExecutor.execute(env, it)}
  }
}

object StmtExecutor: Executor<StmtNode> {
  override fun execute(env: Environment, node: StmtNode) {
    when (node) {
      is VarNode -> VarExecutor.execute(env, node)
      is CommandNode -> CommandExecutor.execute(env, node)
      is IfNode -> IfExecutor.execute(env, node)
      is WhileNode -> WhileExecutor.execute(env, node)
      is RepeatNode -> RepeatExecutor.execute(env, node)
      is BlockNode -> BlockExecutor.execute(env, node)
      else -> throw RuntimeException()
    }
  }
}

object VarExecutor: Executor<VarNode> {
  override fun execute(env: Environment, node: VarNode) {
    env.setBinding(node.name, ExprEvaluator.evaluate(env, node.value))
  }
}

object IfExecutor: Executor<IfNode> {
  override fun execute(env: Environment, node: IfNode) {
    if (ExprEvaluator.evaluate(env, node.condition) == MBoolean.True) BlockExecutor.execute(env, node.code)
  }
}

object WhileExecutor: Executor<WhileNode> {
  override fun execute(env: Environment, node: WhileNode) {
    while (ExprEvaluator.evaluate(env, node.condition) == MBoolean.True) BlockExecutor.execute(env, node.code)
  }
}

object RepeatExecutor: Executor<RepeatNode> {
  override fun execute(env: Environment, node: RepeatNode) {
    var count = (ExprEvaluator.evaluate(env, node.count) as MNumber).value.toInt()
    while (count-- > 0) BlockExecutor.execute(env, node.code)
  }
}

object CommandExecutor: Executor<CommandNode> {
  override fun execute(env: Environment, node: CommandNode) {
    env.getCommand(node.target)(node.args.map{ExprEvaluator.evaluate(env, it)}.toTypedArray())
  }
}

object ExprEvaluator: Evaluator<ExprNode> {
  override fun evaluate(env: Environment, node: ExprNode): MValue {
    TODO("Not yet implemented")
  }
}
