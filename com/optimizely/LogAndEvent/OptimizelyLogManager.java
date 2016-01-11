package com.optimizely.LogAndEvent;

import android.content.Context;
import android.net.ParseException;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;
import com.optimizely.Build;
import com.optimizely.Core.OptimizelyData;
import com.optimizely.Core.OptimizelyUtils;
import com.optimizely.Optimizely;
import com.optimizely.Optimizely.OptimizelyRunningMode;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import org.json.JSONException;
import org.json.JSONObject;

public class OptimizelyLogManager
{
  private static final String LOG_TAG = "OptimizelyLogManager";
  private static final String kOptimizelyLogClientApiHost = "client-error-log.dz.optimizely.com";
  private static final String kOptimizelyLogClientApiPath = "/log";
  private static final String kOptimizelyLogClientProtocol = "https://";
  final OptimizelyDataStore dataStore;
  @NonNull
  private final String logEndpoint;
  protected LogEventTask logEventTask;
  private final Optimizely optimizely;
  private ThreadPoolExecutor threadPoolExecutor;
  
  public OptimizelyLogManager(@NonNull Optimizely paramOptimizely, @NonNull OptimizelyDataStore paramOptimizelyDataStore, @NonNull ThreadPoolExecutor paramThreadPoolExecutor)
  {
    this.optimizely = paramOptimizely;
    this.dataStore = paramOptimizelyDataStore;
    this.threadPoolExecutor = paramThreadPoolExecutor;
    this.logEndpoint = "https://client-error-log.dz.optimizely.com/log";
  }
  
  @NonNull
  private JSONObject getMetadata()
    throws JSONException
  {
    Context localContext = this.optimizely.getCurrentContext();
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put("account_id", this.optimizely.getOptimizelyData().getAccountId());
    localJSONObject.put("project_id", this.optimizely.getProjectId());
    localJSONObject.put("sdk_version", Build.sdkVersion());
    localJSONObject.put("active_experiments", this.optimizely.getOptimizelyData().getExperimentLogInformation());
    localJSONObject.put("running_mode", Optimizely.getRunningMode().toString());
    localJSONObject.put("is_appstore", OptimizelyUtils.isAppStoreApp(localContext));
    localJSONObject.put("device_name", OptimizelyUtils.deviceName());
    localJSONObject.put("device_model", OptimizelyUtils.deviceModel());
    localJSONObject.put("app_version", OptimizelyUtils.applicationVersion(this.optimizely));
    localJSONObject.put("bundle_identifier", OptimizelyUtils.applicationName(localContext));
    localJSONObject.put("source", "android_sdk");
    return localJSONObject;
  }
  
  public Result<LogEventTask> flushLogs()
  {
    final Result localResult = new Result();
    if (this.logEventTask != null)
    {
      localResult.resolve(true, null);
      return localResult;
    }
    this.dataStore.getPendingLogs().then(new Result.Handler()
    {
      public void onResolve(boolean paramAnonymousBoolean, @Nullable ArrayList<Pair<Long, String>> paramAnonymousArrayList)
      {
        if (OptimizelyLogManager.this.logEventTask != null)
        {
          localResult.resolve(true, null);
          return;
        }
        if ((paramAnonymousBoolean) && (paramAnonymousArrayList != null) && (!paramAnonymousArrayList.isEmpty()))
        {
          OptimizelyLogManager.this.logEventTask = new OptimizelyLogManager.LogEventTask(OptimizelyLogManager.this);
          OptimizelyLogManager.this.logEventTask.executeOnExecutor(OptimizelyLogManager.this.threadPoolExecutor, paramAnonymousArrayList.toArray(new Pair[paramAnonymousArrayList.size()]));
          localResult.resolve(true, OptimizelyLogManager.this.logEventTask);
          return;
        }
        localResult.resolve(true, null);
      }
    });
    return localResult;
  }
  
  @NonNull
  public Result<Long> log(String paramString1, String paramString2)
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("timestamp", System.currentTimeMillis());
      localJSONObject.put("clientEngine", "android");
      localJSONObject.put("clientVersion", Build.sdkVersion());
      localJSONObject.put("errorClass", paramString1);
      localJSONObject.put("message", getMetadata().toString());
      localJSONObject.put("stacktrace", paramString2);
      return this.dataStore.storeLog(localJSONObject.toString());
    }
    catch (JSONException paramString1)
    {
      for (;;)
      {
        this.optimizely.verboseLog(true, "OptimizelyLogManager", "Failed to convert log metadata to JSON", new Object[0]);
      }
    }
  }
  
  protected class LogEventTask
    extends AsyncTask<Pair<Long, String>, Integer, Void>
  {
    protected LogEventTask() {}
    
    @SafeVarargs
    @Nullable
    protected final Void doInBackground(@NonNull Pair<Long, String>... paramVarArgs)
    {
      OkHttpClient localOkHttpClient = OptimizelyLogManager.this.optimizely.getHttpClient();
      ArrayList localArrayList = new ArrayList();
      int k = paramVarArgs.length;
      int i = 0;
      for (;;)
      {
        if (i < k)
        {
          Pair<Long, String> localPair = paramVarArgs[i];
          try
          {
            if (localPair.second == null) {
              break label315;
            }
            Object localObject = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (String)localPair.second);
            localObject = localOkHttpClient.newCall(new Request.Builder().url(OptimizelyLogManager.this.logEndpoint).post((RequestBody)localObject).header("Accept", "*/*").build()).execute();
            if (localObject == null) {
              break label195;
            }
            j = ((Response)localObject).code();
          }
          catch (ParseException localParseException)
          {
            int j;
            for (;;)
            {
              OptimizelyLogManager.this.optimizely.verboseLog(true, "OptimizelyLogManager", "Error parsing server response while sending log: " + localParseException.getLocalizedMessage(), new Object[0]);
              break;
              j = 400;
            }
            OptimizelyLogManager.this.optimizely.verboseLog(true, "OptimizelyLogManager", "Error sending log to server. Got status code %1$d", new Object[] { Integer.valueOf(j) });
          }
          catch (IOException localIOException)
          {
            OptimizelyLogManager.this.optimizely.verboseLog(true, "OptimizelyLogManager", "Error relieving server response while sending log: " + localIOException.getLocalizedMessage(), new Object[0]);
          }
          if ((j >= 200) && (j < 300))
          {
            localArrayList.add(localPair.first);
            break label315;
          }
        }
        label195:
        if (!OptimizelyLogManager.this.dataStore.deleteLogs(localArrayList).getSuccess()) {
          OptimizelyLogManager.this.optimizely.verboseLog(true, "OptimizelyLogManager", "Error clearing logs that were sent to the server", new Object[0]);
        }
        return null;
        label315:
        i += 1;
      }
    }
    
    protected void onPostExecute(Void paramVoid)
    {
      OptimizelyLogManager.this.logEventTask = null;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/LogAndEvent/OptimizelyLogManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */