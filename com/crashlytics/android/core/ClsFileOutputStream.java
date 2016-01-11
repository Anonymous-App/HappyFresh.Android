package com.crashlytics.android.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;

class ClsFileOutputStream
  extends FileOutputStream
{
  public static final String IN_PROGRESS_SESSION_FILE_EXTENSION = ".cls_temp";
  public static final String SESSION_FILE_EXTENSION = ".cls";
  public static final FilenameFilter TEMP_FILENAME_FILTER = new FilenameFilter()
  {
    public boolean accept(File paramAnonymousFile, String paramAnonymousString)
    {
      return paramAnonymousString.endsWith(".cls_temp");
    }
  };
  private boolean closed = false;
  private File complete;
  private File inProgress;
  private final String root;
  
  public ClsFileOutputStream(File paramFile, String paramString)
    throws FileNotFoundException
  {
    super(new File(paramFile, paramString + ".cls_temp"));
    this.root = (paramFile + File.separator + paramString);
    this.inProgress = new File(this.root + ".cls_temp");
  }
  
  public ClsFileOutputStream(String paramString1, String paramString2)
    throws FileNotFoundException
  {
    this(new File(paramString1), paramString2);
  }
  
  public void close()
    throws IOException
  {
    File localFile;
    try
    {
      boolean bool = this.closed;
      if (bool) {}
      for (;;)
      {
        return;
        this.closed = true;
        super.flush();
        super.close();
        localFile = new File(this.root + ".cls");
        if (!this.inProgress.renameTo(localFile)) {
          break;
        }
        this.inProgress = null;
        this.complete = localFile;
      }
      str = "";
    }
    finally {}
    String str;
    if (localFile.exists()) {
      str = " (target already exists)";
    }
    for (;;)
    {
      throw new IOException("Could not rename temp file: " + this.inProgress + " -> " + localFile + str);
      if (!this.inProgress.exists()) {
        str = " (source does not exist)";
      }
    }
  }
  
  public void closeInProgressStream()
    throws IOException
  {
    if (this.closed) {
      return;
    }
    this.closed = true;
    super.flush();
    super.close();
  }
  
  public File getCompleteFile()
  {
    return this.complete;
  }
  
  public File getInProgressFile()
  {
    return this.inProgress;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/crashlytics/android/core/ClsFileOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */