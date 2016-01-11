package com.mixpanel.android.java_websocket.handshake;

public class HandshakeImpl1Server
  extends HandshakedataImpl1
  implements ServerHandshakeBuilder
{
  private short httpstatus;
  private String httpstatusmessage;
  
  public short getHttpStatus()
  {
    return this.httpstatus;
  }
  
  public String getHttpStatusMessage()
  {
    return this.httpstatusmessage;
  }
  
  public void setHttpStatus(short paramShort)
  {
    this.httpstatus = paramShort;
  }
  
  public void setHttpStatusMessage(String paramString)
  {
    this.httpstatusmessage = paramString;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/java_websocket/handshake/HandshakeImpl1Server.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */