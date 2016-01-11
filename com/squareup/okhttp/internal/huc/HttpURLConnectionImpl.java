package com.squareup.okhttp.internal.huc;

import com.squareup.okhttp.Connection;
import com.squareup.okhttp.Handshake;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Headers.Builder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.squareup.okhttp.Route;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.HttpDate;
import com.squareup.okhttp.internal.http.HttpEngine;
import com.squareup.okhttp.internal.http.HttpMethod;
import com.squareup.okhttp.internal.http.OkHeaders;
import com.squareup.okhttp.internal.http.RetryableSink;
import com.squareup.okhttp.internal.http.StatusLine;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.SocketPermission;
import java.net.URL;
import java.security.Permission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import okio.BufferedSink;
import okio.Sink;

public class HttpURLConnectionImpl
  extends HttpURLConnection
{
  private static final RequestBody EMPTY_REQUEST_BODY = RequestBody.create(null, new byte[0]);
  private static final Set<String> METHODS = new LinkedHashSet(Arrays.asList(new String[] { "OPTIONS", "GET", "HEAD", "POST", "PUT", "DELETE", "TRACE", "PATCH" }));
  final OkHttpClient client;
  private long fixedContentLength = -1L;
  private int followUpCount;
  Handshake handshake;
  protected HttpEngine httpEngine;
  protected IOException httpEngineFailure;
  private Headers.Builder requestHeaders = new Headers.Builder();
  private Headers responseHeaders;
  private Route route;
  
  public HttpURLConnectionImpl(URL paramURL, OkHttpClient paramOkHttpClient)
  {
    super(paramURL);
    this.client = paramOkHttpClient;
  }
  
  private String defaultUserAgent()
  {
    String str = System.getProperty("http.agent");
    if (str != null) {
      return str;
    }
    return "Java" + System.getProperty("java.version");
  }
  
  /* Error */
  private boolean execute(boolean paramBoolean)
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 120	com/squareup/okhttp/internal/huc/HttpURLConnectionImpl:httpEngine	Lcom/squareup/okhttp/internal/http/HttpEngine;
    //   4: invokevirtual 125	com/squareup/okhttp/internal/http/HttpEngine:sendRequest	()V
    //   7: aload_0
    //   8: aload_0
    //   9: getfield 120	com/squareup/okhttp/internal/huc/HttpURLConnectionImpl:httpEngine	Lcom/squareup/okhttp/internal/http/HttpEngine;
    //   12: invokevirtual 129	com/squareup/okhttp/internal/http/HttpEngine:getRoute	()Lcom/squareup/okhttp/Route;
    //   15: putfield 131	com/squareup/okhttp/internal/huc/HttpURLConnectionImpl:route	Lcom/squareup/okhttp/Route;
    //   18: aload_0
    //   19: getfield 120	com/squareup/okhttp/internal/huc/HttpURLConnectionImpl:httpEngine	Lcom/squareup/okhttp/internal/http/HttpEngine;
    //   22: invokevirtual 135	com/squareup/okhttp/internal/http/HttpEngine:getConnection	()Lcom/squareup/okhttp/Connection;
    //   25: ifnull +32 -> 57
    //   28: aload_0
    //   29: getfield 120	com/squareup/okhttp/internal/huc/HttpURLConnectionImpl:httpEngine	Lcom/squareup/okhttp/internal/http/HttpEngine;
    //   32: invokevirtual 135	com/squareup/okhttp/internal/http/HttpEngine:getConnection	()Lcom/squareup/okhttp/Connection;
    //   35: invokevirtual 141	com/squareup/okhttp/Connection:getHandshake	()Lcom/squareup/okhttp/Handshake;
    //   38: astore_2
    //   39: aload_0
    //   40: aload_2
    //   41: putfield 143	com/squareup/okhttp/internal/huc/HttpURLConnectionImpl:handshake	Lcom/squareup/okhttp/Handshake;
    //   44: iload_1
    //   45: ifeq +10 -> 55
    //   48: aload_0
    //   49: getfield 120	com/squareup/okhttp/internal/huc/HttpURLConnectionImpl:httpEngine	Lcom/squareup/okhttp/internal/http/HttpEngine;
    //   52: invokevirtual 146	com/squareup/okhttp/internal/http/HttpEngine:readResponse	()V
    //   55: iconst_1
    //   56: ireturn
    //   57: aconst_null
    //   58: astore_2
    //   59: goto -20 -> 39
    //   62: astore_2
    //   63: aload_2
    //   64: invokevirtual 150	com/squareup/okhttp/internal/http/RequestException:getCause	()Ljava/io/IOException;
    //   67: astore_2
    //   68: aload_0
    //   69: aload_2
    //   70: putfield 152	com/squareup/okhttp/internal/huc/HttpURLConnectionImpl:httpEngineFailure	Ljava/io/IOException;
    //   73: aload_2
    //   74: athrow
    //   75: astore_2
    //   76: aload_0
    //   77: getfield 120	com/squareup/okhttp/internal/huc/HttpURLConnectionImpl:httpEngine	Lcom/squareup/okhttp/internal/http/HttpEngine;
    //   80: aload_2
    //   81: invokevirtual 156	com/squareup/okhttp/internal/http/HttpEngine:recover	(Lcom/squareup/okhttp/internal/http/RouteException;)Lcom/squareup/okhttp/internal/http/HttpEngine;
    //   84: astore_3
    //   85: aload_3
    //   86: ifnull +10 -> 96
    //   89: aload_0
    //   90: aload_3
    //   91: putfield 120	com/squareup/okhttp/internal/huc/HttpURLConnectionImpl:httpEngine	Lcom/squareup/okhttp/internal/http/HttpEngine;
    //   94: iconst_0
    //   95: ireturn
    //   96: aload_2
    //   97: invokevirtual 159	com/squareup/okhttp/internal/http/RouteException:getLastConnectException	()Ljava/io/IOException;
    //   100: astore_2
    //   101: aload_0
    //   102: aload_2
    //   103: putfield 152	com/squareup/okhttp/internal/huc/HttpURLConnectionImpl:httpEngineFailure	Ljava/io/IOException;
    //   106: aload_2
    //   107: athrow
    //   108: astore_2
    //   109: aload_0
    //   110: getfield 120	com/squareup/okhttp/internal/huc/HttpURLConnectionImpl:httpEngine	Lcom/squareup/okhttp/internal/http/HttpEngine;
    //   113: aload_2
    //   114: invokevirtual 162	com/squareup/okhttp/internal/http/HttpEngine:recover	(Ljava/io/IOException;)Lcom/squareup/okhttp/internal/http/HttpEngine;
    //   117: astore_3
    //   118: aload_3
    //   119: ifnull +10 -> 129
    //   122: aload_0
    //   123: aload_3
    //   124: putfield 120	com/squareup/okhttp/internal/huc/HttpURLConnectionImpl:httpEngine	Lcom/squareup/okhttp/internal/http/HttpEngine;
    //   127: iconst_0
    //   128: ireturn
    //   129: aload_0
    //   130: aload_2
    //   131: putfield 152	com/squareup/okhttp/internal/huc/HttpURLConnectionImpl:httpEngineFailure	Ljava/io/IOException;
    //   134: aload_2
    //   135: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	136	0	this	HttpURLConnectionImpl
    //   0	136	1	paramBoolean	boolean
    //   38	21	2	localHandshake	Handshake
    //   62	2	2	localRequestException	com.squareup.okhttp.internal.http.RequestException
    //   67	7	2	localIOException1	IOException
    //   75	22	2	localRouteException	com.squareup.okhttp.internal.http.RouteException
    //   100	7	2	localIOException2	IOException
    //   108	27	2	localIOException3	IOException
    //   84	40	3	localHttpEngine	HttpEngine
    // Exception table:
    //   from	to	target	type
    //   0	39	62	com/squareup/okhttp/internal/http/RequestException
    //   39	44	62	com/squareup/okhttp/internal/http/RequestException
    //   48	55	62	com/squareup/okhttp/internal/http/RequestException
    //   0	39	75	com/squareup/okhttp/internal/http/RouteException
    //   39	44	75	com/squareup/okhttp/internal/http/RouteException
    //   48	55	75	com/squareup/okhttp/internal/http/RouteException
    //   0	39	108	java/io/IOException
    //   39	44	108	java/io/IOException
    //   48	55	108	java/io/IOException
  }
  
  private Headers getHeaders()
    throws IOException
  {
    if (this.responseHeaders == null)
    {
      Response localResponse = getResponse().getResponse();
      this.responseHeaders = localResponse.headers().newBuilder().add(Platform.get().getPrefix() + "-Response-Source", responseSourceHeader(localResponse)).build();
    }
    return this.responseHeaders;
  }
  
  private HttpEngine getResponse()
    throws IOException
  {
    initHttpEngine();
    if (this.httpEngine.hasResponse()) {
      return this.httpEngine;
    }
    for (;;)
    {
      if (execute(true))
      {
        Response localResponse = this.httpEngine.getResponse();
        Request localRequest = this.httpEngine.followUpRequest();
        if (localRequest == null)
        {
          this.httpEngine.releaseConnection();
          return this.httpEngine;
        }
        int i = this.followUpCount + 1;
        this.followUpCount = i;
        if (i > 20) {
          throw new ProtocolException("Too many follow-up requests: " + this.followUpCount);
        }
        this.url = localRequest.url();
        this.requestHeaders = localRequest.headers().newBuilder();
        Sink localSink = this.httpEngine.getRequestBody();
        if (!localRequest.method().equals(this.method)) {
          localSink = null;
        }
        if ((localSink != null) && (!(localSink instanceof RetryableSink))) {
          throw new HttpRetryException("Cannot retry streamed HTTP body", this.responseCode);
        }
        if (!this.httpEngine.sameConnection(localRequest.url())) {
          this.httpEngine.releaseConnection();
        }
        Connection localConnection = this.httpEngine.close();
        this.httpEngine = newHttpEngine(localRequest.method(), localConnection, (RetryableSink)localSink, localResponse);
      }
    }
  }
  
  private void initHttpEngine()
    throws IOException
  {
    if (this.httpEngineFailure != null) {
      throw this.httpEngineFailure;
    }
    if (this.httpEngine != null) {
      return;
    }
    this.connected = true;
    do
    {
      try
      {
        if (this.doOutput)
        {
          if (this.method.equals("GET")) {
            this.method = "POST";
          }
        }
        else
        {
          this.httpEngine = newHttpEngine(this.method, null, null, null);
          return;
        }
      }
      catch (IOException localIOException)
      {
        this.httpEngineFailure = localIOException;
        throw localIOException;
      }
    } while (HttpMethod.permitsRequestBody(this.method));
    throw new ProtocolException(this.method + " does not support writing");
  }
  
  private HttpEngine newHttpEngine(String paramString, Connection paramConnection, RetryableSink paramRetryableSink, Response paramResponse)
  {
    if (HttpMethod.requiresRequestBody(paramString)) {}
    Object localObject2;
    for (Object localObject1 = EMPTY_REQUEST_BODY;; localObject1 = null)
    {
      localObject1 = new Request.Builder().url(getURL()).method(paramString, (RequestBody)localObject1);
      localObject2 = this.requestHeaders.build();
      int i = 0;
      int j = ((Headers)localObject2).size();
      while (i < j)
      {
        ((Request.Builder)localObject1).addHeader(((Headers)localObject2).name(i), ((Headers)localObject2).value(i));
        i += 1;
      }
    }
    boolean bool2 = false;
    boolean bool1 = false;
    if (HttpMethod.permitsRequestBody(paramString))
    {
      if (this.fixedContentLength == -1L) {
        break label259;
      }
      ((Request.Builder)localObject1).header("Content-Length", Long.toString(this.fixedContentLength));
    }
    for (;;)
    {
      bool2 = bool1;
      if (((Headers)localObject2).get("Content-Type") == null)
      {
        ((Request.Builder)localObject1).header("Content-Type", "application/x-www-form-urlencoded");
        bool2 = bool1;
      }
      if (((Headers)localObject2).get("User-Agent") == null) {
        ((Request.Builder)localObject1).header("User-Agent", defaultUserAgent());
      }
      localObject2 = ((Request.Builder)localObject1).build();
      localObject1 = this.client;
      paramString = (String)localObject1;
      if (Internal.instance.internalCache((OkHttpClient)localObject1) != null)
      {
        paramString = (String)localObject1;
        if (!getUseCaches()) {
          paramString = this.client.clone().setCache(null);
        }
      }
      return new HttpEngine(paramString, (Request)localObject2, bool2, true, false, paramConnection, null, paramRetryableSink, paramResponse);
      label259:
      if (this.chunkLength > 0) {
        ((Request.Builder)localObject1).header("Transfer-Encoding", "chunked");
      } else {
        bool1 = true;
      }
    }
  }
  
  private static String responseSourceHeader(Response paramResponse)
  {
    if (paramResponse.networkResponse() == null)
    {
      if (paramResponse.cacheResponse() == null) {
        return "NONE";
      }
      return "CACHE " + paramResponse.code();
    }
    if (paramResponse.cacheResponse() == null) {
      return "NETWORK " + paramResponse.code();
    }
    return "CONDITIONAL_CACHE " + paramResponse.networkResponse().code();
  }
  
  private void setProtocols(String paramString, boolean paramBoolean)
  {
    ArrayList localArrayList = new ArrayList();
    if (paramBoolean) {
      localArrayList.addAll(this.client.getProtocols());
    }
    paramString = paramString.split(",", -1);
    int j = paramString.length;
    int i = 0;
    while (i < j)
    {
      String str = paramString[i];
      try
      {
        localArrayList.add(Protocol.get(str));
        i += 1;
      }
      catch (IOException paramString)
      {
        throw new IllegalStateException(paramString);
      }
    }
    this.client.setProtocols(localArrayList);
  }
  
  public final void addRequestProperty(String paramString1, String paramString2)
  {
    if (this.connected) {
      throw new IllegalStateException("Cannot add request property after connection is made");
    }
    if (paramString1 == null) {
      throw new NullPointerException("field == null");
    }
    if (paramString2 == null)
    {
      Platform.get().logW("Ignoring header " + paramString1 + " because its value was null.");
      return;
    }
    if (("X-Android-Transports".equals(paramString1)) || ("X-Android-Protocols".equals(paramString1)))
    {
      setProtocols(paramString2, true);
      return;
    }
    this.requestHeaders.add(paramString1, paramString2);
  }
  
  public final void connect()
    throws IOException
  {
    initHttpEngine();
    while (!execute(false)) {}
  }
  
  public final void disconnect()
  {
    if (this.httpEngine == null) {
      return;
    }
    this.httpEngine.disconnect();
  }
  
  public int getConnectTimeout()
  {
    return this.client.getConnectTimeout();
  }
  
  public final InputStream getErrorStream()
  {
    Object localObject2 = null;
    try
    {
      HttpEngine localHttpEngine = getResponse();
      Object localObject1 = localObject2;
      if (HttpEngine.hasBody(localHttpEngine.getResponse()))
      {
        localObject1 = localObject2;
        if (localHttpEngine.getResponse().code() >= 400) {
          localObject1 = localHttpEngine.getResponse().body().byteStream();
        }
      }
      return (InputStream)localObject1;
    }
    catch (IOException localIOException) {}
    return null;
  }
  
  public final String getHeaderField(int paramInt)
  {
    try
    {
      String str = getHeaders().value(paramInt);
      return str;
    }
    catch (IOException localIOException) {}
    return null;
  }
  
  public final String getHeaderField(String paramString)
  {
    if (paramString == null) {}
    try
    {
      return StatusLine.get(getResponse().getResponse()).toString();
    }
    catch (IOException paramString) {}
    paramString = getHeaders().get(paramString);
    return paramString;
    return null;
  }
  
  public final String getHeaderFieldKey(int paramInt)
  {
    try
    {
      String str = getHeaders().name(paramInt);
      return str;
    }
    catch (IOException localIOException) {}
    return null;
  }
  
  public final Map<String, List<String>> getHeaderFields()
  {
    try
    {
      Map localMap = OkHeaders.toMultimap(getHeaders(), StatusLine.get(getResponse().getResponse()).toString());
      return localMap;
    }
    catch (IOException localIOException) {}
    return Collections.emptyMap();
  }
  
  public final InputStream getInputStream()
    throws IOException
  {
    if (!this.doInput) {
      throw new ProtocolException("This protocol does not support input");
    }
    HttpEngine localHttpEngine = getResponse();
    if (getResponseCode() >= 400) {
      throw new FileNotFoundException(this.url.toString());
    }
    return localHttpEngine.getResponse().body().byteStream();
  }
  
  public final OutputStream getOutputStream()
    throws IOException
  {
    connect();
    BufferedSink localBufferedSink = this.httpEngine.getBufferedRequestBody();
    if (localBufferedSink == null) {
      throw new ProtocolException("method does not support a request body: " + this.method);
    }
    if (this.httpEngine.hasResponse()) {
      throw new ProtocolException("cannot write request body after response has been read");
    }
    return localBufferedSink.outputStream();
  }
  
  public final Permission getPermission()
    throws IOException
  {
    String str = getURL().getHost();
    int i = Util.getEffectivePort(getURL());
    if (usingProxy())
    {
      InetSocketAddress localInetSocketAddress = (InetSocketAddress)this.client.getProxy().address();
      str = localInetSocketAddress.getHostName();
      i = localInetSocketAddress.getPort();
    }
    return new SocketPermission(str + ":" + i, "connect, resolve");
  }
  
  public int getReadTimeout()
  {
    return this.client.getReadTimeout();
  }
  
  public final Map<String, List<String>> getRequestProperties()
  {
    if (this.connected) {
      throw new IllegalStateException("Cannot access request header fields after connection is set");
    }
    return OkHeaders.toMultimap(this.requestHeaders.build(), null);
  }
  
  public final String getRequestProperty(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    return this.requestHeaders.get(paramString);
  }
  
  public final int getResponseCode()
    throws IOException
  {
    return getResponse().getResponse().code();
  }
  
  public String getResponseMessage()
    throws IOException
  {
    return getResponse().getResponse().message();
  }
  
  public void setConnectTimeout(int paramInt)
  {
    this.client.setConnectTimeout(paramInt, TimeUnit.MILLISECONDS);
  }
  
  public void setFixedLengthStreamingMode(int paramInt)
  {
    setFixedLengthStreamingMode(paramInt);
  }
  
  public void setFixedLengthStreamingMode(long paramLong)
  {
    if (this.connected) {
      throw new IllegalStateException("Already connected");
    }
    if (this.chunkLength > 0) {
      throw new IllegalStateException("Already in chunked mode");
    }
    if (paramLong < 0L) {
      throw new IllegalArgumentException("contentLength < 0");
    }
    this.fixedContentLength = paramLong;
    this.fixedContentLength = ((int)Math.min(paramLong, 2147483647L));
  }
  
  public void setIfModifiedSince(long paramLong)
  {
    super.setIfModifiedSince(paramLong);
    if (this.ifModifiedSince != 0L)
    {
      this.requestHeaders.set("If-Modified-Since", HttpDate.format(new Date(this.ifModifiedSince)));
      return;
    }
    this.requestHeaders.removeAll("If-Modified-Since");
  }
  
  public void setInstanceFollowRedirects(boolean paramBoolean)
  {
    this.client.setFollowRedirects(paramBoolean);
  }
  
  public void setReadTimeout(int paramInt)
  {
    this.client.setReadTimeout(paramInt, TimeUnit.MILLISECONDS);
  }
  
  public void setRequestMethod(String paramString)
    throws ProtocolException
  {
    if (!METHODS.contains(paramString)) {
      throw new ProtocolException("Expected one of " + METHODS + " but was " + paramString);
    }
    this.method = paramString;
  }
  
  public final void setRequestProperty(String paramString1, String paramString2)
  {
    if (this.connected) {
      throw new IllegalStateException("Cannot set request property after connection is made");
    }
    if (paramString1 == null) {
      throw new NullPointerException("field == null");
    }
    if (paramString2 == null)
    {
      Platform.get().logW("Ignoring header " + paramString1 + " because its value was null.");
      return;
    }
    if (("X-Android-Transports".equals(paramString1)) || ("X-Android-Protocols".equals(paramString1)))
    {
      setProtocols(paramString2, false);
      return;
    }
    this.requestHeaders.set(paramString1, paramString2);
  }
  
  public final boolean usingProxy()
  {
    if (this.route != null) {}
    for (Proxy localProxy = this.route.getProxy(); (localProxy != null) && (localProxy.type() != Proxy.Type.DIRECT); localProxy = this.client.getProxy()) {
      return true;
    }
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/squareup/okhttp/internal/huc/HttpURLConnectionImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */