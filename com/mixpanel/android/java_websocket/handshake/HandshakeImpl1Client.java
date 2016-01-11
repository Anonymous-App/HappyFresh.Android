package com.mixpanel.android.java_websocket.handshake;

public class HandshakeImpl1Client
  extends HandshakedataImpl1
  implements ClientHandshakeBuilder
{
  private String resourceDescriptor = "*";
  
  public String getResourceDescriptor()
  {
    return this.resourceDescriptor;
  }
  
  public void setResourceDescriptor(String paramString)
    throws IllegalArgumentException
  {
    if (paramString == null) {
      throw new IllegalArgumentException("http resource descriptor must not be null");
    }
    this.resourceDescriptor = paramString;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/java_websocket/handshake/HandshakeImpl1Client.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */