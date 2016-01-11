package com.crashlytics.android.core;

abstract interface FileLogStore
{
  public abstract void closeLogFile();
  
  public abstract void deleteLogFile();
  
  public abstract ByteString getLogAsByteString();
  
  public abstract void writeToLog(long paramLong, String paramString);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/crashlytics/android/core/FileLogStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */