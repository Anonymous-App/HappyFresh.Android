package com.optimizely.integration;

import android.os.Bundle;
import java.util.List;

public abstract interface OptimizelyEventListener
{
  public abstract void onGoalTriggered(String paramString, List<OptimizelyExperimentData> paramList);
  
  public abstract void onMessage(String paramString1, String paramString2, Bundle paramBundle);
  
  public abstract void onOptimizelyDataFileLoaded();
  
  public abstract void onOptimizelyEditorEnabled();
  
  public abstract void onOptimizelyExperimentViewed(OptimizelyExperimentData paramOptimizelyExperimentData);
  
  public abstract void onOptimizelyExperimentVisited(OptimizelyExperimentData paramOptimizelyExperimentData);
  
  public abstract void onOptimizelyFailedToStart(String paramString);
  
  public abstract void onOptimizelyStarted();
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/integration/OptimizelyEventListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */