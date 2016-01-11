package com.optimizely.integration;

import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View.OnTouchListener;
import com.optimizely.Optimizely;
import java.util.List;
import org.json.JSONObject;

public abstract interface OptimizelyPlugin
{
  @Nullable
  public abstract Application.ActivityLifecycleCallbacks getActivityLifecycleCallbacks();
  
  public abstract List<String> getDependencies();
  
  @Nullable
  public abstract View.OnTouchListener getOnTouchListener();
  
  @Nullable
  public abstract OptimizelyEventListener getOptimizelyEventsListener();
  
  @NonNull
  public abstract String getPluginId();
  
  @Nullable
  public abstract List<String> getRequiredPermissions(Context paramContext);
  
  public abstract boolean start(Optimizely paramOptimizely, JSONObject paramJSONObject);
  
  public abstract void stop();
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/integration/OptimizelyPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */