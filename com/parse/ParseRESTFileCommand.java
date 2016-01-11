package com.parse;

import com.parse.http.ParseHttpBody;
import com.parse.http.ParseHttpRequest.Method;
import java.io.File;

class ParseRESTFileCommand
  extends ParseRESTCommand
{
  private final String contentType;
  private final byte[] data;
  private final File file;
  
  public ParseRESTFileCommand(Builder paramBuilder)
  {
    super(paramBuilder);
    if ((paramBuilder.file != null) && (paramBuilder.data != null)) {
      throw new IllegalArgumentException("File and data can not be set at the same time");
    }
    this.data = paramBuilder.data;
    this.contentType = paramBuilder.contentType;
    this.file = paramBuilder.file;
  }
  
  protected ParseHttpBody newBody(ProgressCallback paramProgressCallback)
  {
    if (paramProgressCallback == null)
    {
      if (this.data != null) {
        return new ParseByteArrayHttpBody(this.data, this.contentType);
      }
      return new ParseFileHttpBody(this.file, this.contentType);
    }
    if (this.data != null) {
      return new ParseCountingByteArrayHttpBody(this.data, this.contentType, paramProgressCallback);
    }
    return new ParseCountingFileHttpBody(this.file, this.contentType, paramProgressCallback);
  }
  
  public static class Builder
    extends ParseRESTCommand.Init<Builder>
  {
    private String contentType = null;
    private byte[] data = null;
    private File file;
    
    public Builder()
    {
      method(ParseHttpRequest.Method.POST);
    }
    
    public ParseRESTFileCommand build()
    {
      return new ParseRESTFileCommand(this);
    }
    
    public Builder contentType(String paramString)
    {
      this.contentType = paramString;
      return this;
    }
    
    public Builder data(byte[] paramArrayOfByte)
    {
      this.data = paramArrayOfByte;
      return this;
    }
    
    public Builder file(File paramFile)
    {
      this.file = paramFile;
      return this;
    }
    
    public Builder fileName(String paramString)
    {
      return (Builder)httpPath(String.format("files/%s", new Object[] { paramString }));
    }
    
    Builder self()
    {
      return this;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseRESTFileCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */