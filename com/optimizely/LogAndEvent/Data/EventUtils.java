package com.optimizely.LogAndEvent.Data;

import com.optimizely.Build;
import com.optimizely.Core.OptimizelyData;
import com.optimizely.Core.OptimizelyUtils;
import com.optimizely.JSON.OptimizelyConfig;
import com.optimizely.JSON.OptimizelyExperiment;
import com.optimizely.JSON.OptimizelyVariation;
import com.optimizely.Optimizely;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

class EventUtils
{
  static void putEventParams(Optimizely paramOptimizely, String paramString1, String paramString2, List<OptimizelyExperiment> paramList, JSONObject paramJSONObject)
    throws JSONException
  {
    putSharedEventParams(paramOptimizely, paramJSONObject);
    paramJSONObject.put("n", paramString2);
    paramJSONObject.put("g[]", paramString1);
    paramString1 = paramList.iterator();
    if (paramString1.hasNext())
    {
      paramOptimizely = (OptimizelyExperiment)paramString1.next();
      paramString2 = "x" + paramOptimizely.getExperimentId();
      paramOptimizely = paramOptimizely.getActiveVariation();
      if ((paramOptimizely != null) && (paramOptimizely.getVariationId() != null)) {}
      for (paramOptimizely = paramOptimizely.getVariationId();; paramOptimizely = "null")
      {
        paramJSONObject.put(paramString2, paramOptimizely);
        break;
      }
    }
  }
  
  private static void putSharedEventParams(Optimizely paramOptimizely, JSONObject paramJSONObject)
    throws JSONException
  {
    OptimizelyConfig localOptimizelyConfig = paramOptimizely.getOptimizelyData().getConfig();
    int i;
    if (localOptimizelyConfig != null)
    {
      i = 1;
      paramJSONObject.put("u", "oeu-" + paramOptimizely.getRandomUserId(paramOptimizely.getCurrentContext()));
      if (paramOptimizely.getPPID(paramOptimizely.getCurrentContext()) != null) {
        paramJSONObject.put("p", paramOptimizely.getPPID(paramOptimizely.getCurrentContext()));
      }
      paramJSONObject.put("a", paramOptimizely.getProjectId());
      paramJSONObject.put("d", paramOptimizely.getOptimizelyData().getAccountId());
      paramJSONObject.put("time", Long.toString(System.currentTimeMillis() / 1000L));
      if ((i == 0) || (!localOptimizelyConfig.isIpAnonymization())) {
        break label173;
      }
    }
    label173:
    for (boolean bool = true;; bool = false)
    {
      paramJSONObject.put("y", bool);
      paramJSONObject.put("src", String.format("%s:%s", new Object[] { OptimizelyUtils.deviceName(), Build.sdkVersion() }));
      return;
      i = 0;
      break;
    }
  }
  
  static void putVisitorEventParams(Optimizely paramOptimizely, OptimizelyExperiment paramOptimizelyExperiment, JSONObject paramJSONObject)
    throws JSONException
  {
    putSharedEventParams(paramOptimizely, paramJSONObject);
    paramJSONObject.put("n", "visitor-event");
    paramJSONObject.put("g[]", paramOptimizelyExperiment.getExperimentId());
    String str = "x" + paramOptimizelyExperiment.getExperimentId();
    paramOptimizely = paramOptimizelyExperiment.getActiveVariation();
    if ((paramOptimizely != null) && (paramOptimizely.getVariationId() != null)) {}
    for (paramOptimizely = paramOptimizely.getVariationId();; paramOptimizely = "null")
    {
      paramJSONObject.put(str, paramOptimizely);
      return;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/LogAndEvent/Data/EventUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */