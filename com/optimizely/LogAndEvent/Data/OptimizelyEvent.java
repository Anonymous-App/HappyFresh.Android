package com.optimizely.LogAndEvent.Data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.optimizely.Core.OptimizelyData;
import com.optimizely.Core.OptimizelySegmentsManager;
import com.optimizely.JSON.OptimizelyExperiment;
import com.optimizely.Optimizely;
import com.optimizely.integration.IntegrationEventsDispatcher;
import com.optimizely.integration.OptimizelyExperimentData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class OptimizelyEvent
{
  private final String description;
  private final String goalId;
  private final Optimizely optimizely;
  private final OptimizelySegmentsManager segmentsManager;
  
  public OptimizelyEvent(@NonNull Optimizely paramOptimizely, @NonNull String paramString1, @Nullable String paramString2)
  {
    this.optimizely = paramOptimizely;
    this.goalId = paramString1;
    if (paramString2 != null) {}
    for (;;)
    {
      this.description = paramString2;
      this.segmentsManager = new OptimizelySegmentsManager(paramOptimizely);
      return;
      paramString2 = "";
    }
  }
  
  @NonNull
  private List<OptimizelyExperiment> getAffectedExperiments()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.optimizely.getOptimizelyData().getRunningExperimentsById().entrySet().iterator();
    while (localIterator.hasNext())
    {
      OptimizelyExperiment localOptimizelyExperiment = (OptimizelyExperiment)((Map.Entry)localIterator.next()).getValue();
      if ((localOptimizelyExperiment != null) && (localOptimizelyExperiment.getExperimentId() != null) && (localOptimizelyExperiment.getActiveVariation() != null) && (localOptimizelyExperiment.getVisitedCount() > 0) && (this.optimizely.getOptimizelyData().goalContainsExperiment(this.goalId, localOptimizelyExperiment.getExperimentId()))) {
        localArrayList.add(localOptimizelyExperiment);
      }
    }
    return localArrayList;
  }
  
  @NonNull
  public List<OptimizelyExperimentData> getAffectedExperimentData()
  {
    Object localObject = getAffectedExperiments();
    ArrayList localArrayList = new ArrayList(((List)localObject).size());
    localObject = ((List)localObject).iterator();
    while (((Iterator)localObject).hasNext())
    {
      OptimizelyExperiment localOptimizelyExperiment = (OptimizelyExperiment)((Iterator)localObject).next();
      localArrayList.add(IntegrationEventsDispatcher.getStateForExperiment(this.optimizely.getOptimizelyData(), localOptimizelyExperiment));
    }
    return localArrayList;
  }
  
  @Nullable
  public String getJSON()
  {
    JSONObject localJSONObject = getJSONObject();
    if (localJSONObject != null) {
      return localJSONObject.toString();
    }
    return null;
  }
  
  @Nullable
  protected JSONObject getJSONObject()
  {
    List localList = getAffectedExperiments();
    if (!localList.isEmpty())
    {
      JSONObject localJSONObject = new JSONObject();
      try
      {
        EventUtils.putEventParams(this.optimizely, this.goalId, this.description, localList, localJSONObject);
        this.segmentsManager.addSegmentingInformation(localJSONObject);
        return localJSONObject;
      }
      catch (JSONException localJSONException)
      {
        return null;
      }
    }
    return null;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/LogAndEvent/Data/OptimizelyEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */