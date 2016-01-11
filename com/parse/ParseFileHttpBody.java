package com.parse;

import com.parse.http.ParseHttpBody;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class ParseFileHttpBody
  extends ParseHttpBody
{
  final File file;
  
  public ParseFileHttpBody(File paramFile)
  {
    this(paramFile, null);
  }
  
  public ParseFileHttpBody(File paramFile, String paramString)
  {
    super(paramString, paramFile.length());
    this.file = paramFile;
  }
  
  public InputStream getContent()
    throws IOException
  {
    return new FileInputStream(this.file);
  }
  
  public void writeTo(OutputStream paramOutputStream)
    throws IOException
  {
    if (paramOutputStream == null) {
      throw new IllegalArgumentException("Output stream can not be null");
    }
    FileInputStream localFileInputStream = new FileInputStream(this.file);
    try
    {
      ParseIOUtils.copy(localFileInputStream, paramOutputStream);
      return;
    }
    finally
    {
      ParseIOUtils.closeQuietly(localFileInputStream);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseFileHttpBody.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */