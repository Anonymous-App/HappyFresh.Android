package com.mixpanel.android.java_websocket.exceptions;

public class LimitExedeedException
  extends InvalidDataException
{
  private static final long serialVersionUID = 6908339749836826785L;
  
  public LimitExedeedException()
  {
    super(1009);
  }
  
  public LimitExedeedException(String paramString)
  {
    super(1009, paramString);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/java_websocket/exceptions/LimitExedeedException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */