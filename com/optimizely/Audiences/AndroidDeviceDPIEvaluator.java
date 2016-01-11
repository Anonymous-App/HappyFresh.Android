package com.optimizely.Audiences;

import android.support.annotation.NonNull;
import com.optimizely.Core.OptimizelyUtils;
import com.optimizely.Optimizely;
import java.util.HashMap;
import java.util.Map;

public class AndroidDeviceDPIEvaluator
  implements DimensionsEvaluator<Map<String, String>>
{
  private static final String COMPONENT = "AndroidDeviceDPIEvaluator";
  static Map<String, Integer> DPI_NAME_TO_CONSTANT_MAPPING = new HashMap(6);
  private Optimizely optimizely;
  
  static
  {
    DPI_NAME_TO_CONSTANT_MAPPING.put("ldpi", Integer.valueOf(120));
    DPI_NAME_TO_CONSTANT_MAPPING.put("mdpi", Integer.valueOf(160));
    DPI_NAME_TO_CONSTANT_MAPPING.put("hdpi", Integer.valueOf(240));
    DPI_NAME_TO_CONSTANT_MAPPING.put("xhdpi", Integer.valueOf(320));
    DPI_NAME_TO_CONSTANT_MAPPING.put("xxhdpi", Integer.valueOf(480));
    DPI_NAME_TO_CONSTANT_MAPPING.put("xxxhdpi", Integer.valueOf(640));
  }
  
  public AndroidDeviceDPIEvaluator(@NonNull Optimizely paramOptimizely)
  {
    this.optimizely = paramOptimizely;
  }
  
  public boolean evaluate(@NonNull Map<String, String> paramMap)
  {
    String str1 = (String)paramMap.get("match");
    String str2 = (String)paramMap.get("value");
    if ((str2 == null) || (!DPI_NAME_TO_CONSTANT_MAPPING.containsKey(str2))) {
      return false;
    }
    try
    {
      boolean bool = Matchers.matchNumeric(str1, ((Integer)DPI_NAME_TO_CONSTANT_MAPPING.get(str2)).intValue(), OptimizelyUtils.getDeviceDPI(this.optimizely.getCurrentContext()));
      return bool;
    }
    catch (Exception localException)
    {
      this.optimizely.verboseLog(true, "AndroidDeviceDPIEvaluator", "Failure in processing audiences for dimension data %s", new Object[] { paramMap, localException });
    }
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Audiences/AndroidDeviceDPIEvaluator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */