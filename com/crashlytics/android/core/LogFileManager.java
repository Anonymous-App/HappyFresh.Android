package com.crashlytics.android.core;

import android.content.Context;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.File;
import java.util.Set;

class LogFileManager
{
  private static final String DIRECTORY_NAME = "log-files";
  private static final String LOGFILE_EXT = ".temp";
  private static final String LOGFILE_PREFIX = "crashlytics-userlog-";
  static final int MAX_LOG_SIZE = 65536;
  private static final NoopLogStore NOOP_LOG_STORE = new NoopLogStore(null);
  private final Context context;
  private FileLogStore currentLog;
  private final File logFileDir;
  
  public LogFileManager(Context paramContext, File paramFile)
  {
    this(paramContext, paramFile, null);
  }
  
  public LogFileManager(Context paramContext, File paramFile, String paramString)
  {
    this.context = paramContext;
    this.logFileDir = new File(paramFile, "log-files");
    this.currentLog = NOOP_LOG_STORE;
    setCurrentSession(paramString);
  }
  
  private void ensureTargetDirectoryExists()
  {
    if (!this.logFileDir.exists()) {
      this.logFileDir.mkdirs();
    }
  }
  
  private String getSessionIdForFile(File paramFile)
  {
    paramFile = paramFile.getName();
    int i = paramFile.lastIndexOf(".temp");
    if (i == -1) {
      return paramFile;
    }
    return paramFile.substring("crashlytics-userlog-".length(), i);
  }
  
  private File getWorkingFileForSession(String paramString)
  {
    ensureTargetDirectoryExists();
    paramString = "crashlytics-userlog-" + paramString + ".temp";
    return new File(this.logFileDir, paramString);
  }
  
  private boolean isLoggingEnabled()
  {
    return CommonUtils.getBooleanResourceValue(this.context, "com.crashlytics.CollectCustomLogs", true);
  }
  
  public void clearLog()
  {
    this.currentLog.deleteLogFile();
  }
  
  public void discardOldLogFiles(Set<String> paramSet)
  {
    File[] arrayOfFile = this.logFileDir.listFiles();
    if (arrayOfFile != null)
    {
      int j = arrayOfFile.length;
      int i = 0;
      while (i < j)
      {
        File localFile = arrayOfFile[i];
        if (!paramSet.contains(getSessionIdForFile(localFile))) {
          localFile.delete();
        }
        i += 1;
      }
    }
  }
  
  public ByteString getByteStringForLog()
  {
    return this.currentLog.getLogAsByteString();
  }
  
  public final void setCurrentSession(String paramString)
  {
    this.currentLog.closeLogFile();
    this.currentLog = NOOP_LOG_STORE;
    if (paramString == null) {
      return;
    }
    if (!isLoggingEnabled())
    {
      Fabric.getLogger().d("CrashlyticsCore", "Preferences requested no custom logs. Aborting log file creation.");
      return;
    }
    setLogFile(getWorkingFileForSession(paramString), 65536);
  }
  
  void setLogFile(File paramFile, int paramInt)
  {
    this.currentLog = new QueueFileLogStore(paramFile, paramInt);
  }
  
  public void writeToLog(long paramLong, String paramString)
  {
    this.currentLog.writeToLog(paramLong, paramString);
  }
  
  private static final class NoopLogStore
    implements FileLogStore
  {
    public void closeLogFile() {}
    
    public void deleteLogFile() {}
    
    public ByteString getLogAsByteString()
    {
      return null;
    }
    
    public void writeToLog(long paramLong, String paramString) {}
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/crashlytics/android/core/LogFileManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */