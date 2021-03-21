package moe.kouyou.mikan.script.api;

import moe.kouyou.mikan.script.exec.MValue;
import org.jetbrains.annotations.NotNull;

public interface Executable {
  void execute(@NotNull MValue[] args);
}
