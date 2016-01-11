package com.optimizely.LogAndEvent.Data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.optimizely.Core.OptimizelySegmentsManager;
import com.optimizely.JSON.OptimizelyExperiment;
import com.optimizely.Optimizely;
import org.json.JSONException;
import org.json.JSONObject;

public class OptimizelyVisitorEvent
{
  private final OptimizelyExperiment experiment;
  private final Optimizely optimizely;
  private final OptimizelySegmentsManager segmentsManager;
  
  public OptimizelyVisitorEvent(@NonNull Optimizely paramOptimizely, @NonNull OptimizelyExperiment paramOptimizelyExperiment)
  {
    this.optimizely = paramOptimizely;
    this.experiment = paramOptimizelyExperiment;
    this.segmentsManager = new OptimizelySegmentsManager(paramOptimizely);
  }
  
  @Nullable
  public String getJSON()
  {
    Object localObject = new JSONObject();
    try
    {
      EventUtils.putVisitorEventParams(this.optimizely, this.experiment, (JSONObject)localObject);
      this.segmentsManager.addSegmentingInformation((JSONObject)localObject);
      localObject = ((JSONObject)localObject).toString();
      return (String)localObject;
    }
    catch (JSONException localJSONException) {}
    return null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/LogAndEvent/Data/OptimizelyVisitorEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */