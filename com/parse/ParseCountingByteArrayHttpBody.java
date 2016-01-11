package com.parse;

import java.io.IOException;
import java.io.OutputStream;

class ParseCountingByteArrayHttpBody
  extends ParseByteArrayHttpBody
{
  private static final int DEFAULT_CHUNK_SIZE = 4096;
  private final ProgressCallback progressCallback;
  
  public ParseCountingByteArrayHttpBody(byte[] paramArrayOfByte, String paramString, ProgressCallback paramProgressCallback)
  {
    super(paramArrayOfByte, paramString);
    this.progressCallback = paramProgressCallback;
  }
  
  public void writeTo(OutputStream paramOutputStream)
    throws IOException
  {
    if (paramOutputStream == null) {
      throw new IllegalArgumentException("Output stream may not be null");
    }
    int i = 0;
    int j = this.content.length;
    while (i < j)
    {
      int k = Math.min(j - i, 4096);
      paramOutputStream.write(this.content, i, k);
      paramOutputStream.flush();
      if (this.progressCallback != null)
      {
        i += k;
        k = i * 100 / j;
        this.progressCallback.done(Integer.valueOf(k));
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseCountingByteArrayHttpBody.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */