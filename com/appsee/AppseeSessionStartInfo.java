package com.appsee;

public class AppseeSessionStartInfo
{
  private String sessionId;
  private boolean videoRecorded;
  
  public AppseeSessionStartInfo(String paramString, boolean paramBoolean)
  {
    this.sessionId = paramString;
    this.videoRecorded = paramBoolean;
  }
  
  public String getSessionId()
  {
    return this.sessionId;
  }
  
  public boolean isVideoRecorded()
  {
    return this.videoRecorded;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/AppseeSessionStartInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */