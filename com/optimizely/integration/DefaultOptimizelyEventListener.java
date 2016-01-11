package com.optimizely.integration;

import android.os.Bundle;
import java.util.List;

public class DefaultOptimizelyEventListener
  implements OptimizelyEventListener
{
  public void onGoalTriggered(String paramString, List<OptimizelyExperimentData> paramList) {}
  
  public void onMessage(String paramString1, String paramString2, Bundle paramBundle) {}
  
  public void onOptimizelyDataFileLoaded() {}
  
  public void onOptimizelyEditorEnabled() {}
  
  public void onOptimizelyExperimentViewed(OptimizelyExperimentData paramOptimizelyExperimentData) {}
  
  public void onOptimizelyExperimentVisited(OptimizelyExperimentData paramOptimizelyExperimentData)
  {
    onOptimizelyExperimentViewed(paramOptimizelyExperimentData);
  }
  
  public void onOptimizelyFailedToStart(String paramString) {}
  
  public void onOptimizelyStarted() {}
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/integration/DefaultOptimizelyEventListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */