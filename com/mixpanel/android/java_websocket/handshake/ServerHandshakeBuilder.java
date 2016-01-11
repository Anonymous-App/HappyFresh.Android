package com.mixpanel.android.java_websocket.handshake;

public abstract interface ServerHandshakeBuilder
  extends HandshakeBuilder, ServerHandshake
{
  public abstract void setHttpStatus(short paramShort);
  
  public abstract void setHttpStatusMessage(String paramString);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/java_websocket/handshake/ServerHandshakeBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */