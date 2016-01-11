package com.crashlytics.android.core;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.QueueFile;
import io.fabric.sdk.android.services.common.QueueFile.ElementReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

class QueueFileLogStore
  implements FileLogStore
{
  private QueueFile logFile;
  private final int maxLogSize;
  private final File workingFile;
  
  public QueueFileLogStore(File paramFile, int paramInt)
  {
    this.workingFile = paramFile;
    this.maxLogSize = paramInt;
  }
  
  private void doWriteToLog(long paramLong, String paramString)
  {
    if (this.logFile == null) {}
    for (;;)
    {
      return;
      String str = paramString;
      if (paramString == null) {
        str = "null";
      }
      try
      {
        int i = this.maxLogSize / 4;
        paramString = str;
        if (str.length() > i) {
          paramString = "..." + str.substring(str.length() - i);
        }
        paramString = paramString.replaceAll("\r", " ").replaceAll("\n", " ");
        paramString = String.format(Locale.US, "%d %s%n", new Object[] { Long.valueOf(paramLong), paramString }).getBytes("UTF-8");
        this.logFile.add(paramString);
        while ((!this.logFile.isEmpty()) && (this.logFile.usedBytes() > this.maxLogSize)) {
          this.logFile.remove();
        }
        return;
      }
      catch (IOException paramString)
      {
        Fabric.getLogger().e("CrashlyticsCore", "There was a problem writing to the Crashlytics log.", paramString);
      }
    }
  }
  
  private void openLogFile()
  {
    if (this.logFile == null) {}
    try
    {
      this.logFile = new QueueFile(this.workingFile);
      return;
    }
    catch (IOException localIOException)
    {
      Fabric.getLogger().e("CrashlyticsCore", "Could not open log file: " + this.workingFile, localIOException);
    }
  }
  
  public void closeLogFile()
  {
    CommonUtils.closeOrLog(this.logFile, "There was a problem closing the Crashlytics log file.");
    this.logFile = null;
  }
  
  public void deleteLogFile()
  {
    closeLogFile();
    this.workingFile.delete();
  }
  
  public ByteString getLogAsByteString()
  {
    if (!this.workingFile.exists()) {}
    do
    {
      return null;
      openLogFile();
    } while (this.logFile == null);
    final int[] arrayOfInt = new int[1];
    arrayOfInt[0] = 0;
    final byte[] arrayOfByte = new byte[this.logFile.usedBytes()];
    try
    {
      this.logFile.forEach(new QueueFile.ElementReader()
      {
        public void read(InputStream paramAnonymousInputStream, int paramAnonymousInt)
          throws IOException
        {
          try
          {
            paramAnonymousInputStream.read(arrayOfByte, arrayOfInt[0], paramAnonymousInt);
            int[] arrayOfInt = arrayOfInt;
            arrayOfInt[0] += paramAnonymousInt;
            return;
          }
          finally
          {
            paramAnonymousInputStream.close();
          }
        }
      });
      return ByteString.copyFrom(arrayOfByte, 0, arrayOfInt[0]);
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        Fabric.getLogger().e("CrashlyticsCore", "A problem occurred while reading the Crashlytics log file.", localIOException);
      }
    }
  }
  
  public void writeToLog(long paramLong, String paramString)
  {
    openLogFile();
    doWriteToLog(paramLong, paramString);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/crashlytics/android/core/QueueFileLogStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */