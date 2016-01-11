package io.fabric.sdk.android.services.persistence;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Environment;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import java.io.File;

public class FileStoreImpl
  implements FileStore
{
  private final String contentPath;
  private final Context context;
  private final String legacySupport;
  
  public FileStoreImpl(Kit paramKit)
  {
    if (paramKit.getContext() == null) {
      throw new IllegalStateException("Cannot get directory before context has been set. Call Fabric.with() first");
    }
    this.context = paramKit.getContext();
    this.contentPath = paramKit.getPath();
    this.legacySupport = ("Android/" + this.context.getPackageName());
  }
  
  public File getCacheDir()
  {
    return prepare(this.context.getCacheDir());
  }
  
  public File getExternalCacheDir()
  {
    File localFile = null;
    if (isExternalStorageAvailable()) {
      if (Build.VERSION.SDK_INT < 8) {
        break label31;
      }
    }
    label31:
    for (localFile = this.context.getExternalCacheDir();; localFile = new File(Environment.getExternalStorageDirectory(), this.legacySupport + "/cache/" + this.contentPath)) {
      return prepare(localFile);
    }
  }
  
  @TargetApi(8)
  public File getExternalFilesDir()
  {
    File localFile = null;
    if (isExternalStorageAvailable()) {
      if (Build.VERSION.SDK_INT < 8) {
        break label32;
      }
    }
    label32:
    for (localFile = this.context.getExternalFilesDir(null);; localFile = new File(Environment.getExternalStorageDirectory(), this.legacySupport + "/files/" + this.contentPath)) {
      return prepare(localFile);
    }
  }
  
  public File getFilesDir()
  {
    return prepare(this.context.getFilesDir());
  }
  
  boolean isExternalStorageAvailable()
  {
    if (!"mounted".equals(Environment.getExternalStorageState()))
    {
      Fabric.getLogger().w("Fabric", "External Storage is not mounted and/or writable\nHave you declared android.permission.WRITE_EXTERNAL_STORAGE in the manifest?");
      return false;
    }
    return true;
  }
  
  File prepare(File paramFile)
  {
    if (paramFile != null)
    {
      if ((paramFile.exists()) || (paramFile.mkdirs())) {
        return paramFile;
      }
      Fabric.getLogger().w("Fabric", "Couldn't create file");
    }
    for (;;)
    {
      return null;
      Fabric.getLogger().d("Fabric", "Null File");
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/persistence/FileStoreImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */