package com.parse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

class ParseCountingFileHttpBody
  extends ParseFileHttpBody
{
  private static final int DEFAULT_CHUNK_SIZE = 4096;
  private static final int EOF = -1;
  private final ProgressCallback progressCallback;
  
  public ParseCountingFileHttpBody(File paramFile, ProgressCallback paramProgressCallback)
  {
    this(paramFile, null, paramProgressCallback);
  }
  
  public ParseCountingFileHttpBody(File paramFile, String paramString, ProgressCallback paramProgressCallback)
  {
    super(paramFile, paramString);
    this.progressCallback = paramProgressCallback;
  }
  
  public void writeTo(OutputStream paramOutputStream)
    throws IOException
  {
    if (paramOutputStream == null) {
      throw new IllegalArgumentException("Output stream may not be null");
    }
    FileInputStream localFileInputStream = new FileInputStream(this.file);
    try
    {
      byte[] arrayOfByte = new byte['က'];
      long l3 = this.file.length();
      long l1 = 0L;
      for (;;)
      {
        int i = localFileInputStream.read(arrayOfByte);
        if (-1 == i) {
          break;
        }
        paramOutputStream.write(arrayOfByte, 0, i);
        long l2 = l1 + i;
        l1 = l2;
        if (this.progressCallback != null)
        {
          i = (int)(100L * l2 / l3);
          this.progressCallback.done(Integer.valueOf(i));
          l1 = l2;
        }
      }
    }
    finally
    {
      ParseIOUtils.closeQuietly(localFileInputStream);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseCountingFileHttpBody.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */