package moe.kouyou.mikan.execute

interface Environment {
    fun getVariable(name: String): MValue
    fun setVariable(name: String, value: MValue)
    fun isVariableExists(name: String): Boolean
    fun enterStackFrame()
    fun leaveStackFrame()
}

inline fun defaultEnvironment() = EnvironmentImpl()

class EnvironmentImpl(private val globals: MutableMap<String, MValue>) : Environment {
    private val stackFrames: MutableList<MutableMap<String, MValue>> = arrayListOf()
    private var currentFrame: MutableMap<String, MValue>

    init {
        currentFrame = newFrame()
    }

    constructor() : this(hashMapOf())

    private inline fun newFrame() = hashMapOf<String, MValue>().also { it.putAll(globals) }

    override fun getVariable(name: String): MValue = this.currentFrame.getOrElse(name) { throw RuntimeException() }

    override fun setVariable(name: String, value: MValue) {
        this.currentFrame[name] = value
        globals.computeIfPresent(name) { _: String, _: MValue -> value}
    }
    override fun isVariableExists(name: String): Boolean = this.currentFrame.containsKey(name)

    override fun enterStackFrame(){
        stackFrames.add(0, newFrame())
    }

    override fun leaveStackFrame() {
        currentFrame = stackFrames.removeAt(0)
    }
}
