package retrofit;

import retrofit.client.Response;

final class ResponseWrapper
{
  final Response response;
  final Object responseBody;
  
  ResponseWrapper(Response paramResponse, Object paramObject)
  {
    this.response = paramResponse;
    this.responseBody = paramObject;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/retrofit/ResponseWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */