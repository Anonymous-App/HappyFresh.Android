package com.optimizely.Core;

import java.util.Map;

public abstract interface OptimizelyStorage
{
  public abstract boolean clearAll();
  
  public abstract boolean contains(String paramString);
  
  public abstract Map<String, ?> getAll();
  
  public abstract boolean getBoolean(String paramString);
  
  public abstract boolean getBoolean(String paramString, boolean paramBoolean);
  
  public abstract float getFloat(String paramString);
  
  public abstract float getFloat(String paramString, float paramFloat);
  
  public abstract int getInt(String paramString);
  
  public abstract int getInt(String paramString, int paramInt);
  
  public abstract long getLong(String paramString);
  
  public abstract long getLong(String paramString, long paramLong);
  
  public abstract Map<String, String> getMap(String paramString);
  
  public abstract String getString(String paramString);
  
  public abstract String getString(String paramString1, String paramString2);
  
  public abstract boolean putBoolean(String paramString, boolean paramBoolean);
  
  public abstract boolean putFloat(String paramString, float paramFloat);
  
  public abstract boolean putInt(String paramString, int paramInt);
  
  public abstract boolean putLong(String paramString, long paramLong);
  
  public abstract boolean putMap(String paramString, Map<String, String> paramMap);
  
  public abstract boolean putString(String paramString1, String paramString2);
  
  public abstract boolean remove(String paramString);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Core/OptimizelyStorage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */