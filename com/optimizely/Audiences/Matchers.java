package com.optimizely.Audiences;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Matchers
{
  public static final String MATCH_TYPE_EQ = "equals";
  public static final String MATCH_TYPE_EXACT = "exact";
  public static final String MATCH_TYPE_EXISTS = "exists";
  public static final String MATCH_TYPE_GT = "greater";
  public static final String MATCH_TYPE_GTE = "greater_equals";
  public static final String MATCH_TYPE_LT = "less";
  public static final String MATCH_TYPE_LTE = "less_equals";
  public static final String MATCH_TYPE_REGEX = "regex";
  public static final String MATCH_TYPE_SUBSTRING = "substring";
  
  private static int compare(@NonNull String paramString1, @NonNull String paramString2)
  {
    paramString1 = paramString1.split("\\.");
    paramString2 = paramString2.split("\\.");
    int i = 0;
    while ((i < paramString1.length) && (i < paramString2.length) && (paramString1[i].equals(paramString2[i]))) {
      i += 1;
    }
    if ((i < paramString1.length) && (i < paramString2.length)) {
      return Integer.signum(Integer.valueOf(paramString1[i]).compareTo(Integer.valueOf(paramString2[i])));
    }
    return Integer.signum(paramString1.length - paramString2.length);
  }
  
  public static boolean matchNumeric(@Nullable String paramString, @Nullable double paramDouble1, @Nullable double paramDouble2)
  {
    String str = paramString;
    if (paramString == null) {
      str = "equals";
    }
    if ("equals".equals(str)) {
      if (paramDouble1 != paramDouble2) {}
    }
    do
    {
      do
      {
        do
        {
          do
          {
            return true;
            return false;
            if (!"greater".equals(str)) {
              break;
            }
          } while (paramDouble2 > paramDouble1);
          return false;
          if (!"greater_equals".equalsIgnoreCase(str)) {
            break;
          }
        } while (paramDouble2 >= paramDouble1);
        return false;
        if (!"less".equalsIgnoreCase(str)) {
          break;
        }
      } while (paramDouble2 < paramDouble1);
      return false;
    } while ((!"less_equals".equalsIgnoreCase(str)) || (paramDouble2 <= paramDouble1));
    return false;
  }
  
  public static boolean matchString(@Nullable String paramString1, @Nullable String paramString2, @Nullable String paramString3)
  {
    boolean bool2 = true;
    boolean bool1;
    if ((paramString2 == null) || (paramString3 == null)) {
      bool1 = false;
    }
    int i;
    do
    {
      String str;
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
                return bool1;
                str = paramString1;
                if (paramString1 == null) {
                  str = "equals";
                }
                i = paramString2.compareToIgnoreCase(paramString3);
                if ((!"equals".equals(str)) && (!"exact".equals(str))) {
                  break;
                }
                bool1 = bool2;
              } while (i == 0);
              return false;
              if (!"greater".equals(str)) {
                break;
              }
              bool1 = bool2;
            } while (i > 0);
            return false;
            if (!"greater_equals".equalsIgnoreCase(str)) {
              break;
            }
            bool1 = bool2;
          } while (i >= 0);
          return false;
          if (!"less".equalsIgnoreCase(str)) {
            break;
          }
          bool1 = bool2;
        } while (i < 0);
        return false;
        bool1 = bool2;
      } while (!"less_equals".equalsIgnoreCase(str));
      bool1 = bool2;
    } while (i <= 0);
    return false;
  }
  
  public static boolean matchStringNumeric(@Nullable String paramString1, @Nullable String paramString2, @Nullable String paramString3)
  {
    boolean bool2 = true;
    boolean bool1;
    if ((paramString2 == null) || (paramString3 == null)) {
      bool1 = false;
    }
    int i;
    do
    {
      String str;
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
                return bool1;
                str = paramString1;
                if (paramString1 == null) {
                  str = "equals";
                }
                i = compare(paramString2, paramString3);
                if ((!"equals".equalsIgnoreCase(str)) && (!"exact".equals(str))) {
                  break;
                }
                bool1 = bool2;
              } while (i == 0);
              return false;
              if (!"greater".equalsIgnoreCase(str)) {
                break;
              }
              bool1 = bool2;
            } while (i > 0);
            return false;
            if (!"greater_equals".equalsIgnoreCase(str)) {
              break;
            }
            bool1 = bool2;
          } while (i >= 0);
          return false;
          if (!"less".equalsIgnoreCase(str)) {
            break;
          }
          bool1 = bool2;
        } while (i < 0);
        return false;
        bool1 = bool2;
      } while (!"less_equals".equalsIgnoreCase(str));
      bool1 = bool2;
    } while (i <= 0);
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Audiences/Matchers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */