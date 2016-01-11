package com.mixpanel.android.java_websocket.handshake;

public abstract interface ServerHandshake
  extends Handshakedata
{
  public abstract short getHttpStatus();
  
  public abstract String getHttpStatusMessage();
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/java_websocket/handshake/ServerHandshake.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */