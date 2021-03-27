package moe.kouyou.mikan.core.api;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import moe.kouyou.mikan.core.exec.MValue;
import moe.kouyou.mikan.core.exec.SimpleEnv;
import moe.kouyou.mikan.core.parse.ProcedureNode;
import org.jetbrains.annotations.NotNull;

public interface Environment{
  void exec(@NotNull ProcedureNode node);
  void setBinding(@NotNull String name, @NotNull MValue value);
  @NotNull MValue getBinding(@NotNull String name);
  @NotNull Function1<MValue[], Unit> getCommand(@NotNull String name);
  
  static Environment create(){
    return new SimpleEnv();
  }
}
