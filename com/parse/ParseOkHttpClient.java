package com.parse;

import android.net.SSLCertificateSocketFactory;
import android.net.SSLSessionCache;
import bolts.Capture;
import com.parse.http.ParseHttpBody;
import com.parse.http.ParseHttpRequest;
import com.parse.http.ParseHttpRequest.Builder;
import com.parse.http.ParseHttpRequest.Method;
import com.parse.http.ParseHttpResponse;
import com.parse.http.ParseHttpResponse.Builder;
import com.parse.http.ParseNetworkInterceptor;
import com.parse.http.ParseNetworkInterceptor.Chain;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Headers.Builder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Interceptor.Chain;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Response.Builder;
import com.squareup.okhttp.ResponseBody;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

class ParseOkHttpClient
  extends ParseHttpClient<Request, Response>
{
  private static final String OKHTTP_DELETE = "DELETE";
  private static final String OKHTTP_GET = "GET";
  private static final String OKHTTP_POST = "POST";
  private static final String OKHTTP_PUT = "PUT";
  private OkHttpClient okHttpClient = new OkHttpClient();
  
  public ParseOkHttpClient(int paramInt, SSLSessionCache paramSSLSessionCache)
  {
    this.okHttpClient.setConnectTimeout(paramInt, TimeUnit.MILLISECONDS);
    this.okHttpClient.setReadTimeout(paramInt, TimeUnit.MILLISECONDS);
    this.okHttpClient.setFollowRedirects(false);
    this.okHttpClient.setSslSocketFactory(SSLCertificateSocketFactory.getDefault(paramInt, paramSSLSessionCache));
  }
  
  private ParseHttpRequest getParseHttpRequest(Request paramRequest)
  {
    ParseHttpRequest.Builder localBuilder = new ParseHttpRequest.Builder();
    Object localObject = paramRequest.method();
    int i = -1;
    switch (((String)localObject).hashCode())
    {
    }
    for (;;)
    {
      switch (i)
      {
      default: 
        throw new IllegalArgumentException("Invalid http method " + paramRequest.method());
        if (((String)localObject).equals("GET"))
        {
          i = 0;
          continue;
          if (((String)localObject).equals("DELETE"))
          {
            i = 1;
            continue;
            if (((String)localObject).equals("POST"))
            {
              i = 2;
              continue;
              if (((String)localObject).equals("PUT")) {
                i = 3;
              }
            }
          }
        }
        break;
      }
    }
    localBuilder.setMethod(ParseHttpRequest.Method.GET);
    for (;;)
    {
      localBuilder.setUrl(paramRequest.urlString());
      localObject = paramRequest.headers().toMultimap().entrySet().iterator();
      while (((Iterator)localObject).hasNext())
      {
        Map.Entry localEntry = (Map.Entry)((Iterator)localObject).next();
        localBuilder.addHeader((String)localEntry.getKey(), (String)((List)localEntry.getValue()).get(0));
      }
      localBuilder.setMethod(ParseHttpRequest.Method.DELETE);
      continue;
      localBuilder.setMethod(ParseHttpRequest.Method.POST);
      continue;
      localBuilder.setMethod(ParseHttpRequest.Method.PUT);
    }
    paramRequest = (ParseOkHttpRequestBody)paramRequest.body();
    if (paramRequest != null) {
      localBuilder.setBody(paramRequest.getParseHttpBody());
    }
    return localBuilder.build();
  }
  
  void addExternalInterceptor(final ParseNetworkInterceptor paramParseNetworkInterceptor)
  {
    this.okHttpClient.networkInterceptors().add(new Interceptor()
    {
      public Response intercept(final Interceptor.Chain paramAnonymousChain)
        throws IOException
      {
        final Object localObject1 = paramAnonymousChain.request();
        final Object localObject2 = ParseOkHttpClient.this.getParseHttpRequest((Request)localObject1);
        localObject1 = new Capture();
        paramAnonymousChain = paramParseNetworkInterceptor.intercept(new ParseNetworkInterceptor.Chain()
        {
          public ParseHttpRequest getRequest()
          {
            return localObject2;
          }
          
          public ParseHttpResponse proceed(ParseHttpRequest paramAnonymous2ParseHttpRequest)
            throws IOException
          {
            paramAnonymous2ParseHttpRequest = ParseOkHttpClient.this.getRequest(paramAnonymous2ParseHttpRequest);
            paramAnonymous2ParseHttpRequest = paramAnonymousChain.proceed(paramAnonymous2ParseHttpRequest);
            localObject1.set(paramAnonymous2ParseHttpRequest);
            return ParseOkHttpClient.this.getResponse(paramAnonymous2ParseHttpRequest);
          }
        });
        localObject1 = ((Response)((Capture)localObject1).get()).newBuilder();
        ((Response.Builder)localObject1).code(paramAnonymousChain.getStatusCode()).message(paramAnonymousChain.getReasonPhrase());
        if (paramAnonymousChain.getAllHeaders() != null)
        {
          localObject2 = paramAnonymousChain.getAllHeaders().entrySet().iterator();
          while (((Iterator)localObject2).hasNext())
          {
            Map.Entry localEntry = (Map.Entry)((Iterator)localObject2).next();
            ((Response.Builder)localObject1).header((String)localEntry.getKey(), (String)localEntry.getValue());
          }
        }
        ((Response.Builder)localObject1).body(new ResponseBody()
        {
          public long contentLength()
            throws IOException
          {
            return paramAnonymousChain.getTotalSize();
          }
          
          public MediaType contentType()
          {
            if (paramAnonymousChain.getContentType() == null) {
              return null;
            }
            return MediaType.parse(paramAnonymousChain.getContentType());
          }
          
          public BufferedSource source()
            throws IOException
          {
            if (paramAnonymousChain.getContent() == null) {
              return null;
            }
            return Okio.buffer(Okio.source(paramAnonymousChain.getContent()));
          }
        });
        return ((Response.Builder)localObject1).build();
      }
    });
  }
  
  ParseHttpResponse executeInternal(ParseHttpRequest paramParseHttpRequest)
    throws IOException
  {
    paramParseHttpRequest = getRequest(paramParseHttpRequest);
    return getResponse(this.okHttpClient.newCall(paramParseHttpRequest).execute());
  }
  
  Request getRequest(ParseHttpRequest paramParseHttpRequest)
    throws IOException
  {
    Request.Builder localBuilder = new Request.Builder();
    ParseHttpRequest.Method localMethod = paramParseHttpRequest.getMethod();
    switch (localMethod)
    {
    default: 
      throw new IllegalStateException("Unsupported http method " + localMethod.toString());
    case ???: 
      localBuilder.get();
    }
    for (;;)
    {
      localBuilder.url(paramParseHttpRequest.getUrl());
      localObject = new Headers.Builder();
      Iterator localIterator = paramParseHttpRequest.getAllHeaders().entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        ((Headers.Builder)localObject).add((String)localEntry.getKey(), (String)localEntry.getValue());
      }
      localBuilder.delete();
    }
    localBuilder.headers(((Headers.Builder)localObject).build());
    Object localObject = paramParseHttpRequest.getBody();
    paramParseHttpRequest = null;
    if ((localObject instanceof ParseByteArrayHttpBody)) {
      paramParseHttpRequest = new ParseOkHttpRequestBody((ParseHttpBody)localObject);
    }
    switch (localMethod)
    {
    }
    for (;;)
    {
      return localBuilder.build();
      localBuilder.put(paramParseHttpRequest);
      continue;
      localBuilder.post(paramParseHttpRequest);
    }
  }
  
  ParseHttpResponse getResponse(Response paramResponse)
    throws IOException
  {
    int i = paramResponse.code();
    InputStream localInputStream = paramResponse.body().byteStream();
    int j = (int)paramResponse.body().contentLength();
    String str = paramResponse.message();
    HashMap localHashMap = new HashMap();
    Iterator localIterator = paramResponse.headers().names().iterator();
    while (localIterator.hasNext())
    {
      localObject = (String)localIterator.next();
      localHashMap.put(localObject, paramResponse.header((String)localObject));
    }
    localIterator = null;
    Object localObject = paramResponse.body();
    paramResponse = localIterator;
    if (localObject != null)
    {
      paramResponse = localIterator;
      if (((ResponseBody)localObject).contentType() != null) {
        paramResponse = ((ResponseBody)localObject).contentType().toString();
      }
    }
    return new ParseHttpResponse.Builder().setStatusCode(i).setContent(localInputStream).setTotalSize(j).setReasonPhrase(str).setHeaders(localHashMap).setContentType(paramResponse).build();
  }
  
  private static class ParseOkHttpRequestBody
    extends RequestBody
  {
    private ParseHttpBody parseBody;
    
    public ParseOkHttpRequestBody(ParseHttpBody paramParseHttpBody)
    {
      this.parseBody = paramParseHttpBody;
    }
    
    public long contentLength()
      throws IOException
    {
      return this.parseBody.getContentLength();
    }
    
    public MediaType contentType()
    {
      if (this.parseBody.getContentType() == null) {
        return null;
      }
      return MediaType.parse(this.parseBody.getContentType());
    }
    
    public ParseHttpBody getParseHttpBody()
    {
      return this.parseBody;
    }
    
    public void writeTo(BufferedSink paramBufferedSink)
      throws IOException
    {
      this.parseBody.writeTo(paramBufferedSink.outputStream());
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseOkHttpClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */