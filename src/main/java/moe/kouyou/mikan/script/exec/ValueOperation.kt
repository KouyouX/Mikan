package moe.kouyou.mikan.script.exec

inline operator fun MNumber.plus(right: MNumber) = MNumber(this.value as Double + right.value as Double)
inline operator fun MNumber.minus(right: MNumber) = MNumber(this.value as Double - right.value as Double)
inline operator fun MNumber.times(right: MNumber) = MNumber(this.value as Double * right.value as Double)
inline operator fun MNumber.div(right: MNumber) = MNumber(this.value as Double / right.value as Double)
inline operator fun MNumber.rem(right: MNumber) = MNumber(this.value as Double % right.value as Double)

/*
inline operator fun MString.plus(right: MNumber) = MString(this.value + right.value)
inline operator fun MNumber.plus(right: MString) = MString(this.value.toString() + right.value)
inline operator fun MString.div(right: MNumber) = MString(this.value.repeat(right.value.toInt()))
inline operator fun MNumber.times(right: MString) = MString(right.value.repeat(this.value.toInt()))
*/
