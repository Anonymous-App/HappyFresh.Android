package com.optimizely.integrations.amplitude;

import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View.OnTouchListener;
import com.amplitude.api.AmplitudeClient;
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

public class OptimizelyAmplitudeIntegration
  implements OptimizelyPlugin
{
  private AmplitudeClient mAmplitudeAPI;
  private OptimizelyEventListener mListener = new DefaultOptimizelyEventListener()
  {
    public void onGoalTriggered(String paramAnonymousString, List<OptimizelyExperimentData> paramAnonymousList)
    {
      JSONObject localJSONObject;
      if (OptimizelyAmplitudeIntegration.this.mAmplitudeAPI != null) {
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
          Log.e("OPTAMP", paramAnonymousString.getLocalizedMessage());
        }
      }
      localJSONObject.put("experiments", paramAnonymousString);
      OptimizelyAmplitudeIntegration.this.mAmplitudeAPI.logEvent("Optimizely Goal Triggered", localJSONObject);
    }
    
    public void onOptimizelyExperimentVisited(OptimizelyExperimentData paramAnonymousOptimizelyExperimentData)
    {
      try
      {
        JSONObject localJSONObject = new JSONObject();
        localJSONObject.put("experiment_name", paramAnonymousOptimizelyExperimentData.experimentName);
        localJSONObject.put("variation_name", paramAnonymousOptimizelyExperimentData.variationName);
        OptimizelyAmplitudeIntegration.this.mAmplitudeAPI.logEvent("Optimizely Experiment Visited", localJSONObject);
        OptimizelyAmplitudeIntegration.addUserPropertiesFromOptimizely(OptimizelyAmplitudeIntegration.this.mAmplitudeAPI);
        return;
      }
      catch (JSONException paramAnonymousOptimizelyExperimentData)
      {
        Log.e("OPTAMP", paramAnonymousOptimizelyExperimentData.getLocalizedMessage());
      }
    }
  };
  
  private static void addUserPropertiesFromOptimizely(@NonNull AmplitudeClient paramAmplitudeClient)
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
      paramAmplitudeClient.setUserProperties(localJSONObject);
      return;
    }
    catch (JSONException paramAmplitudeClient) {}
  }
  
  @Nullable
  public Application.ActivityLifecycleCallbacks getActivityLifecycleCallbacks()
  {
    return null;
  }
  
  @Nullable
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
    return "amplitude_mobile";
  }
  
  @Nullable
  public List<String> getRequiredPermissions(Context paramContext)
  {
    return Arrays.asList(new String[] { "android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE" });
  }
  
  public boolean start(Optimizely paramOptimizely, JSONObject paramJSONObject)
  {
    this.mAmplitudeAPI = AmplitudeClient.getInstance();
    if (this.mAmplitudeAPI != null) {
      addUserPropertiesFromOptimizely(this.mAmplitudeAPI);
    }
    return this.mAmplitudeAPI != null;
  }
  
  public void stop()
  {
    this.mAmplitudeAPI = null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/integrations/amplitude/OptimizelyAmplitudeIntegration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */