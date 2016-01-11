package com.parse;

import android.net.SSLCertificateSocketFactory;
import android.net.SSLSessionCache;
import com.parse.http.ParseHttpBody;
import com.parse.http.ParseHttpRequest;
import com.parse.http.ParseHttpRequest.Method;
import com.parse.http.ParseHttpResponse;
import com.parse.http.ParseHttpResponse.Builder;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.net.ssl.HttpsURLConnection;

class ParseURLConnectionHttpClient
  extends ParseHttpClient<HttpURLConnection, HttpURLConnection>
{
  private static final String ACCEPT_ENCODING_HEADER = "Accept-encoding";
  private static final String CONTENT_LENGTH_HEADER = "Content-Length";
  private static final String CONTENT_TYPE_HEADER = "Content-Type";
  private static final String GZIP_ENCODING = "gzip";
  private int socketOperationTimeout;
  
  public ParseURLConnectionHttpClient(int paramInt, SSLSessionCache paramSSLSessionCache)
  {
    this.socketOperationTimeout = paramInt;
    HttpsURLConnection.setDefaultSSLSocketFactory(SSLCertificateSocketFactory.getDefault(paramInt, paramSSLSessionCache));
  }
  
  ParseHttpResponse executeInternal(ParseHttpRequest paramParseHttpRequest)
    throws IOException
  {
    HttpURLConnection localHttpURLConnection = getRequest(paramParseHttpRequest);
    paramParseHttpRequest = paramParseHttpRequest.getBody();
    if (paramParseHttpRequest != null)
    {
      OutputStream localOutputStream = localHttpURLConnection.getOutputStream();
      paramParseHttpRequest.writeTo(localOutputStream);
      localOutputStream.flush();
      localOutputStream.close();
    }
    return getResponse(localHttpURLConnection);
  }
  
  HttpURLConnection getRequest(ParseHttpRequest paramParseHttpRequest)
    throws IOException
  {
    HttpURLConnection localHttpURLConnection = (HttpURLConnection)new URL(paramParseHttpRequest.getUrl()).openConnection();
    localHttpURLConnection.setRequestMethod(paramParseHttpRequest.getMethod().toString());
    localHttpURLConnection.setConnectTimeout(this.socketOperationTimeout);
    localHttpURLConnection.setReadTimeout(this.socketOperationTimeout);
    localHttpURLConnection.setDoInput(true);
    localHttpURLConnection.setInstanceFollowRedirects(false);
    Iterator localIterator = paramParseHttpRequest.getAllHeaders().entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      localHttpURLConnection.setRequestProperty((String)localEntry.getKey(), (String)localEntry.getValue());
    }
    if (disableHttpLibraryAutoDecompress()) {
      localHttpURLConnection.setRequestProperty("Accept-encoding", "gzip");
    }
    paramParseHttpRequest = paramParseHttpRequest.getBody();
    if (paramParseHttpRequest != null)
    {
      localHttpURLConnection.setRequestProperty("Content-Length", String.valueOf(paramParseHttpRequest.getContentLength()));
      localHttpURLConnection.setRequestProperty("Content-Type", paramParseHttpRequest.getContentType());
      localHttpURLConnection.setFixedLengthStreamingMode(paramParseHttpRequest.getContentLength());
      localHttpURLConnection.setDoOutput(true);
    }
    return localHttpURLConnection;
  }
  
  ParseHttpResponse getResponse(HttpURLConnection paramHttpURLConnection)
    throws IOException
  {
    int i = paramHttpURLConnection.getResponseCode();
    InputStream localInputStream;
    int j;
    String str;
    HashMap localHashMap;
    label54:
    Object localObject2;
    if (i < 400)
    {
      localInputStream = paramHttpURLConnection.getInputStream();
      j = paramHttpURLConnection.getContentLength();
      str = paramHttpURLConnection.getResponseMessage();
      localHashMap = new HashMap();
      Iterator localIterator = paramHttpURLConnection.getHeaderFields().entrySet().iterator();
      do
      {
        if (!localIterator.hasNext()) {
          break;
        }
        localObject1 = (Map.Entry)localIterator.next();
      } while ((((Map.Entry)localObject1).getKey() == null) || (((List)((Map.Entry)localObject1).getValue()).size() <= 0));
      localObject2 = ((Map.Entry)localObject1).getKey();
      if (((Map.Entry)localObject1).getValue() != null) {
        break label151;
      }
    }
    label151:
    for (Object localObject1 = "";; localObject1 = (String)((List)((Map.Entry)localObject1).getValue()).get(0))
    {
      localHashMap.put(localObject2, localObject1);
      break label54;
      localInputStream = paramHttpURLConnection.getErrorStream();
      break;
    }
    paramHttpURLConnection = paramHttpURLConnection.getContentType();
    return new ParseHttpResponse.Builder().setStatusCode(i).setContent(localInputStream).setTotalSize(j).setReasonPhrase(str).setHeaders(localHashMap).setContentType(paramHttpURLConnection).build();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseURLConnectionHttpClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */