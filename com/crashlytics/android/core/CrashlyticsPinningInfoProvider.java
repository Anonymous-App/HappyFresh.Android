package com.crashlytics.android.core;

import java.io.InputStream;

class CrashlyticsPinningInfoProvider
  implements io.fabric.sdk.android.services.network.PinningInfoProvider
{
  private final PinningInfoProvider pinningInfo;
  
  public CrashlyticsPinningInfoProvider(PinningInfoProvider paramPinningInfoProvider)
  {
    this.pinningInfo = paramPinningInfoProvider;
  }
  
  public String getKeyStorePassword()
  {
    return this.pinningInfo.getKeyStorePassword();
  }
  
  public InputStream getKeyStoreStream()
  {
    return this.pinningInfo.getKeyStoreStream();
  }
  
  public long getPinCreationTimeInMillis()
  {
    return -1L;
  }
  
  public String[] getPins()
  {
    return this.pinningInfo.getPins();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/crashlytics/android/core/CrashlyticsPinningInfoProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */