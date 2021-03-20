package moe.kouyou.mikan.script.exec;

public final class MBoolean implements MValue{
  public final boolean value;
  
  private MBoolean(boolean value){
    this.value = value;
  }
  
  public static final MBoolean True = new MBoolean(true);
  public static final MBoolean False = new MBoolean(false);
  
  public static MBoolean valueOf(boolean b){
    return b ? True : False;
  }
}
