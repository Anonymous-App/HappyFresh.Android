package com.adjust.sdk;

import android.net.Uri;
import android.net.Uri.Builder;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONObject;

public class AttributionHandler
  implements IAttributionHandler
{
  private IActivityHandler activityHandler;
  private ActivityPackage attributionPackage;
  private HttpClient httpClient = Util.getHttpClient();
  private ILogger logger = AdjustFactory.getLogger();
  private boolean paused;
  private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
  private ScheduledFuture waitingTask;
  
  public AttributionHandler(IActivityHandler paramIActivityHandler, ActivityPackage paramActivityPackage, boolean paramBoolean)
  {
    init(paramIActivityHandler, paramActivityPackage, paramBoolean);
  }
  
  private Uri buildUri(ActivityPackage paramActivityPackage)
  {
    Uri.Builder localBuilder = new Uri.Builder();
    localBuilder.scheme("https");
    localBuilder.authority("app.adjust.com");
    localBuilder.appendPath(paramActivityPackage.getPath());
    paramActivityPackage = paramActivityPackage.getParameters().entrySet().iterator();
    while (paramActivityPackage.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)paramActivityPackage.next();
      localBuilder.appendQueryParameter((String)localEntry.getKey(), (String)localEntry.getValue());
    }
    return localBuilder.build();
  }
  
  private void checkAttributionInternal(JSONObject paramJSONObject)
  {
    if (paramJSONObject == null) {
      return;
    }
    AdjustAttribution localAdjustAttribution = AdjustAttribution.fromJson(paramJSONObject.optJSONObject("attribution"));
    int i = paramJSONObject.optInt("ask_in", -1);
    if (i < 0)
    {
      this.activityHandler.tryUpdateAttribution(localAdjustAttribution);
      this.activityHandler.setAskingAttribution(false);
      return;
    }
    this.activityHandler.setAskingAttribution(true);
    getAttribution(i);
  }
  
  private void getAttribution(int paramInt)
  {
    if (this.waitingTask != null) {
      this.waitingTask.cancel(false);
    }
    if (paramInt != 0) {
      this.logger.debug("Waiting to query attribution in %d milliseconds", new Object[] { Integer.valueOf(paramInt) });
    }
    this.waitingTask = this.scheduler.schedule(new Runnable()
    {
      public void run()
      {
        AttributionHandler.this.getAttributionInternal();
      }
    }, paramInt, TimeUnit.MILLISECONDS);
  }
  
  private void getAttributionInternal()
  {
    if (this.paused)
    {
      this.logger.debug("Attribution Handler is paused", new Object[0]);
      return;
    }
    this.logger.verbose("%s", new Object[] { this.attributionPackage.getExtendedString() });
    try
    {
      Object localObject = getRequest(this.attributionPackage);
      localObject = this.httpClient.execute((HttpUriRequest)localObject);
      checkAttributionInternal(Util.parseJsonResponse((HttpResponse)localObject));
      return;
    }
    catch (Exception localException)
    {
      this.logger.error("Failed to get attribution (%s)", new Object[] { localException.getMessage() });
    }
  }
  
  private HttpGet getRequest(ActivityPackage paramActivityPackage)
    throws URISyntaxException
  {
    HttpGet localHttpGet = new HttpGet();
    localHttpGet.setURI(new URI(buildUri(paramActivityPackage).toString()));
    localHttpGet.addHeader("Client-SDK", paramActivityPackage.getClientSdk());
    return localHttpGet;
  }
  
  public void checkAttribution(final JSONObject paramJSONObject)
  {
    this.scheduler.submit(new Runnable()
    {
      public void run()
      {
        AttributionHandler.this.checkAttributionInternal(paramJSONObject);
      }
    });
  }
  
  public void getAttribution()
  {
    getAttribution(0);
  }
  
  public void init(IActivityHandler paramIActivityHandler, ActivityPackage paramActivityPackage, boolean paramBoolean)
  {
    this.activityHandler = paramIActivityHandler;
    this.attributionPackage = paramActivityPackage;
    this.paused = paramBoolean;
  }
  
  public void pauseSending()
  {
    this.paused = true;
  }
  
  public void resumeSending()
  {
    this.paused = false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/adjust/sdk/AttributionHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */