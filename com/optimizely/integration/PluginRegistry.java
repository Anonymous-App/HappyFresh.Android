package com.optimizely.integration;

import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.optimizely.Core.OptimizelyData;
import com.optimizely.JSON.OptimizelyPluginConfig;
import com.optimizely.Optimizely;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class PluginRegistry
{
  @NonNull
  private static final Set<String> sTRUSTED_PLUGINS = Collections.unmodifiableSet(new HashSet());
  @NonNull
  private Set<String> mActivePlugins = new HashSet();
  @NonNull
  private Set<String> mDelayedStartPlugins = new HashSet();
  @NonNull
  private Set<String> mDisabledPlugins = new HashSet();
  @NonNull
  private Set<String> mEnabledPlugins = new HashSet();
  @NonNull
  private Map<String, Application.ActivityLifecycleCallbacks> mManagedLifecycleCallbacks = new HashMap();
  @NonNull
  private Optimizely mOptimizely;
  @NonNull
  private final Map<String, OptimizelyPluginConfig> mPluginConfigs = new HashMap();
  @NonNull
  protected Map<String, OptimizelyPlugin> mPlugins = new HashMap();
  
  public PluginRegistry(@NonNull Optimizely paramOptimizely)
  {
    this.mOptimizely = paramOptimizely;
  }
  
  @NonNull
  private static JSONObject convertJSON(@Nullable JsonObject paramJsonObject)
  {
    if (paramJsonObject == null) {
      return new JSONObject();
    }
    try
    {
      paramJsonObject = new JSONObject(paramJsonObject.toString());
      return paramJsonObject;
    }
    catch (JSONException paramJsonObject) {}
    return new JSONObject();
  }
  
  private void startPlugin(@NonNull OptimizelyPlugin paramOptimizelyPlugin)
  {
    String str = paramOptimizelyPlugin.getPluginId();
    try
    {
      Object localObject = new JSONObject();
      if (this.mPluginConfigs.containsKey(str)) {
        localObject = convertJSON(((OptimizelyPluginConfig)this.mPluginConfigs.get(str)).getConfig());
      }
      if (!paramOptimizelyPlugin.start(this.mOptimizely, (JSONObject)localObject))
      {
        this.mOptimizely.verboseLog(true, str, "Plugin failed to start.", new Object[0]);
        unregisterPlugin(paramOptimizelyPlugin);
      }
      this.mActivePlugins.add(str);
      localObject = paramOptimizelyPlugin.getActivityLifecycleCallbacks();
      if (localObject != null)
      {
        this.mOptimizely.getApplication().registerActivityLifecycleCallbacks((Application.ActivityLifecycleCallbacks)localObject);
        this.mManagedLifecycleCallbacks.put(paramOptimizelyPlugin.getPluginId(), localObject);
      }
      localObject = paramOptimizelyPlugin.getOptimizelyEventsListener();
      if (localObject != null) {
        Optimizely.addOptimizelyEventListener((OptimizelyEventListener)localObject);
      }
      this.mOptimizely.verboseLog(str, "Plugin Activated Successfully", new Object[0]);
      return;
    }
    catch (Throwable localThrowable)
    {
      this.mOptimizely.verboseLog(true, str, "Plugin failed to start with error %s", new Object[] { localThrowable.getLocalizedMessage() });
      unregisterPlugin(paramOptimizelyPlugin);
    }
  }
  
  protected boolean appHasPermissions(@NonNull OptimizelyPlugin paramOptimizelyPlugin)
  {
    Object localObject1 = this.mOptimizely.getCurrentContext();
    HashSet localHashSet = new HashSet();
    Object localObject2 = paramOptimizelyPlugin.getRequiredPermissions((Context)localObject1);
    if (localObject2 == null) {
      return true;
    }
    localHashSet.addAll((Collection)localObject2);
    try
    {
      localObject2 = localHashSet.iterator();
      while (((Iterator)localObject2).hasNext())
      {
        String str = (String)((Iterator)localObject2).next();
        if ((str != null) && (((Context)localObject1).checkCallingOrSelfPermission(str) == 0)) {
          ((Iterator)localObject2).remove();
        }
      }
      localObject1 = localHashSet.iterator();
    }
    catch (Exception paramOptimizelyPlugin)
    {
      return false;
    }
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (String)((Iterator)localObject1).next();
      this.mOptimizely.verboseLog(true, paramOptimizelyPlugin.getPluginId(), String.format("Cannot activate %s. Missing permission %s", new Object[] { paramOptimizelyPlugin.getPluginId(), localObject2 }), new Object[0]);
    }
    return localHashSet.isEmpty();
  }
  
  protected boolean dependenciesSatisfied(@NonNull OptimizelyPlugin paramOptimizelyPlugin)
  {
    Object localObject = paramOptimizelyPlugin.getDependencies();
    if (localObject == null) {
      return true;
    }
    HashSet localHashSet = new HashSet();
    localHashSet.addAll((Collection)localObject);
    localObject = ((List)localObject).iterator();
    String str;
    while (((Iterator)localObject).hasNext())
    {
      str = (String)((Iterator)localObject).next();
      if (this.mActivePlugins.contains(str)) {
        localHashSet.remove(str);
      }
    }
    paramOptimizelyPlugin = paramOptimizelyPlugin.getPluginId();
    localObject = localHashSet.iterator();
    while (((Iterator)localObject).hasNext())
    {
      str = (String)((Iterator)localObject).next();
      this.mOptimizely.verboseLog(true, paramOptimizelyPlugin, String.format("Cannot activate %s. Missing dependency %s", new Object[] { paramOptimizelyPlugin, str }), new Object[0]);
    }
    return localHashSet.isEmpty();
  }
  
  public void dispatchInternalMessage(String paramString1, String paramString2, Bundle paramBundle)
  {
    Iterator localIterator = this.mActivePlugins.iterator();
    while (localIterator.hasNext())
    {
      Object localObject = (String)localIterator.next();
      localObject = (OptimizelyPlugin)this.mPlugins.get(localObject);
      if (localObject != null)
      {
        localObject = ((OptimizelyPlugin)localObject).getOptimizelyEventsListener();
        if (localObject != null) {
          ((OptimizelyEventListener)localObject).onMessage(paramString1, paramString2, paramBundle);
        }
      }
    }
  }
  
  public void dispatchTouchEvent(View paramView, MotionEvent paramMotionEvent)
  {
    Iterator localIterator = this.mActivePlugins.iterator();
    while (localIterator.hasNext())
    {
      Object localObject = (String)localIterator.next();
      localObject = (OptimizelyPlugin)this.mPlugins.get(localObject);
      if (localObject != null)
      {
        localObject = ((OptimizelyPlugin)localObject).getOnTouchListener();
        if (localObject != null) {
          ((View.OnTouchListener)localObject).onTouch(paramView, paramMotionEvent);
        }
      }
    }
  }
  
  public void enablePlugin(@NonNull String paramString, @Nullable JSONObject paramJSONObject)
  {
    OptimizelyPluginConfig localOptimizelyPluginConfig = new OptimizelyPluginConfig();
    localOptimizelyPluginConfig.setEnabled(Boolean.valueOf(true));
    localOptimizelyPluginConfig.setId(paramString);
    if (paramJSONObject != null)
    {
      paramJSONObject = paramJSONObject.toString();
      if (paramJSONObject != null)
      {
        paramJSONObject = new JsonParser().parse(paramJSONObject);
        if (paramJSONObject != null)
        {
          paramJSONObject = paramJSONObject.getAsJsonObject();
          if (paramJSONObject != null) {
            localOptimizelyPluginConfig.setConfig(paramJSONObject);
          }
        }
      }
    }
    this.mPluginConfigs.put(paramString, localOptimizelyPluginConfig);
    this.mEnabledPlugins.add(paramString);
  }
  
  public Map<String, Application.ActivityLifecycleCallbacks> getManagedLifeCycleCallbacks()
  {
    return this.mManagedLifecycleCallbacks;
  }
  
  public String getPluginDebugInfo()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Plugins:\n");
    Iterator localIterator = this.mDelayedStartPlugins.iterator();
    while (localIterator.hasNext())
    {
      localStringBuilder.append((String)localIterator.next());
      localStringBuilder.append(" (Waiting to Start)");
      localStringBuilder.append("\n");
    }
    localIterator = this.mActivePlugins.iterator();
    while (localIterator.hasNext())
    {
      localStringBuilder.append((String)localIterator.next());
      localStringBuilder.append(" (Active)");
      localStringBuilder.append("\n");
    }
    localIterator = this.mDisabledPlugins.iterator();
    while (localIterator.hasNext())
    {
      localStringBuilder.append((String)localIterator.next());
      localStringBuilder.append(" (Disabled)");
      localStringBuilder.append("\n");
    }
    return localStringBuilder.toString();
  }
  
  protected boolean isAllowed(@NonNull OptimizelyPlugin paramOptimizelyPlugin)
  {
    return sTRUSTED_PLUGINS.contains(paramOptimizelyPlugin.getPluginId()) | this.mEnabledPlugins.contains(paramOptimizelyPlugin.getPluginId());
  }
  
  public void loadPluginConfigs()
  {
    Object localObject = this.mOptimizely.getOptimizelyData();
    if (localObject != null)
    {
      localObject = ((OptimizelyData)localObject).getPluginConfigs().iterator();
      while (((Iterator)localObject).hasNext())
      {
        OptimizelyPluginConfig localOptimizelyPluginConfig = (OptimizelyPluginConfig)((Iterator)localObject).next();
        if (localOptimizelyPluginConfig.getEnabled().booleanValue())
        {
          this.mPluginConfigs.put(localOptimizelyPluginConfig.getId(), localOptimizelyPluginConfig);
          this.mEnabledPlugins.add(localOptimizelyPluginConfig.getId());
        }
      }
    }
  }
  
  public void registerPlugin(@NonNull OptimizelyPlugin paramOptimizelyPlugin)
  {
    registerPlugin(paramOptimizelyPlugin, false);
  }
  
  public void registerPlugin(@NonNull OptimizelyPlugin paramOptimizelyPlugin, boolean paramBoolean)
  {
    String str = paramOptimizelyPlugin.getPluginId();
    this.mOptimizely.verboseLog(str, "Activating Plugin", new Object[0]);
    this.mPlugins.put(str, paramOptimizelyPlugin);
    if (paramBoolean)
    {
      this.mDelayedStartPlugins.add(str);
      return;
    }
    if ((isAllowed(paramOptimizelyPlugin)) && (!this.mActivePlugins.contains(str)) && (dependenciesSatisfied(paramOptimizelyPlugin)) && (appHasPermissions(paramOptimizelyPlugin)))
    {
      startPlugin(paramOptimizelyPlugin);
      return;
    }
    this.mDisabledPlugins.add(str);
    this.mOptimizely.verboseLog(true, str, "Plugin could not be activated.", new Object[0]);
  }
  
  public void startDelayedPlugins()
  {
    Object localObject = topologicalSort(this.mDelayedStartPlugins);
    if (localObject == null) {
      return;
    }
    localObject = ((List)localObject).iterator();
    while (((Iterator)localObject).hasNext())
    {
      String str = (String)((Iterator)localObject).next();
      OptimizelyPlugin localOptimizelyPlugin = (OptimizelyPlugin)this.mPlugins.get(str);
      if ((localOptimizelyPlugin != null) && (isAllowed(localOptimizelyPlugin)) && (appHasPermissions(localOptimizelyPlugin)) && (dependenciesSatisfied(localOptimizelyPlugin)) && (!this.mActivePlugins.contains(str))) {
        startPlugin(localOptimizelyPlugin);
      }
    }
    this.mDelayedStartPlugins.clear();
  }
  
  List<String> topologicalSort(@NonNull Set<String> paramSet)
  {
    LinkedList localLinkedList = new LinkedList();
    HashSet localHashSet1 = new HashSet();
    HashSet localHashSet2 = new HashSet();
    paramSet = paramSet.iterator();
    while (paramSet.hasNext())
    {
      String str = (String)paramSet.next();
      localHashSet1.clear();
      if (!localHashSet2.contains(str)) {
        topologicalVisit(str, localLinkedList, localHashSet1, localHashSet2);
      }
    }
    return new ArrayList(localLinkedList);
  }
  
  boolean topologicalVisit(@NonNull String paramString, @NonNull LinkedList<String> paramLinkedList, @NonNull Set<String> paramSet1, @NonNull Set<String> paramSet2)
  {
    Object localObject = (OptimizelyPlugin)this.mPlugins.get(paramString);
    if (localObject == null) {
      return false;
    }
    if (paramSet1.contains(paramString))
    {
      this.mOptimizely.verboseLog(true, paramString, "Plugin %s has a circular dependency, and cannot be started.", new Object[] { paramString });
      paramSet2.add(paramString);
      unregisterPlugin((OptimizelyPlugin)localObject);
      return false;
    }
    if (paramSet2.contains(paramString)) {
      return paramLinkedList.contains(paramString);
    }
    paramSet1.add(paramString);
    boolean bool2 = true;
    boolean bool1 = true;
    if (((OptimizelyPlugin)localObject).getDependencies() != null)
    {
      localObject = ((OptimizelyPlugin)localObject).getDependencies().iterator();
      for (;;)
      {
        bool2 = bool1;
        if (!((Iterator)localObject).hasNext()) {
          break;
        }
        bool1 &= topologicalVisit((String)((Iterator)localObject).next(), paramLinkedList, paramSet1, paramSet2);
      }
    }
    if (bool2)
    {
      paramSet2.add(paramString);
      paramLinkedList.addLast(paramString);
    }
    paramSet1.remove(paramString);
    return bool2;
  }
  
  public void unregisterPlugin(@NonNull OptimizelyPlugin paramOptimizelyPlugin)
  {
    String str = paramOptimizelyPlugin.getPluginId();
    if (this.mActivePlugins.remove(str))
    {
      paramOptimizelyPlugin.stop();
      paramOptimizelyPlugin = (Application.ActivityLifecycleCallbacks)this.mManagedLifecycleCallbacks.get(paramOptimizelyPlugin.getPluginId());
      if (paramOptimizelyPlugin != null) {
        this.mOptimizely.getApplication().unregisterActivityLifecycleCallbacks(paramOptimizelyPlugin);
      }
      this.mDisabledPlugins.add(str);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/integration/PluginRegistry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */