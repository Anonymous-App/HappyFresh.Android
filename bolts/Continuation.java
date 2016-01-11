package bolts;

public abstract interface Continuation<TTaskResult, TContinuationResult>
{
  public abstract TContinuationResult then(Task<TTaskResult> paramTask)
    throws Exception;
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/bolts/Continuation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */