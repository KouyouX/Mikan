package moe.kouyou.mikan.core.api;

import org.jetbrains.annotations.NotNull;

public interface Executor<T extends AstNode>{
  void execute(@NotNull Environment env, @NotNull T node);
}
