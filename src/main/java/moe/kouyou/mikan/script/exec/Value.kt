package moe.kouyou.mikan.script.exec

sealed class MValue

class MNumber(val value: Double): MValue() {
  companion object {
    @JvmStatic
    val True = MNumber(1.0)
    @JvmStatic
    val False = MNumber(0.0)
    
    @JvmStatic
    fun valueOfBoolean(b: Boolean) = if (b) True else False
  }
  
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is MNumber) return false
    return this.value == other.value
  }
  
  override fun hashCode(): Int {
    return value.hashCode()
  }
}

class MString(val value: String): MValue() {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is MString) return false
    return this.value == other.value
  }
  
  override fun hashCode(): Int {
    return value.hashCode()
  }
}

/*
class MBoolean private constructor(val value: Boolean): MValue() {
  companion object {
    @JvmStatic
    val True = MBoolean(true)
    @JvmStatic
    val False = MBoolean(false)
    
    @JvmStatic
    fun valueOf(b: Boolean): MBoolean {
      return if (b) True else False
    }
  }
}
*/
