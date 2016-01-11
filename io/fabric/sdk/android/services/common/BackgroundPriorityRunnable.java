package io.fabric.sdk.android.services.common;

import android.os.Process;

public abstract class BackgroundPriorityRunnable
  implements Runnable
{
  protected abstract void onRun();
  
  public final void run()
  {
    Process.setThreadPriority(10);
    onRun();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/common/BackgroundPriorityRunnable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */