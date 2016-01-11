package bolts;

public class TaskCompletionSource<TResult>
{
  private final Task<TResult> task = new Task();
  
  public Task<TResult> getTask()
  {
    return this.task;
  }
  
  public void setCancelled()
  {
    if (!trySetCancelled()) {
      throw new IllegalStateException("Cannot cancel a completed task.");
    }
  }
  
  public void setError(Exception paramException)
  {
    if (!trySetError(paramException)) {
      throw new IllegalStateException("Cannot set the error on a completed task.");
    }
  }
  
  public void setResult(TResult paramTResult)
  {
    if (!trySetResult(paramTResult)) {
      throw new IllegalStateException("Cannot set the result of a completed task.");
    }
  }
  
  public boolean trySetCancelled()
  {
    return this.task.trySetCancelled();
  }
  
  public boolean trySetError(Exception paramException)
  {
    return this.task.trySetError(paramException);
  }
  
  public boolean trySetResult(TResult paramTResult)
  {
    return this.task.trySetResult(paramTResult);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/bolts/TaskCompletionSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */