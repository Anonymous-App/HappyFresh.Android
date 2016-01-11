package com.optimizely.Audiences;

import android.support.annotation.NonNull;
import com.optimizely.Core.OptimizelyUtils;
import com.optimizely.Optimizely;
import java.util.Map;

public class AndroidDeviceScreenSizeInchesEvaluator
  implements DimensionsEvaluator<Map<String, String>>
{
  private static final String COMPONENT = "AndroidDeviceScreenSizeInchesEvaluator";
  private Optimizely optimizely;
  
  public AndroidDeviceScreenSizeInchesEvaluator(@NonNull Optimizely paramOptimizely)
  {
    this.optimizely = paramOptimizely;
  }
  
  public boolean evaluate(@NonNull Map<String, String> paramMap)
  {
    String str1 = (String)paramMap.get("match");
    String str2 = (String)paramMap.get("value");
    try
    {
      double d = OptimizelyUtils.getScreenSizeInInches(this.optimizely).doubleValue();
      boolean bool = Matchers.matchNumeric(str1, Long.valueOf(str2).longValue(), d);
      return bool;
    }
    catch (Exception localException)
    {
      this.optimizely.verboseLog(true, "AndroidDeviceScreenSizeInchesEvaluator", "Failure in processing audiences for dimension data %s", new Object[] { paramMap, localException });
    }
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Audiences/AndroidDeviceScreenSizeInchesEvaluator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */