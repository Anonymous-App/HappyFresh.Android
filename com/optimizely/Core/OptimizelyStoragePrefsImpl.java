package com.optimizely.Core;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.annotation.NonNull;
import com.google.gson.Gson;
import com.optimizely.Optimizely;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class OptimizelyStoragePrefsImpl
  implements OptimizelyStorage
{
  private static final String kOptimizelyDefaultsKey = "OptimizelyUserData";
  SharedPreferences optimizelyPreferences;
  
  OptimizelyStoragePrefsImpl(@NonNull Optimizely paramOptimizely)
  {
    this.optimizelyPreferences = paramOptimizely.getCurrentContext().getSharedPreferences("OptimizelyUserData", 0);
  }
  
  private SharedPreferences getPreferences()
  {
    return this.optimizelyPreferences;
  }
  
  public boolean clearAll()
  {
    return this.optimizelyPreferences.edit().clear().commit();
  }
  
  public boolean contains(String paramString)
  {
    if (paramString == null) {
      throw new NullPointerException("Null keys are not permitted");
    }
    return this.optimizelyPreferences.contains(paramString);
  }
  
  public Map<String, ?> getAll()
  {
    return Collections.unmodifiableMap(getPreferences().getAll());
  }
  
  public boolean getBoolean(String paramString)
  {
    if (paramString == null) {
      throw new NullPointerException("Null keys are not permitted");
    }
    return getBoolean(paramString, false);
  }
  
  public boolean getBoolean(String paramString, boolean paramBoolean)
  {
    if (paramString == null) {
      throw new NullPointerException("Null keys are not permitted");
    }
    return this.optimizelyPreferences.getBoolean(paramString, paramBoolean);
  }
  
  public float getFloat(String paramString)
  {
    if (paramString == null) {
      throw new NullPointerException("Null keys are not permitted");
    }
    return getFloat(paramString, -1.0F);
  }
  
  public float getFloat(String paramString, float paramFloat)
  {
    if (paramString == null) {
      throw new NullPointerException("Null keys are not permitted");
    }
    return this.optimizelyPreferences.getFloat(paramString, paramFloat);
  }
  
  public int getInt(String paramString)
  {
    if (paramString == null) {
      throw new NullPointerException("Null keys are not permitted");
    }
    return getInt(paramString, -1);
  }
  
  public int getInt(String paramString, int paramInt)
  {
    if (paramString == null) {
      throw new NullPointerException("Null keys are not permitted");
    }
    return this.optimizelyPreferences.getInt(paramString, paramInt);
  }
  
  public long getLong(String paramString)
  {
    if (paramString == null) {
      throw new NullPointerException("Null keys are not permitted");
    }
    return getLong(paramString, -1L);
  }
  
  public long getLong(String paramString, long paramLong)
  {
    if (paramString == null) {
      throw new NullPointerException("Null keys are not permitted");
    }
    return this.optimizelyPreferences.getLong(paramString, paramLong);
  }
  
  public Map<String, String> getMap(String paramString)
  {
    if (paramString == null) {
      throw new NullPointerException("Null keys are not permitted");
    }
    paramString = this.optimizelyPreferences.getString(paramString, null);
    return (Map)new Gson().fromJson(paramString, new HashMap().getClass());
  }
  
  public String getString(String paramString)
  {
    if (paramString == null) {
      throw new NullPointerException("Null keys are not permitted");
    }
    return getString(paramString, null);
  }
  
  public String getString(String paramString1, String paramString2)
  {
    if (paramString1 == null) {
      throw new NullPointerException("Null keys are not permitted");
    }
    return this.optimizelyPreferences.getString(paramString1, paramString2);
  }
  
  public boolean putBoolean(String paramString, boolean paramBoolean)
  {
    if (paramString == null) {
      throw new NullPointerException("Null keys are not permitted");
    }
    return this.optimizelyPreferences.edit().putBoolean(paramString, paramBoolean).commit();
  }
  
  public boolean putFloat(String paramString, float paramFloat)
  {
    if (paramString == null) {
      throw new NullPointerException("Null keys are not permitted");
    }
    return this.optimizelyPreferences.edit().putFloat(paramString, paramFloat).commit();
  }
  
  public boolean putInt(String paramString, int paramInt)
  {
    if (paramString == null) {
      throw new NullPointerException("Null keys are not permitted");
    }
    return this.optimizelyPreferences.edit().putInt(paramString, paramInt).commit();
  }
  
  public boolean putLong(String paramString, long paramLong)
  {
    if (paramString == null) {
      throw new NullPointerException("Null keys are not permitted");
    }
    return this.optimizelyPreferences.edit().putLong(paramString, paramLong).commit();
  }
  
  public boolean putMap(String paramString, Map<String, String> paramMap)
  {
    if (paramString == null) {
      throw new NullPointerException("Null keys are not permitted");
    }
    paramMap = new Gson().toJson(paramMap);
    return this.optimizelyPreferences.edit().putString(paramString, paramMap).commit();
  }
  
  public boolean putString(String paramString1, String paramString2)
  {
    if (paramString1 == null) {
      throw new NullPointerException("Null keys are not permitted");
    }
    return this.optimizelyPreferences.edit().putString(paramString1, paramString2).commit();
  }
  
  public boolean remove(String paramString)
  {
    if (paramString == null) {
      throw new NullPointerException("Null keys are not permitted");
    }
    return this.optimizelyPreferences.edit().remove(paramString).commit();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Core/OptimizelyStoragePrefsImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */