package moe.kouyou.mikan.core.api;

import moe.kouyou.mikan.core.lexical.TokenStream;
import org.jetbrains.annotations.NotNull;

public interface ParserModule<T extends AstNode>{
  @NotNull T parse(@NotNull TokenStream stream);
}
