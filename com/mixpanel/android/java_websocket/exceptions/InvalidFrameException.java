package com.mixpanel.android.java_websocket.exceptions;

public class InvalidFrameException
  extends InvalidDataException
{
  private static final long serialVersionUID = -9016496369828887591L;
  
  public InvalidFrameException()
  {
    super(1002);
  }
  
  public InvalidFrameException(String paramString)
  {
    super(1002, paramString);
  }
  
  public InvalidFrameException(String paramString, Throwable paramThrowable)
  {
    super(1002, paramString, paramThrowable);
  }
  
  public InvalidFrameException(Throwable paramThrowable)
  {
    super(1002, paramThrowable);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/java_websocket/exceptions/InvalidFrameException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */