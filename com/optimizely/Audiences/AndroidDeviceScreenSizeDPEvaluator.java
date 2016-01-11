package com.optimizely.Audiences;

import android.support.annotation.NonNull;
import com.optimizely.Core.OptimizelyUtils;
import com.optimizely.Optimizely;
import java.util.HashMap;
import java.util.Map;

public class AndroidDeviceScreenSizeDPEvaluator
  implements DimensionsEvaluator<Map<String, String>>
{
  private static final String COMPONENT = "AndroidDeviceScreenSizeDPEvaluator";
  static Map<String, Integer> DP_NAME_TO_CONSTANT_MAPPING = new HashMap(6);
  private Optimizely optimizely;
  
  static
  {
    DP_NAME_TO_CONSTANT_MAPPING.put("small", Integer.valueOf(1));
    DP_NAME_TO_CONSTANT_MAPPING.put("normal", Integer.valueOf(2));
    DP_NAME_TO_CONSTANT_MAPPING.put("large", Integer.valueOf(3));
    DP_NAME_TO_CONSTANT_MAPPING.put("xlarge", Integer.valueOf(4));
  }
  
  public AndroidDeviceScreenSizeDPEvaluator(@NonNull Optimizely paramOptimizely)
  {
    this.optimizely = paramOptimizely;
  }
  
  public boolean evaluate(@NonNull Map<String, String> paramMap)
  {
    String str1 = (String)paramMap.get("match");
    String str2 = (String)paramMap.get("value");
    if ((str2 == null) || (!DP_NAME_TO_CONSTANT_MAPPING.containsKey(str2))) {
      return false;
    }
    try
    {
      boolean bool = Matchers.matchNumeric(str1, ((Integer)DP_NAME_TO_CONSTANT_MAPPING.get(str2)).intValue(), OptimizelyUtils.getScreenSize(this.optimizely.getCurrentContext()));
      return bool;
    }
    catch (Exception localException)
    {
      this.optimizely.verboseLog(true, "AndroidDeviceScreenSizeDPEvaluator", "Failure in processing audiences for dimension data %s", new Object[] { paramMap, localException });
    }
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Audiences/AndroidDeviceScreenSizeDPEvaluator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */