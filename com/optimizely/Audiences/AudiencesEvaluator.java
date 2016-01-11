package com.optimizely.Audiences;

import android.support.annotation.NonNull;
import com.optimizely.Optimizely;
import java.util.List;
import java.util.Map;

public class AudiencesEvaluator
  implements DimensionsEvaluator<Map<String, Object>>
{
  private static final String COMPONENT = "AudiencesEvaluator";
  static final String KEY = "conditions";
  private DimensionsEvaluatorFactory dimensionsEvaluatorFactory;
  private Optimizely optimizely;
  
  public AudiencesEvaluator(@NonNull Optimizely paramOptimizely, @NonNull DimensionsEvaluatorFactory paramDimensionsEvaluatorFactory)
  {
    this.optimizely = paramOptimizely;
    this.dimensionsEvaluatorFactory = paramDimensionsEvaluatorFactory;
  }
  
  public boolean evaluate(@NonNull Map<String, Object> paramMap)
  {
    List localList = (List)paramMap.get("conditions");
    try
    {
      String str = AudienceUtils.findOperator(localList);
      boolean bool = this.dimensionsEvaluatorFactory.get(str).evaluate(localList);
      return bool;
    }
    catch (Exception localException)
    {
      this.optimizely.verboseLog(true, "AudiencesEvaluator", "Failure in processing audiences for dimension data %s", new Object[] { paramMap, localException });
    }
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Audiences/AudiencesEvaluator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */