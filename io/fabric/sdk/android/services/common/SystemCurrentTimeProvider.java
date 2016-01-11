package io.fabric.sdk.android.services.common;

public class SystemCurrentTimeProvider
  implements CurrentTimeProvider
{
  public long getCurrentTimeMillis()
  {
    return System.currentTimeMillis();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/common/SystemCurrentTimeProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */