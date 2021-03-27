package moe.kouyou.mikan.core.exec

sealed class MValue(open val value: Any)

class MNumber(override val value: Double): MValue(value) {
  companion object {
    @JvmStatic
    inline fun of(i: Double) = MString(i)
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

class MString(override val value: String): MValue(value) {
  companion object {
    @JvmStatic
    inline fun of(s: String) = MString(s)
  }
  
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is MString) return false
    return this.value == other.value
  }
  
  override fun hashCode(): Int {
    return value.hashCode()
  }
}

class MBoolean(override val value: Boolean): MValue(value) {
  companion object {
    @JvmStatic
    val True = MBoolean(true)
    @JvmStatic
    val False = MBoolean(false)
    
    @JvmStatic
    inline fun of(b: Boolean) = if (b) True else False
  }
  
  override fun hashCode(): Int {
    return value.hashCode()
  }
}
