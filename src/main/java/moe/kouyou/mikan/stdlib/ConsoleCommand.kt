package moe.kouyou.mikan.stdlib

import moe.kouyou.mikan.core.api.Executable
import moe.kouyou.mikan.core.util.*
import org.bukkit.Bukkit

val broadcast = Executable {Bukkit.broadcastMessage(it.getString(0))}
