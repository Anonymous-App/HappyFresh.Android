package com.optimizely.Core;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.annotation.NonNull;
import com.optimizely.Assets.OptimizelyAssets;
import com.optimizely.Audiences.AudienceUtils;
import com.optimizely.Audiences.OptimizelyAudiencesManager;
import com.optimizely.JSON.OptimizelyCodeTest;
import com.optimizely.JSON.OptimizelyExperiment;
import com.optimizely.JSON.OptimizelyVariable;
import com.optimizely.JSON.OptimizelyVariation;
import com.optimizely.JSON.OptimizelyView;
import com.optimizely.LogAndEvent.OptimizelyTimeSeriesEventsManager;
import com.optimizely.LogAndEvent.OptimizelyTimeSeriesEventsManager.OptimizelyTimeSeriesMetric;
import com.optimizely.Optimizely;
import com.optimizely.Optimizely.OptimizelyRunningMode;
import com.optimizely.Preview.OptimizelyPreview;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class OptimizelyBucketing
{
  private static final String BUCKETING_COMPONENT = "OptimizelyBucketing";
  @NonNull
  final HashMap<String, String> forceBucketingSettings = new HashMap();
  @NonNull
  final HashSet<String> lockedCodeBlockKeys = new HashSet();
  @NonNull
  final HashMap<String, HashSet<String>> lockedOptimizelyIdToViewProperties = new HashMap();
  @NonNull
  final HashSet<String> lockedVariableKeys = new HashSet();
  @NonNull
  private final Optimizely optimizely;
  
  static
  {
    if (!OptimizelyBucketing.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public OptimizelyBucketing(@NonNull Optimizely paramOptimizely)
  {
    this.optimizely = paramOptimizely;
  }
  
  private void addNoOpsAndLockKeyPaths(@NonNull OptimizelyExperiment paramOptimizelyExperiment)
  {
    OptimizelyVariation localOptimizelyVariation = paramOptimizelyExperiment.getActiveVariation();
    if (localOptimizelyVariation == null)
    {
      this.optimizely.verboseLog(true, "OptimizelyBucketing", "Experiment %s has no active variation! Cannot process locking", new Object[] { paramOptimizelyExperiment.getExperimentId() });
      return;
    }
    HashSet localHashSet1 = new HashSet();
    List localList1 = localOptimizelyVariation.getVariables();
    if (localList1 != null)
    {
      localObject1 = localList1.iterator();
      while (((Iterator)localObject1).hasNext()) {
        localHashSet1.add(((OptimizelyVariable)((Iterator)localObject1).next()).getVariableKey());
      }
    }
    Object localObject1 = new HashMap();
    List localList2 = localOptimizelyVariation.getViews();
    if (localList2 != null)
    {
      localObject2 = localList2.iterator();
      while (((Iterator)localObject2).hasNext())
      {
        localObject3 = (OptimizelyView)((Iterator)localObject2).next();
        if (!((HashMap)localObject1).containsKey(((OptimizelyView)localObject3).getOptimizelyId())) {
          ((HashMap)localObject1).put(((OptimizelyView)localObject3).getOptimizelyId(), new HashSet());
        }
        ((HashSet)((HashMap)localObject1).get(((OptimizelyView)localObject3).getOptimizelyId())).add(((OptimizelyView)localObject3).getKey());
      }
    }
    Object localObject2 = new HashSet();
    Object localObject3 = localOptimizelyVariation.getCodeTests();
    Object localObject4;
    if (localObject3 != null)
    {
      localObject4 = ((List)localObject3).iterator();
      while (((Iterator)localObject4).hasNext()) {
        ((HashSet)localObject2).add(((OptimizelyCodeTest)((Iterator)localObject4).next()).getBlockName());
      }
    }
    paramOptimizelyExperiment = paramOptimizelyExperiment.getVariations();
    if (paramOptimizelyExperiment != null)
    {
      paramOptimizelyExperiment = paramOptimizelyExperiment.iterator();
      while (paramOptimizelyExperiment.hasNext())
      {
        localObject4 = (OptimizelyVariation)paramOptimizelyExperiment.next();
        if (localObject4 != localOptimizelyVariation)
        {
          Object localObject5 = ((OptimizelyVariation)localObject4).getVariables();
          Object localObject6;
          if (localObject5 != null)
          {
            localObject5 = ((List)localObject5).iterator();
            while (((Iterator)localObject5).hasNext())
            {
              localObject6 = (OptimizelyVariable)((Iterator)localObject5).next();
              if (!localHashSet1.contains(((OptimizelyVariable)localObject6).getVariableKey()))
              {
                ((OptimizelyVariable)localObject6).setValue(null);
                if (localList1 != null) {
                  localList1.add(localObject6);
                }
                localHashSet1.add(((OptimizelyVariable)localObject6).getVariableKey());
              }
            }
          }
          localObject5 = ((OptimizelyVariation)localObject4).getViews();
          if (localObject5 != null)
          {
            localObject5 = ((List)localObject5).iterator();
            while (((Iterator)localObject5).hasNext())
            {
              localObject6 = (OptimizelyView)((Iterator)localObject5).next();
              if (!((HashMap)localObject1).containsKey(((OptimizelyView)localObject6).getOptimizelyId())) {
                ((HashMap)localObject1).put(((OptimizelyView)localObject6).getOptimizelyId(), new HashSet());
              }
              HashSet localHashSet2 = (HashSet)((HashMap)localObject1).get(((OptimizelyView)localObject6).getOptimizelyId());
              assert (localHashSet2 != null);
              if (!localHashSet2.contains(((OptimizelyView)localObject6).getKey()))
              {
                ((OptimizelyView)localObject6).setValue(null);
                if (localList2 != null) {
                  localList2.add(localObject6);
                }
                localHashSet2.add(((OptimizelyView)localObject6).getKey());
              }
            }
          }
          localObject4 = ((OptimizelyVariation)localObject4).getCodeTests();
          if (localObject4 != null)
          {
            localObject4 = ((List)localObject4).iterator();
            while (((Iterator)localObject4).hasNext())
            {
              localObject5 = (OptimizelyCodeTest)((Iterator)localObject4).next();
              if (!((HashSet)localObject2).contains(((OptimizelyCodeTest)localObject5).getBlockName()))
              {
                ((OptimizelyCodeTest)localObject5).setBlockKey("");
                if (localObject3 != null) {
                  ((List)localObject3).add(localObject5);
                }
                ((HashSet)localObject2).add(((OptimizelyCodeTest)localObject5).getBlockName());
              }
            }
          }
        }
      }
    }
    this.lockedVariableKeys.addAll(localHashSet1);
    this.lockedCodeBlockKeys.addAll((Collection)localObject2);
    this.lockedOptimizelyIdToViewProperties.putAll((Map)localObject1);
  }
  
  private boolean assetsAreCached(@NonNull OptimizelyVariation paramOptimizelyVariation)
  {
    boolean bool2 = true;
    boolean bool1 = true;
    OptimizelyAssets localOptimizelyAssets = this.optimizely.getAssets();
    paramOptimizelyVariation = paramOptimizelyVariation.getViews();
    if (paramOptimizelyVariation != null)
    {
      paramOptimizelyVariation = paramOptimizelyVariation.iterator();
      for (;;)
      {
        bool2 = bool1;
        if (!paramOptimizelyVariation.hasNext()) {
          break;
        }
        OptimizelyView localOptimizelyView = (OptimizelyView)paramOptimizelyVariation.next();
        if (("image".equalsIgnoreCase(localOptimizelyView.getKey())) && (!localOptimizelyAssets.isAssetCached((Map)((Map)localOptimizelyView.getValue()).get("all")))) {
          bool1 = false;
        }
      }
    }
    return bool2;
  }
  
  private boolean forceBucketUserForExperiment(@NonNull OptimizelyExperiment paramOptimizelyExperiment)
  {
    Object localObject = paramOptimizelyExperiment.getVariations();
    if (paramOptimizelyExperiment.getExperimentId() == null) {
      this.optimizely.verboseLog(true, "OptimizelyBucketing", "Malformed experiment - missing ID. Refusing to bucket", new Object[0]);
    }
    do
    {
      return false;
      String str = (String)this.forceBucketingSettings.get(paramOptimizelyExperiment.getExperimentId());
      localObject = ((List)localObject).iterator();
      while (((Iterator)localObject).hasNext())
      {
        OptimizelyVariation localOptimizelyVariation = (OptimizelyVariation)((Iterator)localObject).next();
        if ((str != null) && (str.equals(localOptimizelyVariation.getVariationId()))) {
          paramOptimizelyExperiment.setActiveVariation(localOptimizelyVariation);
        }
      }
      if (areAssetsLocked(paramOptimizelyExperiment))
      {
        paramOptimizelyExperiment.setActiveVariation(null);
        return false;
      }
    } while (paramOptimizelyExperiment.getActiveVariation() == null);
    paramOptimizelyExperiment.setState("ExperimentStateRunning");
    addNoOpsAndLockKeyPaths(paramOptimizelyExperiment);
    return true;
  }
  
  @NonNull
  public static String getVisitedCountKey(@NonNull String paramString)
  {
    return paramString + "_count";
  }
  
  private boolean normallyBucketUserForExperiment(@NonNull OptimizelyExperiment paramOptimizelyExperiment)
  {
    Object localObject2 = Optimizely.getRunningMode();
    Object localObject1 = paramOptimizelyExperiment.getVariations();
    if (localObject1 == null)
    {
      this.optimizely.verboseLog(true, "OptimizelyBucketing", "Experiment %s has no variations. Cannot perform bucketing!", new Object[] { paramOptimizelyExperiment.getExperimentId() });
      return false;
    }
    Object localObject3;
    if (localObject2 == Optimizely.OptimizelyRunningMode.PREVIEW)
    {
      paramOptimizelyExperiment.setActive(Boolean.TRUE.booleanValue());
      paramOptimizelyExperiment.setState("ExperimentStateRunning");
      localObject2 = this.optimizely.getPreviewManager().getVariationForExperiment(paramOptimizelyExperiment.getExperimentId());
      localObject1 = ((List)localObject1).iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject3 = (OptimizelyVariation)((Iterator)localObject1).next();
        if ((localObject2 != null) && (((String)localObject2).equals(((OptimizelyVariation)localObject3).getVariationId()))) {
          paramOptimizelyExperiment.setActiveVariation((OptimizelyVariation)localObject3);
        }
      }
      if (paramOptimizelyExperiment.getActiveVariation() == null)
      {
        this.optimizely.verboseLog(true, "OptimizelyBucketing", "Cannot Launch Preview: Variation with ID %1$s not found for experiment %2$s", new Object[] { localObject2, paramOptimizelyExperiment.getExperimentId() });
        paramOptimizelyExperiment.setActive(false);
        return false;
      }
    }
    else if (localObject2 == Optimizely.OptimizelyRunningMode.NORMAL)
    {
      if (paramOptimizelyExperiment.getExperimentId() == null)
      {
        this.optimizely.verboseLog(true, "OptimizelyBucketing", "Malformed experiment - missing ID. Refusing to bucket", new Object[0]);
        return false;
      }
      localObject2 = this.optimizely.getUserDefaults(this.optimizely.getCurrentContext());
      localObject3 = ((SharedPreferences)localObject2).getString(paramOptimizelyExperiment.getExperimentId(), null);
      if (localObject3 != null)
      {
        Iterator localIterator = ((List)localObject1).iterator();
        while (localIterator.hasNext())
        {
          OptimizelyVariation localOptimizelyVariation = (OptimizelyVariation)localIterator.next();
          if (((String)localObject3).equals(localOptimizelyVariation.getVariationId())) {
            paramOptimizelyExperiment.setActiveVariation(localOptimizelyVariation);
          }
        }
        paramOptimizelyExperiment.setVisitedCount(((SharedPreferences)localObject2).getInt(getVisitedCountKey(paramOptimizelyExperiment.getExperimentId()), 0));
      }
      if (paramOptimizelyExperiment.getActiveVariation() == null)
      {
        int k = OptimizelyRandom.optimizelyRandom(paramOptimizelyExperiment.getExperimentId(), 1, Optimizely.getUserId(this.optimizely.getCurrentContext()));
        int i = 0;
        localObject1 = ((List)localObject1).iterator();
        while (((Iterator)localObject1).hasNext())
        {
          localObject3 = (OptimizelyVariation)((Iterator)localObject1).next();
          if (((OptimizelyVariation)localObject3).getTraffic() == null)
          {
            this.optimizely.verboseLog(true, "OptimizelyBucketing", "Warning: expected number for variation traffic allocation. Got null", new Object[0]);
          }
          else
          {
            int j = (int)(i + ((OptimizelyVariation)localObject3).getTraffic().doubleValue());
            i = j;
            if (j > k) {
              paramOptimizelyExperiment.setActiveVariation((OptimizelyVariation)localObject3);
            }
          }
        }
      }
      if (areAssetsLocked(paramOptimizelyExperiment))
      {
        paramOptimizelyExperiment.setActiveVariation(null);
        return false;
      }
      if (paramOptimizelyExperiment.getActiveVariation() == null) {
        return false;
      }
      paramOptimizelyExperiment.setState("ExperimentStateRunning");
      addNoOpsAndLockKeyPaths(paramOptimizelyExperiment);
      localObject1 = ((SharedPreferences)localObject2).edit();
      ((SharedPreferences.Editor)localObject1).putString(paramOptimizelyExperiment.getExperimentId(), paramOptimizelyExperiment.getActiveVariation().getVariationId());
      ((SharedPreferences.Editor)localObject1).apply();
    }
    this.optimizely.verboseLog("OptimizelyBucketing", "Bucketed user in variation: %s for experiment: %s", new Object[] { paramOptimizelyExperiment.getActiveVariation().getDescription(), paramOptimizelyExperiment.getDescription() });
    return true;
  }
  
  void addExperimentIdAndVariationId(String paramString1, String paramString2)
  {
    this.forceBucketingSettings.put(paramString1, paramString2);
  }
  
  public boolean areAssetsLocked(@NonNull OptimizelyExperiment paramOptimizelyExperiment)
  {
    if (paramOptimizelyExperiment.getVariations() == null) {
      return true;
    }
    Iterator localIterator1 = paramOptimizelyExperiment.getVariations().iterator();
    while (localIterator1.hasNext())
    {
      OptimizelyVariation localOptimizelyVariation = (OptimizelyVariation)localIterator1.next();
      Iterator localIterator2;
      Object localObject;
      if (localOptimizelyVariation.getVariables() != null)
      {
        localIterator2 = localOptimizelyVariation.getVariables().iterator();
        while (localIterator2.hasNext())
        {
          localObject = (OptimizelyVariable)localIterator2.next();
          if (this.lockedVariableKeys.contains(((OptimizelyVariable)localObject).getVariableKey()))
          {
            this.optimizely.getOptimizelyTimeSeriesEventManager().trackEventWithMetric(OptimizelyTimeSeriesEventsManager.OptimizelyTimeSeriesMetric.LOCK_CONFLICT);
            this.optimizely.verboseLog("OptimizelyBucketing", "Can't activate experiment %1$s:%2$s variable %3$s locked.", new Object[] { paramOptimizelyExperiment.getDescription(), paramOptimizelyExperiment.getExperimentId(), ((OptimizelyVariable)localObject).getVariableKey() });
            paramOptimizelyExperiment.setLocked(true);
            return true;
          }
        }
      }
      if (localOptimizelyVariation.getCodeTests() != null)
      {
        localIterator2 = localOptimizelyVariation.getCodeTests().iterator();
        while (localIterator2.hasNext())
        {
          localObject = (OptimizelyCodeTest)localIterator2.next();
          if (this.lockedCodeBlockKeys.contains(((OptimizelyCodeTest)localObject).getBlockKey()))
          {
            this.optimizely.getOptimizelyTimeSeriesEventManager().trackEventWithMetric(OptimizelyTimeSeriesEventsManager.OptimizelyTimeSeriesMetric.LOCK_CONFLICT);
            this.optimizely.verboseLog("OptimizelyBucketing", "Can't activate experiment %1$s:%2$s code test %3$s locked.", new Object[] { paramOptimizelyExperiment.getDescription(), paramOptimizelyExperiment.getExperimentId(), ((OptimizelyCodeTest)localObject).getBlockKey() });
            paramOptimizelyExperiment.setLocked(true);
            return true;
          }
        }
      }
      if (localOptimizelyVariation.getViews() != null)
      {
        localIterator2 = localOptimizelyVariation.getViews().iterator();
        while (localIterator2.hasNext())
        {
          localObject = (OptimizelyView)localIterator2.next();
          HashSet localHashSet = (HashSet)this.lockedOptimizelyIdToViewProperties.get(((OptimizelyView)localObject).getOptimizelyId());
          if ((localHashSet != null) && (localHashSet.contains(((OptimizelyView)localObject).getKey())))
          {
            this.optimizely.getOptimizelyTimeSeriesEventManager().trackEventWithMetric(OptimizelyTimeSeriesEventsManager.OptimizelyTimeSeriesMetric.LOCK_CONFLICT);
            this.optimizely.verboseLog("OptimizelyBucketing", "Can't activate experiment %1$s:%2$s view %3$s locked.", new Object[] { paramOptimizelyExperiment.getDescription(), paramOptimizelyExperiment.getExperimentId(), ((OptimizelyView)localObject).getKey() });
            paramOptimizelyExperiment.setLocked(true);
            return true;
          }
        }
      }
      if (!assetsAreCached(localOptimizelyVariation))
      {
        paramOptimizelyExperiment.setLocked(true);
        return true;
      }
    }
    return false;
  }
  
  public boolean bucketUserForExperiment(@NonNull OptimizelyExperiment paramOptimizelyExperiment)
  {
    if (shouldForceBucketExperiment(paramOptimizelyExperiment)) {
      return forceBucketUserForExperiment(paramOptimizelyExperiment);
    }
    return normallyBucketUserForExperiment(paramOptimizelyExperiment);
  }
  
  public boolean canPassTargeting(@NonNull OptimizelyExperiment paramOptimizelyExperiment)
  {
    if (Optimizely.getRunningMode().equals(Optimizely.OptimizelyRunningMode.PREVIEW)) {
      return this.optimizely.getPreviewManager().canActivateExperiment(paramOptimizelyExperiment);
    }
    boolean bool2 = paramOptimizelyExperiment.isActive();
    if (AudienceUtils.isAudiencesEnabled(paramOptimizelyExperiment)) {}
    for (boolean bool1 = this.optimizely.getAudiencesManager().evaluate(paramOptimizelyExperiment);; bool1 = OptimizelyTargetingManager.evaluate(this.optimizely, paramOptimizelyExperiment))
    {
      paramOptimizelyExperiment.setPassesTargeting(bool1);
      if ((bool2) && (bool1)) {
        break;
      }
      return false;
    }
    return true;
  }
  
  public void clearAllLocks()
  {
    this.lockedCodeBlockKeys.clear();
    this.lockedVariableKeys.clear();
    this.lockedOptimizelyIdToViewProperties.clear();
  }
  
  void clearForceBucketingSettings()
  {
    this.forceBucketingSettings.clear();
  }
  
  boolean isImplementorAttemptingToForceBucket()
  {
    return (this.forceBucketingSettings.size() > 0) && (Optimizely.getRunningMode().equals(Optimizely.OptimizelyRunningMode.NORMAL));
  }
  
  public boolean isUserIncluded(@NonNull OptimizelyExperiment paramOptimizelyExperiment)
  {
    boolean bool = true;
    String str = paramOptimizelyExperiment.getExperimentId();
    if (str == null)
    {
      this.optimizely.verboseLog(true, "OptimizelyBucketing", "Experiment id is null. Returning false for isUserIncluded", new Object[0]);
      return false;
    }
    int i = OptimizelyRandom.optimizelyRandom(str, 0, Optimizely.getUserId(this.optimizely.getCurrentContext()));
    if ((paramOptimizelyExperiment.getPercentageIncluded() != null) && (i <= paramOptimizelyExperiment.getPercentageIncluded().doubleValue())) {}
    for (;;)
    {
      return bool;
      bool = false;
    }
  }
  
  public boolean shouldForceBucketExperiment(OptimizelyExperiment paramOptimizelyExperiment)
  {
    if (isImplementorAttemptingToForceBucket())
    {
      String str = (String)this.forceBucketingSettings.get(paramOptimizelyExperiment.getExperimentId());
      boolean bool2 = false;
      paramOptimizelyExperiment = paramOptimizelyExperiment.getVariations().iterator();
      OptimizelyVariation localOptimizelyVariation;
      do
      {
        bool1 = bool2;
        if (!paramOptimizelyExperiment.hasNext()) {
          break;
        }
        localOptimizelyVariation = (OptimizelyVariation)paramOptimizelyExperiment.next();
      } while ((str == null) || (!str.equals(localOptimizelyVariation.getVariationId())));
      boolean bool1 = true;
      return bool1;
    }
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Core/OptimizelyBucketing.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */