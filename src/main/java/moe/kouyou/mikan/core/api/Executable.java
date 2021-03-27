package moe.kouyou.mikan.core.api;

import moe.kouyou.mikan.core.exec.MValue;
import org.jetbrains.annotations.NotNull;

public interface Executable {
  void execute(@NotNull MValue[] args);
}
