package com.afollestad.materialdialogs;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build.VERSION;

public enum GravityEnum
{
  private static final boolean HAS_RTL;
  
  static
  {
    boolean bool = true;
    START = new GravityEnum("START", 0);
    CENTER = new GravityEnum("CENTER", 1);
    END = new GravityEnum("END", 2);
    $VALUES = new GravityEnum[] { START, CENTER, END };
    if (Build.VERSION.SDK_INT >= 17) {}
    for (;;)
    {
      HAS_RTL = bool;
      return;
      bool = false;
    }
  }
  
  private GravityEnum() {}
  
  @SuppressLint({"RtlHardcoded"})
  public int getGravityInt()
  {
    switch (this)
    {
    default: 
      throw new IllegalStateException("Invalid gravity constant");
    case ???: 
      if (HAS_RTL) {
        return 8388611;
      }
      return 3;
    case ???: 
      return 1;
    }
    if (HAS_RTL) {
      return 8388613;
    }
    return 5;
  }
  
  @TargetApi(17)
  public int getTextAlignment()
  {
    switch (this)
    {
    default: 
      return 5;
    case ???: 
      return 4;
    }
    return 6;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/afollestad/materialdialogs/GravityEnum.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */