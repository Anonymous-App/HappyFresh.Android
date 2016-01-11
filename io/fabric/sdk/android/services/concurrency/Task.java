package io.fabric.sdk.android.services.concurrency;

public abstract interface Task
{
  public abstract Throwable getError();
  
  public abstract boolean isFinished();
  
  public abstract void setError(Throwable paramThrowable);
  
  public abstract void setFinished(boolean paramBoolean);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/concurrency/Task.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */