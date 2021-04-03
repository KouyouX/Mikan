package moe.kouyou.mikan.stdlib

import moe.kouyou.mikan.execute.MValue
import org.bukkit.Bukkit

val broadcast: (Array<MValue>) -> Unit = {Bukkit.broadcastMessage(it[0].toString())}
