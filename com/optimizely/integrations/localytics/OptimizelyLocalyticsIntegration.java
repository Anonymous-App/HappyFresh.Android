package com.optimizely.integrations.localytics;

import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View.OnTouchListener;
import com.localytics.android.Localytics;
import com.localytics.android.Localytics.ProfileScope;
import com.optimizely.Optimizely;
import com.optimizely.integration.DefaultOptimizelyEventListener;
import com.optimizely.integration.OptimizelyEventListener;
import com.optimizely.integration.OptimizelyExperimentData;
import com.optimizely.integration.OptimizelyPlugin;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONObject;

public class OptimizelyLocalyticsIntegration
  implements OptimizelyPlugin
{
  public static final String EVENT_ATTR_EXP_NAME = "Name";
  public static final String EVENT_ATTR_EXP_NAME_AND_VAR_NAME = "Name + Variation";
  public static final String EVENT_ATTR_EXP_VAR_NAME = "Variation Name";
  public static final String EVENT_NAME = "Optimizely Experiment Visited";
  public static final String LOCALYTICS_COMPONENT = "Optimizely Localytics Integration";
  public static final String PLUGIN_ID = "localytics_mobile";
  public static final String PROFILE_ATTR_NAME = "Optimizely Experiments Visited";
  private OptimizelyEventListener mListener = new DefaultOptimizelyEventListener()
  {
    public void onOptimizelyExperimentViewed(OptimizelyExperimentData paramAnonymousOptimizelyExperimentData)
    {
      OptimizelyLocalyticsIntegration.this.logExperimentViewed(paramAnonymousOptimizelyExperimentData);
      OptimizelyLocalyticsIntegration.this.loadProfile(paramAnonymousOptimizelyExperimentData);
    }
    
    public void onOptimizelyStarted()
    {
      Iterator localIterator = Optimizely.getVisitedExperiments().entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        OptimizelyLocalyticsIntegration.this.loadProfile((OptimizelyExperimentData)localEntry.getValue());
      }
    }
  };
  private Optimizely optimizely;
  
  private void loadProfile(OptimizelyExperimentData paramOptimizelyExperimentData)
  {
    String str = paramOptimizelyExperimentData.experimentName + " - " + paramOptimizelyExperimentData.variationName;
    Localytics.ProfileScope localProfileScope = Localytics.ProfileScope.APPLICATION;
    Localytics.addProfileAttributesToSet("Optimizely Experiments Visited", new String[] { str }, localProfileScope);
    this.optimizely.trackGoal("Optimizely Localytics Integration", "Add profile attribute for experiment: %s, variation: %s", new Object[] { paramOptimizelyExperimentData.experimentName, paramOptimizelyExperimentData.variationName });
  }
  
  private void logExperimentViewed(OptimizelyExperimentData paramOptimizelyExperimentData)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("Name", paramOptimizelyExperimentData.experimentName);
    localHashMap.put("Variation Name", paramOptimizelyExperimentData.variationName);
    localHashMap.put("Name + Variation", paramOptimizelyExperimentData.experimentName + " - " + paramOptimizelyExperimentData.variationName);
    Localytics.tagEvent("Optimizely Experiment Visited", localHashMap);
    this.optimizely.trackGoal("Optimizely Localytics Integration", "Tag event in experiment: %s, variation: %s", new Object[] { paramOptimizelyExperimentData.experimentName, paramOptimizelyExperimentData.variationName });
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
    return "localytics_mobile";
  }
  
  @Nullable
  public List<String> getRequiredPermissions(Context paramContext)
  {
    return Collections.singletonList("android.permission.INTERNET");
  }
  
  public boolean start(@NonNull Optimizely paramOptimizely, @NonNull JSONObject paramJSONObject)
  {
    this.optimizely = paramOptimizely;
    return true;
  }
  
  public void stop() {}
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/integrations/localytics/OptimizelyLocalyticsIntegration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */