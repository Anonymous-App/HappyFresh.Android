package io.fabric.sdk.android.services.settings;

public class SessionSettingsData
{
  public final int identifierMask;
  public final int logBufferSize;
  public final int maxChainedExceptionDepth;
  public final int maxCustomExceptionEvents;
  public final int maxCustomKeyValuePairs;
  public final boolean sendSessionWithoutCrash;
  
  public SessionSettingsData(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean)
  {
    this.logBufferSize = paramInt1;
    this.maxChainedExceptionDepth = paramInt2;
    this.maxCustomExceptionEvents = paramInt3;
    this.maxCustomKeyValuePairs = paramInt4;
    this.identifierMask = paramInt5;
    this.sendSessionWithoutCrash = paramBoolean;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/settings/SessionSettingsData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */