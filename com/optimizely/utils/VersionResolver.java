package com.optimizely.utils;

import android.support.annotation.NonNull;
import com.optimizely.Build;
import com.optimizely.Core.OptimizelyUtils;
import com.optimizely.Optimizely;

public class VersionResolver
{
  private final Optimizely optimizely;
  
  public VersionResolver(Optimizely paramOptimizely)
  {
    this.optimizely = paramOptimizely;
  }
  
  @NonNull
  public String getAppVersion()
  {
    return OptimizelyUtils.applicationVersion(this.optimizely);
  }
  
  @NonNull
  public String getSdkVersion()
  {
    return Build.sdkVersion();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/utils/VersionResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */