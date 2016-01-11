package io.fabric.sdk.android.services.common;

public abstract class Crash
{
  private final String sessionId;
  
  public Crash(String paramString)
  {
    this.sessionId = paramString;
  }
  
  public String getSessionId()
  {
    return this.sessionId;
  }
  
  public static class FatalException
    extends Crash
  {
    public FatalException(String paramString)
    {
      super();
    }
  }
  
  public static class LoggedException
    extends Crash
  {
    public LoggedException(String paramString)
    {
      super();
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/common/Crash.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */