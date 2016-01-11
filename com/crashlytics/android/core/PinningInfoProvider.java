package com.crashlytics.android.core;

import java.io.InputStream;

public abstract interface PinningInfoProvider
{
  public abstract String getKeyStorePassword();
  
  public abstract InputStream getKeyStoreStream();
  
  public abstract String[] getPins();
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/crashlytics/android/core/PinningInfoProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */