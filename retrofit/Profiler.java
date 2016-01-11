package retrofit;

public abstract interface Profiler<T>
{
  public abstract void afterCall(RequestInformation paramRequestInformation, long paramLong, int paramInt, T paramT);
  
  public abstract T beforeCall();
  
  public static final class RequestInformation
  {
    private final String baseUrl;
    private final long contentLength;
    private final String contentType;
    private final String method;
    private final String relativePath;
    
    public RequestInformation(String paramString1, String paramString2, String paramString3, long paramLong, String paramString4)
    {
      this.method = paramString1;
      this.baseUrl = paramString2;
      this.relativePath = paramString3;
      this.contentLength = paramLong;
      this.contentType = paramString4;
    }
    
    public String getBaseUrl()
    {
      return this.baseUrl;
    }
    
    public long getContentLength()
    {
      return this.contentLength;
    }
    
    public String getContentType()
    {
      return this.contentType;
    }
    
    public String getMethod()
    {
      return this.method;
    }
    
    public String getRelativePath()
    {
      return this.relativePath;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/retrofit/Profiler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */