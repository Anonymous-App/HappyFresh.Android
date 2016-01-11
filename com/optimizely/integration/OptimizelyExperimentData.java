package com.optimizely.integration;

import android.support.annotation.NonNull;
import java.util.List;

public class OptimizelyExperimentData
{
  public List<String> audiences;
  public Boolean enabled;
  public String experimentId;
  public String experimentName;
  public boolean isManual;
  public boolean locked;
  public String state;
  public String targetingConditions;
  public boolean targetingMet;
  public String variationId;
  public String variationName;
  public List<OptimizelyVariationData> variations;
  public int visitedCount;
  public boolean visitedEver;
  public boolean visitedThisSession;
  
  @NonNull
  public String toString()
  {
    StringBuilder localStringBuilder1 = new StringBuilder();
    localStringBuilder1.append("Experiment Data for ").append(this.experimentName).append("\n");
    StringBuilder localStringBuilder2 = localStringBuilder1.append("Currently ");
    if ((this.enabled != null) && (this.enabled.booleanValue()))
    {
      str = "enabled";
      localStringBuilder2.append(str).append("\n");
      localStringBuilder2 = localStringBuilder1.append("In state ");
      if (this.state == null) {
        break label164;
      }
      str = this.state;
      label84:
      localStringBuilder2.append(str);
      localStringBuilder2 = localStringBuilder1.append("With variation ");
      if (this.variationName != null) {
        break label170;
      }
      str = "<none>";
      label107:
      localStringBuilder2.append(str).append("\n");
      localStringBuilder2 = localStringBuilder1.append("Has been visited ").append(this.visitedCount).append(" times ");
      if (!this.visitedThisSession) {
        break label178;
      }
    }
    label164:
    label170:
    label178:
    for (String str = " including once this session";; str = " and not during this session\n")
    {
      localStringBuilder2.append(str);
      return localStringBuilder1.toString();
      str = "disabled";
      break;
      str = "null";
      break label84;
      str = this.variationName;
      break label107;
    }
  }
  
  public static class OptimizelyExperimentDataState
  {
    public static final String OPTIMIZELY_EXPERIMENT_DATA_STATE_DEACTIVATED = "optimizelyExperimentDataStateDeactivated";
    public static final String OPTIMIZELY_EXPERIMENT_DATA_STATE_DISABLED = "optimizelyExperimentDataStateDisabled";
    public static final String OPTIMIZELY_EXPERIMENT_DATA_STATE_RUNNING = "optimizelyExperimentDataStateRunning";
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/integration/OptimizelyExperimentData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */