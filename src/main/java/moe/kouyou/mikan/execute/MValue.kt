package moe.kouyou.mikan.execute

sealed class MValue(open val value: Any?)

class MInteger(override val value: Int): MValue(value) {
    override fun toString(): String = value.toString()
}

class MFloat(override val value: Float): MValue(value) {
    override fun toString(): String = value.toString()
}

class MString(override val value: String): MValue(value) {
    override fun toString(): String = value
}

class MBoolean private constructor(override val value: Boolean): MValue(value) {
    companion object {
        @JvmStatic
        val True = MBoolean(true)

        @JvmStatic
        val False = MBoolean(false)

        @JvmStatic
        fun of(value: Boolean) = if(value) True else False

        @JvmStatic
        fun of(value: String) = if(value == "true") True else False
    }

    override fun toString(): String = if(value) "true" else "false"
}

object MNull: MValue(null)