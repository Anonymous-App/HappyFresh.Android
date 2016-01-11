package com.parse.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class ParseHttpBody
{
  private final long contentLength;
  private final String contentType;
  
  public ParseHttpBody(String paramString, long paramLong)
  {
    this.contentType = paramString;
    this.contentLength = paramLong;
  }
  
  public abstract InputStream getContent()
    throws IOException;
  
  public long getContentLength()
  {
    return this.contentLength;
  }
  
  public String getContentType()
  {
    return this.contentType;
  }
  
  public abstract void writeTo(OutputStream paramOutputStream)
    throws IOException;
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/http/ParseHttpBody.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */