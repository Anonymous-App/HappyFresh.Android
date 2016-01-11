package com.optimizely.Network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Interceptor.Chain;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.nio.charset.Charset;
import okio.Buffer;

public class LoggingInterceptor
  implements Interceptor
{
  private static final String NETWORK_DEBUGGING_COMPONENT = "OptimizelyNetworkDebug";
  
  @Nullable
  public Response intercept(@NonNull Interceptor.Chain paramChain)
  {
    Request localRequest = paramChain.request();
    RequestBody localRequestBody;
    Buffer localBuffer;
    if (localRequest != null)
    {
      Log.d("OptimizelyNetworkDebug", localRequest.toString());
      localRequestBody = localRequest.body();
      if (localRequestBody != null) {
        localBuffer = new Buffer();
      }
    }
    try
    {
      localRequestBody.writeTo(localBuffer);
      Log.d("OptimizelyNetworkDebug", localBuffer.readString(Charset.defaultCharset()));
      try
      {
        paramChain = paramChain.proceed(localRequest);
        return paramChain;
      }
      catch (IOException paramChain)
      {
        return null;
      }
    }
    catch (IOException localIOException)
    {
      for (;;) {}
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Network/LoggingInterceptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */