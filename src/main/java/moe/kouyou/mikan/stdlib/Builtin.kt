package moe.kouyou.mikan.stdlib

import moe.kouyou.mikan.core.api.Registry

val nope = Registry.registerCommand("nope") {}
val print = Registry.registerCommand("print") {it.forEach(::println)}


