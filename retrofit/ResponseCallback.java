package retrofit;

import retrofit.client.Response;

public abstract class ResponseCallback
  implements Callback<Response>
{
  public abstract void success(Response paramResponse);
  
  public void success(Response paramResponse1, Response paramResponse2)
  {
    success(paramResponse1);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/retrofit/ResponseCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */