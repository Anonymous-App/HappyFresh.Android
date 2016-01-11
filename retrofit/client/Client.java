package retrofit.client;

import java.io.IOException;

public abstract interface Client
{
  public abstract Response execute(Request paramRequest)
    throws IOException;
  
  public static abstract interface Provider
  {
    public abstract Client get();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/retrofit/client/Client.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */