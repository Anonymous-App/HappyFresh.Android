package com.mixpanel.android.java_websocket.exceptions;

public class IncompleteHandshakeException
  extends RuntimeException
{
  private static final long serialVersionUID = 7906596804233893092L;
  private int newsize;
  
  public IncompleteHandshakeException()
  {
    this.newsize = 0;
  }
  
  public IncompleteHandshakeException(int paramInt)
  {
    this.newsize = paramInt;
  }
  
  public int getPreferedSize()
  {
    return this.newsize;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/java_websocket/exceptions/IncompleteHandshakeException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */