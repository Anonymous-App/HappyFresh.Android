package com.parse.http;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class ParseHttpResponse
{
  private final InputStream content;
  private final String contentType;
  private final Map<String, String> headers;
  private final String reasonPhrase;
  private final int statusCode;
  private final long totalSize;
  
  private ParseHttpResponse(Builder paramBuilder)
  {
    this.statusCode = paramBuilder.statusCode;
    this.content = paramBuilder.content;
    this.totalSize = paramBuilder.totalSize;
    this.reasonPhrase = paramBuilder.reasonPhrase;
    this.headers = Collections.unmodifiableMap(new HashMap(paramBuilder.headers));
    this.contentType = paramBuilder.contentType;
  }
  
  public Map<String, String> getAllHeaders()
  {
    return this.headers;
  }
  
  public InputStream getContent()
  {
    return this.content;
  }
  
  public String getContentType()
  {
    return this.contentType;
  }
  
  public String getHeader(String paramString)
  {
    return (String)this.headers.get(paramString);
  }
  
  public String getReasonPhrase()
  {
    return this.reasonPhrase;
  }
  
  public int getStatusCode()
  {
    return this.statusCode;
  }
  
  public long getTotalSize()
  {
    return this.totalSize;
  }
  
  public static final class Builder
  {
    private InputStream content;
    private String contentType;
    private Map<String, String> headers;
    private String reasonPhrase;
    private int statusCode;
    private long totalSize;
    
    public Builder()
    {
      this.totalSize = -1L;
      this.headers = new HashMap();
    }
    
    public Builder(ParseHttpResponse paramParseHttpResponse)
    {
      setStatusCode(paramParseHttpResponse.getStatusCode());
      setContent(paramParseHttpResponse.getContent());
      setTotalSize(paramParseHttpResponse.getTotalSize());
      setContentType(paramParseHttpResponse.getContentType());
      setHeaders(paramParseHttpResponse.getAllHeaders());
      setReasonPhrase(paramParseHttpResponse.getReasonPhrase());
    }
    
    public Builder addHeader(String paramString1, String paramString2)
    {
      this.headers.put(paramString1, paramString2);
      return this;
    }
    
    public Builder addHeaders(Map<String, String> paramMap)
    {
      this.headers.putAll(paramMap);
      return this;
    }
    
    public ParseHttpResponse build()
    {
      return new ParseHttpResponse(this, null);
    }
    
    public Builder setContent(InputStream paramInputStream)
    {
      this.content = paramInputStream;
      return this;
    }
    
    public Builder setContentType(String paramString)
    {
      this.contentType = paramString;
      return this;
    }
    
    public Builder setHeaders(Map<String, String> paramMap)
    {
      this.headers = new HashMap(paramMap);
      return this;
    }
    
    public Builder setReasonPhrase(String paramString)
    {
      this.reasonPhrase = paramString;
      return this;
    }
    
    public Builder setStatusCode(int paramInt)
    {
      this.statusCode = paramInt;
      return this;
    }
    
    public Builder setTotalSize(long paramLong)
    {
      this.totalSize = paramLong;
      return this;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/http/ParseHttpResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */