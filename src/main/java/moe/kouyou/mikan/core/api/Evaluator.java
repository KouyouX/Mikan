package moe.kouyou.mikan.core.api;

import moe.kouyou.mikan.core.exec.MValue;
import org.jetbrains.annotations.NotNull;

public interface Evaluator<T>{
  @NotNull MValue evaluate(@NotNull Environment env, @NotNull T node);
}
