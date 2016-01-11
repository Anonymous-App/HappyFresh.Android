package com.mixpanel.android.java_websocket.exceptions;

public class InvalidDataException
  extends Exception
{
  private static final long serialVersionUID = 3731842424390998726L;
  private int closecode;
  
  public InvalidDataException(int paramInt)
  {
    this.closecode = paramInt;
  }
  
  public InvalidDataException(int paramInt, String paramString)
  {
    super(paramString);
    this.closecode = paramInt;
  }
  
  public InvalidDataException(int paramInt, String paramString, Throwable paramThrowable)
  {
    super(paramString, paramThrowable);
    this.closecode = paramInt;
  }
  
  public InvalidDataException(int paramInt, Throwable paramThrowable)
  {
    super(paramThrowable);
    this.closecode = paramInt;
  }
  
  public int getCloseCode()
  {
    return this.closecode;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/java_websocket/exceptions/InvalidDataException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */