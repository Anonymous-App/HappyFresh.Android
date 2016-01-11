package com.optimizely.Audiences;

import android.support.annotation.NonNull;
import com.optimizely.Core.OptimizelyData;
import com.optimizely.JSON.OptimizelyExperiment;
import com.optimizely.Optimizely;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class OptimizelyAudiencesManager
{
  private DimensionsEvaluatorFactory dimensionsEvaluatorFactory;
  private Optimizely optimizely;
  
  public OptimizelyAudiencesManager(@NonNull Optimizely paramOptimizely, @NonNull DimensionsEvaluatorFactory paramDimensionsEvaluatorFactory)
  {
    this.optimizely = paramOptimizely;
    this.dimensionsEvaluatorFactory = paramDimensionsEvaluatorFactory;
  }
  
  public boolean checkUserForAudience(@NonNull String paramString, Map<String, Object> paramMap)
  {
    paramMap = (Map)paramMap.get(paramString);
    if (paramMap == null)
    {
      this.optimizely.verboseLog(true, "AUDIENCES", "Audience with ID %s was not found", new Object[] { paramString });
      return false;
    }
    return this.dimensionsEvaluatorFactory.getDefault().evaluate(paramMap);
  }
  
  public boolean evaluate(@NonNull OptimizelyExperiment paramOptimizelyExperiment)
  {
    Map localMap = (Map)this.optimizely.getOptimizelyData().getAudiences();
    paramOptimizelyExperiment = paramOptimizelyExperiment.getAudiences();
    boolean bool = true;
    paramOptimizelyExperiment = paramOptimizelyExperiment.iterator();
    while (paramOptimizelyExperiment.hasNext()) {
      bool &= checkUserForAudience((String)paramOptimizelyExperiment.next(), localMap);
    }
    return bool;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Audiences/OptimizelyAudiencesManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */