package com.optimizely.integrations.mixpanel;

import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View.OnTouchListener;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.mixpanel.android.mpmetrics.MixpanelApiRetriever;
import com.mixpanel.android.viewcrawler.TrackingDebug;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OptimizelyMixpanelIntegration
  implements OptimizelyPlugin, TrackingDebug
{
  private OptimizelyEventListener mListener = new DefaultOptimizelyEventListener()
  {
    public void onGoalTriggered(String paramAnonymousString, List<OptimizelyExperimentData> paramAnonymousList)
    {
      JSONObject localJSONObject;
      if (OptimizelyMixpanelIntegration.this.mMixpanelApi != null) {
        try
        {
          localJSONObject = new JSONObject();
          localJSONObject.put("goal_description", paramAnonymousString);
          paramAnonymousString = new JSONArray();
          paramAnonymousList = paramAnonymousList.iterator();
          while (paramAnonymousList.hasNext())
          {
            paramAnonymousString.put(((OptimizelyExperimentData)paramAnonymousList.next()).experimentName);
            continue;
            return;
          }
        }
        catch (JSONException paramAnonymousString)
        {
          Log.e("OPTMP", paramAnonymousString.getLocalizedMessage());
        }
      }
      localJSONObject.put("experiments", paramAnonymousString);
    }
    
    public void onOptimizelyExperimentVisited(OptimizelyExperimentData paramAnonymousOptimizelyExperimentData)
    {
      if (OptimizelyMixpanelIntegration.this.mMixpanelApi != null) {}
      try
      {
        JSONObject localJSONObject = new JSONObject();
        localJSONObject.put("experiment_name", paramAnonymousOptimizelyExperimentData.experimentName);
        localJSONObject.put("variation_name", paramAnonymousOptimizelyExperimentData.variationName);
        OptimizelyMixpanelIntegration.addSuperPropertiesFromOptimizely(OptimizelyMixpanelIntegration.this.mMixpanelApi);
        return;
      }
      catch (JSONException paramAnonymousOptimizelyExperimentData)
      {
        Log.e("OPTMP", paramAnonymousOptimizelyExperimentData.getLocalizedMessage());
      }
    }
  };
  private MixpanelAPI mMixpanelApi;
  
  private static void addSuperPropertiesFromOptimizely(MixpanelAPI paramMixpanelAPI)
  {
    try
    {
      JSONObject localJSONObject = new JSONObject();
      Iterator localIterator = Optimizely.getVisitedExperiments().values().iterator();
      while (localIterator.hasNext())
      {
        OptimizelyExperimentData localOptimizelyExperimentData = (OptimizelyExperimentData)localIterator.next();
        if ((localOptimizelyExperimentData.experimentName != null) && (localOptimizelyExperimentData.variationName != null)) {
          localJSONObject.put("[Optimizely] " + localOptimizelyExperimentData.experimentName, localOptimizelyExperimentData.variationName);
        }
      }
      paramMixpanelAPI.registerSuperProperties(localJSONObject);
      return;
    }
    catch (JSONException paramMixpanelAPI) {}
  }
  
  private static void setCustomTagsFromMixpanel(MixpanelAPI paramMixpanelAPI)
  {
    paramMixpanelAPI = paramMixpanelAPI.getSuperProperties();
    Iterator localIterator = paramMixpanelAPI.keys();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      try
      {
        Optimizely.setCustomTag("MP: " + str, paramMixpanelAPI.get(str).toString());
      }
      catch (JSONException localJSONException) {}
    }
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
    return "mixpanel_mobile";
  }
  
  @Nullable
  public List<String> getRequiredPermissions(Context paramContext)
  {
    return Arrays.asList(new String[] { "android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE" });
  }
  
  public void reportTrack(String paramString)
  {
    if (!paramString.startsWith("MP: ")) {
      Optimizely.trackEvent("MP: " + paramString);
    }
  }
  
  public boolean start(Optimizely paramOptimizely, JSONObject paramJSONObject)
  {
    this.mMixpanelApi = MixpanelApiRetriever.getApiInstance();
    if (this.mMixpanelApi != null)
    {
      setCustomTagsFromMixpanel(this.mMixpanelApi);
      addSuperPropertiesFromOptimizely(this.mMixpanelApi);
    }
    return this.mMixpanelApi != null;
  }
  
  public void stop()
  {
    this.mMixpanelApi = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/integrations/mixpanel/OptimizelyMixpanelIntegration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */