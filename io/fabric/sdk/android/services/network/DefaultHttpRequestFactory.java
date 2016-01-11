package io.fabric.sdk.android.services.network;

import io.fabric.sdk.android.DefaultLogger;
import io.fabric.sdk.android.Logger;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public class DefaultHttpRequestFactory
  implements HttpRequestFactory
{
  private static final String HTTPS = "https";
  private boolean attemptedSslInit;
  private final Logger logger;
  private PinningInfoProvider pinningInfo;
  private SSLSocketFactory sslSocketFactory;
  
  public DefaultHttpRequestFactory()
  {
    this(new DefaultLogger());
  }
  
  public DefaultHttpRequestFactory(Logger paramLogger)
  {
    this.logger = paramLogger;
  }
  
  private SSLSocketFactory getSSLSocketFactory()
  {
    try
    {
      if ((this.sslSocketFactory == null) && (!this.attemptedSslInit)) {
        this.sslSocketFactory = initSSLSocketFactory();
      }
      SSLSocketFactory localSSLSocketFactory = this.sslSocketFactory;
      return localSSLSocketFactory;
    }
    finally {}
  }
  
  /* Error */
  private SSLSocketFactory initSSLSocketFactory()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: iconst_1
    //   4: putfield 39	io/fabric/sdk/android/services/network/DefaultHttpRequestFactory:attemptedSslInit	Z
    //   7: aload_0
    //   8: getfield 46	io/fabric/sdk/android/services/network/DefaultHttpRequestFactory:pinningInfo	Lio/fabric/sdk/android/services/network/PinningInfoProvider;
    //   11: invokestatic 51	io/fabric/sdk/android/services/network/NetworkUtils:getSSLSocketFactory	(Lio/fabric/sdk/android/services/network/PinningInfoProvider;)Ljavax/net/ssl/SSLSocketFactory;
    //   14: astore_1
    //   15: aload_0
    //   16: getfield 33	io/fabric/sdk/android/services/network/DefaultHttpRequestFactory:logger	Lio/fabric/sdk/android/Logger;
    //   19: ldc 53
    //   21: ldc 55
    //   23: invokeinterface 61 3 0
    //   28: aload_0
    //   29: monitorexit
    //   30: aload_1
    //   31: areturn
    //   32: astore_1
    //   33: aload_0
    //   34: getfield 33	io/fabric/sdk/android/services/network/DefaultHttpRequestFactory:logger	Lio/fabric/sdk/android/Logger;
    //   37: ldc 53
    //   39: ldc 63
    //   41: aload_1
    //   42: invokeinterface 67 4 0
    //   47: aconst_null
    //   48: astore_1
    //   49: goto -21 -> 28
    //   52: astore_1
    //   53: aload_0
    //   54: monitorexit
    //   55: aload_1
    //   56: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	57	0	this	DefaultHttpRequestFactory
    //   14	17	1	localSSLSocketFactory	SSLSocketFactory
    //   32	10	1	localException	Exception
    //   48	1	1	localObject1	Object
    //   52	4	1	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   7	28	32	java/lang/Exception
    //   2	7	52	finally
    //   7	28	52	finally
    //   33	47	52	finally
  }
  
  private boolean isHttps(String paramString)
  {
    return (paramString != null) && (paramString.toLowerCase(Locale.US).startsWith("https"));
  }
  
  private void resetSSLSocketFactory()
  {
    try
    {
      this.attemptedSslInit = false;
      this.sslSocketFactory = null;
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public HttpRequest buildHttpRequest(HttpMethod paramHttpMethod, String paramString)
  {
    return buildHttpRequest(paramHttpMethod, paramString, Collections.emptyMap());
  }
  
  public HttpRequest buildHttpRequest(HttpMethod paramHttpMethod, String paramString, Map<String, String> paramMap)
  {
    switch (paramHttpMethod)
    {
    default: 
      throw new IllegalArgumentException("Unsupported HTTP method!");
    case ???: 
      paramHttpMethod = HttpRequest.get(paramString, paramMap, true);
    }
    for (;;)
    {
      if ((isHttps(paramString)) && (this.pinningInfo != null))
      {
        paramString = getSSLSocketFactory();
        if (paramString != null) {
          ((HttpsURLConnection)paramHttpMethod.getConnection()).setSSLSocketFactory(paramString);
        }
      }
      return paramHttpMethod;
      paramHttpMethod = HttpRequest.post(paramString, paramMap, true);
      continue;
      paramHttpMethod = HttpRequest.put(paramString);
      continue;
      paramHttpMethod = HttpRequest.delete(paramString);
    }
  }
  
  public PinningInfoProvider getPinningInfoProvider()
  {
    return this.pinningInfo;
  }
  
  public void setPinningInfoProvider(PinningInfoProvider paramPinningInfoProvider)
  {
    if (this.pinningInfo != paramPinningInfoProvider)
    {
      this.pinningInfo = paramPinningInfoProvider;
      resetSSLSocketFactory();
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/network/DefaultHttpRequestFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */