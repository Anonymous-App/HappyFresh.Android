package retrofit;

public abstract interface ErrorHandler
{
  public static final ErrorHandler DEFAULT = new ErrorHandler()
  {
    public Throwable handleError(RetrofitError paramAnonymousRetrofitError)
    {
      return paramAnonymousRetrofitError;
    }
  };
  
  public abstract Throwable handleError(RetrofitError paramRetrofitError);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/retrofit/ErrorHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */