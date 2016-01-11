package io.fabric.sdk.android.services.network;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.security.AccessController;
import java.security.GeneralSecurityException;
import java.security.PrivilegedAction;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpRequest
{
  private static final String BOUNDARY = "00content0boundary00";
  public static final String CHARSET_UTF8 = "UTF-8";
  private static ConnectionFactory CONNECTION_FACTORY = ConnectionFactory.DEFAULT;
  public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
  public static final String CONTENT_TYPE_JSON = "application/json";
  private static final String CONTENT_TYPE_MULTIPART = "multipart/form-data; boundary=00content0boundary00";
  private static final String CRLF = "\r\n";
  private static final String[] EMPTY_STRINGS = new String[0];
  public static final String ENCODING_GZIP = "gzip";
  public static final String HEADER_ACCEPT = "Accept";
  public static final String HEADER_ACCEPT_CHARSET = "Accept-Charset";
  public static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
  public static final String HEADER_AUTHORIZATION = "Authorization";
  public static final String HEADER_CACHE_CONTROL = "Cache-Control";
  public static final String HEADER_CONTENT_ENCODING = "Content-Encoding";
  public static final String HEADER_CONTENT_LENGTH = "Content-Length";
  public static final String HEADER_CONTENT_TYPE = "Content-Type";
  public static final String HEADER_DATE = "Date";
  public static final String HEADER_ETAG = "ETag";
  public static final String HEADER_EXPIRES = "Expires";
  public static final String HEADER_IF_NONE_MATCH = "If-None-Match";
  public static final String HEADER_LAST_MODIFIED = "Last-Modified";
  public static final String HEADER_LOCATION = "Location";
  public static final String HEADER_PROXY_AUTHORIZATION = "Proxy-Authorization";
  public static final String HEADER_REFERER = "Referer";
  public static final String HEADER_SERVER = "Server";
  public static final String HEADER_USER_AGENT = "User-Agent";
  public static final String METHOD_DELETE = "DELETE";
  public static final String METHOD_GET = "GET";
  public static final String METHOD_HEAD = "HEAD";
  public static final String METHOD_OPTIONS = "OPTIONS";
  public static final String METHOD_POST = "POST";
  public static final String METHOD_PUT = "PUT";
  public static final String METHOD_TRACE = "TRACE";
  public static final String PARAM_CHARSET = "charset";
  private static SSLSocketFactory TRUSTED_FACTORY;
  private static HostnameVerifier TRUSTED_VERIFIER;
  private int bufferSize = 8192;
  private HttpURLConnection connection = null;
  private boolean form;
  private String httpProxyHost;
  private int httpProxyPort;
  private boolean ignoreCloseExceptions = true;
  private boolean multipart;
  private RequestOutputStream output;
  private final String requestMethod;
  private boolean uncompress = false;
  public final URL url;
  
  public HttpRequest(CharSequence paramCharSequence, String paramString)
    throws HttpRequest.HttpRequestException
  {
    try
    {
      this.url = new URL(paramCharSequence.toString());
      this.requestMethod = paramString;
      return;
    }
    catch (MalformedURLException paramCharSequence)
    {
      throw new HttpRequestException(paramCharSequence);
    }
  }
  
  public HttpRequest(URL paramURL, String paramString)
    throws HttpRequest.HttpRequestException
  {
    this.url = paramURL;
    this.requestMethod = paramString;
  }
  
  private static StringBuilder addParamPrefix(String paramString, StringBuilder paramStringBuilder)
  {
    int i = paramString.indexOf('?');
    int j = paramStringBuilder.length() - 1;
    if (i == -1) {
      paramStringBuilder.append('?');
    }
    while ((i >= j) || (paramString.charAt(j) == '&')) {
      return paramStringBuilder;
    }
    paramStringBuilder.append('&');
    return paramStringBuilder;
  }
  
  private static StringBuilder addPathSeparator(String paramString, StringBuilder paramStringBuilder)
  {
    if (paramString.indexOf(':') + 2 == paramString.lastIndexOf('/')) {
      paramStringBuilder.append('/');
    }
    return paramStringBuilder;
  }
  
  public static String append(CharSequence paramCharSequence, Map<?, ?> paramMap)
  {
    Object localObject = paramCharSequence.toString();
    if ((paramMap == null) || (paramMap.isEmpty())) {
      return (String)localObject;
    }
    paramCharSequence = new StringBuilder((String)localObject);
    addPathSeparator((String)localObject, paramCharSequence);
    addParamPrefix((String)localObject, paramCharSequence);
    paramMap = paramMap.entrySet().iterator();
    localObject = (Map.Entry)paramMap.next();
    paramCharSequence.append(((Map.Entry)localObject).getKey().toString());
    paramCharSequence.append('=');
    localObject = ((Map.Entry)localObject).getValue();
    if (localObject != null) {
      paramCharSequence.append(localObject);
    }
    while (paramMap.hasNext())
    {
      paramCharSequence.append('&');
      localObject = (Map.Entry)paramMap.next();
      paramCharSequence.append(((Map.Entry)localObject).getKey().toString());
      paramCharSequence.append('=');
      localObject = ((Map.Entry)localObject).getValue();
      if (localObject != null) {
        paramCharSequence.append(localObject);
      }
    }
    return paramCharSequence.toString();
  }
  
  public static String append(CharSequence paramCharSequence, Object... paramVarArgs)
  {
    Object localObject = paramCharSequence.toString();
    if ((paramVarArgs == null) || (paramVarArgs.length == 0)) {
      return (String)localObject;
    }
    if (paramVarArgs.length % 2 != 0) {
      throw new IllegalArgumentException("Must specify an even number of parameter names/values");
    }
    paramCharSequence = new StringBuilder((String)localObject);
    addPathSeparator((String)localObject, paramCharSequence);
    addParamPrefix((String)localObject, paramCharSequence);
    paramCharSequence.append(paramVarArgs[0]);
    paramCharSequence.append('=');
    localObject = paramVarArgs[1];
    if (localObject != null) {
      paramCharSequence.append(localObject);
    }
    int i = 2;
    while (i < paramVarArgs.length)
    {
      paramCharSequence.append('&');
      paramCharSequence.append(paramVarArgs[i]);
      paramCharSequence.append('=');
      localObject = paramVarArgs[(i + 1)];
      if (localObject != null) {
        paramCharSequence.append(localObject);
      }
      i += 2;
    }
    return paramCharSequence.toString();
  }
  
  /* Error */
  private HttpURLConnection createConnection()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 309	io/fabric/sdk/android/services/network/HttpRequest:httpProxyHost	Ljava/lang/String;
    //   4: ifnull +30 -> 34
    //   7: getstatic 182	io/fabric/sdk/android/services/network/HttpRequest:CONNECTION_FACTORY	Lio/fabric/sdk/android/services/network/HttpRequest$ConnectionFactory;
    //   10: aload_0
    //   11: getfield 210	io/fabric/sdk/android/services/network/HttpRequest:url	Ljava/net/URL;
    //   14: aload_0
    //   15: invokespecial 313	io/fabric/sdk/android/services/network/HttpRequest:createProxy	()Ljava/net/Proxy;
    //   18: invokeinterface 317 3 0
    //   23: astore_1
    //   24: aload_1
    //   25: aload_0
    //   26: getfield 212	io/fabric/sdk/android/services/network/HttpRequest:requestMethod	Ljava/lang/String;
    //   29: invokevirtual 322	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
    //   32: aload_1
    //   33: areturn
    //   34: getstatic 182	io/fabric/sdk/android/services/network/HttpRequest:CONNECTION_FACTORY	Lio/fabric/sdk/android/services/network/HttpRequest$ConnectionFactory;
    //   37: aload_0
    //   38: getfield 210	io/fabric/sdk/android/services/network/HttpRequest:url	Ljava/net/URL;
    //   41: invokeinterface 325 2 0
    //   46: astore_1
    //   47: goto -23 -> 24
    //   50: astore_1
    //   51: new 40	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   54: dup
    //   55: aload_1
    //   56: invokespecial 215	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException:<init>	(Ljava/io/IOException;)V
    //   59: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	60	0	this	HttpRequest
    //   23	24	1	localHttpURLConnection	HttpURLConnection
    //   50	6	1	localIOException	IOException
    // Exception table:
    //   from	to	target	type
    //   0	24	50	java/io/IOException
    //   24	32	50	java/io/IOException
    //   34	47	50	java/io/IOException
  }
  
  private Proxy createProxy()
  {
    return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(this.httpProxyHost, this.httpProxyPort));
  }
  
  public static HttpRequest delete(CharSequence paramCharSequence)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramCharSequence, "DELETE");
  }
  
  public static HttpRequest delete(CharSequence paramCharSequence, Map<?, ?> paramMap, boolean paramBoolean)
  {
    paramMap = append(paramCharSequence, paramMap);
    paramCharSequence = paramMap;
    if (paramBoolean) {
      paramCharSequence = encode(paramMap);
    }
    return delete(paramCharSequence);
  }
  
  public static HttpRequest delete(CharSequence paramCharSequence, boolean paramBoolean, Object... paramVarArgs)
  {
    paramVarArgs = append(paramCharSequence, paramVarArgs);
    paramCharSequence = paramVarArgs;
    if (paramBoolean) {
      paramCharSequence = encode(paramVarArgs);
    }
    return delete(paramCharSequence);
  }
  
  public static HttpRequest delete(URL paramURL)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramURL, "DELETE");
  }
  
  /* Error */
  public static String encode(CharSequence paramCharSequence)
    throws HttpRequest.HttpRequestException
  {
    // Byte code:
    //   0: new 199	java/net/URL
    //   3: dup
    //   4: aload_0
    //   5: invokeinterface 205 1 0
    //   10: invokespecial 208	java/net/URL:<init>	(Ljava/lang/String;)V
    //   13: astore_3
    //   14: aload_3
    //   15: invokevirtual 368	java/net/URL:getHost	()Ljava/lang/String;
    //   18: astore_2
    //   19: aload_3
    //   20: invokevirtual 371	java/net/URL:getPort	()I
    //   23: istore_1
    //   24: aload_2
    //   25: astore_0
    //   26: iload_1
    //   27: iconst_m1
    //   28: if_icmpeq +30 -> 58
    //   31: new 232	java/lang/StringBuilder
    //   34: dup
    //   35: invokespecial 372	java/lang/StringBuilder:<init>	()V
    //   38: aload_2
    //   39: invokevirtual 285	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   42: bipush 58
    //   44: invokevirtual 240	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
    //   47: iload_1
    //   48: invokestatic 377	java/lang/Integer:toString	(I)Ljava/lang/String;
    //   51: invokevirtual 285	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   54: invokevirtual 295	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   57: astore_0
    //   58: new 379	java/net/URI
    //   61: dup
    //   62: aload_3
    //   63: invokevirtual 382	java/net/URL:getProtocol	()Ljava/lang/String;
    //   66: aload_0
    //   67: aload_3
    //   68: invokevirtual 385	java/net/URL:getPath	()Ljava/lang/String;
    //   71: aload_3
    //   72: invokevirtual 388	java/net/URL:getQuery	()Ljava/lang/String;
    //   75: aconst_null
    //   76: invokespecial 391	java/net/URI:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   79: invokevirtual 394	java/net/URI:toASCIIString	()Ljava/lang/String;
    //   82: astore_2
    //   83: aload_2
    //   84: bipush 63
    //   86: invokevirtual 230	java/lang/String:indexOf	(I)I
    //   89: istore_1
    //   90: aload_2
    //   91: astore_0
    //   92: iload_1
    //   93: ifle +56 -> 149
    //   96: aload_2
    //   97: astore_0
    //   98: iload_1
    //   99: iconst_1
    //   100: iadd
    //   101: aload_2
    //   102: invokevirtual 395	java/lang/String:length	()I
    //   105: if_icmpge +44 -> 149
    //   108: new 232	java/lang/StringBuilder
    //   111: dup
    //   112: invokespecial 372	java/lang/StringBuilder:<init>	()V
    //   115: aload_2
    //   116: iconst_0
    //   117: iload_1
    //   118: iconst_1
    //   119: iadd
    //   120: invokevirtual 399	java/lang/String:substring	(II)Ljava/lang/String;
    //   123: invokevirtual 285	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   126: aload_2
    //   127: iload_1
    //   128: iconst_1
    //   129: iadd
    //   130: invokevirtual 401	java/lang/String:substring	(I)Ljava/lang/String;
    //   133: ldc_w 403
    //   136: ldc_w 405
    //   139: invokevirtual 409	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   142: invokevirtual 285	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   145: invokevirtual 295	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   148: astore_0
    //   149: aload_0
    //   150: areturn
    //   151: astore_0
    //   152: new 40	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   155: dup
    //   156: aload_0
    //   157: invokespecial 215	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException:<init>	(Ljava/io/IOException;)V
    //   160: athrow
    //   161: astore_0
    //   162: new 307	java/io/IOException
    //   165: dup
    //   166: ldc_w 411
    //   169: invokespecial 412	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   172: astore_2
    //   173: aload_2
    //   174: aload_0
    //   175: invokevirtual 416	java/io/IOException:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   178: pop
    //   179: new 40	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   182: dup
    //   183: aload_2
    //   184: invokespecial 215	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException:<init>	(Ljava/io/IOException;)V
    //   187: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	188	0	paramCharSequence	CharSequence
    //   23	107	1	i	int
    //   18	166	2	localObject	Object
    //   13	59	3	localURL	URL
    // Exception table:
    //   from	to	target	type
    //   0	14	151	java/io/IOException
    //   58	90	161	java/net/URISyntaxException
    //   98	149	161	java/net/URISyntaxException
  }
  
  public static HttpRequest get(CharSequence paramCharSequence)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramCharSequence, "GET");
  }
  
  public static HttpRequest get(CharSequence paramCharSequence, Map<?, ?> paramMap, boolean paramBoolean)
  {
    paramMap = append(paramCharSequence, paramMap);
    paramCharSequence = paramMap;
    if (paramBoolean) {
      paramCharSequence = encode(paramMap);
    }
    return get(paramCharSequence);
  }
  
  public static HttpRequest get(CharSequence paramCharSequence, boolean paramBoolean, Object... paramVarArgs)
  {
    paramVarArgs = append(paramCharSequence, paramVarArgs);
    paramCharSequence = paramVarArgs;
    if (paramBoolean) {
      paramCharSequence = encode(paramVarArgs);
    }
    return get(paramCharSequence);
  }
  
  public static HttpRequest get(URL paramURL)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramURL, "GET");
  }
  
  private static SSLSocketFactory getTrustedFactory()
    throws HttpRequest.HttpRequestException
  {
    X509TrustManager local1;
    if (TRUSTED_FACTORY == null) {
      local1 = new X509TrustManager()
      {
        public void checkClientTrusted(X509Certificate[] paramAnonymousArrayOfX509Certificate, String paramAnonymousString) {}
        
        public void checkServerTrusted(X509Certificate[] paramAnonymousArrayOfX509Certificate, String paramAnonymousString) {}
        
        public X509Certificate[] getAcceptedIssuers()
        {
          return new X509Certificate[0];
        }
      };
    }
    try
    {
      localObject = SSLContext.getInstance("TLS");
      SecureRandom localSecureRandom = new SecureRandom();
      ((SSLContext)localObject).init(null, new TrustManager[] { local1 }, localSecureRandom);
      TRUSTED_FACTORY = ((SSLContext)localObject).getSocketFactory();
      return TRUSTED_FACTORY;
    }
    catch (GeneralSecurityException localGeneralSecurityException)
    {
      Object localObject = new IOException("Security exception configuring SSL context");
      ((IOException)localObject).initCause(localGeneralSecurityException);
      throw new HttpRequestException((IOException)localObject);
    }
  }
  
  private static HostnameVerifier getTrustedVerifier()
  {
    if (TRUSTED_VERIFIER == null) {
      TRUSTED_VERIFIER = new HostnameVerifier()
      {
        public boolean verify(String paramAnonymousString, SSLSession paramAnonymousSSLSession)
        {
          return true;
        }
      };
    }
    return TRUSTED_VERIFIER;
  }
  
  private static String getValidCharset(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      return paramString;
    }
    return "UTF-8";
  }
  
  public static HttpRequest head(CharSequence paramCharSequence)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramCharSequence, "HEAD");
  }
  
  public static HttpRequest head(CharSequence paramCharSequence, Map<?, ?> paramMap, boolean paramBoolean)
  {
    paramMap = append(paramCharSequence, paramMap);
    paramCharSequence = paramMap;
    if (paramBoolean) {
      paramCharSequence = encode(paramMap);
    }
    return head(paramCharSequence);
  }
  
  public static HttpRequest head(CharSequence paramCharSequence, boolean paramBoolean, Object... paramVarArgs)
  {
    paramVarArgs = append(paramCharSequence, paramVarArgs);
    paramCharSequence = paramVarArgs;
    if (paramBoolean) {
      paramCharSequence = encode(paramVarArgs);
    }
    return head(paramCharSequence);
  }
  
  public static HttpRequest head(URL paramURL)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramURL, "HEAD");
  }
  
  public static void keepAlive(boolean paramBoolean)
  {
    setProperty("http.keepAlive", Boolean.toString(paramBoolean));
  }
  
  public static void nonProxyHosts(String... paramVarArgs)
  {
    if ((paramVarArgs != null) && (paramVarArgs.length > 0))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      int j = paramVarArgs.length - 1;
      int i = 0;
      while (i < j)
      {
        localStringBuilder.append(paramVarArgs[i]).append('|');
        i += 1;
      }
      localStringBuilder.append(paramVarArgs[j]);
      setProperty("http.nonProxyHosts", localStringBuilder.toString());
      return;
    }
    setProperty("http.nonProxyHosts", null);
  }
  
  public static HttpRequest options(CharSequence paramCharSequence)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramCharSequence, "OPTIONS");
  }
  
  public static HttpRequest options(URL paramURL)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramURL, "OPTIONS");
  }
  
  public static HttpRequest post(CharSequence paramCharSequence)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramCharSequence, "POST");
  }
  
  public static HttpRequest post(CharSequence paramCharSequence, Map<?, ?> paramMap, boolean paramBoolean)
  {
    paramMap = append(paramCharSequence, paramMap);
    paramCharSequence = paramMap;
    if (paramBoolean) {
      paramCharSequence = encode(paramMap);
    }
    return post(paramCharSequence);
  }
  
  public static HttpRequest post(CharSequence paramCharSequence, boolean paramBoolean, Object... paramVarArgs)
  {
    paramVarArgs = append(paramCharSequence, paramVarArgs);
    paramCharSequence = paramVarArgs;
    if (paramBoolean) {
      paramCharSequence = encode(paramVarArgs);
    }
    return post(paramCharSequence);
  }
  
  public static HttpRequest post(URL paramURL)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramURL, "POST");
  }
  
  public static void proxyHost(String paramString)
  {
    setProperty("http.proxyHost", paramString);
    setProperty("https.proxyHost", paramString);
  }
  
  public static void proxyPort(int paramInt)
  {
    String str = Integer.toString(paramInt);
    setProperty("http.proxyPort", str);
    setProperty("https.proxyPort", str);
  }
  
  public static HttpRequest put(CharSequence paramCharSequence)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramCharSequence, "PUT");
  }
  
  public static HttpRequest put(CharSequence paramCharSequence, Map<?, ?> paramMap, boolean paramBoolean)
  {
    paramMap = append(paramCharSequence, paramMap);
    paramCharSequence = paramMap;
    if (paramBoolean) {
      paramCharSequence = encode(paramMap);
    }
    return put(paramCharSequence);
  }
  
  public static HttpRequest put(CharSequence paramCharSequence, boolean paramBoolean, Object... paramVarArgs)
  {
    paramVarArgs = append(paramCharSequence, paramVarArgs);
    paramCharSequence = paramVarArgs;
    if (paramBoolean) {
      paramCharSequence = encode(paramVarArgs);
    }
    return put(paramCharSequence);
  }
  
  public static HttpRequest put(URL paramURL)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramURL, "PUT");
  }
  
  public static void setConnectionFactory(ConnectionFactory paramConnectionFactory)
  {
    if (paramConnectionFactory == null)
    {
      CONNECTION_FACTORY = ConnectionFactory.DEFAULT;
      return;
    }
    CONNECTION_FACTORY = paramConnectionFactory;
  }
  
  private static String setProperty(String paramString1, final String paramString2)
  {
    if (paramString2 != null) {}
    for (paramString1 = new PrivilegedAction()
        {
          public String run()
          {
            return System.setProperty(this.val$name, paramString2);
          }
        };; paramString1 = new PrivilegedAction()
        {
          public String run()
          {
            return System.clearProperty(this.val$name);
          }
        }) {
      return (String)AccessController.doPrivileged(paramString1);
    }
  }
  
  public static HttpRequest trace(CharSequence paramCharSequence)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramCharSequence, "TRACE");
  }
  
  public static HttpRequest trace(URL paramURL)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramURL, "TRACE");
  }
  
  public HttpRequest accept(String paramString)
  {
    return header("Accept", paramString);
  }
  
  public HttpRequest acceptCharset(String paramString)
  {
    return header("Accept-Charset", paramString);
  }
  
  public HttpRequest acceptEncoding(String paramString)
  {
    return header("Accept-Encoding", paramString);
  }
  
  public HttpRequest acceptGzipEncoding()
  {
    return acceptEncoding("gzip");
  }
  
  public HttpRequest acceptJson()
  {
    return accept("application/json");
  }
  
  public HttpRequest authorization(String paramString)
  {
    return header("Authorization", paramString);
  }
  
  public boolean badRequest()
    throws HttpRequest.HttpRequestException
  {
    return 400 == code();
  }
  
  public HttpRequest basic(String paramString1, String paramString2)
  {
    return authorization("Basic " + Base64.encode(new StringBuilder().append(paramString1).append(':').append(paramString2).toString()));
  }
  
  public HttpRequest body(AtomicReference<String> paramAtomicReference)
    throws HttpRequest.HttpRequestException
  {
    paramAtomicReference.set(body());
    return this;
  }
  
  public HttpRequest body(AtomicReference<String> paramAtomicReference, String paramString)
    throws HttpRequest.HttpRequestException
  {
    paramAtomicReference.set(body(paramString));
    return this;
  }
  
  public String body()
    throws HttpRequest.HttpRequestException
  {
    return body(charset());
  }
  
  public String body(String paramString)
    throws HttpRequest.HttpRequestException
  {
    ByteArrayOutputStream localByteArrayOutputStream = byteStream();
    try
    {
      copy(buffer(), localByteArrayOutputStream);
      paramString = localByteArrayOutputStream.toString(getValidCharset(paramString));
      return paramString;
    }
    catch (IOException paramString)
    {
      throw new HttpRequestException(paramString);
    }
  }
  
  public BufferedInputStream buffer()
    throws HttpRequest.HttpRequestException
  {
    return new BufferedInputStream(stream(), this.bufferSize);
  }
  
  public int bufferSize()
  {
    return this.bufferSize;
  }
  
  public HttpRequest bufferSize(int paramInt)
  {
    if (paramInt < 1) {
      throw new IllegalArgumentException("Size must be greater than zero");
    }
    this.bufferSize = paramInt;
    return this;
  }
  
  public BufferedReader bufferedReader()
    throws HttpRequest.HttpRequestException
  {
    return bufferedReader(charset());
  }
  
  public BufferedReader bufferedReader(String paramString)
    throws HttpRequest.HttpRequestException
  {
    return new BufferedReader(reader(paramString), this.bufferSize);
  }
  
  protected ByteArrayOutputStream byteStream()
  {
    int i = contentLength();
    if (i > 0) {
      return new ByteArrayOutputStream(i);
    }
    return new ByteArrayOutputStream();
  }
  
  public byte[] bytes()
    throws HttpRequest.HttpRequestException
  {
    ByteArrayOutputStream localByteArrayOutputStream = byteStream();
    try
    {
      copy(buffer(), localByteArrayOutputStream);
      return localByteArrayOutputStream.toByteArray();
    }
    catch (IOException localIOException)
    {
      throw new HttpRequestException(localIOException);
    }
  }
  
  public String cacheControl()
  {
    return header("Cache-Control");
  }
  
  public String charset()
  {
    return parameter("Content-Type", "charset");
  }
  
  public HttpRequest chunk(int paramInt)
  {
    getConnection().setChunkedStreamingMode(paramInt);
    return this;
  }
  
  protected HttpRequest closeOutput()
    throws IOException
  {
    if (this.output == null) {
      return this;
    }
    if (this.multipart) {
      this.output.write("\r\n--00content0boundary00--\r\n");
    }
    if (this.ignoreCloseExceptions) {}
    try
    {
      this.output.close();
      for (;;)
      {
        this.output = null;
        return this;
        this.output.close();
      }
    }
    catch (IOException localIOException)
    {
      for (;;) {}
    }
  }
  
  protected HttpRequest closeOutputQuietly()
    throws HttpRequest.HttpRequestException
  {
    try
    {
      HttpRequest localHttpRequest = closeOutput();
      return localHttpRequest;
    }
    catch (IOException localIOException)
    {
      throw new HttpRequestException(localIOException);
    }
  }
  
  public int code()
    throws HttpRequest.HttpRequestException
  {
    try
    {
      closeOutput();
      int i = getConnection().getResponseCode();
      return i;
    }
    catch (IOException localIOException)
    {
      throw new HttpRequestException(localIOException);
    }
  }
  
  public HttpRequest code(AtomicInteger paramAtomicInteger)
    throws HttpRequest.HttpRequestException
  {
    paramAtomicInteger.set(code());
    return this;
  }
  
  public HttpRequest connectTimeout(int paramInt)
  {
    getConnection().setConnectTimeout(paramInt);
    return this;
  }
  
  public String contentEncoding()
  {
    return header("Content-Encoding");
  }
  
  public int contentLength()
  {
    return intHeader("Content-Length");
  }
  
  public HttpRequest contentLength(int paramInt)
  {
    getConnection().setFixedLengthStreamingMode(paramInt);
    return this;
  }
  
  public HttpRequest contentLength(String paramString)
  {
    return contentLength(Integer.parseInt(paramString));
  }
  
  public HttpRequest contentType(String paramString)
  {
    return contentType(paramString, null);
  }
  
  public HttpRequest contentType(String paramString1, String paramString2)
  {
    if ((paramString2 != null) && (paramString2.length() > 0)) {
      return header("Content-Type", paramString1 + "; charset=" + paramString2);
    }
    return header("Content-Type", paramString1);
  }
  
  public String contentType()
  {
    return header("Content-Type");
  }
  
  protected HttpRequest copy(final InputStream paramInputStream, final OutputStream paramOutputStream)
    throws IOException
  {
    (HttpRequest)new CloseOperation(paramInputStream, this.ignoreCloseExceptions)
    {
      public HttpRequest run()
        throws IOException
      {
        byte[] arrayOfByte = new byte[HttpRequest.this.bufferSize];
        for (;;)
        {
          int i = paramInputStream.read(arrayOfByte);
          if (i == -1) {
            break;
          }
          paramOutputStream.write(arrayOfByte, 0, i);
        }
        return HttpRequest.this;
      }
    }.call();
  }
  
  protected HttpRequest copy(final Reader paramReader, final Writer paramWriter)
    throws IOException
  {
    (HttpRequest)new CloseOperation(paramReader, this.ignoreCloseExceptions)
    {
      public HttpRequest run()
        throws IOException
      {
        char[] arrayOfChar = new char[HttpRequest.this.bufferSize];
        for (;;)
        {
          int i = paramReader.read(arrayOfChar);
          if (i == -1) {
            break;
          }
          paramWriter.write(arrayOfChar, 0, i);
        }
        return HttpRequest.this;
      }
    }.call();
  }
  
  public boolean created()
    throws HttpRequest.HttpRequestException
  {
    return 201 == code();
  }
  
  public long date()
  {
    return dateHeader("Date");
  }
  
  public long dateHeader(String paramString)
    throws HttpRequest.HttpRequestException
  {
    return dateHeader(paramString, -1L);
  }
  
  public long dateHeader(String paramString, long paramLong)
    throws HttpRequest.HttpRequestException
  {
    closeOutputQuietly();
    return getConnection().getHeaderFieldDate(paramString, paramLong);
  }
  
  public HttpRequest disconnect()
  {
    getConnection().disconnect();
    return this;
  }
  
  public String eTag()
  {
    return header("ETag");
  }
  
  public long expires()
  {
    return dateHeader("Expires");
  }
  
  public HttpRequest followRedirects(boolean paramBoolean)
  {
    getConnection().setInstanceFollowRedirects(paramBoolean);
    return this;
  }
  
  public HttpRequest form(Object paramObject1, Object paramObject2)
    throws HttpRequest.HttpRequestException
  {
    return form(paramObject1, paramObject2, "UTF-8");
  }
  
  public HttpRequest form(Object paramObject1, Object paramObject2, String paramString)
    throws HttpRequest.HttpRequestException
  {
    if (!this.form) {}
    for (int i = 1;; i = 0)
    {
      if (i != 0)
      {
        contentType("application/x-www-form-urlencoded", paramString);
        this.form = true;
      }
      paramString = getValidCharset(paramString);
      try
      {
        openOutput();
        if (i == 0) {
          this.output.write(38);
        }
        this.output.write(URLEncoder.encode(paramObject1.toString(), paramString));
        this.output.write(61);
        if (paramObject2 != null) {
          this.output.write(URLEncoder.encode(paramObject2.toString(), paramString));
        }
        return this;
      }
      catch (IOException paramObject1)
      {
        throw new HttpRequestException((IOException)paramObject1);
      }
    }
  }
  
  public HttpRequest form(Map.Entry<?, ?> paramEntry)
    throws HttpRequest.HttpRequestException
  {
    return form(paramEntry, "UTF-8");
  }
  
  public HttpRequest form(Map.Entry<?, ?> paramEntry, String paramString)
    throws HttpRequest.HttpRequestException
  {
    return form(paramEntry.getKey(), paramEntry.getValue(), paramString);
  }
  
  public HttpRequest form(Map<?, ?> paramMap)
    throws HttpRequest.HttpRequestException
  {
    return form(paramMap, "UTF-8");
  }
  
  public HttpRequest form(Map<?, ?> paramMap, String paramString)
    throws HttpRequest.HttpRequestException
  {
    if (!paramMap.isEmpty())
    {
      paramMap = paramMap.entrySet().iterator();
      while (paramMap.hasNext()) {
        form((Map.Entry)paramMap.next(), paramString);
      }
    }
    return this;
  }
  
  public HttpURLConnection getConnection()
  {
    if (this.connection == null) {
      this.connection = createConnection();
    }
    return this.connection;
  }
  
  protected String getParam(String paramString1, String paramString2)
  {
    if ((paramString1 == null) || (paramString1.length() == 0))
    {
      paramString1 = null;
      return paramString1;
    }
    int j = paramString1.length();
    int m = paramString1.indexOf(';') + 1;
    if ((m == 0) || (m == j)) {
      return null;
    }
    int n = paramString1.indexOf(';', m);
    int i = n;
    int k = m;
    if (n == -1)
    {
      i = j;
      k = m;
    }
    for (;;)
    {
      if (k >= i) {
        break label236;
      }
      m = paramString1.indexOf('=', k);
      if ((m != -1) && (m < i) && (paramString2.equals(paramString1.substring(k, m).trim())))
      {
        String str = paramString1.substring(m + 1, i).trim();
        k = str.length();
        if (k != 0)
        {
          paramString1 = str;
          if (k <= 2) {
            break;
          }
          paramString1 = str;
          if ('"' != str.charAt(0)) {
            break;
          }
          paramString1 = str;
          if ('"' != str.charAt(k - 1)) {
            break;
          }
          return str.substring(1, k - 1);
        }
      }
      m = i + 1;
      n = paramString1.indexOf(';', m);
      i = n;
      k = m;
      if (n == -1)
      {
        i = j;
        k = m;
      }
    }
    label236:
    return null;
  }
  
  protected Map<String, String> getParams(String paramString)
  {
    Object localObject;
    if ((paramString == null) || (paramString.length() == 0)) {
      localObject = Collections.emptyMap();
    }
    int j;
    int k;
    int i;
    LinkedHashMap localLinkedHashMap;
    do
    {
      return (Map<String, String>)localObject;
      j = paramString.length();
      k = paramString.indexOf(';') + 1;
      if ((k == 0) || (k == j)) {
        return Collections.emptyMap();
      }
      m = paramString.indexOf(';', k);
      i = m;
      if (m == -1) {
        i = j;
      }
      localLinkedHashMap = new LinkedHashMap();
      localObject = localLinkedHashMap;
    } while (k >= i);
    int m = paramString.indexOf('=', k);
    String str;
    if ((m != -1) && (m < i))
    {
      localObject = paramString.substring(k, m).trim();
      if (((String)localObject).length() > 0)
      {
        str = paramString.substring(m + 1, i).trim();
        k = str.length();
        if (k != 0)
        {
          if ((k <= 2) || ('"' != str.charAt(0)) || ('"' != str.charAt(k - 1))) {
            break label246;
          }
          localLinkedHashMap.put(localObject, str.substring(1, k - 1));
        }
      }
    }
    for (;;)
    {
      m = i + 1;
      int n = paramString.indexOf(';', m);
      i = n;
      k = m;
      if (n != -1) {
        break;
      }
      i = j;
      k = m;
      break;
      label246:
      localLinkedHashMap.put(localObject, str);
    }
  }
  
  public HttpRequest header(String paramString, Number paramNumber)
  {
    if (paramNumber != null) {}
    for (paramNumber = paramNumber.toString();; paramNumber = null) {
      return header(paramString, paramNumber);
    }
  }
  
  public HttpRequest header(String paramString1, String paramString2)
  {
    getConnection().setRequestProperty(paramString1, paramString2);
    return this;
  }
  
  public HttpRequest header(Map.Entry<String, String> paramEntry)
  {
    return header((String)paramEntry.getKey(), (String)paramEntry.getValue());
  }
  
  public String header(String paramString)
    throws HttpRequest.HttpRequestException
  {
    closeOutputQuietly();
    return getConnection().getHeaderField(paramString);
  }
  
  public HttpRequest headers(Map<String, String> paramMap)
  {
    if (!paramMap.isEmpty())
    {
      paramMap = paramMap.entrySet().iterator();
      while (paramMap.hasNext()) {
        header((Map.Entry)paramMap.next());
      }
    }
    return this;
  }
  
  public Map<String, List<String>> headers()
    throws HttpRequest.HttpRequestException
  {
    closeOutputQuietly();
    return getConnection().getHeaderFields();
  }
  
  public String[] headers(String paramString)
  {
    Map localMap = headers();
    if ((localMap == null) || (localMap.isEmpty())) {
      return EMPTY_STRINGS;
    }
    paramString = (List)localMap.get(paramString);
    if ((paramString != null) && (!paramString.isEmpty())) {
      return (String[])paramString.toArray(new String[paramString.size()]);
    }
    return EMPTY_STRINGS;
  }
  
  public HttpRequest ifModifiedSince(long paramLong)
  {
    getConnection().setIfModifiedSince(paramLong);
    return this;
  }
  
  public HttpRequest ifNoneMatch(String paramString)
  {
    return header("If-None-Match", paramString);
  }
  
  public HttpRequest ignoreCloseExceptions(boolean paramBoolean)
  {
    this.ignoreCloseExceptions = paramBoolean;
    return this;
  }
  
  public boolean ignoreCloseExceptions()
  {
    return this.ignoreCloseExceptions;
  }
  
  public int intHeader(String paramString)
    throws HttpRequest.HttpRequestException
  {
    return intHeader(paramString, -1);
  }
  
  public int intHeader(String paramString, int paramInt)
    throws HttpRequest.HttpRequestException
  {
    closeOutputQuietly();
    return getConnection().getHeaderFieldInt(paramString, paramInt);
  }
  
  public boolean isBodyEmpty()
    throws HttpRequest.HttpRequestException
  {
    return contentLength() == 0;
  }
  
  public long lastModified()
  {
    return dateHeader("Last-Modified");
  }
  
  public String location()
  {
    return header("Location");
  }
  
  public String message()
    throws HttpRequest.HttpRequestException
  {
    try
    {
      closeOutput();
      String str = getConnection().getResponseMessage();
      return str;
    }
    catch (IOException localIOException)
    {
      throw new HttpRequestException(localIOException);
    }
  }
  
  public String method()
  {
    return getConnection().getRequestMethod();
  }
  
  public boolean notFound()
    throws HttpRequest.HttpRequestException
  {
    return 404 == code();
  }
  
  public boolean notModified()
    throws HttpRequest.HttpRequestException
  {
    return 304 == code();
  }
  
  public boolean ok()
    throws HttpRequest.HttpRequestException
  {
    return 200 == code();
  }
  
  protected HttpRequest openOutput()
    throws IOException
  {
    if (this.output != null) {
      return this;
    }
    getConnection().setDoOutput(true);
    String str = getParam(getConnection().getRequestProperty("Content-Type"), "charset");
    this.output = new RequestOutputStream(getConnection().getOutputStream(), str, this.bufferSize);
    return this;
  }
  
  public String parameter(String paramString1, String paramString2)
  {
    return getParam(header(paramString1), paramString2);
  }
  
  public Map<String, String> parameters(String paramString)
  {
    return getParams(header(paramString));
  }
  
  public HttpRequest part(String paramString, File paramFile)
    throws HttpRequest.HttpRequestException
  {
    return part(paramString, null, paramFile);
  }
  
  public HttpRequest part(String paramString, InputStream paramInputStream)
    throws HttpRequest.HttpRequestException
  {
    return part(paramString, null, null, paramInputStream);
  }
  
  public HttpRequest part(String paramString, Number paramNumber)
    throws HttpRequest.HttpRequestException
  {
    return part(paramString, null, paramNumber);
  }
  
  public HttpRequest part(String paramString1, String paramString2)
  {
    return part(paramString1, null, paramString2);
  }
  
  public HttpRequest part(String paramString1, String paramString2, File paramFile)
    throws HttpRequest.HttpRequestException
  {
    return part(paramString1, paramString2, null, paramFile);
  }
  
  public HttpRequest part(String paramString1, String paramString2, Number paramNumber)
    throws HttpRequest.HttpRequestException
  {
    if (paramNumber != null) {}
    for (paramNumber = paramNumber.toString();; paramNumber = null) {
      return part(paramString1, paramString2, paramNumber);
    }
  }
  
  public HttpRequest part(String paramString1, String paramString2, String paramString3)
    throws HttpRequest.HttpRequestException
  {
    return part(paramString1, paramString2, null, paramString3);
  }
  
  /* Error */
  public HttpRequest part(String paramString1, String paramString2, String paramString3, File paramFile)
    throws HttpRequest.HttpRequestException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 5
    //   3: aconst_null
    //   4: astore 6
    //   6: new 566	java/io/BufferedInputStream
    //   9: dup
    //   10: new 855	java/io/FileInputStream
    //   13: dup
    //   14: aload 4
    //   16: invokespecial 858	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   19: invokespecial 861	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   22: astore 4
    //   24: aload_0
    //   25: aload_1
    //   26: aload_2
    //   27: aload_3
    //   28: aload 4
    //   30: invokevirtual 841	io/fabric/sdk/android/services/network/HttpRequest:part	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/io/InputStream;)Lio/fabric/sdk/android/services/network/HttpRequest;
    //   33: astore_1
    //   34: aload 4
    //   36: ifnull +8 -> 44
    //   39: aload 4
    //   41: invokevirtual 864	java/io/InputStream:close	()V
    //   44: aload_1
    //   45: areturn
    //   46: astore_1
    //   47: aload 6
    //   49: astore 5
    //   51: new 40	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   54: dup
    //   55: aload_1
    //   56: invokespecial 215	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException:<init>	(Ljava/io/IOException;)V
    //   59: athrow
    //   60: astore_1
    //   61: aload 5
    //   63: ifnull +8 -> 71
    //   66: aload 5
    //   68: invokevirtual 864	java/io/InputStream:close	()V
    //   71: aload_1
    //   72: athrow
    //   73: astore_2
    //   74: aload_1
    //   75: areturn
    //   76: astore_2
    //   77: goto -6 -> 71
    //   80: astore_1
    //   81: aload 4
    //   83: astore 5
    //   85: goto -24 -> 61
    //   88: astore_1
    //   89: aload 4
    //   91: astore 5
    //   93: goto -42 -> 51
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	96	0	this	HttpRequest
    //   0	96	1	paramString1	String
    //   0	96	2	paramString2	String
    //   0	96	3	paramString3	String
    //   0	96	4	paramFile	File
    //   1	91	5	localObject1	Object
    //   4	44	6	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   6	24	46	java/io/IOException
    //   6	24	60	finally
    //   51	60	60	finally
    //   39	44	73	java/io/IOException
    //   66	71	76	java/io/IOException
    //   24	34	80	finally
    //   24	34	88	java/io/IOException
  }
  
  public HttpRequest part(String paramString1, String paramString2, String paramString3, InputStream paramInputStream)
    throws HttpRequest.HttpRequestException
  {
    try
    {
      startPart();
      writePartHeader(paramString1, paramString2, paramString3);
      copy(paramInputStream, this.output);
      return this;
    }
    catch (IOException paramString1)
    {
      throw new HttpRequestException(paramString1);
    }
  }
  
  public HttpRequest part(String paramString1, String paramString2, String paramString3, String paramString4)
    throws HttpRequest.HttpRequestException
  {
    try
    {
      startPart();
      writePartHeader(paramString1, paramString2, paramString3);
      this.output.write(paramString4);
      return this;
    }
    catch (IOException paramString1)
    {
      throw new HttpRequestException(paramString1);
    }
  }
  
  public HttpRequest partHeader(String paramString1, String paramString2)
    throws HttpRequest.HttpRequestException
  {
    return send(paramString1).send(": ").send(paramString2).send("\r\n");
  }
  
  public HttpRequest proxyAuthorization(String paramString)
  {
    return header("Proxy-Authorization", paramString);
  }
  
  public HttpRequest proxyBasic(String paramString1, String paramString2)
  {
    return proxyAuthorization("Basic " + Base64.encode(new StringBuilder().append(paramString1).append(':').append(paramString2).toString()));
  }
  
  public HttpRequest readTimeout(int paramInt)
  {
    getConnection().setReadTimeout(paramInt);
    return this;
  }
  
  public InputStreamReader reader()
    throws HttpRequest.HttpRequestException
  {
    return reader(charset());
  }
  
  public InputStreamReader reader(String paramString)
    throws HttpRequest.HttpRequestException
  {
    try
    {
      paramString = new InputStreamReader(stream(), getValidCharset(paramString));
      return paramString;
    }
    catch (UnsupportedEncodingException paramString)
    {
      throw new HttpRequestException(paramString);
    }
  }
  
  public HttpRequest receive(final File paramFile)
    throws HttpRequest.HttpRequestException
  {
    try
    {
      paramFile = new BufferedOutputStream(new FileOutputStream(paramFile), this.bufferSize);
      (HttpRequest)new CloseOperation(paramFile, this.ignoreCloseExceptions)
      {
        protected HttpRequest run()
          throws HttpRequest.HttpRequestException, IOException
        {
          return HttpRequest.this.receive(paramFile);
        }
      }.call();
    }
    catch (FileNotFoundException paramFile)
    {
      throw new HttpRequestException(paramFile);
    }
  }
  
  public HttpRequest receive(OutputStream paramOutputStream)
    throws HttpRequest.HttpRequestException
  {
    try
    {
      paramOutputStream = copy(buffer(), paramOutputStream);
      return paramOutputStream;
    }
    catch (IOException paramOutputStream)
    {
      throw new HttpRequestException(paramOutputStream);
    }
  }
  
  public HttpRequest receive(PrintStream paramPrintStream)
    throws HttpRequest.HttpRequestException
  {
    return receive(paramPrintStream);
  }
  
  public HttpRequest receive(final Writer paramWriter)
    throws HttpRequest.HttpRequestException
  {
    final BufferedReader localBufferedReader = bufferedReader();
    (HttpRequest)new CloseOperation(localBufferedReader, this.ignoreCloseExceptions)
    {
      public HttpRequest run()
        throws IOException
      {
        return HttpRequest.this.copy(localBufferedReader, paramWriter);
      }
    }.call();
  }
  
  public HttpRequest receive(final Appendable paramAppendable)
    throws HttpRequest.HttpRequestException
  {
    final BufferedReader localBufferedReader = bufferedReader();
    (HttpRequest)new CloseOperation(localBufferedReader, this.ignoreCloseExceptions)
    {
      public HttpRequest run()
        throws IOException
      {
        CharBuffer localCharBuffer = CharBuffer.allocate(HttpRequest.this.bufferSize);
        for (;;)
        {
          int i = localBufferedReader.read(localCharBuffer);
          if (i == -1) {
            break;
          }
          localCharBuffer.rewind();
          paramAppendable.append(localCharBuffer, 0, i);
          localCharBuffer.rewind();
        }
        return HttpRequest.this;
      }
    }.call();
  }
  
  public HttpRequest referer(String paramString)
  {
    return header("Referer", paramString);
  }
  
  public HttpRequest send(File paramFile)
    throws HttpRequest.HttpRequestException
  {
    try
    {
      paramFile = new BufferedInputStream(new FileInputStream(paramFile));
      return send(paramFile);
    }
    catch (FileNotFoundException paramFile)
    {
      throw new HttpRequestException(paramFile);
    }
  }
  
  public HttpRequest send(InputStream paramInputStream)
    throws HttpRequest.HttpRequestException
  {
    try
    {
      openOutput();
      copy(paramInputStream, this.output);
      return this;
    }
    catch (IOException paramInputStream)
    {
      throw new HttpRequestException(paramInputStream);
    }
  }
  
  public HttpRequest send(final Reader paramReader)
    throws HttpRequest.HttpRequestException
  {
    try
    {
      openOutput();
      final OutputStreamWriter localOutputStreamWriter = new OutputStreamWriter(this.output, this.output.encoder.charset());
      (HttpRequest)new FlushOperation(localOutputStreamWriter)
      {
        protected HttpRequest run()
          throws IOException
        {
          return HttpRequest.this.copy(paramReader, localOutputStreamWriter);
        }
      }.call();
    }
    catch (IOException paramReader)
    {
      throw new HttpRequestException(paramReader);
    }
  }
  
  public HttpRequest send(CharSequence paramCharSequence)
    throws HttpRequest.HttpRequestException
  {
    try
    {
      openOutput();
      this.output.write(paramCharSequence.toString());
      return this;
    }
    catch (IOException paramCharSequence)
    {
      throw new HttpRequestException(paramCharSequence);
    }
  }
  
  public HttpRequest send(byte[] paramArrayOfByte)
    throws HttpRequest.HttpRequestException
  {
    return send(new ByteArrayInputStream(paramArrayOfByte));
  }
  
  public String server()
  {
    return header("Server");
  }
  
  public boolean serverError()
    throws HttpRequest.HttpRequestException
  {
    return 500 == code();
  }
  
  protected HttpRequest startPart()
    throws IOException
  {
    if (!this.multipart)
    {
      this.multipart = true;
      contentType("multipart/form-data; boundary=00content0boundary00").openOutput();
      this.output.write("--00content0boundary00\r\n");
      return this;
    }
    this.output.write("\r\n--00content0boundary00\r\n");
    return this;
  }
  
  public InputStream stream()
    throws HttpRequest.HttpRequestException
  {
    if (code() < 400) {}
    for (;;)
    {
      try
      {
        InputStream localInputStream1 = getConnection().getInputStream();
        if ((this.uncompress) && ("gzip".equals(contentEncoding()))) {
          break;
        }
        return localInputStream1;
      }
      catch (IOException localIOException1)
      {
        throw new HttpRequestException(localIOException1);
      }
      InputStream localInputStream2 = getConnection().getErrorStream();
      Object localObject = localInputStream2;
      if (localInputStream2 == null) {
        try
        {
          localObject = getConnection().getInputStream();
        }
        catch (IOException localIOException2)
        {
          throw new HttpRequestException(localIOException2);
        }
      }
    }
    try
    {
      GZIPInputStream localGZIPInputStream = new GZIPInputStream(localIOException2);
      return localGZIPInputStream;
    }
    catch (IOException localIOException3)
    {
      throw new HttpRequestException(localIOException3);
    }
  }
  
  public String toString()
  {
    return method() + ' ' + url();
  }
  
  public HttpRequest trustAllCerts()
    throws HttpRequest.HttpRequestException
  {
    HttpURLConnection localHttpURLConnection = getConnection();
    if ((localHttpURLConnection instanceof HttpsURLConnection)) {
      ((HttpsURLConnection)localHttpURLConnection).setSSLSocketFactory(getTrustedFactory());
    }
    return this;
  }
  
  public HttpRequest trustAllHosts()
  {
    HttpURLConnection localHttpURLConnection = getConnection();
    if ((localHttpURLConnection instanceof HttpsURLConnection)) {
      ((HttpsURLConnection)localHttpURLConnection).setHostnameVerifier(getTrustedVerifier());
    }
    return this;
  }
  
  public HttpRequest uncompress(boolean paramBoolean)
  {
    this.uncompress = paramBoolean;
    return this;
  }
  
  public URL url()
  {
    return getConnection().getURL();
  }
  
  public HttpRequest useCaches(boolean paramBoolean)
  {
    getConnection().setUseCaches(paramBoolean);
    return this;
  }
  
  public HttpRequest useProxy(String paramString, int paramInt)
  {
    if (this.connection != null) {
      throw new IllegalStateException("The connection has already been created. This method must be called before reading or writing to the request.");
    }
    this.httpProxyHost = paramString;
    this.httpProxyPort = paramInt;
    return this;
  }
  
  public HttpRequest userAgent(String paramString)
  {
    return header("User-Agent", paramString);
  }
  
  protected HttpRequest writePartHeader(String paramString1, String paramString2)
    throws IOException
  {
    return writePartHeader(paramString1, paramString2, null);
  }
  
  protected HttpRequest writePartHeader(String paramString1, String paramString2, String paramString3)
    throws IOException
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("form-data; name=\"").append(paramString1);
    if (paramString2 != null) {
      localStringBuilder.append("\"; filename=\"").append(paramString2);
    }
    localStringBuilder.append('"');
    partHeader("Content-Disposition", localStringBuilder.toString());
    if (paramString3 != null) {
      partHeader("Content-Type", paramString3);
    }
    return send("\r\n");
  }
  
  public OutputStreamWriter writer()
    throws HttpRequest.HttpRequestException
  {
    try
    {
      openOutput();
      OutputStreamWriter localOutputStreamWriter = new OutputStreamWriter(this.output, this.output.encoder.charset());
      return localOutputStreamWriter;
    }
    catch (IOException localIOException)
    {
      throw new HttpRequestException(localIOException);
    }
  }
  
  public static class Base64
  {
    private static final byte EQUALS_SIGN = 61;
    private static final String PREFERRED_ENCODING = "US-ASCII";
    private static final byte[] _STANDARD_ALPHABET = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
    
    public static String encode(String paramString)
    {
      try
      {
        byte[] arrayOfByte = paramString.getBytes("US-ASCII");
        paramString = arrayOfByte;
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        for (;;)
        {
          paramString = paramString.getBytes();
        }
      }
      return encodeBytes(paramString);
    }
    
    private static byte[] encode3to4(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3)
    {
      int k = 0;
      byte[] arrayOfByte = _STANDARD_ALPHABET;
      int i;
      if (paramInt2 > 0)
      {
        i = paramArrayOfByte1[paramInt1] << 24 >>> 8;
        label23:
        if (paramInt2 <= 1) {
          break label104;
        }
      }
      label104:
      for (int j = paramArrayOfByte1[(paramInt1 + 1)] << 24 >>> 16;; j = 0)
      {
        if (paramInt2 > 2) {
          k = paramArrayOfByte1[(paramInt1 + 2)] << 24 >>> 24;
        }
        paramInt1 = j | i | k;
        switch (paramInt2)
        {
        default: 
          return paramArrayOfByte2;
          i = 0;
          break label23;
        }
      }
      paramArrayOfByte2[paramInt3] = arrayOfByte[(paramInt1 >>> 18)];
      paramArrayOfByte2[(paramInt3 + 1)] = arrayOfByte[(paramInt1 >>> 12 & 0x3F)];
      paramArrayOfByte2[(paramInt3 + 2)] = arrayOfByte[(paramInt1 >>> 6 & 0x3F)];
      paramArrayOfByte2[(paramInt3 + 3)] = arrayOfByte[(paramInt1 & 0x3F)];
      return paramArrayOfByte2;
      paramArrayOfByte2[paramInt3] = arrayOfByte[(paramInt1 >>> 18)];
      paramArrayOfByte2[(paramInt3 + 1)] = arrayOfByte[(paramInt1 >>> 12 & 0x3F)];
      paramArrayOfByte2[(paramInt3 + 2)] = arrayOfByte[(paramInt1 >>> 6 & 0x3F)];
      paramArrayOfByte2[(paramInt3 + 3)] = 61;
      return paramArrayOfByte2;
      paramArrayOfByte2[paramInt3] = arrayOfByte[(paramInt1 >>> 18)];
      paramArrayOfByte2[(paramInt3 + 1)] = arrayOfByte[(paramInt1 >>> 12 & 0x3F)];
      paramArrayOfByte2[(paramInt3 + 2)] = 61;
      paramArrayOfByte2[(paramInt3 + 3)] = 61;
      return paramArrayOfByte2;
    }
    
    public static String encodeBytes(byte[] paramArrayOfByte)
    {
      return encodeBytes(paramArrayOfByte, 0, paramArrayOfByte.length);
    }
    
    public static String encodeBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    {
      paramArrayOfByte = encodeBytesToBytes(paramArrayOfByte, paramInt1, paramInt2);
      try
      {
        String str = new String(paramArrayOfByte, "US-ASCII");
        return str;
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException) {}
      return new String(paramArrayOfByte);
    }
    
    public static byte[] encodeBytesToBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    {
      if (paramArrayOfByte == null) {
        throw new NullPointerException("Cannot serialize a null array.");
      }
      if (paramInt1 < 0) {
        throw new IllegalArgumentException("Cannot have negative offset: " + paramInt1);
      }
      if (paramInt2 < 0) {
        throw new IllegalArgumentException("Cannot have length offset: " + paramInt2);
      }
      if (paramInt1 + paramInt2 > paramArrayOfByte.length) {
        throw new IllegalArgumentException(String.format("Cannot have offset of %d and length of %d with array of length %d", new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramArrayOfByte.length) }));
      }
      int j = paramInt2 / 3;
      if (paramInt2 % 3 > 0) {}
      byte[] arrayOfByte;
      for (int i = 4;; i = 0)
      {
        arrayOfByte = new byte[j * 4 + i];
        j = 0;
        i = 0;
        while (j < paramInt2 - 2)
        {
          encode3to4(paramArrayOfByte, j + paramInt1, 3, arrayOfByte, i);
          j += 3;
          i += 4;
        }
      }
      int k = i;
      if (j < paramInt2)
      {
        encode3to4(paramArrayOfByte, j + paramInt1, paramInt2 - j, arrayOfByte, i);
        k = i + 4;
      }
      if (k <= arrayOfByte.length - 1)
      {
        paramArrayOfByte = new byte[k];
        System.arraycopy(arrayOfByte, 0, paramArrayOfByte, 0, k);
        return paramArrayOfByte;
      }
      return arrayOfByte;
    }
  }
  
  protected static abstract class CloseOperation<V>
    extends HttpRequest.Operation<V>
  {
    private final Closeable closeable;
    private final boolean ignoreCloseExceptions;
    
    protected CloseOperation(Closeable paramCloseable, boolean paramBoolean)
    {
      this.closeable = paramCloseable;
      this.ignoreCloseExceptions = paramBoolean;
    }
    
    protected void done()
      throws IOException
    {
      if ((this.closeable instanceof Flushable)) {
        ((Flushable)this.closeable).flush();
      }
      if (this.ignoreCloseExceptions) {}
      try
      {
        this.closeable.close();
        return;
      }
      catch (IOException localIOException) {}
      this.closeable.close();
      return;
    }
  }
  
  public static abstract interface ConnectionFactory
  {
    public static final ConnectionFactory DEFAULT = new ConnectionFactory()
    {
      public HttpURLConnection create(URL paramAnonymousURL)
        throws IOException
      {
        return (HttpURLConnection)paramAnonymousURL.openConnection();
      }
      
      public HttpURLConnection create(URL paramAnonymousURL, Proxy paramAnonymousProxy)
        throws IOException
      {
        return (HttpURLConnection)paramAnonymousURL.openConnection(paramAnonymousProxy);
      }
    };
    
    public abstract HttpURLConnection create(URL paramURL)
      throws IOException;
    
    public abstract HttpURLConnection create(URL paramURL, Proxy paramProxy)
      throws IOException;
  }
  
  protected static abstract class FlushOperation<V>
    extends HttpRequest.Operation<V>
  {
    private final Flushable flushable;
    
    protected FlushOperation(Flushable paramFlushable)
    {
      this.flushable = paramFlushable;
    }
    
    protected void done()
      throws IOException
    {
      this.flushable.flush();
    }
  }
  
  public static class HttpRequestException
    extends RuntimeException
  {
    private static final long serialVersionUID = -1170466989781746231L;
    
    protected HttpRequestException(IOException paramIOException)
    {
      super();
    }
    
    public IOException getCause()
    {
      return (IOException)super.getCause();
    }
  }
  
  protected static abstract class Operation<V>
    implements Callable<V>
  {
    /* Error */
    public V call()
      throws HttpRequest.HttpRequestException
    {
      // Byte code:
      //   0: iconst_0
      //   1: istore_1
      //   2: aload_0
      //   3: invokevirtual 24	io/fabric/sdk/android/services/network/HttpRequest$Operation:run	()Ljava/lang/Object;
      //   6: astore_2
      //   7: aload_0
      //   8: invokevirtual 27	io/fabric/sdk/android/services/network/HttpRequest$Operation:done	()V
      //   11: aload_2
      //   12: areturn
      //   13: astore_3
      //   14: iconst_0
      //   15: ifne -4 -> 11
      //   18: new 19	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
      //   21: dup
      //   22: aload_3
      //   23: invokespecial 30	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException:<init>	(Ljava/io/IOException;)V
      //   26: athrow
      //   27: astore_2
      //   28: iconst_1
      //   29: istore_1
      //   30: aload_2
      //   31: athrow
      //   32: astore_2
      //   33: aload_0
      //   34: invokevirtual 27	io/fabric/sdk/android/services/network/HttpRequest$Operation:done	()V
      //   37: aload_2
      //   38: athrow
      //   39: astore_2
      //   40: iconst_1
      //   41: istore_1
      //   42: new 19	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
      //   45: dup
      //   46: aload_2
      //   47: invokespecial 30	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException:<init>	(Ljava/io/IOException;)V
      //   50: athrow
      //   51: astore_3
      //   52: iload_1
      //   53: ifne -16 -> 37
      //   56: new 19	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
      //   59: dup
      //   60: aload_3
      //   61: invokespecial 30	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException:<init>	(Ljava/io/IOException;)V
      //   64: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	65	0	this	Operation
      //   1	52	1	i	int
      //   6	6	2	localObject1	Object
      //   27	4	2	localHttpRequestException	HttpRequest.HttpRequestException
      //   32	6	2	localObject2	Object
      //   39	8	2	localIOException1	IOException
      //   13	10	3	localIOException2	IOException
      //   51	10	3	localIOException3	IOException
      // Exception table:
      //   from	to	target	type
      //   7	11	13	java/io/IOException
      //   2	7	27	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
      //   2	7	32	finally
      //   30	32	32	finally
      //   42	51	32	finally
      //   2	7	39	java/io/IOException
      //   33	37	51	java/io/IOException
    }
    
    protected abstract void done()
      throws IOException;
    
    protected abstract V run()
      throws HttpRequest.HttpRequestException, IOException;
  }
  
  public static class RequestOutputStream
    extends BufferedOutputStream
  {
    private final CharsetEncoder encoder;
    
    public RequestOutputStream(OutputStream paramOutputStream, String paramString, int paramInt)
    {
      super(paramInt);
      this.encoder = Charset.forName(HttpRequest.getValidCharset(paramString)).newEncoder();
    }
    
    public RequestOutputStream write(String paramString)
      throws IOException
    {
      paramString = this.encoder.encode(CharBuffer.wrap(paramString));
      super.write(paramString.array(), 0, paramString.limit());
      return this;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/io/fabric/sdk/android/services/network/HttpRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */