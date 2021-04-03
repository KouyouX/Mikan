package moe.kouyou.mikan.execute

object ValueCalc {
    fun handlePlus(left: MValue, right: MValue): MValue {
        return when (left) {
            is MInteger -> {
                when (right) {
                    is MInteger -> MInteger(left.value + right.value)
                    is MFloat -> MFloat(left.value + right.value)
                    is MString -> MString(left.value.toString() + right.value)
                    else -> throw RuntimeException()
                }
            }
            is MFloat -> {
                when (right) {
                    is MInteger -> MFloat(left.value + right.value)
                    is MFloat -> MFloat(left.value + right.value)
                    is MString -> MString(left.value.toString() + right.value)
                    else -> throw RuntimeException()
                }
            }
            is MString -> {
                MString(left.value + right.value.toString())
            }
            else -> throw RuntimeException()
        }
    }

    fun handleMinus(left: MValue, right: MValue): MValue {
        return when (left) {
            is MInteger -> {
                when (right) {
                    is MInteger -> MInteger(left.value - right.value)
                    is MFloat -> MFloat(left.value - right.value)
                    else -> throw RuntimeException()
                }
            }
            is MFloat -> {
                when (right) {
                    is MInteger -> MFloat(left.value + right.value)
                    is MFloat -> MFloat(left.value + right.value)
                    else -> throw RuntimeException()
                }
            }
            else -> throw RuntimeException()
        }
    }

    fun handleTimes(left: MValue, right: MValue): MValue {
        return if (left is MInteger) {
            when (right) {
                is MInteger -> MInteger(left.value * right.value)
                is MFloat -> MFloat(left.value * right.value)
                is MString -> MString(right.value.repeat(left.value))
                else -> throw RuntimeException()
            }
        } else if (left is MFloat) {
            when (right) {
                is MInteger -> MFloat(left.value * right.value)
                is MFloat -> MFloat(left.value * right.value)
                else -> throw RuntimeException()
            }
        } else if (left is MString && right is MInteger) MString(left.value.repeat(right.value))
        else throw RuntimeException()
    }

    fun handleDiv(left: MValue, right: MValue): MValue {
        return when (left) {
            is MInteger -> {
                when (right) {
                    is MInteger -> MInteger(left.value / right.value)
                    is MFloat -> MFloat(left.value / right.value)
                    else -> throw RuntimeException()
                }
            }
            is MFloat -> {
                when (right) {
                    is MInteger -> MFloat(left.value / right.value)
                    is MFloat -> MFloat(left.value / right.value)
                    else -> throw RuntimeException()
                }
            }
            else -> throw RuntimeException()
        }
    }

    fun handleRem(left: MValue, right: MValue): MValue {
        return when (left) {
            is MInteger -> {
                when (right) {
                    is MInteger -> MInteger(left.value % right.value)
                    is MFloat -> MFloat(left.value % right.value)
                    else -> throw RuntimeException()
                }
            }
            is MFloat -> {
                when (right) {
                    is MInteger -> MFloat(left.value % right.value)
                    is MFloat -> MFloat(left.value % right.value)
                    else -> throw RuntimeException()
                }
            }
            else -> throw RuntimeException()
        }
    }

    fun handleNegate(left: MValue): MValue {
        return when (left) {
            is MInteger -> MInteger(-left.value)
            is MFloat -> MFloat(-left.value)
            else -> throw RuntimeException()
        }
    }

    fun handleAnd(left: MValue, right: MValue): MValue {
        return if (left is MBoolean && right is MBoolean) MBoolean.of(left.value && right.value)
        else throw RuntimeException()
    }

    fun handleOr(left: MValue, right: MValue): MValue {
        return if (left is MBoolean && right is MBoolean) MBoolean.of(left.value || right.value)
        else throw RuntimeException()
    }

    fun handleNot(left: MValue): MValue {
        return if (left is MBoolean) if (left.value) MBoolean.False else MBoolean.True
        else throw RuntimeException()
    }

    fun handleEqual(left: MValue, right: MValue): MValue {
        return if (left === right) MBoolean.True
        else if (left is MInteger) {
            when (right) {
                is MInteger -> MBoolean.of(left.value == right.value)
                is MFloat -> MBoolean.of(left.value.toFloat() == right.value)
                else -> throw RuntimeException()
            }
        } else if (left is MFloat) {
            when (right) {
                is MInteger -> MBoolean.of(left.value == right.value.toFloat())
                is MFloat -> MBoolean.of(left.value == right.value)
                else -> throw RuntimeException()
            }
        } else if (left is MString && right is MString) MBoolean.of(left == right)
        else if (left is MBoolean && right is MBoolean) MBoolean.of(left.value && right.value)
        else throw RuntimeException()
    }

    fun handleNotEqual(left: MValue, right: MValue): MValue {
        return if (left !== right) MBoolean.True
        else if (left is MInteger) {
            when (right) {
                is MInteger -> MBoolean.of(left.value != right.value)
                is MFloat -> MBoolean.of(left.value.toFloat() != right.value)
                else -> throw RuntimeException()
            }
        } else if (left is MFloat) {
            when (right) {
                is MInteger -> MBoolean.of(left.value != right.value.toFloat())
                is MFloat -> MBoolean.of(left.value != right.value)
                else -> throw RuntimeException()
            }
        } else if (left is MString && right is MString) MBoolean.of(left != right)
        else if (left is MBoolean && right is MBoolean) MBoolean.of(!(left.value && right.value))
        else throw RuntimeException()
    }
}
