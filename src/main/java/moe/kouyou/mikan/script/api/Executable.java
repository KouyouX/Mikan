package moe.kouyou.mikan.script.api;

import moe.kouyou.mikan.script.exec.MValue;

public interface Executable{
  void execute(MValue[] args);
}
