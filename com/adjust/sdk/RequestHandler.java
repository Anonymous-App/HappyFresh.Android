package com.adjust.sdk;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;

public class RequestHandler
  extends HandlerThread
  implements IRequestHandler
{
  private HttpClient httpClient;
  private InternalHandler internalHandler;
  private ILogger logger;
  private IPackageHandler packageHandler;
  
  public RequestHandler(IPackageHandler paramIPackageHandler)
  {
    super("Adjust", 1);
    setDaemon(true);
    start();
    this.logger = AdjustFactory.getLogger();
    this.internalHandler = new InternalHandler(getLooper(), this);
    init(paramIPackageHandler);
    paramIPackageHandler = Message.obtain();
    paramIPackageHandler.arg1 = 72401;
    this.internalHandler.sendMessage(paramIPackageHandler);
  }
  
  private void closePackage(ActivityPackage paramActivityPackage, String paramString, Throwable paramThrowable, boolean paramBoolean)
  {
    paramActivityPackage = paramActivityPackage.getFailureMessage();
    String str = this.packageHandler.getFailureMessage();
    paramString = getReasonString(paramString, paramThrowable);
    this.logger.error("%s. (%s) %s", new Object[] { paramActivityPackage, paramString, str });
    if (paramBoolean) {
      this.packageHandler.closeFirstPackage();
    }
  }
  
  private String getReasonString(String paramString, Throwable paramThrowable)
  {
    if (paramThrowable != null) {
      return String.format(Locale.US, "%s: %s", new Object[] { paramString, paramThrowable });
    }
    return String.format(Locale.US, "%s", new Object[] { paramString });
  }
  
  private HttpUriRequest getRequest(ActivityPackage paramActivityPackage)
    throws UnsupportedEncodingException
  {
    HttpPost localHttpPost = new HttpPost("https://app.adjust.com" + paramActivityPackage.getPath());
    Object localObject = Locale.getDefault().getLanguage();
    localHttpPost.addHeader("Client-SDK", paramActivityPackage.getClientSdk());
    localHttpPost.addHeader("Accept-Language", (String)localObject);
    localObject = new ArrayList();
    paramActivityPackage = paramActivityPackage.getParameters().entrySet().iterator();
    while (paramActivityPackage.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)paramActivityPackage.next();
      ((List)localObject).add(new BasicNameValuePair((String)localEntry.getKey(), (String)localEntry.getValue()));
    }
    ((List)localObject).add(new BasicNameValuePair("sent_at", Util.dateFormat(System.currentTimeMillis())));
    paramActivityPackage = new UrlEncodedFormEntity((List)localObject);
    paramActivityPackage.setContentType("application/x-www-form-urlencoded");
    localHttpPost.setEntity(paramActivityPackage);
    return localHttpPost;
  }
  
  private void initInternal()
  {
    this.httpClient = Util.getHttpClient();
  }
  
  private void requestFinished(HttpResponse paramHttpResponse, boolean paramBoolean)
  {
    paramHttpResponse = Util.parseJsonResponse(paramHttpResponse);
    if (paramHttpResponse == null) {
      if (paramBoolean) {
        this.packageHandler.closeFirstPackage();
      }
    }
    do
    {
      return;
      this.packageHandler.finishedTrackingActivity(paramHttpResponse);
    } while (!paramBoolean);
    this.packageHandler.sendNextPackage();
  }
  
  private void sendInternal(ActivityPackage paramActivityPackage, boolean paramBoolean)
  {
    try
    {
      HttpUriRequest localHttpUriRequest = getRequest(paramActivityPackage);
      requestFinished(this.httpClient.execute(localHttpUriRequest), paramBoolean);
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      sendNextPackage(paramActivityPackage, "Failed to encode parameters", localUnsupportedEncodingException, paramBoolean);
      return;
    }
    catch (ClientProtocolException localClientProtocolException)
    {
      closePackage(paramActivityPackage, "Client protocol error", localClientProtocolException, paramBoolean);
      return;
    }
    catch (SocketTimeoutException localSocketTimeoutException)
    {
      closePackage(paramActivityPackage, "Request timed out", localSocketTimeoutException, paramBoolean);
      return;
    }
    catch (IOException localIOException)
    {
      closePackage(paramActivityPackage, "Request failed", localIOException, paramBoolean);
      return;
    }
    catch (Throwable localThrowable)
    {
      sendNextPackage(paramActivityPackage, "Runtime exception", localThrowable, paramBoolean);
    }
  }
  
  private void sendNextPackage(ActivityPackage paramActivityPackage, String paramString, Throwable paramThrowable, boolean paramBoolean)
  {
    paramActivityPackage = paramActivityPackage.getFailureMessage();
    paramString = getReasonString(paramString, paramThrowable);
    this.logger.error("%s. (%s)", new Object[] { paramActivityPackage, paramString });
    if (paramBoolean) {
      this.packageHandler.sendNextPackage();
    }
  }
  
  public void init(IPackageHandler paramIPackageHandler)
  {
    this.packageHandler = paramIPackageHandler;
  }
  
  public void sendClickPackage(ActivityPackage paramActivityPackage)
  {
    Message localMessage = Message.obtain();
    localMessage.arg1 = 72402;
    localMessage.obj = paramActivityPackage;
    this.internalHandler.sendMessage(localMessage);
  }
  
  public void sendPackage(ActivityPackage paramActivityPackage)
  {
    Message localMessage = Message.obtain();
    localMessage.arg1 = 72400;
    localMessage.obj = paramActivityPackage;
    this.internalHandler.sendMessage(localMessage);
  }
  
  private static final class InternalHandler
    extends Handler
  {
    private static final int INIT = 72401;
    private static final int SEND = 72400;
    private static final int SEND_CLICK = 72402;
    private final WeakReference<RequestHandler> requestHandlerReference;
    
    protected InternalHandler(Looper paramLooper, RequestHandler paramRequestHandler)
    {
      super();
      this.requestHandlerReference = new WeakReference(paramRequestHandler);
    }
    
    public void handleMessage(Message paramMessage)
    {
      super.handleMessage(paramMessage);
      RequestHandler localRequestHandler = (RequestHandler)this.requestHandlerReference.get();
      if (localRequestHandler == null) {
        return;
      }
      switch (paramMessage.arg1)
      {
      default: 
        return;
      case 72400: 
        localRequestHandler.sendInternal((ActivityPackage)paramMessage.obj, true);
        return;
      case 72401: 
        localRequestHandler.initInternal();
        return;
      }
      localRequestHandler.sendInternal((ActivityPackage)paramMessage.obj, false);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/adjust/sdk/RequestHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */