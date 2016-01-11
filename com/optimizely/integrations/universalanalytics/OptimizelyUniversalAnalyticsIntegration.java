package com.optimizely.integrations.universalanalytics;

import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View.OnTouchListener;
import com.google.android.gms.analytics.Tracker;
import com.optimizely.Optimizely;
import com.optimizely.integration.DefaultOptimizelyEventListener;
import com.optimizely.integration.OptimizelyEventListener;
import com.optimizely.integration.OptimizelyExperimentData;
import com.optimizely.integration.OptimizelyPlugin;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class OptimizelyUniversalAnalyticsIntegration
  implements OptimizelyPlugin
{
  private static Tracker mTracker;
  @NonNull
  private JSONObject mConfig;
  private OptimizelyEventListener mListener = new DefaultOptimizelyEventListener()
  {
    public void onOptimizelyExperimentViewed(OptimizelyExperimentData paramAnonymousOptimizelyExperimentData)
    {
      OptimizelyUniversalAnalyticsIntegration.this.repopulateCustomDimensions();
    }
    
    public void onOptimizelyStarted()
    {
      OptimizelyUniversalAnalyticsIntegration.this.repopulateCustomDimensions();
    }
  };
  
  private void repopulateCustomDimensions()
  {
    Object localObject;
    JSONObject localJSONObject1;
    if (mTracker != null)
    {
      localObject = Optimizely.getVisitedExperiments();
      localJSONObject1 = this.mConfig.optJSONObject("experiments");
      if (localJSONObject1 != null) {
        break label25;
      }
    }
    for (;;)
    {
      return;
      label25:
      localObject = ((Map)localObject).values().iterator();
      while (((Iterator)localObject).hasNext())
      {
        OptimizelyExperimentData localOptimizelyExperimentData = (OptimizelyExperimentData)((Iterator)localObject).next();
        JSONObject localJSONObject2 = localJSONObject1.optJSONObject(localOptimizelyExperimentData.experimentId);
        if (localJSONObject2 != null)
        {
          int i = localJSONObject2.optInt("universal_analytics_slot", 0);
          if (i > 0) {
            mTracker.set(String.format("&cd%d", new Object[] { Integer.valueOf(i) }), String.format("Optimizely:%s-%s", new Object[] { localOptimizelyExperimentData.experimentName, localOptimizelyExperimentData.variationName }));
          }
        }
      }
    }
  }
  
  public static void setTracker(@Nullable Tracker paramTracker)
  {
    mTracker = paramTracker;
  }
  
  @Nullable
  public Application.ActivityLifecycleCallbacks getActivityLifecycleCallbacks()
  {
    return null;
  }
  
  public List<String> getDependencies()
  {
    return null;
  }
  
  @Nullable
  public View.OnTouchListener getOnTouchListener()
  {
    return null;
  }
  
  @Nullable
  public OptimizelyEventListener getOptimizelyEventsListener()
  {
    return this.mListener;
  }
  
  @NonNull
  public String getPluginId()
  {
    return "google_universal_analytics_mobile";
  }
  
  @Nullable
  public List<String> getRequiredPermissions(Context paramContext)
  {
    return Arrays.asList(new String[] { "android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE" });
  }
  
  public boolean start(@NonNull Optimizely paramOptimizely, @NonNull JSONObject paramJSONObject)
  {
    if (mTracker == null)
    {
      paramOptimizely.verboseLog(true, getPluginId(), "Please call setTracker() before registering this plugin.", new Object[0]);
      return false;
    }
    this.mConfig = paramJSONObject;
    return true;
  }
  
  public void stop()
  {
    mTracker = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/integrations/universalanalytics/OptimizelyUniversalAnalyticsIntegration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */