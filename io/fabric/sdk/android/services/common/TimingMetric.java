package io.fabric.sdk.android.services.common;

import android.util.Log;

public class TimingMetric
{
  private final boolean disabled;
  private long duration;
  private final String eventName;
  private long start;
  private final String tag;
  
  public TimingMetric(String paramString1, String paramString2)
  {
    this.eventName = paramString1;
    this.tag = paramString2;
    if (!Log.isLoggable(paramString2, 2)) {}
    for (boolean bool = true;; bool = false)
    {
      this.disabled = bool;
      return;
    }
  }
  
  private void reportToLog()
  {
    Log.v(this.tag, this.eventName + ": " + this.duration + "ms");
  }
  
  public long getDuration()
  {
    return this.duration;
  }
  
  /* Error */
  public void startMeasuring()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 29	io/fabric/sdk/android/services/common/TimingMetric:disabled	Z
    //   6: istore_1
    //   7: iload_1
    //   8: ifeq +6 -> 14
    //   11: aload_0
    //   12: monitorexit
    //   13: return
    //   14: aload_0
    //   15: invokestatic 63	android/os/SystemClock:elapsedRealtime	()J
    //   18: putfield 65	io/fabric/sdk/android/services/common/TimingMetric:start	J
    //   21: aload_0
    //   22: lconst_0
    //   23: putfield 42	io/fabric/sdk/android/services/common/TimingMetric:duration	J
    //   26: goto -15 -> 11
    //   29: astore_2
    //   30: aload_0
    //   31: monitorexit
    //   32: aload_2
    //   33: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	34	0	this	TimingMetric
    //   6	2	1	bool	boolean
    //   29	4	2	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   2	7	29	finally
    //   14	26	29	finally
  }
  
  /* Error */
  public void stopMeasuring()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 29	io/fabric/sdk/android/services/common/TimingMetric:disabled	Z
    //   6: istore_1
    //   7: iload_1
    //   8: ifeq +6 -> 14
    //   11: aload_0
    //   12: monitorexit
    //   13: return
    //   14: aload_0
    //   15: getfield 42	io/fabric/sdk/android/services/common/TimingMetric:duration	J
    //   18: lconst_0
    //   19: lcmp
    //   20: ifne -9 -> 11
    //   23: aload_0
    //   24: invokestatic 63	android/os/SystemClock:elapsedRealtime	()J
    //   27: aload_0
    //   28: getfield 65	io/fabric/sdk/android/services/common/TimingMetric:start	J
    //   31: lsub
    //   32: putfield 42	io/fabric/sdk/android/services/common/TimingMetric:duration	J
    //   35: aload_0
    //   36: invokespecial 68	io/fabric/sdk/android/services/common/TimingMetric:reportToLog	()V
    //   39: goto -28 -> 11
    //   42: astore_2
    //   43: aload_0
    //   44: monitorexit
    //   45: aload_2
    //   46: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	47	0	this	TimingMetric
    //   6	2	1	bool	boolean
    //   42	4	2	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   2	7	42	finally
    //   14	39	42	finally
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/common/TimingMetric.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */