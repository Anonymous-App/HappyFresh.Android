package retrofit.android;

import android.net.http.AndroidHttpClient;
import retrofit.client.ApacheClient;

public final class AndroidApacheClient
  extends ApacheClient
{
  public AndroidApacheClient()
  {
    super(AndroidHttpClient.newInstance("Retrofit"));
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/retrofit/android/AndroidApacheClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */