package com.crashlytics.android.core;

public class CrashlyticsMissingDependencyException
  extends RuntimeException
{
  private static final long serialVersionUID = -1151536370019872859L;
  
  CrashlyticsMissingDependencyException(String paramString)
  {
    super(buildExceptionMessage(paramString));
  }
  
  private static String buildExceptionMessage(String paramString)
  {
    return "\n" + paramString + "\n";
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/crashlytics/android/core/CrashlyticsMissingDependencyException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */