package com.facebook;

public class FacebookOperationCanceledException
  extends FacebookException
{
  static final long serialVersionUID = 1L;
  
  public FacebookOperationCanceledException() {}
  
  public FacebookOperationCanceledException(String paramString)
  {
    super(paramString);
  }
  
  public FacebookOperationCanceledException(String paramString, Throwable paramThrowable)
  {
    super(paramString, paramThrowable);
  }
  
  public FacebookOperationCanceledException(Throwable paramThrowable)
  {
    super(paramThrowable);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/FacebookOperationCanceledException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */