package moe.kouyou.mikan.stdlib

import moe.kouyou.mikan.execute.MValue

val nope: (Array<MValue>) -> Unit = {}
val print: (Array<MValue>) -> Unit = {it.forEach(::println)}

