package com.mixpanel.android.util;

import android.content.Context;
import java.io.IOException;
import java.util.List;
import javax.net.ssl.SSLSocketFactory;
import org.apache.http.NameValuePair;

public abstract interface RemoteService
{
  public abstract boolean isOnline(Context paramContext);
  
  public abstract byte[] performRequest(String paramString, List<NameValuePair> paramList, SSLSocketFactory paramSSLSocketFactory)
    throws RemoteService.ServiceUnavailableException, IOException;
  
  public static class ServiceUnavailableException
    extends Exception
  {
    private final int mRetryAfter;
    
    public ServiceUnavailableException(String paramString1, String paramString2)
    {
      super();
      try
      {
        i = Integer.parseInt(paramString2);
        this.mRetryAfter = i;
        return;
      }
      catch (NumberFormatException paramString1)
      {
        for (;;)
        {
          int i = 0;
        }
      }
    }
    
    public int getRetryAfter()
    {
      return this.mRetryAfter;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/mixpanel/android/util/RemoteService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */