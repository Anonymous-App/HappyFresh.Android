package com.crashlytics.android.core;

import android.os.AsyncTask;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;

public class CrashTest
{
  private void privateMethodThatThrowsException(String paramString)
  {
    throw new RuntimeException(paramString);
  }
  
  public void crashAsyncTask(final long paramLong)
  {
    new AsyncTask()
    {
      protected Void doInBackground(Void... paramAnonymousVarArgs)
      {
        try
        {
          Thread.sleep(paramLong);
          CrashTest.this.throwRuntimeException("Background thread crash");
          return null;
        }
        catch (InterruptedException paramAnonymousVarArgs)
        {
          for (;;) {}
        }
      }
    }.execute(new Void[] { (Void)null });
  }
  
  public void indexOutOfBounds()
  {
    int i = new int[2][10];
    Fabric.getLogger().d("CrashlyticsCore", "Out of bounds value: " + i);
  }
  
  public int stackOverflow()
  {
    return stackOverflow() + (int)Math.random();
  }
  
  public void throwFiveChainedExceptions()
  {
    try
    {
      privateMethodThatThrowsException("1");
      return;
    }
    catch (Exception localException1)
    {
      try
      {
        throw new RuntimeException("2", localException1);
      }
      catch (Exception localException2)
      {
        try
        {
          throw new RuntimeException("3", localException2);
        }
        catch (Exception localException3)
        {
          try
          {
            throw new RuntimeException("4", localException3);
          }
          catch (Exception localException4)
          {
            throw new RuntimeException("5", localException4);
          }
        }
      }
    }
  }
  
  public void throwRuntimeException(String paramString)
  {
    throw new RuntimeException(paramString);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/crashlytics/android/core/CrashTest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */