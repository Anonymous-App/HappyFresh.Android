package com.optimizely.Network;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.annotation.NonNull;
import com.google.gson.Gson;
import com.optimizely.Core.OptimizelyData;
import com.optimizely.Core.OptimizelyUtils;
import com.optimizely.LogAndEvent.Result;
import com.optimizely.Optimizely;
import java.io.File;
import java.util.Map;

public class OptimizelyPreRunActions
{
  private static final String ACTION_KEY = "action";
  private static final int ACTION_TIMEOUT_MILLIS = 500;
  private static final String CLEAR_LOCAL_FILE_ACTION = "clear_local_file";
  private static final String KILL_ACTION = "kill";
  private static final String PREFS_KEY_SHOULD_RUN = "should_optimizely_run";
  private static final String PRE_RUN_COMPONENT = "OptimizelyPreRunActions";
  
  public static boolean checkIfOptimizelyShouldRun(@NonNull Optimizely paramOptimizely)
  {
    if (paramOptimizely.getProjectId().isEmpty()) {}
    while (paramOptimizely.getCurrentContext().checkCallingOrSelfPermission("android.permission.INTERNET") != 0) {
      return false;
    }
    final Result localResult = new Result();
    paramOptimizely.getDownloader().downloadActionFile(new OptimizelyNetworkResult()
    {
      public void onDownloadError(int paramAnonymousInt)
      {
        switch (paramAnonymousInt)
        {
        default: 
          localResult.resolve(true, Boolean.valueOf(false));
          return;
        }
        SharedPreferences localSharedPreferences = this.val$optimizely.getUserDefaults(this.val$optimizely.getCurrentContext());
        localResult.resolve(true, Boolean.valueOf(localSharedPreferences.getBoolean("should_optimizely_run", true)));
      }
      
      public void onDownloadFinished(String paramAnonymousString)
      {
        SharedPreferences localSharedPreferences = this.val$optimizely.getUserDefaults(this.val$optimizely.getCurrentContext());
        try
        {
          Object localObject = (Map)new Gson().fromJson(paramAnonymousString, Map.class);
          if (localObject == null)
          {
            localResult.resolve(true, Boolean.valueOf(true));
            return;
          }
          localObject = ((Map)localObject).get("action");
          if ("clear_local_file".equals(localObject))
          {
            localSharedPreferences.edit().putBoolean("should_optimizely_run", true).commit();
            this.val$optimizely.getOptimizelyData().getDataFile().delete();
            OptimizelyUtils.reset(this.val$optimizely);
            localResult.resolve(true, Boolean.valueOf(true));
            return;
          }
        }
        catch (Exception localException)
        {
          this.val$optimizely.verboseLog(true, "OptimizelyPreRunActions", "Failure in parsing kill message %s. Continuing with the saved resolution.", new Object[] { paramAnonymousString });
          localResult.resolve(true, Boolean.valueOf(localSharedPreferences.getBoolean("should_optimizely_run", true)));
          return;
        }
        if ("kill".equals(localException))
        {
          localSharedPreferences.edit().putBoolean("should_optimizely_run", false).commit();
          localResult.resolve(true, Boolean.valueOf(false));
          return;
        }
        localSharedPreferences.edit().putBoolean("should_optimizely_run", true).commit();
        localResult.resolve(true, Boolean.valueOf(true));
      }
    }, 500);
    return ((Boolean)localResult.get()).booleanValue();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Network/OptimizelyPreRunActions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */