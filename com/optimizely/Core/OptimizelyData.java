package com.optimizely.Core;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.optimizely.Assets.OptimizelyAssets;
import com.optimizely.JSON.OptimizelyConfig;
import com.optimizely.JSON.OptimizelyExperiment;
import com.optimizely.JSON.OptimizelyGoal;
import com.optimizely.JSON.OptimizelyJSON;
import com.optimizely.JSON.OptimizelyPluginConfig;
import com.optimizely.JSON.OptimizelySegment;
import com.optimizely.JSON.OptimizelyVariation;
import com.optimizely.JSON.OptimizelyView;
import com.optimizely.LogAndEvent.OptimizelyEventsManager;
import com.optimizely.LogAndEvent.OptimizelyTimeSeriesEventsManager;
import com.optimizely.LogAndEvent.OptimizelyTimeSeriesEventsManager.OptimizelyTimeSeriesMetric;
import com.optimizely.Network.OptimizelyDownloader;
import com.optimizely.Network.OptimizelyNetworkResult;
import com.optimizely.Optimizely;
import com.optimizely.View.GoalHandler.Goal;
import com.optimizely.View.ViewUtils;
import com.optimizely.View.idmanager.OptimizelyIdManager;
import com.optimizely.integration.IntegrationEventsDispatcher;
import com.optimizely.utils.VersionResolver;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OptimizelyData
{
  private static final String DATA_FILE = "Optimizely_Config";
  static final String OPTIMIZELY_DATA_COMPONENT = "OptimizelyData";
  private String accountId;
  @NonNull
  OptimizelyBucketing bucketing;
  @NonNull
  private final Map<String, String> customTags;
  @Nullable
  private OptimizelyJSON data;
  private OptimizelyDownloader downloader;
  @NonNull
  private final Map<String, Set<String>> experimentIdsByGoalId;
  @NonNull
  private final Map<String, OptimizelyExperiment> experimentsById;
  @Nullable
  private TypeAdapter<OptimizelyJSON> gsonAdapter;
  @NonNull
  private final Optimizely optimizely;
  @NonNull
  private final Map<String, OptimizelyExperiment> pendingExperimentsById = new ConcurrentHashMap();
  @NonNull
  private final Map<String, OptimizelyExperiment> runningExperimentsById = new ConcurrentHashMap();
  private boolean shouldLoadData;
  
  static
  {
    if (!OptimizelyData.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public OptimizelyData(@NonNull Optimizely paramOptimizely, @NonNull OptimizelyBucketing paramOptimizelyBucketing)
  {
    this.optimizely = paramOptimizely;
    this.bucketing = paramOptimizelyBucketing;
    this.customTags = new ConcurrentHashMap();
    this.experimentsById = new ConcurrentHashMap();
    this.experimentIdsByGoalId = new HashMap();
    setShouldLoadData(true);
  }
  
  private TypeAdapter<OptimizelyJSON> getGsonAdapter()
  {
    Gson localGson;
    if (this.gsonAdapter == null) {
      localGson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    }
    try
    {
      this.gsonAdapter = localGson.getAdapter(OptimizelyJSON.class);
      return this.gsonAdapter;
    }
    catch (IncompatibleClassChangeError localIncompatibleClassChangeError)
    {
      for (;;)
      {
        OptimizelyUtils.disableOptimizely(this.optimizely, new VersionResolver(this.optimizely));
        this.optimizely.stopOptimizely();
      }
    }
  }
  
  private void loadExperimentsAndGoals(@NonNull OptimizelyJSON paramOptimizelyJSON)
  {
    this.data = paramOptimizelyJSON;
    this.accountId = paramOptimizelyJSON.getAccountId();
    this.experimentsById.clear();
    this.experimentIdsByGoalId.clear();
    if (paramOptimizelyJSON.getExperiments() == null)
    {
      this.optimizely.verboseLog(true, "OptimizelyData", "Malformed data file - Missing experiments", new Object[0]);
      return;
    }
    Iterator localIterator = paramOptimizelyJSON.getExperiments().iterator();
    while (localIterator.hasNext())
    {
      OptimizelyExperiment localOptimizelyExperiment = (OptimizelyExperiment)localIterator.next();
      if (localOptimizelyExperiment != null)
      {
        this.experimentsById.put(localOptimizelyExperiment.getExperimentId(), localOptimizelyExperiment);
        if (!localOptimizelyExperiment.isManual()) {
          setupExperiment(localOptimizelyExperiment);
        }
        for (;;)
        {
          prepareExperiment(localOptimizelyExperiment);
          break;
          localOptimizelyExperiment.setState("ExperimentStatePending");
        }
      }
    }
    refreshRunningExperiments();
    loadGoals(paramOptimizelyJSON.getGoals());
  }
  
  public static void markExperimentAsViewedIfNecessary(@NonNull OptimizelyExperiment paramOptimizelyExperiment, @NonNull Optimizely paramOptimizely)
  {
    if (paramOptimizelyExperiment == null) {}
    while (!paramOptimizelyExperiment.getState().equals("ExperimentStateRunning")) {
      return;
    }
    paramOptimizelyExperiment.setState("ExperimentStateRunningAndViewed");
    paramOptimizely.getOptimizelyTimeSeriesEventManager().trackEventWithMetric(OptimizelyTimeSeriesEventsManager.OptimizelyTimeSeriesMetric.VISITOR_EVENT);
    paramOptimizely.getOptimizelyEventsManager().storeVisitorEventForExperiment(paramOptimizelyExperiment);
    Object localObject = paramOptimizely.getOptimizelyData().getBucketing();
    if ((localObject != null) && (!((OptimizelyBucketing)localObject).shouldForceBucketExperiment(paramOptimizelyExperiment)))
    {
      paramOptimizelyExperiment.setVisitedCount(paramOptimizelyExperiment.getVisitedCount() + 1);
      localObject = paramOptimizelyExperiment.getExperimentId();
      assert (localObject != null);
      localObject = OptimizelyBucketing.getVisitedCountKey((String)localObject);
      paramOptimizely.getUserDefaults(paramOptimizely.getCurrentContext()).edit().putInt((String)localObject, paramOptimizelyExperiment.getVisitedCount()).apply();
    }
    paramOptimizely.getIntegrationEventsDispatcher().sendExperimentVisitedEvent(paramOptimizely.getOptimizelyData(), paramOptimizelyExperiment);
    paramOptimizely.trackGoal("OptimizelyData", "Marked experiment: %s as viewed", new Object[] { paramOptimizelyExperiment.getDescription() });
  }
  
  public void addExperimentToPendingExperiments(OptimizelyExperiment paramOptimizelyExperiment)
  {
    if (paramOptimizelyExperiment.getState().equals("ExperimentStatePending")) {
      this.pendingExperimentsById.put(paramOptimizelyExperiment.getExperimentId(), paramOptimizelyExperiment);
    }
  }
  
  public void addExperimentToRunningExperiments(OptimizelyExperiment paramOptimizelyExperiment)
  {
    String str = paramOptimizelyExperiment.getState();
    if ((str.equals("ExperimentStateRunning")) || (str.equals("ExperimentStateRunningAndViewed"))) {
      this.runningExperimentsById.put(paramOptimizelyExperiment.getExperimentId(), paramOptimizelyExperiment);
    }
  }
  
  public boolean addLockedCodeBlock(String paramString)
  {
    return this.bucketing.lockedCodeBlockKeys.add(paramString);
  }
  
  public boolean addLockedVariable(String paramString)
  {
    return this.bucketing.lockedVariableKeys.add(paramString);
  }
  
  public boolean addLockedView(@NonNull OptimizelyView paramOptimizelyView)
  {
    if (!this.bucketing.lockedOptimizelyIdToViewProperties.containsKey(paramOptimizelyView.getOptimizelyId())) {}
    for (boolean bool = true; bool; bool = false)
    {
      HashSet localHashSet = new HashSet();
      localHashSet.add(paramOptimizelyView.getKey());
      this.bucketing.lockedOptimizelyIdToViewProperties.put(paramOptimizelyView.getOptimizelyId(), localHashSet);
      return bool;
    }
    ((HashSet)this.bucketing.lockedOptimizelyIdToViewProperties.get(paramOptimizelyView.getOptimizelyId())).add(paramOptimizelyView.getKey());
    return bool;
  }
  
  public void cancelAllPendingDownloads()
  {
    if (this.downloader != null) {
      this.downloader.cancelDownload();
    }
  }
  
  public void clearForceBucketingSettings()
  {
    this.bucketing.clearForceBucketingSettings();
  }
  
  public boolean containsLockedCodeBlock(String paramString)
  {
    return this.bucketing.lockedCodeBlockKeys.contains(paramString);
  }
  
  public boolean containsLockedVariable(String paramString)
  {
    return this.bucketing.lockedVariableKeys.contains(paramString);
  }
  
  public void fetchRemoteDataFile(@Nullable final OptimizelyNetworkResult<String> paramOptimizelyNetworkResult, boolean paramBoolean)
  {
    this.downloader = new OptimizelyDownloader(this.optimizely);
    this.downloader.downloadDataFile(new OptimizelyNetworkResult()
    {
      public void onDownloadError(int paramAnonymousInt)
      {
        if (paramOptimizelyNetworkResult != null) {
          paramOptimizelyNetworkResult.onDownloadError(paramAnonymousInt);
        }
      }
      
      public void onDownloadFinished(@NonNull String paramAnonymousString)
      {
        OptimizelyJSON localOptimizelyJSON = OptimizelyUtils.marshallDataFile(OptimizelyData.this.optimizely, OptimizelyData.this.getGsonAdapter(), paramAnonymousString);
        if ((localOptimizelyJSON != null) && (OptimizelyData.this.parseData(localOptimizelyJSON, false))) {
          OptimizelyData.this.writeDataFile(paramAnonymousString);
        }
        if (paramOptimizelyNetworkResult != null) {
          paramOptimizelyNetworkResult.onDownloadFinished(paramAnonymousString);
        }
      }
    }, this.optimizely.getNetworkTimeout(), paramBoolean);
  }
  
  public void forceVariationForExperiment(String paramString1, String paramString2)
  {
    this.bucketing.addExperimentIdAndVariationId(paramString2, paramString1);
  }
  
  @Nullable
  public String getAccountId()
  {
    return this.accountId;
  }
  
  public Object getAudiences()
  {
    if ((this.data == null) || (this.data.getAudiences() == null)) {
      return new HashMap();
    }
    return this.data.getAudiences();
  }
  
  public OptimizelyBucketing getBucketing()
  {
    return this.bucketing;
  }
  
  @Nullable
  public Double getCodeRevision()
  {
    if (this.data != null) {
      return this.data.getCodeRevision();
    }
    return null;
  }
  
  @Nullable
  public OptimizelyConfig getConfig()
  {
    if (this.data != null) {
      return this.data.getConfig();
    }
    return null;
  }
  
  @Nullable
  public String getCustomTag(@Nullable String paramString)
  {
    if (paramString == null) {
      return null;
    }
    return (String)this.customTags.get(paramString);
  }
  
  public Map<String, String> getCustomTags()
  {
    return this.customTags;
  }
  
  @NonNull
  public File getDataFile()
  {
    Context localContext = this.optimizely.getCurrentContext();
    if (localContext != null) {
      return new File(localContext.getFilesDir(), "Optimizely_Config");
    }
    return null;
  }
  
  @NonNull
  public JSONArray getExperimentLogInformation()
  {
    JSONArray localJSONArray = new JSONArray();
    Iterator localIterator = this.experimentsById.entrySet().iterator();
    while (localIterator.hasNext())
    {
      OptimizelyExperiment localOptimizelyExperiment = (OptimizelyExperiment)((Map.Entry)localIterator.next()).getValue();
      JSONObject localJSONObject = new JSONObject();
      if (localOptimizelyExperiment != null) {
        try
        {
          localJSONObject.put("id", localOptimizelyExperiment.getExperimentId());
          localJSONObject.put("last_modified", localOptimizelyExperiment.getLastModified());
          localJSONObject.put("state", localOptimizelyExperiment.getState());
          if (localOptimizelyExperiment.getActiveVariation() != null) {
            localJSONObject.put("active_variation_id", localOptimizelyExperiment.getActiveVariation().getVariationId());
          }
          localJSONArray.put(localJSONObject);
        }
        catch (JSONException localJSONException)
        {
          this.optimizely.verboseLog(true, "OptimizelyData", "Error converting experiment to log: %e", new Object[] { localJSONException.getLocalizedMessage() });
        }
      }
    }
    return localJSONArray;
  }
  
  @NonNull
  public Map<String, OptimizelyExperiment> getExperimentsById()
  {
    return this.experimentsById;
  }
  
  @NonNull
  public List<OptimizelyGoal> getGoals()
  {
    if ((this.data != null) && (this.data.getGoals() != null)) {
      return this.data.getGoals();
    }
    return Collections.emptyList();
  }
  
  public List<OptimizelyPluginConfig> getPluginConfigs()
  {
    if (this.data != null) {
      return this.data.getPlugins();
    }
    return Collections.emptyList();
  }
  
  @NonNull
  public Map<String, OptimizelyExperiment> getRunningExperimentsById()
  {
    return this.runningExperimentsById;
  }
  
  @NonNull
  public List<OptimizelySegment> getSegments()
  {
    if ((this.data != null) && (this.data.getSegments() != null)) {
      return this.data.getSegments();
    }
    return Collections.emptyList();
  }
  
  public boolean goalContainsExperiment(String paramString1, String paramString2)
  {
    return (this.experimentIdsByGoalId.containsKey(paramString1)) && (((Set)this.experimentIdsByGoalId.get(paramString1)).contains(paramString2));
  }
  
  public boolean hasRunnableExperiments()
  {
    return (!this.runningExperimentsById.isEmpty()) || (!this.pendingExperimentsById.isEmpty());
  }
  
  public boolean isImplementorAttemptingToForceBucket()
  {
    return this.bucketing.isImplementorAttemptingToForceBucket();
  }
  
  public void loadGoals(@Nullable List<OptimizelyGoal> paramList)
  {
    if (paramList == null) {}
    for (;;)
    {
      return;
      OptimizelyIdManager localOptimizelyIdManager = this.optimizely.getIdManager();
      paramList = paramList.iterator();
      while (paramList.hasNext())
      {
        Object localObject1 = (OptimizelyGoal)paramList.next();
        HashSet localHashSet = new HashSet();
        Object localObject2 = ((OptimizelyGoal)localObject1).getExperimentIds();
        if ((localObject2 != null) && (((OptimizelyGoal)localObject1).getElementIds() != null))
        {
          Object localObject3;
          Iterator localIterator;
          if (GoalHandler.Goal.MOBILE_TAP.value().equals(((OptimizelyGoal)localObject1).getType()))
          {
            localObject3 = new ArrayList(((OptimizelyGoal)localObject1).getElementIds().size());
            localIterator = ((OptimizelyGoal)localObject1).getElementIds().iterator();
            while (localIterator.hasNext()) {
              ((List)localObject3).add(localOptimizelyIdManager.stripPackageNameFromOptimizelyId((String)localIterator.next()));
            }
            ((OptimizelyGoal)localObject1).setElementIds((List)localObject3);
          }
          for (;;)
          {
            localObject2 = ((List)localObject2).iterator();
            while (((Iterator)localObject2).hasNext())
            {
              localObject3 = (String)((Iterator)localObject2).next();
              if (this.experimentsById.containsKey(localObject3)) {
                localHashSet.add(localObject3);
              }
            }
            if (GoalHandler.Goal.MOBILE_VIEW.value().equals(((OptimizelyGoal)localObject1).getType()))
            {
              localObject3 = new ArrayList(((OptimizelyGoal)localObject1).getElementIds().size());
              localIterator = ((OptimizelyGoal)localObject1).getElementIds().iterator();
              while (localIterator.hasNext()) {
                ((List)localObject3).add(ViewUtils.stripPackage((String)localIterator.next()));
              }
              ((OptimizelyGoal)localObject1).setElementIds((List)localObject3);
            }
          }
          localObject1 = ((OptimizelyGoal)localObject1).getId();
          if (localObject1 != null) {
            this.experimentIdsByGoalId.put(Long.toString(((Long)localObject1).longValue()), localHashSet);
          }
        }
      }
    }
  }
  
  public boolean loadLocalDataFile()
  {
    return loadLocalDataFile(false);
  }
  
  public boolean loadLocalDataFile(boolean paramBoolean)
  {
    Object localObject = OptimizelyUtils.readDataFile(getDataFile(), this.optimizely);
    if (localObject != null)
    {
      localObject = OptimizelyUtils.marshallDataFile(this.optimizely, getGsonAdapter(), (String)localObject);
      if (localObject != null) {
        return parseData((OptimizelyJSON)localObject, paramBoolean);
      }
    }
    return false;
  }
  
  boolean parseData(@NonNull OptimizelyJSON paramOptimizelyJSON, boolean paramBoolean)
  {
    if ((this.shouldLoadData) || (paramBoolean)) {
      loadExperimentsAndGoals(paramOptimizelyJSON);
    }
    this.optimizely.verboseLog(false, "OptimizelyData", "Parsed data file version %.0f", new Object[] { paramOptimizelyJSON.getCodeRevision() });
    this.optimizely.getIntegrationEventsDispatcher().sendDataFileLoadedEvent();
    return true;
  }
  
  public void prepareExperiment(@NonNull OptimizelyExperiment paramOptimizelyExperiment)
  {
    if (paramOptimizelyExperiment.getVariations() == null) {
      label7:
      return;
    } else {
      paramOptimizelyExperiment = paramOptimizelyExperiment.getVariations().iterator();
    }
    for (;;)
    {
      if (!paramOptimizelyExperiment.hasNext()) {
        break label7;
      }
      Object localObject = (OptimizelyVariation)paramOptimizelyExperiment.next();
      OptimizelyAssets localOptimizelyAssets = this.optimizely.getAssets();
      localObject = ((OptimizelyVariation)localObject).getViews();
      if (localObject == null) {
        break;
      }
      localObject = ((List)localObject).iterator();
      while (((Iterator)localObject).hasNext())
      {
        OptimizelyView localOptimizelyView = (OptimizelyView)((Iterator)localObject).next();
        if ("image".equalsIgnoreCase(localOptimizelyView.getKey())) {
          localOptimizelyAssets.prepareAsset((Map)localOptimizelyView.getValue());
        }
      }
    }
  }
  
  public void refreshRunningExperiments()
  {
    this.runningExperimentsById.clear();
    Iterator localIterator = this.experimentsById.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      addExperimentToRunningExperiments((OptimizelyExperiment)localEntry.getValue());
      addExperimentToPendingExperiments((OptimizelyExperiment)localEntry.getValue());
    }
  }
  
  public void reloadData()
  {
    if ((this.optimizely.isActive()) && (!this.optimizely.isEditorEnabled().booleanValue()))
    {
      this.bucketing.clearAllLocks();
      this.experimentsById.clear();
      loadLocalDataFile(true);
    }
  }
  
  public boolean removeDataFile()
  {
    return getDataFile().delete();
  }
  
  public void setCustomTag(@NonNull String paramString1, @Nullable String paramString2)
  {
    if (paramString2 != null)
    {
      this.customTags.put(paramString1, paramString2);
      return;
    }
    this.customTags.remove(paramString1);
  }
  
  void setExperimentsById(Map<String, OptimizelyExperiment> paramMap)
  {
    this.experimentsById.clear();
    this.experimentsById.putAll(paramMap);
  }
  
  public void setShouldLoadData(boolean paramBoolean)
  {
    this.shouldLoadData = paramBoolean;
  }
  
  public void setupExperiment(@NonNull OptimizelyExperiment paramOptimizelyExperiment)
  {
    if ((this.bucketing.canPassTargeting(paramOptimizelyExperiment)) && (this.bucketing.isUserIncluded(paramOptimizelyExperiment)))
    {
      if (!this.bucketing.bucketUserForExperiment(paramOptimizelyExperiment)) {
        paramOptimizelyExperiment.setActive(false);
      }
      OptimizelyVariation localOptimizelyVariation;
      do
      {
        return;
        localOptimizelyVariation = paramOptimizelyExperiment.getActiveVariation();
      } while (localOptimizelyVariation == null);
      this.optimizely.getOptimizelyTimeSeriesEventManager().trackEventWithMetric(OptimizelyTimeSeriesEventsManager.OptimizelyTimeSeriesMetric.EXPERIMENT_ACTIVATED);
      this.optimizely.verboseLog("OptimizelyData", "Activating experiment %1$s, variation = %2$s", new Object[] { paramOptimizelyExperiment.getDescription(), localOptimizelyVariation.getDescription() });
      return;
    }
    paramOptimizelyExperiment.setActive(false);
    paramOptimizelyExperiment.setState("ExperimentStateDeactivated");
  }
  
  public boolean writeDataFile(@NonNull String paramString)
  {
    File localFile = getDataFile();
    try
    {
      FileOutputStream localFileOutputStream = new FileOutputStream(localFile);
      localFileOutputStream.write(paramString.getBytes());
      localFileOutputStream.close();
      this.optimizely.verboseLog(false, "OptimizelyData", "Successfully saved data file to disk.", new Object[0]);
      return true;
    }
    catch (FileNotFoundException paramString)
    {
      this.optimizely.verboseLog(true, "OptimizelyData", "Writing data file to (%1$s) failed: %2$s", new Object[] { localFile.getPath(), paramString.getLocalizedMessage() });
      return false;
    }
    catch (IOException paramString)
    {
      this.optimizely.verboseLog(true, "OptimizelyData", "Writing data file to (%1$s) failed: %2$s", new Object[] { localFile.getPath(), paramString.getLocalizedMessage() });
    }
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/Core/OptimizelyData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */