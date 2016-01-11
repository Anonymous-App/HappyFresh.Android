package com.optimizely.Core;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.optimizely.Optimizely;

public class OptimizelyStorageFactory
{
  @Nullable
  static OptimizelyStoragePrefsImpl prefsInstance;
  
  public static OptimizelyStorage getSharedPreferenceInstance(@NonNull Optimizely paramOptimizely)
  {
    if (prefsInstance != null) {
      return prefsInstance;
    }
    prefsInstance = new OptimizelyStoragePrefsImpl(paramOptimizely);
    return prefsInstance;
  }
  
  public static OptimizelyStorage getTestInstance(@NonNull Optimizely paramOptimizely)
  {
    if (prefsInstance != null) {
      return prefsInstance;
    }
    prefsInstance = new OptimizelyStoragePrefsImpl(paramOptimizely);
    return prefsInstance;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Core/OptimizelyStorageFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */