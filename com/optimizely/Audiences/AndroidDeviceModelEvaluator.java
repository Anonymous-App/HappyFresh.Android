package com.optimizely.Audiences;

import android.support.annotation.NonNull;
import com.optimizely.Core.OptimizelyUtils;
import com.optimizely.Optimizely;
import java.util.Map;

public class AndroidDeviceModelEvaluator
  implements DimensionsEvaluator<Map<String, String>>
{
  private static final String COMPONENT = "AndroidDeviceModelEvaluator";
  private Optimizely optimizely;
  
  public AndroidDeviceModelEvaluator(@NonNull Optimizely paramOptimizely)
  {
    this.optimizely = paramOptimizely;
  }
  
  public boolean evaluate(@NonNull Map<String, String> paramMap)
  {
    boolean bool1 = true;
    String str = (String)paramMap.get("value");
    if (str == null) {
      return false;
    }
    try
    {
      boolean bool2 = OptimizelyUtils.isTablet(this.optimizely.getCurrentContext());
      boolean bool3 = str.equalsIgnoreCase("tablet");
      if (bool3 == bool2) {}
      for (;;)
      {
        return bool1;
        bool1 = false;
      }
      return false;
    }
    catch (Exception localException)
    {
      this.optimizely.verboseLog(true, "AndroidDeviceModelEvaluator", "Failure in processing audiences for dimension data %s", new Object[] { paramMap, localException });
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Audiences/AndroidDeviceModelEvaluator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */