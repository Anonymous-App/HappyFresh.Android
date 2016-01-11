package com.parse;

import com.parse.http.ParseHttpBody;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

class ParseByteArrayHttpBody
  extends ParseHttpBody
{
  final byte[] content;
  final InputStream contentInputStream;
  
  public ParseByteArrayHttpBody(String paramString1, String paramString2)
    throws UnsupportedEncodingException
  {
    this(paramString1.getBytes("UTF-8"), paramString2);
  }
  
  public ParseByteArrayHttpBody(byte[] paramArrayOfByte, String paramString)
  {
    super(paramString, paramArrayOfByte.length);
    this.content = paramArrayOfByte;
    this.contentInputStream = new ByteArrayInputStream(paramArrayOfByte);
  }
  
  public InputStream getContent()
  {
    return this.contentInputStream;
  }
  
  public void writeTo(OutputStream paramOutputStream)
    throws IOException
  {
    if (paramOutputStream == null) {
      throw new IllegalArgumentException("Output stream may not be null");
    }
    paramOutputStream.write(this.content);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseByteArrayHttpBody.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */