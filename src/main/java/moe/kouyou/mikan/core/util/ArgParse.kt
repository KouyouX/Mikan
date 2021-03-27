package moe.kouyou.mikan.core.util

import moe.kouyou.mikan.core.exec.*

fun Array<MValue>.getString(index: Int): String {
  return (this[index] as MString).value
}

fun Array<MValue>.getBoolean(index: Int): Boolean {
  return this[index] == MNumber.True
}

fun Array<MValue>.getInt(index: Int): Int {
  return (this[index] as MNumber).value.toInt()
}

fun Array<MValue>.getLong(index: Int): Long {
  return (this[index] as MNumber).value.toLong()
}

fun Array<MValue>.getFloat(index: Int): Float {
  return (this[index] as MNumber).value.toFloat()
}

fun Array<MValue>.getDouble(index: Int): Double {
  return (this[index] as MNumber).value
}
