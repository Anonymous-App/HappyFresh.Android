package com.optimizely;

import android.support.annotation.NonNull;

public final class Build
{
  private static final String buildVersion = "6";
  private static final String majorVersion = "1";
  private static final String minorVersion = "3";
  public static final String platform = "android";
  
  @NonNull
  public static String majorMinorVersion()
  {
    return String.format("%s.%s", new Object[] { "1", "3" });
  }
  
  @NonNull
  public static String sdkVersion()
  {
    return String.format("%s.%s.%s", new Object[] { "1", "3", "6" });
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Build.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */