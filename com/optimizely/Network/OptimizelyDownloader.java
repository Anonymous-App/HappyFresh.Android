package com.optimizely.Network;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;
import com.optimizely.Build;
import com.optimizely.Optimizely;
import com.optimizely.utils.OptimizelyThreadPoolExecutor;
import com.squareup.okhttp.OkHttpClient;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class OptimizelyDownloader
{
  private static final String OPTIMIZELY_DOWNLOADER_COMPONENT = "OptimizelyDownloader";
  private static final String cdnURL = "https://cdn.optimizely.com";
  @Nullable
  private DownloadTask downloadTask;
  @NonNull
  private final Optimizely optimizely;
  
  public OptimizelyDownloader(@NonNull Optimizely paramOptimizely)
  {
    this.optimizely = paramOptimizely;
  }
  
  private void executeDownloadTask(@NonNull DownloadTask paramDownloadTask, String paramString, boolean paramBoolean)
  {
    paramDownloadTask.executeOnExecutor(OptimizelyThreadPoolExecutor.instance(), new String[] { paramString });
    if (paramBoolean) {}
    try
    {
      paramDownloadTask.get(paramDownloadTask.networkTimeout, TimeUnit.MILLISECONDS);
      return;
    }
    catch (InterruptedException paramString)
    {
      this.optimizely.verboseLog(true, "OptimizelyDownloader", "Download Task Interrupted before finishing!", new Object[0]);
      paramDownloadTask.handler.onDownloadError(3586);
      return;
    }
    catch (ExecutionException paramString)
    {
      this.optimizely.verboseLog(true, "OptimizelyDownloader", "DownloadTask finished with error %s", new Object[] { paramString.getMessage() });
      paramDownloadTask.handler.onDownloadError(3586);
      return;
    }
    catch (TimeoutException paramString)
    {
      this.optimizely.verboseLog(true, "OptimizelyDownloader", "Cancelled download because it took longer than %d", new Object[] { Integer.valueOf(paramDownloadTask.networkTimeout) });
      paramDownloadTask.handler.onDownloadError(3586);
    }
  }
  
  @NonNull
  private String jsonActionURI()
  {
    return String.format("%s/json/android/kill_switch/%s.json", new Object[] { "https://cdn.optimizely.com", this.optimizely.getProjectId() });
  }
  
  @NonNull
  private String jsonURI()
  {
    return String.format("%s/json/android/%s/%s.json", new Object[] { "https://cdn.optimizely.com", Build.majorMinorVersion(), this.optimizely.getProjectId() });
  }
  
  public void cancelDownload()
  {
    if (this.downloadTask != null) {
      this.downloadTask.cancel(true);
    }
  }
  
  public void downloadActionFile(@NonNull OptimizelyNetworkResult<String> paramOptimizelyNetworkResult, int paramInt)
  {
    this.downloadTask = new DownloadTask(paramOptimizelyNetworkResult, paramInt);
    executeDownloadTask(this.downloadTask, jsonActionURI(), true);
  }
  
  public void downloadDataFile(@NonNull OptimizelyNetworkResult<String> paramOptimizelyNetworkResult, int paramInt, boolean paramBoolean)
  {
    if (this.downloadTask == null)
    {
      this.downloadTask = new DownloadTask(paramOptimizelyNetworkResult, paramInt);
      executeDownloadTask(this.downloadTask, jsonURI(), paramBoolean);
      return;
    }
    this.optimizely.verboseLog("OptimizelyDownloader", "Download already in progress; Skipping this download request.", new Object[0]);
  }
  
  @NonNull
  protected Pair<String, Integer> downloadFromUrl(@NonNull String paramString, int paramInt, @NonNull Optimizely paramOptimizely)
  {
    return new OptimizelyNetworkUtil(paramOptimizely, paramInt, OptimizelyNetworkUtil.TRANSFORM_TO_STRING).downloadFromUrl(getClient(), paramString);
  }
  
  @NonNull
  protected OkHttpClient getClient()
  {
    return new OkHttpClient();
  }
  
  private class DownloadTask
    extends AsyncTask<String, Integer, String>
  {
    private final OptimizelyNetworkResult<String> handler;
    private final int networkTimeout;
    
    public DownloadTask(int paramInt)
    {
      this.handler = paramInt;
      int i;
      this.networkTimeout = i;
    }
    
    @Nullable
    protected String doInBackground(@NonNull String... paramVarArgs)
    {
      paramVarArgs = paramVarArgs[0];
      if (paramVarArgs == null)
      {
        this.handler.onDownloadError(3587);
        return null;
      }
      paramVarArgs = OptimizelyDownloader.this.downloadFromUrl(paramVarArgs, this.networkTimeout, OptimizelyDownloader.this.optimizely);
      String str = (String)paramVarArgs.first;
      if (str != null)
      {
        this.handler.onDownloadFinished(str);
        return str;
      }
      this.handler.onDownloadError(((Integer)paramVarArgs.second).intValue());
      return str;
    }
    
    protected void onPostExecute(String paramString)
    {
      OptimizelyDownloader.access$302(OptimizelyDownloader.this, null);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Network/OptimizelyDownloader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */