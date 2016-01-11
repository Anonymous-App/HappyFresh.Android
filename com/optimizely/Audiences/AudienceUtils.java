package com.optimizely.Audiences;

import android.support.annotation.NonNull;
import com.optimizely.JSON.OptimizelyExperiment;
import java.util.List;
import java.util.Map;

public class AudienceUtils
{
  public static String findOperator(@NonNull Object paramObject)
  {
    try
    {
      if ((paramObject instanceof List)) {
        paramObject = (String)((List)paramObject).get(0);
      }
      while (paramObject == null)
      {
        throw new IllegalArgumentException("Unexpected argument: cannot find the operator.");
        if ((paramObject instanceof Map)) {
          paramObject = (String)((Map)paramObject).get("condition_type");
        } else {
          throw new IllegalArgumentException("Unsupported audience json: expected either list or map.");
        }
      }
    }
    catch (Exception paramObject)
    {
      throw new IllegalArgumentException("Unexpected argument: cannot find the operator.", (Throwable)paramObject);
    }
    return (String)paramObject;
  }
  
  public static boolean isAudiencesEnabled(@NonNull OptimizelyExperiment paramOptimizelyExperiment)
  {
    return (paramOptimizelyExperiment.getAudiences() != null) && (!paramOptimizelyExperiment.getAudiences().isEmpty());
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Audiences/AudienceUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */