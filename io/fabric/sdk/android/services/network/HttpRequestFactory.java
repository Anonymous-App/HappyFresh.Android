package io.fabric.sdk.android.services.network;

import java.util.Map;

public abstract interface HttpRequestFactory
{
  public abstract HttpRequest buildHttpRequest(HttpMethod paramHttpMethod, String paramString);
  
  public abstract HttpRequest buildHttpRequest(HttpMethod paramHttpMethod, String paramString, Map<String, String> paramMap);
  
  public abstract PinningInfoProvider getPinningInfoProvider();
  
  public abstract void setPinningInfoProvider(PinningInfoProvider paramPinningInfoProvider);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/network/HttpRequestFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */