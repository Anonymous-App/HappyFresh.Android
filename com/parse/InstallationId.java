package com.parse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

class InstallationId
{
  private static final String TAG = "InstallationId";
  private final File file;
  private String installationId;
  private final Object lock = new Object();
  
  public InstallationId(File paramFile)
  {
    this.file = paramFile;
  }
  
  private void setInternal(String paramString)
  {
    synchronized (this.lock)
    {
      try
      {
        ParseFileUtils.writeStringToFile(this.file, paramString, "UTF-8");
        this.installationId = paramString;
        return;
      }
      catch (IOException localIOException)
      {
        for (;;)
        {
          PLog.e("InstallationId", "Unexpected exception writing installation id to disk", localIOException);
        }
      }
    }
  }
  
  void clear()
  {
    synchronized (this.lock)
    {
      this.installationId = null;
      ParseFileUtils.deleteQuietly(this.file);
      return;
    }
  }
  
  public String get()
  {
    synchronized (this.lock)
    {
      String str = this.installationId;
      if (str != null) {}
    }
    try
    {
      this.installationId = ParseFileUtils.readFileToString(this.file, "UTF-8");
      if (this.installationId == null) {
        setInternal(UUID.randomUUID().toString());
      }
      return this.installationId;
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      for (;;)
      {
        PLog.i("InstallationId", "Couldn't find existing installationId file. Creating one instead.");
      }
      localObject2 = finally;
      throw ((Throwable)localObject2);
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        PLog.e("InstallationId", "Unexpected exception reading installation id from disk", localIOException);
      }
    }
  }
  
  public void set(String paramString)
  {
    synchronized (this.lock)
    {
      if ((ParseTextUtils.isEmpty(paramString)) || (paramString.equals(get()))) {
        return;
      }
      setInternal(paramString);
      return;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/InstallationId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */