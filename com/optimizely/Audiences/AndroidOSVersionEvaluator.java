package com.optimizely.Audiences;

import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import com.optimizely.Optimizely;
import java.util.Map;

public class AndroidOSVersionEvaluator
  implements DimensionsEvaluator<Map<String, String>>
{
  private static final String COMPONENT = "AndroidOSVersionEvaluator";
  private Optimizely optimizely;
  
  public AndroidOSVersionEvaluator(@NonNull Optimizely paramOptimizely)
  {
    this.optimizely = paramOptimizely;
  }
  
  public boolean evaluate(@NonNull Map<String, String> paramMap)
  {
    String str1 = (String)paramMap.get("match");
    String str2 = (String)paramMap.get("value");
    if (str2 == null) {
      return false;
    }
    try
    {
      boolean bool = Matchers.matchNumeric(str1, Long.valueOf(str2).longValue(), Build.VERSION.SDK_INT);
      return bool;
    }
    catch (Exception localException)
    {
      this.optimizely.verboseLog(true, "AndroidOSVersionEvaluator", "Failure in processing audiences for dimension data %s", new Object[] { paramMap, localException });
    }
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Audiences/AndroidOSVersionEvaluator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */