package moe.kouyou.mikan.core.exec

import moe.kouyou.mikan.core.api.*
import moe.kouyou.mikan.core.parse.ProcedureNode

class SimpleEnv(
    private val bindings: MutableMap<String, MValue> = hashMapOf(),
    private val procedures: MutableMap<String, ProcedureNode> = hashMapOf(),
    private val commands: MutableMap<String, (Array<MValue>) -> Unit> = Registry.getModule("global"),
    private var executor: Executor<ProcedureNode> = ProcedureExecutor
): Environment {
  
  init {
    bindings["\$PROCEDURE_NAME"] = MString("")
  }
  
  override fun setBinding(name: String, value: MValue) {
    bindings[name] = value
  }
  
  override fun getBinding(name: String): MValue {
    return bindings.getOrElse(name) {throw RuntimeException()}
  }
  
  override fun exec(node: ProcedureNode){
    procedures[node.name] = node
    this.executor.execute(this, node)
  }
  
}