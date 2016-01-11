package com.parse;

import android.net.SSLSessionCache;
import android.os.Build.VERSION;
import com.parse.http.ParseHttpRequest;
import com.parse.http.ParseHttpResponse;
import com.parse.http.ParseNetworkInterceptor;
import com.parse.http.ParseNetworkInterceptor.Chain;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

abstract class ParseHttpClient<LibraryRequest, LibraryResponse>
{
  private static final String APACHE_HTTPCLIENT_NAME = "org.apache.http";
  private static final String KEEP_ALIVE_PROPERTY_NAME = "http.keepAlive";
  private static final String MAX_CONNECTIONS_PROPERTY_NAME = "http.maxConnections";
  private static final String OKHTTPCLIENT_PATH = "com.squareup.okhttp.OkHttpClient";
  private static final String OKHTTP_NAME = "com.squareup.okhttp";
  private static final String TAG = "com.parse.ParseHttpClient";
  private static final String URLCONNECTION_NAME = "net.java.URLConnection";
  private List<ParseNetworkInterceptor> externalInterceptors;
  private boolean hasExecuted;
  private List<ParseNetworkInterceptor> internalInterceptors;
  
  public static ParseHttpClient createClient(int paramInt, SSLSessionCache paramSSLSessionCache)
  {
    String str;
    if (hasOkHttpOnClasspath())
    {
      str = "com.squareup.okhttp";
      paramSSLSessionCache = new ParseOkHttpClient(paramInt, paramSSLSessionCache);
    }
    for (;;)
    {
      PLog.i("com.parse.ParseHttpClient", "Using " + str + " library for networking communication.");
      return paramSSLSessionCache;
      if (Build.VERSION.SDK_INT >= 19)
      {
        str = "net.java.URLConnection";
        paramSSLSessionCache = new ParseURLConnectionHttpClient(paramInt, paramSSLSessionCache);
      }
      else
      {
        str = "org.apache.http";
        paramSSLSessionCache = new ParseApacheHttpClient(paramInt, paramSSLSessionCache);
      }
    }
  }
  
  private static boolean hasOkHttpOnClasspath()
  {
    try
    {
      Class.forName("com.squareup.okhttp.OkHttpClient");
      return true;
    }
    catch (ClassNotFoundException localClassNotFoundException) {}
    return false;
  }
  
  public static void setKeepAlive(boolean paramBoolean)
  {
    System.setProperty("http.keepAlive", String.valueOf(paramBoolean));
  }
  
  public static void setMaxConnections(int paramInt)
  {
    if (paramInt <= 0) {
      throw new IllegalArgumentException("Max connections should be large than 0");
    }
    System.setProperty("http.maxConnections", String.valueOf(paramInt));
  }
  
  void addExternalInterceptor(ParseNetworkInterceptor paramParseNetworkInterceptor)
  {
    if (this.externalInterceptors == null) {
      this.externalInterceptors = new ArrayList();
    }
    this.externalInterceptors.add(paramParseNetworkInterceptor);
  }
  
  void addInternalInterceptor(ParseNetworkInterceptor paramParseNetworkInterceptor)
  {
    if (this.hasExecuted) {
      throw new IllegalStateException("`ParseHttpClient#addInternalInterceptor(ParseNetworkInterceptor)` can only be invoked before `ParseHttpClient` execute any request");
    }
    if (this.internalInterceptors == null) {
      this.internalInterceptors = new ArrayList();
    }
    this.internalInterceptors.add(paramParseNetworkInterceptor);
  }
  
  boolean disableHttpLibraryAutoDecompress()
  {
    return (this.externalInterceptors != null) && (this.externalInterceptors.size() > 0);
  }
  
  public final ParseHttpResponse execute(ParseHttpRequest paramParseHttpRequest)
    throws IOException
  {
    if (!this.hasExecuted) {
      this.hasExecuted = true;
    }
    return new ParseNetworkInterceptorChain(0, 0, paramParseHttpRequest).proceed(paramParseHttpRequest);
  }
  
  abstract ParseHttpResponse executeInternal(ParseHttpRequest paramParseHttpRequest)
    throws IOException;
  
  abstract LibraryRequest getRequest(ParseHttpRequest paramParseHttpRequest)
    throws IOException;
  
  abstract ParseHttpResponse getResponse(LibraryResponse paramLibraryResponse)
    throws IOException;
  
  private class ParseNetworkInterceptorChain
    implements ParseNetworkInterceptor.Chain
  {
    private final int externalIndex;
    private final int internalIndex;
    private final ParseHttpRequest request;
    
    ParseNetworkInterceptorChain(int paramInt1, int paramInt2, ParseHttpRequest paramParseHttpRequest)
    {
      this.internalIndex = paramInt1;
      this.externalIndex = paramInt2;
      this.request = paramParseHttpRequest;
    }
    
    public ParseHttpRequest getRequest()
    {
      return this.request;
    }
    
    public ParseHttpResponse proceed(ParseHttpRequest paramParseHttpRequest)
      throws IOException
    {
      if ((ParseHttpClient.this.internalInterceptors != null) && (this.internalIndex < ParseHttpClient.this.internalInterceptors.size()))
      {
        paramParseHttpRequest = new ParseNetworkInterceptorChain(ParseHttpClient.this, this.internalIndex + 1, this.externalIndex, paramParseHttpRequest);
        return ((ParseNetworkInterceptor)ParseHttpClient.this.internalInterceptors.get(this.internalIndex)).intercept(paramParseHttpRequest);
      }
      if ((ParseHttpClient.this.externalInterceptors != null) && (this.externalIndex < ParseHttpClient.this.externalInterceptors.size()))
      {
        paramParseHttpRequest = new ParseNetworkInterceptorChain(ParseHttpClient.this, this.internalIndex, this.externalIndex + 1, paramParseHttpRequest);
        return ((ParseNetworkInterceptor)ParseHttpClient.this.externalInterceptors.get(this.externalIndex)).intercept(paramParseHttpRequest);
      }
      return ParseHttpClient.this.executeInternal(paramParseHttpRequest);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseHttpClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */