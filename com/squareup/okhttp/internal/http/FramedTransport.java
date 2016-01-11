package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Headers.Builder;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Response.Builder;
import com.squareup.okhttp.ResponseBody;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.framed.ErrorCode;
import com.squareup.okhttp.internal.framed.FramedConnection;
import com.squareup.okhttp.internal.framed.FramedStream;
import com.squareup.okhttp.internal.framed.Header;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import okio.ByteString;
import okio.Okio;
import okio.Sink;
import okio.Timeout;

public final class FramedTransport
  implements Transport
{
  private static final ByteString CONNECTION = ByteString.encodeUtf8("connection");
  private static final ByteString ENCODING;
  private static final ByteString HOST = ByteString.encodeUtf8("host");
  private static final List<ByteString> HTTP_2_SKIPPED_REQUEST_HEADERS = Util.immutableList(new ByteString[] { CONNECTION, HOST, KEEP_ALIVE, PROXY_CONNECTION, TE, TRANSFER_ENCODING, ENCODING, UPGRADE, Header.TARGET_METHOD, Header.TARGET_PATH, Header.TARGET_SCHEME, Header.TARGET_AUTHORITY, Header.TARGET_HOST, Header.VERSION });
  private static final List<ByteString> HTTP_2_SKIPPED_RESPONSE_HEADERS = Util.immutableList(new ByteString[] { CONNECTION, HOST, KEEP_ALIVE, PROXY_CONNECTION, TE, TRANSFER_ENCODING, ENCODING, UPGRADE });
  private static final ByteString KEEP_ALIVE = ByteString.encodeUtf8("keep-alive");
  private static final ByteString PROXY_CONNECTION = ByteString.encodeUtf8("proxy-connection");
  private static final List<ByteString> SPDY_3_SKIPPED_REQUEST_HEADERS;
  private static final List<ByteString> SPDY_3_SKIPPED_RESPONSE_HEADERS;
  private static final ByteString TE;
  private static final ByteString TRANSFER_ENCODING = ByteString.encodeUtf8("transfer-encoding");
  private static final ByteString UPGRADE;
  private final FramedConnection framedConnection;
  private final HttpEngine httpEngine;
  private FramedStream stream;
  
  static
  {
    TE = ByteString.encodeUtf8("te");
    ENCODING = ByteString.encodeUtf8("encoding");
    UPGRADE = ByteString.encodeUtf8("upgrade");
    SPDY_3_SKIPPED_REQUEST_HEADERS = Util.immutableList(new ByteString[] { CONNECTION, HOST, KEEP_ALIVE, PROXY_CONNECTION, TRANSFER_ENCODING, Header.TARGET_METHOD, Header.TARGET_PATH, Header.TARGET_SCHEME, Header.TARGET_AUTHORITY, Header.TARGET_HOST, Header.VERSION });
    SPDY_3_SKIPPED_RESPONSE_HEADERS = Util.immutableList(new ByteString[] { CONNECTION, HOST, KEEP_ALIVE, PROXY_CONNECTION, TRANSFER_ENCODING });
  }
  
  public FramedTransport(HttpEngine paramHttpEngine, FramedConnection paramFramedConnection)
  {
    this.httpEngine = paramHttpEngine;
    this.framedConnection = paramFramedConnection;
  }
  
  public static List<Header> http2HeadersList(Request paramRequest)
  {
    Headers localHeaders = paramRequest.headers();
    ArrayList localArrayList = new ArrayList(localHeaders.size() + 4);
    localArrayList.add(new Header(Header.TARGET_METHOD, paramRequest.method()));
    localArrayList.add(new Header(Header.TARGET_PATH, RequestLine.requestPath(paramRequest.httpUrl())));
    localArrayList.add(new Header(Header.TARGET_AUTHORITY, Util.hostHeader(paramRequest.httpUrl())));
    localArrayList.add(new Header(Header.TARGET_SCHEME, paramRequest.httpUrl().scheme()));
    int i = 0;
    int j = localHeaders.size();
    while (i < j)
    {
      paramRequest = ByteString.encodeUtf8(localHeaders.name(i).toLowerCase(Locale.US));
      if (!HTTP_2_SKIPPED_REQUEST_HEADERS.contains(paramRequest)) {
        localArrayList.add(new Header(paramRequest, localHeaders.value(i)));
      }
      i += 1;
    }
    return localArrayList;
  }
  
  private static String joinOnNull(String paramString1, String paramString2)
  {
    return paramString1 + '\000' + paramString2;
  }
  
  public static Response.Builder readHttp2HeadersList(List<Header> paramList)
    throws IOException
  {
    Object localObject1 = null;
    Headers.Builder localBuilder = new Headers.Builder();
    localBuilder.set(OkHeaders.SELECTED_PROTOCOL, Protocol.HTTP_2.toString());
    int i = 0;
    int j = paramList.size();
    if (i < j)
    {
      ByteString localByteString = ((Header)paramList.get(i)).name;
      String str = ((Header)paramList.get(i)).value.utf8();
      Object localObject2;
      if (localByteString.equals(Header.RESPONSE_STATUS)) {
        localObject2 = str;
      }
      for (;;)
      {
        i += 1;
        localObject1 = localObject2;
        break;
        localObject2 = localObject1;
        if (!HTTP_2_SKIPPED_RESPONSE_HEADERS.contains(localByteString))
        {
          localBuilder.add(localByteString.utf8(), str);
          localObject2 = localObject1;
        }
      }
    }
    if (localObject1 == null) {
      throw new ProtocolException("Expected ':status' header not present");
    }
    paramList = StatusLine.parse("HTTP/1.1 " + (String)localObject1);
    return new Response.Builder().protocol(Protocol.HTTP_2).code(paramList.code).message(paramList.message).headers(localBuilder.build());
  }
  
  public static Response.Builder readSpdy3HeadersList(List<Header> paramList)
    throws IOException
  {
    Object localObject2 = null;
    Object localObject1 = "HTTP/1.1";
    Headers.Builder localBuilder = new Headers.Builder();
    localBuilder.set(OkHeaders.SELECTED_PROTOCOL, Protocol.SPDY_3.toString());
    int i = 0;
    int n = paramList.size();
    while (i < n)
    {
      ByteString localByteString = ((Header)paramList.get(i)).name;
      String str2 = ((Header)paramList.get(i)).value.utf8();
      int j = 0;
      if (j < str2.length())
      {
        int m = str2.indexOf(0, j);
        int k = m;
        if (m == -1) {
          k = str2.length();
        }
        String str1 = str2.substring(j, k);
        Object localObject3;
        Object localObject4;
        if (localByteString.equals(Header.RESPONSE_STATUS))
        {
          localObject3 = str1;
          localObject4 = localObject1;
        }
        for (;;)
        {
          j = k + 1;
          localObject2 = localObject3;
          localObject1 = localObject4;
          break;
          if (localByteString.equals(Header.VERSION))
          {
            localObject4 = str1;
            localObject3 = localObject2;
          }
          else
          {
            localObject3 = localObject2;
            localObject4 = localObject1;
            if (!SPDY_3_SKIPPED_RESPONSE_HEADERS.contains(localByteString))
            {
              localBuilder.add(localByteString.utf8(), str1);
              localObject3 = localObject2;
              localObject4 = localObject1;
            }
          }
        }
      }
      i += 1;
    }
    if (localObject2 == null) {
      throw new ProtocolException("Expected ':status' header not present");
    }
    paramList = StatusLine.parse((String)localObject1 + " " + (String)localObject2);
    return new Response.Builder().protocol(Protocol.SPDY_3).code(paramList.code).message(paramList.message).headers(localBuilder.build());
  }
  
  public static List<Header> spdy3HeadersList(Request paramRequest)
  {
    Headers localHeaders = paramRequest.headers();
    ArrayList localArrayList = new ArrayList(localHeaders.size() + 5);
    localArrayList.add(new Header(Header.TARGET_METHOD, paramRequest.method()));
    localArrayList.add(new Header(Header.TARGET_PATH, RequestLine.requestPath(paramRequest.httpUrl())));
    localArrayList.add(new Header(Header.VERSION, "HTTP/1.1"));
    localArrayList.add(new Header(Header.TARGET_HOST, Util.hostHeader(paramRequest.httpUrl())));
    localArrayList.add(new Header(Header.TARGET_SCHEME, paramRequest.httpUrl().scheme()));
    paramRequest = new LinkedHashSet();
    int i = 0;
    int k = localHeaders.size();
    if (i < k)
    {
      ByteString localByteString = ByteString.encodeUtf8(localHeaders.name(i).toLowerCase(Locale.US));
      if (SPDY_3_SKIPPED_REQUEST_HEADERS.contains(localByteString)) {}
      label322:
      for (;;)
      {
        i += 1;
        break;
        String str = localHeaders.value(i);
        if (paramRequest.add(localByteString))
        {
          localArrayList.add(new Header(localByteString, str));
        }
        else
        {
          int j = 0;
          for (;;)
          {
            if (j >= localArrayList.size()) {
              break label322;
            }
            if (((Header)localArrayList.get(j)).name.equals(localByteString))
            {
              localArrayList.set(j, new Header(localByteString, joinOnNull(((Header)localArrayList.get(j)).value.utf8(), str)));
              break;
            }
            j += 1;
          }
        }
      }
    }
    return localArrayList;
  }
  
  public boolean canReuseConnection()
  {
    return true;
  }
  
  public Sink createRequestBody(Request paramRequest, long paramLong)
    throws IOException
  {
    return this.stream.getSink();
  }
  
  public void disconnect(HttpEngine paramHttpEngine)
    throws IOException
  {
    if (this.stream != null) {
      this.stream.close(ErrorCode.CANCEL);
    }
  }
  
  public void finishRequest()
    throws IOException
  {
    this.stream.getSink().close();
  }
  
  public ResponseBody openResponseBody(Response paramResponse)
    throws IOException
  {
    return new RealResponseBody(paramResponse.headers(), Okio.buffer(this.stream.getSource()));
  }
  
  public Response.Builder readResponseHeaders()
    throws IOException
  {
    if (this.framedConnection.getProtocol() == Protocol.HTTP_2) {
      return readHttp2HeadersList(this.stream.getResponseHeaders());
    }
    return readSpdy3HeadersList(this.stream.getResponseHeaders());
  }
  
  public void releaseConnectionOnIdle() {}
  
  public void writeRequestBody(RetryableSink paramRetryableSink)
    throws IOException
  {
    paramRetryableSink.writeToSocket(this.stream.getSink());
  }
  
  public void writeRequestHeaders(Request paramRequest)
    throws IOException
  {
    if (this.stream != null) {
      return;
    }
    this.httpEngine.writingRequestHeaders();
    boolean bool = this.httpEngine.permitsRequestBody(paramRequest);
    if (this.framedConnection.getProtocol() == Protocol.HTTP_2) {}
    for (paramRequest = http2HeadersList(paramRequest);; paramRequest = spdy3HeadersList(paramRequest))
    {
      this.stream = this.framedConnection.newStream(paramRequest, bool, true);
      this.stream.readTimeout().timeout(this.httpEngine.client.getReadTimeout(), TimeUnit.MILLISECONDS);
      return;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/squareup/okhttp/internal/http/FramedTransport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */