package io.fabric.sdk.android.services.persistence;

import java.io.File;

public abstract interface FileStore
{
  public abstract File getCacheDir();
  
  public abstract File getExternalCacheDir();
  
  public abstract File getExternalFilesDir();
  
  public abstract File getFilesDir();
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/persistence/FileStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */