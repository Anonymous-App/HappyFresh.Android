package com.optimizely.Audiences;

import android.content.Context;
import android.support.annotation.NonNull;
import com.optimizely.Optimizely;
import java.util.Locale;
import java.util.Map;

public class LanguageEvaluator
  implements DimensionsEvaluator<Map<String, String>>
{
  private static final String COMPONENT = "LanguageEvaluator";
  private Optimizely optimizely;
  
  public LanguageEvaluator(@NonNull Optimizely paramOptimizely)
  {
    this.optimizely = paramOptimizely;
  }
  
  private Context context()
  {
    return this.optimizely.getCurrentContext();
  }
  
  public boolean evaluate(@NonNull Map<String, String> paramMap)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    try
    {
      String[] arrayOfString = ((String)paramMap.get("value")).split("-");
      bool1 = bool2;
      Locale localLocale = Locale.getDefault();
      bool1 = bool2;
      bool2 = localLocale.getLanguage().equalsIgnoreCase(arrayOfString[0]);
      boolean bool3 = bool2;
      if (bool2)
      {
        bool3 = bool2;
        bool1 = bool2;
        if (arrayOfString.length > 1)
        {
          bool1 = bool2;
          bool3 = localLocale.getCountry().equalsIgnoreCase(arrayOfString[1]);
        }
      }
      return bool3;
    }
    catch (Exception localException)
    {
      this.optimizely.verboseLog(true, "LanguageEvaluator", "Failure in processing audiences for dimension data %s", new Object[] { paramMap, localException });
    }
    return bool1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Audiences/LanguageEvaluator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */