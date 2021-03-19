package moe.kouyou.mikan.script

interface MValue<T>

data class MInteger(val value: Int): MValue<Int>

data class MFloat(val value: Float): MValue<Int>

data class MString(val value: String): MValue<Int>

data class MBoolean(val value: Boolean): MValue<Int>
