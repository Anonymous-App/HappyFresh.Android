package com.optimizely.Audiences;

import android.support.annotation.NonNull;
import com.optimizely.Build;
import com.optimizely.Optimizely;
import java.util.Map;

public class AndroidOptimizelySDKVersionEvaluator
  implements DimensionsEvaluator<Map<String, String>>
{
  private static final String COMPONENT = "AndroidOptimizelySDKVersionEvaluator";
  private Optimizely optimizely;
  
  public AndroidOptimizelySDKVersionEvaluator(@NonNull Optimizely paramOptimizely)
  {
    this.optimizely = paramOptimizely;
  }
  
  public boolean evaluate(@NonNull Map<String, String> paramMap)
  {
    String str1 = (String)paramMap.get("match");
    String str2 = (String)paramMap.get("value");
    try
    {
      boolean bool = Matchers.matchStringNumeric(str1, str2, Build.sdkVersion());
      return bool;
    }
    catch (Exception localException)
    {
      this.optimizely.verboseLog(true, "AndroidOptimizelySDKVersionEvaluator", "Failure in processing audiences for dimension data %s", new Object[] { paramMap, localException });
    }
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Audiences/AndroidOptimizelySDKVersionEvaluator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */