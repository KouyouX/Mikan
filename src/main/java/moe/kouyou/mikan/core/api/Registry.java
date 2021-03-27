package moe.kouyou.mikan.core.api;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import moe.kouyou.mikan.core.exec.MValue;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class Registry{
  private Registry(){}
  
  private static final HashMap<String, HashMap<String, Function1<MValue[], Unit>>> commands = new HashMap<>();
  
  public static Executable registerCommand(@NotNull String name, @NotNull Executable exec){
    registerCommand("global", name, exec);
    return exec;
  }
  
  public static Executable registerCommand(@NotNull String moduleName, @NotNull String name, @NotNull Executable exec){
    registerCommand(moduleName, name, args -> { exec.execute(args); return Unit.INSTANCE; });
    return exec;
  }
  
  public static @NotNull Function1<MValue[], Unit> registerCommand(@NotNull String name, @NotNull Function1<MValue[], Unit> exec){
    registerCommand("global", name, exec);
    return exec;
  }
  
  public static @NotNull Function1<MValue[], Unit> registerCommand(@NotNull String moduleName, @NotNull String name,
      @NotNull Function1<MValue[], Unit> exec){
    HashMap<String, Function1<MValue[], Unit>> module = commands.computeIfAbsent(moduleName, k -> new HashMap<>());
    module.putIfAbsent(name, exec);
    return exec;
  }
  
  public static @NotNull Map<String, Function1<MValue[], Unit>> getModule(@NotNull String name){
    return commands.computeIfAbsent(name, k -> {throw new RuntimeException();});
  }
  
  public static @NotNull Function1<MValue[], Unit> getCommand(@NotNull String moduleName, @NotNull String name){
    HashMap<String, Function1<MValue[], Unit>> module = commands.computeIfAbsent(name, k -> {throw new RuntimeException();});
    return module.computeIfAbsent(name, k -> {throw new RuntimeException();});
  }
  
}
