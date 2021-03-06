package com.crashlytics.android.core;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class SessionReport
  implements Report
{
  private final Map<String, String> customHeaders;
  private final File file;
  
  public SessionReport(File paramFile)
  {
    this(paramFile, Collections.emptyMap());
  }
  
  public SessionReport(File paramFile, Map<String, String> paramMap)
  {
    this.file = paramFile;
    this.customHeaders = new HashMap(paramMap);
    if (this.file.length() == 0L) {
      this.customHeaders.putAll(ReportUploader.HEADER_INVALID_CLS_FILE);
    }
  }
  
  public Map<String, String> getCustomHeaders()
  {
    return Collections.unmodifiableMap(this.customHeaders);
  }
  
  public File getFile()
  {
    return this.file;
  }
  
  public String getFileName()
  {
    return getFile().getName();
  }
  
  public String getIdentifier()
  {
    String str = getFileName();
    return str.substring(0, str.lastIndexOf('.'));
  }
  
  public boolean remove()
  {
    Fabric.getLogger().d("CrashlyticsCore", "Removing report at " + this.file.getPath());
    return this.file.delete();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/crashlytics/android/core/SessionReport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */