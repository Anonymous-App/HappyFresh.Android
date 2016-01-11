package com.optimizely.integration;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.optimizely.Audiences.AudienceUtils;
import com.optimizely.Core.OptimizelyData;
import com.optimizely.JSON.OptimizelyCodeTest;
import com.optimizely.JSON.OptimizelyExperiment;
import com.optimizely.JSON.OptimizelyVariable;
import com.optimizely.JSON.OptimizelyVariation;
import com.optimizely.JSON.OptimizelyView;
import com.optimizely.Optimizely;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class IntegrationEventsDispatcher
{
  private static final String OPTIMIZELY_INTEGRATIONS_EVENTS_DISPATCHER_COMPONENT = "IntegrationEventsDispatcher";
  private static Optimizely optimizely;
  private boolean alreadyTriedToStart = false;
  private String failedToStartError;
  @NonNull
  private final List<WeakReference<OptimizelyEventListener>> listeners = new ArrayList();
  
  public IntegrationEventsDispatcher(Optimizely paramOptimizely)
  {
    optimizely = paramOptimizely;
  }
  
  private static List<String> audienceStrings(@NonNull OptimizelyData paramOptimizelyData, @NonNull OptimizelyExperiment paramOptimizelyExperiment)
  {
    ArrayList localArrayList = new ArrayList();
    Gson localGson = new GsonBuilder().setPrettyPrinting().create();
    paramOptimizelyExperiment = paramOptimizelyExperiment.getAudiences();
    paramOptimizelyData = (Map)paramOptimizelyData.getAudiences();
    paramOptimizelyExperiment = paramOptimizelyExperiment.iterator();
    while (paramOptimizelyExperiment.hasNext()) {
      localArrayList.add(localGson.toJson((Map)paramOptimizelyData.get((String)paramOptimizelyExperiment.next())));
    }
    return localArrayList;
  }
  
  private static String conditionsToString(@Nullable List<Object> paramList)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if ((paramList != null) && (paramList.size() > 1))
    {
      paramList = paramList.iterator();
      while (paramList.hasNext())
      {
        Object localObject = paramList.next();
        if ((localObject instanceof String))
        {
          localStringBuilder.append((String)localObject).append(": ");
        }
        else if ((localObject instanceof List))
        {
          localStringBuilder.append("[");
          localStringBuilder.append(conditionsToString((List)localObject));
          localStringBuilder.append("]");
        }
        else if ((localObject instanceof Map))
        {
          localObject = (Map)localObject;
          localStringBuilder.append(((Map)localObject).get("key"));
          localStringBuilder.append(" ").append(((Map)localObject).get("match")).append(" ");
          localStringBuilder.append(((Map)localObject).get("value"));
          localStringBuilder.append(",\n");
        }
      }
    }
    return localStringBuilder.toString();
  }
  
  public static List<OptimizelyVariationData> generateVariationData(@NonNull OptimizelyExperiment paramOptimizelyExperiment)
  {
    ArrayList localArrayList = new ArrayList();
    paramOptimizelyExperiment = paramOptimizelyExperiment.getVariations().iterator();
    while (paramOptimizelyExperiment.hasNext())
    {
      Object localObject2 = (OptimizelyVariation)paramOptimizelyExperiment.next();
      OptimizelyVariationData localOptimizelyVariationData = new OptimizelyVariationData();
      localOptimizelyVariationData.id = ((OptimizelyVariation)localObject2).getVariationId();
      localOptimizelyVariationData.name = ((OptimizelyVariation)localObject2).getDescription();
      localOptimizelyVariationData.traffic = ((OptimizelyVariation)localObject2).getTraffic();
      Object localObject1 = ((OptimizelyVariation)localObject2).getViews();
      JSONArray localJSONArray = new JSONArray();
      localObject1 = ((List)localObject1).iterator();
      Object localObject4;
      while (((Iterator)localObject1).hasNext())
      {
        OptimizelyView localOptimizelyView = (OptimizelyView)((Iterator)localObject1).next();
        try
        {
          localObject4 = new JSONObject();
          ((JSONObject)localObject4).put("value", localOptimizelyView.getValue());
          ((JSONObject)localObject4).put("key", localOptimizelyView.getKey());
          ((JSONObject)localObject4).put("id", localOptimizelyView.getOptimizelyId());
          ((JSONObject)localObject4).put("type", localOptimizelyView.getType());
          localJSONArray.put(localObject4);
        }
        catch (JSONException localJSONException1)
        {
          optimizely.verboseLog(true, "IntegrationEventsDispatcher", "Error putting JSONObject for view into variation data. Error: %e", new Object[] { localJSONException1.getLocalizedMessage() });
        }
      }
      Object localObject3 = ((OptimizelyVariation)localObject2).getCodeTests();
      localObject1 = new JSONArray();
      localObject3 = ((List)localObject3).iterator();
      JSONObject localJSONObject;
      while (((Iterator)localObject3).hasNext())
      {
        localObject4 = (OptimizelyCodeTest)((Iterator)localObject3).next();
        try
        {
          localJSONObject = new JSONObject();
          localJSONObject.put("blockKey", ((OptimizelyCodeTest)localObject4).getBlockKey());
          localJSONObject.put("blockName", ((OptimizelyCodeTest)localObject4).getBlockName());
          ((JSONArray)localObject1).put(localJSONObject);
        }
        catch (JSONException localJSONException2)
        {
          optimizely.verboseLog(true, "IntegrationEventsDispatcher", "Error putting JSONObject for code block in variation data. Error: %e", new Object[] { localJSONException2.getLocalizedMessage() });
        }
      }
      localObject3 = ((OptimizelyVariation)localObject2).getVariables();
      localObject2 = new JSONArray();
      localObject3 = ((List)localObject3).iterator();
      while (((Iterator)localObject3).hasNext())
      {
        OptimizelyVariable localOptimizelyVariable = (OptimizelyVariable)((Iterator)localObject3).next();
        try
        {
          localJSONObject = new JSONObject();
          localJSONObject.put("value", localOptimizelyVariable.getValue());
          localJSONObject.put("defaultValue", localOptimizelyVariable.getDefaultValue());
          localJSONObject.put("type", localOptimizelyVariable.getType());
          localJSONObject.put("variableKey", localOptimizelyVariable.getVariableKey());
          ((JSONArray)localObject2).put(localJSONObject);
        }
        catch (JSONException localJSONException3)
        {
          optimizely.verboseLog(true, "IntegrationEventsDispatcher", "Error putting JSONObject for live variable in variation data. Error: %e", new Object[] { localJSONException3.getLocalizedMessage() });
        }
      }
      localOptimizelyVariationData.views = localJSONArray;
      localOptimizelyVariationData.codeBlocks = ((JSONArray)localObject1);
      localOptimizelyVariationData.variables = ((JSONArray)localObject2);
      localArrayList.add(localOptimizelyVariationData);
    }
    return localArrayList;
  }
  
  @NonNull
  private List<OptimizelyEventListener> getListeners()
  {
    ArrayList localArrayList = new ArrayList();
    for (;;)
    {
      Object localObject;
      synchronized (this.listeners)
      {
        Iterator localIterator = this.listeners.iterator();
        if (!localIterator.hasNext()) {
          break;
        }
        localObject = (WeakReference)localIterator.next();
        if (localObject == null) {
          continue;
        }
        localObject = (OptimizelyEventListener)((WeakReference)localObject).get();
        if (localObject == null) {
          localIterator.remove();
        }
      }
      localList1.add(localObject);
    }
    return localList1;
  }
  
  @NonNull
  public static OptimizelyExperimentData getStateForExperiment(@NonNull OptimizelyData paramOptimizelyData, @NonNull OptimizelyExperiment paramOptimizelyExperiment)
  {
    int i = 0;
    OptimizelyExperimentData localOptimizelyExperimentData = new OptimizelyExperimentData();
    localOptimizelyExperimentData.experimentId = paramOptimizelyExperiment.getExperimentId();
    localOptimizelyExperimentData.experimentName = paramOptimizelyExperiment.getDescription();
    localOptimizelyExperimentData.enabled = Boolean.valueOf(paramOptimizelyExperiment.isActive());
    OptimizelyVariation localOptimizelyVariation = paramOptimizelyExperiment.getActiveVariation();
    if (localOptimizelyVariation != null)
    {
      localOptimizelyExperimentData.variationId = localOptimizelyVariation.getVariationId();
      localOptimizelyExperimentData.variationName = localOptimizelyVariation.getDescription();
    }
    localOptimizelyExperimentData.visitedCount = paramOptimizelyExperiment.getVisitedCount();
    boolean bool;
    if (paramOptimizelyExperiment.getVisitedCount() > 0)
    {
      bool = true;
      label90:
      localOptimizelyExperimentData.visitedEver = bool;
      localOptimizelyExperimentData.visitedThisSession = paramOptimizelyExperiment.getState().equals("ExperimentStateRunningAndViewed");
      localOptimizelyExperimentData.targetingMet = paramOptimizelyExperiment.isPassesTargeting();
      localOptimizelyExperimentData.locked = paramOptimizelyExperiment.isLocked();
      localOptimizelyExperimentData.isManual = paramOptimizelyExperiment.isManual();
      localOptimizelyExperimentData.variations = generateVariationData(paramOptimizelyExperiment);
      if (!AudienceUtils.isAudiencesEnabled(paramOptimizelyExperiment)) {
        break label256;
      }
      localOptimizelyExperimentData.audiences = audienceStrings(paramOptimizelyData, paramOptimizelyExperiment);
      label164:
      paramOptimizelyData = paramOptimizelyExperiment.getState();
      switch (paramOptimizelyData.hashCode())
      {
      default: 
        label216:
        i = -1;
      }
    }
    for (;;)
    {
      switch (i)
      {
      default: 
        return localOptimizelyExperimentData;
        bool = false;
        break label90;
        label256:
        localOptimizelyExperimentData.targetingConditions = conditionsToString(paramOptimizelyExperiment.getConditions());
        break label164;
        if (!paramOptimizelyData.equals("ExperimentStatePending")) {
          break label216;
        }
        continue;
        if (!paramOptimizelyData.equals("ExperimentStateRunning")) {
          break label216;
        }
        i = 1;
        continue;
        if (!paramOptimizelyData.equals("ExperimentStateRunningAndViewed")) {
          break label216;
        }
        i = 2;
        continue;
        if (!paramOptimizelyData.equals("ExperimentStateDeactivated")) {
          break label216;
        }
        i = 3;
      }
    }
    localOptimizelyExperimentData.state = "optimizelyExperimentDataStateRunning";
    localOptimizelyExperimentData.targetingMet = true;
    return localOptimizelyExperimentData;
    if (paramOptimizelyExperiment.isActive())
    {
      localOptimizelyExperimentData.state = "optimizelyExperimentDataStateDeactivated";
      return localOptimizelyExperimentData;
    }
    localOptimizelyExperimentData.state = "optimizelyExperimentDataStateDisabled";
    return localOptimizelyExperimentData;
  }
  
  public void addListener(@NonNull OptimizelyEventListener paramOptimizelyEventListener)
  {
    List localList = this.listeners;
    for (int i = 0;; i = 1) {
      try
      {
        Iterator localIterator = this.listeners.iterator();
        Object localObject;
        do
        {
          for (;;)
          {
            if (!localIterator.hasNext()) {
              break label91;
            }
            localObject = (WeakReference)localIterator.next();
            if (localObject != null)
            {
              localObject = (OptimizelyEventListener)((WeakReference)localObject).get();
              if (localObject != null) {
                break;
              }
              localIterator.remove();
            }
          }
        } while (!localObject.equals(paramOptimizelyEventListener));
      }
      finally {}
    }
    label91:
    if (i == 0)
    {
      this.listeners.add(new WeakReference(paramOptimizelyEventListener));
      if (this.alreadyTriedToStart)
      {
        if (this.failedToStartError != null) {
          break label136;
        }
        paramOptimizelyEventListener.onOptimizelyStarted();
      }
    }
    for (;;)
    {
      return;
      label136:
      paramOptimizelyEventListener.onOptimizelyFailedToStart(this.failedToStartError);
    }
  }
  
  public void removeListener(@NonNull OptimizelyEventListener paramOptimizelyEventListener)
  {
    synchronized (this.listeners)
    {
      Iterator localIterator = this.listeners.iterator();
      while (localIterator.hasNext())
      {
        Object localObject = (WeakReference)localIterator.next();
        if (localObject != null)
        {
          localObject = (OptimizelyEventListener)((WeakReference)localObject).get();
          if ((localObject == null) || (localObject.equals(paramOptimizelyEventListener))) {
            localIterator.remove();
          }
        }
      }
    }
  }
  
  public void sendDataFileLoadedEvent()
  {
    Iterator localIterator = getListeners().iterator();
    while (localIterator.hasNext()) {
      ((OptimizelyEventListener)localIterator.next()).onOptimizelyDataFileLoaded();
    }
  }
  
  public void sendExperimentVisitedEvent(@NonNull OptimizelyData paramOptimizelyData, @NonNull OptimizelyExperiment paramOptimizelyExperiment)
  {
    paramOptimizelyData = getStateForExperiment(paramOptimizelyData, paramOptimizelyExperiment);
    paramOptimizelyExperiment = getListeners().iterator();
    while (paramOptimizelyExperiment.hasNext()) {
      ((OptimizelyEventListener)paramOptimizelyExperiment.next()).onOptimizelyExperimentVisited(paramOptimizelyData);
    }
  }
  
  public void sendGoalTriggeredEvent(@NonNull String paramString, @NonNull List<OptimizelyExperimentData> paramList)
  {
    Iterator localIterator = getListeners().iterator();
    while (localIterator.hasNext()) {
      ((OptimizelyEventListener)localIterator.next()).onGoalTriggered(paramString, paramList);
    }
  }
  
  public void sendMessage(@NonNull String paramString1, @NonNull String paramString2, @NonNull Bundle paramBundle)
  {
    Iterator localIterator = getListeners().iterator();
    while (localIterator.hasNext()) {
      ((OptimizelyEventListener)localIterator.next()).onMessage(paramString1, paramString2, paramBundle);
    }
  }
  
  public void sendOptimizelyEditorEnabled()
  {
    Iterator localIterator = getListeners().iterator();
    while (localIterator.hasNext()) {
      ((OptimizelyEventListener)localIterator.next()).onOptimizelyEditorEnabled();
    }
    this.alreadyTriedToStart = true;
  }
  
  public void sendOptimizelyFailedToStart(@NonNull String paramString)
  {
    Iterator localIterator = getListeners().iterator();
    while (localIterator.hasNext()) {
      ((OptimizelyEventListener)localIterator.next()).onOptimizelyFailedToStart(paramString);
    }
    this.alreadyTriedToStart = true;
    this.failedToStartError = paramString;
  }
  
  public void sendOptimizelyStarted()
  {
    Iterator localIterator = getListeners().iterator();
    while (localIterator.hasNext()) {
      ((OptimizelyEventListener)localIterator.next()).onOptimizelyStarted();
    }
    this.alreadyTriedToStart = true;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/integration/IntegrationEventsDispatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */