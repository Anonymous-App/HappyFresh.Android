package com.optimizely.View;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.AbsListView;
import com.optimizely.Core.OptimizelyCodec;
import com.optimizely.Core.OptimizelyData;
import com.optimizely.JSON.OptimizelyExperiment;
import com.optimizely.JSON.OptimizelyGoal;
import com.optimizely.JSON.OptimizelyVariation;
import com.optimizely.JSON.OptimizelyView;
import com.optimizely.LogAndEvent.OptimizelyEventsManager;
import com.optimizely.Network.OptimizelyScreenshot;
import com.optimizely.Optimizely;
import com.optimizely.Optimizely.OptimizelyRunningMode;
import com.optimizely.Preview.PreviewFloatingActionButton;
import com.optimizely.Preview.ShortLinkHandler;
import com.optimizely.View.idmanager.OptimizelyIdManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

@TargetApi(18)
public class OptimizelyViews
{
  @NonNull
  private static final String VIEWS_COMPONENT = "OptimizelyViews";
  @NonNull
  final ActiveChangesStack activeChangesStack;
  @Nullable
  private Activity currentActivity;
  @Nullable
  private View currentRootView;
  @NonNull
  private final GoalHandler goalHandler;
  private final Application.ActivityLifecycleCallbacks lifecycleCallbacks;
  @NonNull
  private final ViewTreeObserver.OnScrollChangedListener mScrollListener = new ViewTreeObserver.OnScrollChangedListener()
  {
    public void onScrollChanged()
    {
      OptimizelyViews.this.optimizelyScreenshot.sendScreenShotToEditor();
    }
  };
  private final Optimizely optimizely;
  private final OptimizelyScreenshot optimizelyScreenshot;
  @NonNull
  final HashMap<OptimizelyView, OptimizelyExperiment> sourceForVisualChangeMap = new HashMap();
  @NonNull
  final List<OptimizelyView> visualChangesForCurrentActivity = new ArrayList();
  
  public OptimizelyViews(@NonNull Application paramApplication, @NonNull Optimizely paramOptimizely, @NonNull GoalHandler paramGoalHandler, @NonNull OptimizelyScreenshot paramOptimizelyScreenshot, @NonNull ActiveChangesStack paramActiveChangesStack)
  {
    this.optimizely = paramOptimizely;
    this.goalHandler = paramGoalHandler;
    this.optimizelyScreenshot = paramOptimizelyScreenshot;
    this.activeChangesStack = paramActiveChangesStack;
    this.lifecycleCallbacks = new OptimizelyLifecycleCallbacks(null);
    paramApplication.registerActivityLifecycleCallbacks(this.lifecycleCallbacks);
  }
  
  private void applyChanges(@NonNull List<View> paramList)
  {
    applyViewChanges(this.visualChangesForCurrentActivity, paramList);
    applyGoalHandlers(paramList);
  }
  
  private void attachListeners(@NonNull List<View> paramList)
  {
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      View localView = (View)paramList.next();
      if (localView != null)
      {
        if ((localView instanceof ViewGroup)) {
          OptimizelyOnHierarchyChangeListener.wrap((ViewGroup)localView, this, this.optimizelyScreenshot, this.optimizely);
        }
        if ((localView instanceof ViewGroup)) {
          OptimizelyAnimationListener.wrap((ViewGroup)localView, this.optimizelyScreenshot, this.optimizely);
        }
        if ((localView instanceof DrawerLayout)) {
          OptimizelyDrawerListener.wrap((DrawerLayout)localView, this.optimizelyScreenshot, this.optimizely);
        }
        if ((this.optimizely.isEditorEnabled().booleanValue()) && ((localView instanceof AbsListView))) {
          OptimizelyListScrollListener.wrap((AbsListView)localView, this.optimizelyScreenshot, this.optimizely);
        }
        Optimizely.OptimizelyRunningMode localOptimizelyRunningMode = Optimizely.getRunningMode();
        if (((localOptimizelyRunningMode == Optimizely.OptimizelyRunningMode.NORMAL) || (localOptimizelyRunningMode == Optimizely.OptimizelyRunningMode.PREVIEW)) && (this.optimizely.isEditGestureEnabled())) {
          EditGestureListener.wrap(localView, this.optimizely);
        }
      }
    }
  }
  
  private void updateCurrentVisualChanges(@NonNull Activity paramActivity)
  {
    this.visualChangesForCurrentActivity.clear();
    Object localObject1 = this.optimizely.getOptimizelyData().getRunningExperimentsById();
    OptimizelyIdManager localOptimizelyIdManager = this.optimizely.getIdManager();
    localObject1 = ((Map)localObject1).values().iterator();
    while (((Iterator)localObject1).hasNext())
    {
      OptimizelyExperiment localOptimizelyExperiment = (OptimizelyExperiment)((Iterator)localObject1).next();
      Object localObject2 = localOptimizelyExperiment.getActiveVariation();
      if (localObject2 != null)
      {
        localObject2 = ((OptimizelyVariation)localObject2).getViews();
        if (localObject2 != null)
        {
          localObject2 = ((List)localObject2).iterator();
          while (((Iterator)localObject2).hasNext())
          {
            OptimizelyView localOptimizelyView = (OptimizelyView)((Iterator)localObject2).next();
            String str = localOptimizelyView.getOptimizelyId();
            if ((str != null) && (localOptimizelyIdManager.activityMatchesOptimizelyId(paramActivity, str)))
            {
              this.visualChangesForCurrentActivity.add(localOptimizelyView);
              this.sourceForVisualChangeMap.put(localOptimizelyView, localOptimizelyExperiment);
            }
          }
        }
      }
    }
  }
  
  void applyChangesInEditMode(@NonNull List<View> paramList)
  {
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      View localView = (View)paramList.next();
      String str1 = this.optimizely.getIdManager().getOptimizelyId(localView);
      if (str1 != null)
      {
        this.goalHandler.setUpTouchGoal(localView, str1);
        Map localMap = this.activeChangesStack.getCurrentChangesForId(str1);
        Iterator localIterator = localMap.keySet().iterator();
        while (localIterator.hasNext())
        {
          String str2 = (String)localIterator.next();
          Object localObject = localMap.get(str2);
          this.optimizely.verboseLog(false, "OptimizelyViews", "Setting %s to %s for view %s", new Object[] { str2, localObject, str1 });
          OptimizelyViewPropertyHandler.setViewProperty(localView, str2, localObject, this.optimizely);
        }
      }
    }
    this.optimizelyScreenshot.sendScreenShotToEditor();
  }
  
  void applyGoalHandlers(@NonNull List<View> paramList)
  {
    Iterator localIterator1 = this.optimizely.getOptimizelyData().getGoals().iterator();
    if (localIterator1.hasNext())
    {
      OptimizelyGoal localOptimizelyGoal = (OptimizelyGoal)localIterator1.next();
      Iterator localIterator2 = paramList.iterator();
      while (localIterator2.hasNext())
      {
        View localView = (View)localIterator2.next();
        Iterator localIterator3 = localOptimizelyGoal.getElementIds().iterator();
        while (localIterator3.hasNext())
        {
          String str = (String)localIterator3.next();
          if ((this.optimizely.getIdManager().viewMatchesOptimizelyId(localView, str)) && (GoalHandler.Goal.MOBILE_TAP.toString().equals(localOptimizelyGoal.getType()))) {
            this.goalHandler.setUpTouchGoal(localView, str);
          }
        }
      }
    }
  }
  
  void applyViewChanges(@Nullable List<OptimizelyView> paramList, @NonNull List<View> paramList1)
  {
    if (paramList == null) {}
    for (;;)
    {
      return;
      OptimizelyIdManager localOptimizelyIdManager = this.optimizely.getIdManager();
      paramList = paramList.iterator();
      while (paramList.hasNext())
      {
        OptimizelyView localOptimizelyView = (OptimizelyView)paramList.next();
        String str = localOptimizelyView.getOptimizelyId();
        Iterator localIterator = paramList1.iterator();
        while (localIterator.hasNext())
        {
          View localView = (View)localIterator.next();
          if (localOptimizelyIdManager.viewMatchesOptimizelyId(localView, str))
          {
            OptimizelyData.markExperimentAsViewedIfNecessary((OptimizelyExperiment)this.sourceForVisualChangeMap.get(localOptimizelyView), this.optimizely);
            if ((localOptimizelyView.getValue() != null) && (localOptimizelyView.getKey() != null)) {
              try
              {
                OptimizelyViewPropertyHandler.setViewProperty(localView, localOptimizelyView.getKey(), localOptimizelyView.getValue(), this.optimizely);
              }
              catch (Exception localException)
              {
                this.optimizely.verboseLog(true, "OptimizelyViews", "Failed to apply view changes %s.", new Object[] { localException });
              }
            }
          }
        }
        this.optimizely.getOptimizelyData().addLockedView(localOptimizelyView);
      }
    }
  }
  
  @Nullable
  public Activity getCurrentActivity()
  {
    return this.currentActivity;
  }
  
  @Nullable
  public View getCurrentRootView()
  {
    return this.currentRootView;
  }
  
  @NonNull
  public GoalHandler getGoalHandler()
  {
    return this.goalHandler;
  }
  
  @Nullable
  public Object getViewProperty(String paramString1, String paramString2)
  {
    paramString1 = this.optimizely.getIdManager().findViewsByOptimizelyId(paramString1);
    if (!paramString1.isEmpty()) {
      return OptimizelyViewPropertyHandler.getViewProperties((View)paramString1.get(0), this.optimizely).get(paramString2);
    }
    return null;
  }
  
  public void onViewsAdded(@NonNull List<View> paramList)
  {
    this.optimizely.getIdManager().clearHierarchicalIds(paramList);
    if (this.optimizely.isEditorEnabled().booleanValue()) {
      applyChangesInEditMode(paramList);
    }
    for (;;)
    {
      attachListeners(paramList);
      return;
      applyChanges(paramList);
    }
  }
  
  public void resetViewChangeHistory()
  {
    if ((this.optimizely.isActive()) && (this.optimizely.isEditorEnabled().booleanValue()))
    {
      Iterator localIterator = this.activeChangesStack.getCurrentChangeKeys().iterator();
      while (localIterator.hasNext())
      {
        Pair localPair = (Pair)localIterator.next();
        if ((localPair.first != null) && (localPair.second != null)) {
          this.activeChangesStack.revertToBase((String)localPair.first, (String)localPair.second);
        }
      }
    }
  }
  
  public void sendViewToSocket(@NonNull String paramString, boolean paramBoolean)
  {
    List localList = this.optimizely.getIdManager().findViewsByOptimizelyId(paramString);
    if (!localList.isEmpty())
    {
      this.optimizely.sendMap(ViewUtils.getOptimizelyInfo((View)localList.get(0), this.optimizely, paramBoolean));
      return;
    }
    this.optimizely.verboseLog(true, "OptimizelyViews", "No view exists for viewId %1$s", new Object[] { paramString });
  }
  
  public void setViewProperty(@Nullable String paramString1, @NonNull String paramString2, @NonNull JSONObject paramJSONObject)
  {
    if (paramString1 == null) {
      return;
    }
    try
    {
      paramJSONObject = paramJSONObject.get("value");
      this.activeChangesStack.addChange(paramString1, paramString2, paramJSONObject);
      Iterator localIterator = this.optimizely.getIdManager().findViewsByOptimizelyId(paramString1).iterator();
      while (localIterator.hasNext()) {
        OptimizelyViewPropertyHandler.setViewProperty((View)localIterator.next(), paramString2, OptimizelyCodec.fromJson(paramJSONObject), this.optimizely);
      }
      this.optimizelyScreenshot.sendScreenShotToEditor();
    }
    catch (JSONException paramJSONObject)
    {
      this.optimizely.verboseLog(true, "OptimizelyViews", "failed to set property %1$s for view %2$s with exception %3$s", new Object[] { paramString2, paramString1, paramJSONObject.getLocalizedMessage() });
      return;
    }
  }
  
  public void stop()
  {
    if (this.currentActivity != null)
    {
      Application localApplication = this.currentActivity.getApplication();
      if (localApplication != null) {
        localApplication.unregisterActivityLifecycleCallbacks(this.lifecycleCallbacks);
      }
    }
  }
  
  public void updateCurrentRootView(@NonNull Activity paramActivity)
  {
    if ((this.optimizely.isActive()) && (this.optimizely.isEditorEnabled().booleanValue()) && (this.currentRootView != null)) {
      ViewUtils.clearViewHierarchy(this.currentRootView, this.optimizely);
    }
    this.currentRootView = ViewUtils.rootView(paramActivity);
    this.currentActivity = paramActivity;
    updateCurrentVisualChanges(paramActivity);
    if (this.currentRootView != null)
    {
      paramActivity = this.currentRootView.getViewTreeObserver();
      if (paramActivity != null) {
        paramActivity.addOnScrollChangedListener(this.mScrollListener);
      }
      onViewsAdded(ViewUtils.findAllChildViews(this.currentRootView));
    }
    this.optimizelyScreenshot.sendScreenShotToEditor();
  }
  
  private class OptimizelyLifecycleCallbacks
    implements Application.ActivityLifecycleCallbacks
  {
    private OptimizelyLifecycleCallbacks() {}
    
    private void cleanUp(@NonNull Activity paramActivity)
    {
      if (paramActivity.equals(OptimizelyViews.this.currentActivity))
      {
        OptimizelyViews.access$402(OptimizelyViews.this, null);
        OptimizelyViews.access$502(OptimizelyViews.this, null);
      }
    }
    
    public void onActivityCreated(@NonNull Activity paramActivity, Bundle paramBundle)
    {
      ShortLinkHandler.handleStartActivityIntent(paramActivity.getIntent(), OptimizelyViews.this.optimizely);
    }
    
    public void onActivityDestroyed(@NonNull Activity paramActivity)
    {
      cleanUp(paramActivity);
    }
    
    public void onActivityPaused(Activity paramActivity)
    {
      PreviewFloatingActionButton.hide(paramActivity);
      OptimizelyViews.this.optimizely.dispatchEvents();
      OptimizelyViews.this.optimizely.getOptimizelyEventsManager().endSession();
      Optimizely.setAppInForeground(false);
    }
    
    public void onActivityResumed(@NonNull Activity paramActivity)
    {
      OptimizelyViews.this.goalHandler.handleViewGoal(paramActivity.getClass().getSimpleName());
      ShortLinkHandler.handleStartActivityIntent(paramActivity.getIntent(), OptimizelyViews.this.optimizely);
      if (!OptimizelyViews.this.optimizely.isActive()) {
        return;
      }
      Optimizely.setAppInForeground(true);
      if (OptimizelyViews.this.optimizely.isVisualExperimentsEnabled())
      {
        OptimizelyViews.this.resetViewChangeHistory();
        OptimizelyViews.this.updateCurrentRootView(paramActivity);
        if (OptimizelyViews.this.optimizely.isEditorEnabled().booleanValue()) {
          ViewUtils.sendViewHierarchy(OptimizelyViews.this.currentRootView, OptimizelyViews.this.optimizely);
        }
      }
      OptimizelyViews.this.optimizely.getOptimizelyEventsManager().startSession();
      PreviewFloatingActionButton.show(paramActivity);
    }
    
    public void onActivitySaveInstanceState(Activity paramActivity, Bundle paramBundle) {}
    
    public void onActivityStarted(Activity paramActivity) {}
    
    public void onActivityStopped(Activity paramActivity) {}
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/View/OptimizelyViews.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */