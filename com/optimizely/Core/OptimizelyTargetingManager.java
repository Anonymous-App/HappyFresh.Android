package com.optimizely.Core;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.optimizely.Build;
import com.optimizely.JSON.OptimizelyExperiment;
import com.optimizely.Optimizely;
import java.util.List;
import java.util.Map;

public class OptimizelyTargetingManager
{
  private static final String TARGETING_COMPONENT = "OptimizelyTargetingManager";
  private static final String and_conjunct = "and";
  private static final String or_conjunct = "or";
  
  private static boolean doNumericComparison(float paramFloat1, float paramFloat2, @Nullable String paramString)
  {
    boolean bool = true;
    if (paramString == null) {
      bool = false;
    }
    do
    {
      do
      {
        do
        {
          do
          {
            do
            {
              do
              {
                return bool;
                if (!paramString.equals("!=")) {
                  break;
                }
              } while (paramFloat1 != paramFloat2);
              return false;
              if (!paramString.equals("=")) {
                break;
              }
            } while (paramFloat1 == paramFloat2);
            return false;
            if (!paramString.equals(">=")) {
              break;
            }
          } while (paramFloat1 >= paramFloat2);
          return false;
          if (!paramString.equals(">")) {
            break;
          }
        } while (paramFloat1 > paramFloat2);
        return false;
        if (!paramString.equals("<=")) {
          break;
        }
      } while (paramFloat1 <= paramFloat2);
      return false;
      if (!paramString.equals("<")) {
        break;
      }
    } while (paramFloat1 < paramFloat2);
    return false;
    return false;
  }
  
  private static boolean doStringComparison(@NonNull String paramString1, @NonNull String paramString2, @Nullable String paramString3)
  {
    boolean bool = true;
    if (paramString3 == null) {
      bool = false;
    }
    do
    {
      do
      {
        do
        {
          do
          {
            do
            {
              return bool;
              if (!paramString3.equals("!=")) {
                break;
              }
            } while (!paramString1.equals(paramString2));
            return false;
            if (paramString3.equals("=")) {
              return paramString1.equals(paramString2);
            }
            if (!paramString3.equals(">=")) {
              break;
            }
          } while (paramString1.compareTo(paramString2) >= 0);
          return false;
          if (!paramString3.equals(">")) {
            break;
          }
        } while (paramString1.compareTo(paramString2) > 0);
        return false;
        if (!paramString3.equals("<=")) {
          break;
        }
      } while (paramString1.compareTo(paramString2) <= 0);
      return false;
      if (!paramString3.equals("<")) {
        break;
      }
    } while (paramString1.compareTo(paramString2) < 0);
    return false;
    return false;
  }
  
  public static boolean evaluate(@NonNull Optimizely paramOptimizely, @NonNull OptimizelyExperiment paramOptimizelyExperiment)
  {
    return evaluateSubpart(paramOptimizely, paramOptimizelyExperiment.getConditions());
  }
  
  private static boolean evaluateCondition(@NonNull Optimizely paramOptimizely, @NonNull Map<String, Object> paramMap)
  {
    String str2 = (String)paramMap.get("key");
    String str3 = (String)paramMap.get("match");
    Context localContext = paramOptimizely.getCurrentContext();
    String str1;
    if ("custom_tag".equals(str2))
    {
      paramMap = paramMap.get("value");
      if ((paramMap instanceof Map))
      {
        paramMap = (Map)paramMap;
        str1 = (String)paramMap.get("value");
        str2 = paramOptimizely.getOptimizelyData().getCustomTag((String)paramMap.get("tag"));
        paramMap = str2;
        if (str2 == null) {
          return false;
        }
      }
      else
      {
        paramOptimizely.verboseLog(true, "OptimizelyTargetingManager", "Invalid custom_tag targeting condition value.", new Object[0]);
        return false;
      }
    }
    else
    {
      paramMap = paramMap.get("value");
      if (paramMap == null) {
        return false;
      }
      str1 = paramMap.toString();
      if (str2 == null)
      {
        paramOptimizely.verboseLog(true, "OptimizelyTargetingManager", "Invalid targeting key: null", new Object[0]);
        return false;
      }
      if (!str2.equals("system_version")) {
        break label201;
      }
      paramMap = OptimizelyUtils.androidVersion();
    }
    label201:
    boolean bool;
    while ((paramMap == null) || (str1 == null))
    {
      paramOptimizely.verboseLog(true, "OptimizelyTargetingManager", "Invalid targeting comparison value %1$s", new Object[] { str1 });
      return false;
      if (str2.equals("device_model"))
      {
        paramMap = OptimizelyUtils.deviceModel();
      }
      else if (str2.equals("language"))
      {
        paramMap = OptimizelyUtils.getLanguage();
      }
      else if (str2.equals("locale"))
      {
        paramMap = OptimizelyUtils.getLocale();
      }
      else if (str2.equals("app_version"))
      {
        paramMap = OptimizelyUtils.applicationVersion(paramOptimizely);
      }
      else if (str2.equals("sdk_version"))
      {
        paramMap = Build.sdkVersion();
      }
      else if (str2.equals("is_pad"))
      {
        paramMap = Boolean.toString(OptimizelyUtils.isTablet(localContext));
      }
      else
      {
        if (str2.equals("is_phone"))
        {
          if (!OptimizelyUtils.isTablet(localContext)) {}
          for (bool = true;; bool = false)
          {
            paramMap = Boolean.toString(bool);
            break;
          }
        }
        paramOptimizely.verboseLog(true, "OptimizelyTargetingManager", "Invalid targeting key: %1$s", new Object[] { str2 });
        return false;
      }
    }
    try
    {
      bool = doNumericComparison(Float.parseFloat(paramMap), Float.parseFloat(str1), str3);
      return bool;
    }
    catch (NumberFormatException paramOptimizely) {}
    return doStringComparison(paramMap, str1, str3);
  }
  
  private static boolean evaluateSubpart(@NonNull Optimizely paramOptimizely, @Nullable Object paramObject)
  {
    if (paramObject == null)
    {
      paramOptimizely.verboseLog(true, "OptimizelyTargetingManager", "Invalid null condition in targeting.", new Object[0]);
      return false;
    }
    if ((paramObject instanceof List))
    {
      List localList = (List)paramObject;
      if (((List)paramObject).size() < 2) {
        return true;
      }
      paramObject = (String)localList.get(0);
      if (((String)paramObject).equals("and")) {
        return evaluateSubpartAnd(paramOptimizely, localList);
      }
      if (((String)paramObject).equals("or")) {
        return evaluateSubpartOr(paramOptimizely, localList);
      }
      paramOptimizely.verboseLog(true, "OptimizelyTargetingManager", "Targeting condition lacks and/or condition.", new Object[0]);
      return false;
    }
    if ((paramObject instanceof Map)) {
      return evaluateCondition(paramOptimizely, (Map)paramObject);
    }
    paramOptimizely.verboseLog(true, "OptimizelyTargetingManager", "Invalid targeting subpart", new Object[0]);
    return false;
  }
  
  private static boolean evaluateSubpartAnd(@NonNull Optimizely paramOptimizely, @NonNull List<Object> paramList)
  {
    int i = 1;
    while (i < paramList.size())
    {
      if (!evaluateSubpart(paramOptimizely, paramList.get(i))) {
        return false;
      }
      i += 1;
    }
    return true;
  }
  
  private static boolean evaluateSubpartOr(@NonNull Optimizely paramOptimizely, @NonNull List<Object> paramList)
  {
    int i = 1;
    while (i < paramList.size())
    {
      if (evaluateSubpart(paramOptimizely, paramList.get(i))) {
        return true;
      }
      i += 1;
    }
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Core/OptimizelyTargetingManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */