package io.fabric.sdk.android.services.concurrency;

public class UnmetDependencyException
  extends RuntimeException
{
  public UnmetDependencyException() {}
  
  public UnmetDependencyException(String paramString)
  {
    super(paramString);
  }
  
  public UnmetDependencyException(String paramString, Throwable paramThrowable)
  {
    super(paramString, paramThrowable);
  }
  
  public UnmetDependencyException(Throwable paramThrowable)
  {
    super(paramThrowable);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/concurrency/UnmetDependencyException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */