package com.optimizely.Audiences;

import android.support.annotation.NonNull;
import com.optimizely.Core.OptimizelyData;
import com.optimizely.Optimizely;
import java.util.Map;

public class CustomTagEvaluator
  implements DimensionsEvaluator<Map<String, String>>
{
  private Optimizely optimizely;
  
  public CustomTagEvaluator(@NonNull Optimizely paramOptimizely)
  {
    this.optimizely = paramOptimizely;
  }
  
  public boolean evaluate(@NonNull Map<String, String> paramMap)
  {
    String str2 = (String)paramMap.get("match");
    String str1 = str2;
    if (str2 == null) {
      str1 = "equals";
    }
    str2 = (String)paramMap.get("value");
    paramMap = this.optimizely.getOptimizelyData().getCustomTag((String)paramMap.get("name"));
    if ((str2 == null) || (paramMap == null)) {}
    do
    {
      return false;
      if ("exists".equals(str1))
      {
        if (paramMap != null) {}
        for (boolean bool = true;; bool = false) {
          return bool;
        }
      }
      if ("substring".equals(str1)) {
        return paramMap.contains(str2);
      }
      if (("exact".equals(str1)) || ("equals".equals(str1))) {
        return str2.equals(paramMap);
      }
    } while (!"regex".equals(str1));
    return paramMap.matches(str2);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Audiences/CustomTagEvaluator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */