package com.parse;

import com.parse.http.ParseHttpResponse;
import com.parse.http.ParseHttpResponse.Builder;
import com.parse.http.ParseNetworkInterceptor;
import com.parse.http.ParseNetworkInterceptor.Chain;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

class ParseDecompressInterceptor
  implements ParseNetworkInterceptor
{
  private static final String CONTENT_ENCODING_HEADER = "Content-Encoding";
  private static final String CONTENT_LENGTH_HEADER = "Content-Length";
  private static final String GZIP_ENCODING = "gzip";
  
  public ParseHttpResponse intercept(ParseNetworkInterceptor.Chain paramChain)
    throws IOException
  {
    ParseHttpResponse localParseHttpResponse = paramChain.proceed(paramChain.getRequest());
    paramChain = localParseHttpResponse;
    if ("gzip".equalsIgnoreCase(localParseHttpResponse.getHeader("Content-Encoding")))
    {
      paramChain = new HashMap(localParseHttpResponse.getAllHeaders());
      paramChain.remove("Content-Encoding");
      paramChain.put("Content-Length", "-1");
      paramChain = new ParseHttpResponse.Builder(localParseHttpResponse).setTotalSize(-1L).setHeaders(paramChain).setContent(new GZIPInputStream(localParseHttpResponse.getContent())).build();
    }
    return paramChain;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseDecompressInterceptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */