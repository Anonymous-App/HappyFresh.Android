package com.optimizely.View;

import android.annotation.TargetApi;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.OnHierarchyChangeListener;
import com.optimizely.Network.OptimizelyScreenshot;
import com.optimizely.Optimizely;
import java.lang.reflect.Field;
import java.util.List;

@TargetApi(20)
class OptimizelyOnHierarchyChangeListener
  implements ViewGroup.OnHierarchyChangeListener
{
  private static final String ON_HIERARCHY_CHANGE_COMPONENT = "OptimizelyOnHierarchyChangeListener";
  private static Field sHierarchyField;
  private static Optimizely sOptimizely;
  private final ViewGroup.OnHierarchyChangeListener listener;
  private boolean mAddInProcess;
  private boolean mRemoveInProcess;
  private final OptimizelyScreenshot optimizelyScreenshot;
  private final OptimizelyViews optimizelyViews;
  
  static
  {
    if (!OptimizelyOnHierarchyChangeListener.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  private OptimizelyOnHierarchyChangeListener(@Nullable ViewGroup.OnHierarchyChangeListener paramOnHierarchyChangeListener, @NonNull OptimizelyScreenshot paramOptimizelyScreenshot, @NonNull OptimizelyViews paramOptimizelyViews)
  {
    this.listener = paramOnHierarchyChangeListener;
    this.optimizelyScreenshot = paramOptimizelyScreenshot;
    this.optimizelyViews = paramOptimizelyViews;
  }
  
  @Nullable
  private static ViewGroup.OnHierarchyChangeListener findOnHierarchyChangeListener(@NonNull ViewGroup paramViewGroup)
  {
    try
    {
      if ((!$assertionsDisabled) && (sHierarchyField == null)) {
        throw new AssertionError();
      }
    }
    catch (Exception localException)
    {
      if (sOptimizely != null) {
        sOptimizely.verboseLog(true, "OptimizelyOnHierarchyChangeListener", "Failure in finding OnHierarchyChangeListener for view {%s} ", new Object[] { paramViewGroup });
      }
      return null;
    }
    ViewGroup.OnHierarchyChangeListener localOnHierarchyChangeListener = (ViewGroup.OnHierarchyChangeListener)sHierarchyField.get(paramViewGroup);
    return localOnHierarchyChangeListener;
  }
  
  public static void wrap(@NonNull ViewGroup paramViewGroup, @NonNull OptimizelyViews paramOptimizelyViews, @NonNull OptimizelyScreenshot paramOptimizelyScreenshot, @NonNull Optimizely paramOptimizely)
  {
    if (sOptimizely == null) {
      sOptimizely = paramOptimizely;
    }
    if (sHierarchyField == null) {}
    try
    {
      sHierarchyField = ViewGroup.class.getDeclaredField("mOnHierarchyChangeListener");
      if (sHierarchyField == null) {
        return;
      }
      sHierarchyField.setAccessible(true);
      paramOptimizely = findOnHierarchyChangeListener(paramViewGroup);
      if (!(paramOptimizely instanceof OptimizelyOnHierarchyChangeListener))
      {
        paramViewGroup.setOnHierarchyChangeListener(new OptimizelyOnHierarchyChangeListener(paramOptimizely, paramOptimizelyScreenshot, paramOptimizelyViews));
        return;
      }
    }
    catch (Exception paramOptimizelyViews)
    {
      sOptimizely.verboseLog(true, "OptimizelyOnHierarchyChangeListener", "Failure in finding OnHierarchyChangeListener for view {%s} ", new Object[] { paramViewGroup });
    }
  }
  
  public void onChildViewAdded(View paramView1, View paramView2)
  {
    if (this.mAddInProcess) {
      return;
    }
    this.mAddInProcess = true;
    if ((sOptimizely != null) && (sOptimizely.isActive()))
    {
      List localList = ViewUtils.findAllChildViews(paramView2);
      this.optimizelyViews.onViewsAdded(localList);
      if (sOptimizely.isEditorEnabled().booleanValue())
      {
        ViewUtils.sendViewHierarchy(paramView2, sOptimizely);
        this.optimizelyScreenshot.sendScreenShotToEditor();
      }
    }
    if (this.listener != null) {
      this.listener.onChildViewAdded(paramView1, paramView2);
    }
    this.mAddInProcess = false;
  }
  
  public void onChildViewRemoved(View paramView1, View paramView2)
  {
    if (this.mRemoveInProcess) {
      return;
    }
    this.mRemoveInProcess = true;
    if ((sOptimizely != null) && (sOptimizely.isActive()) && (sOptimizely.isEditorEnabled().booleanValue()))
    {
      ViewUtils.clearViewHierarchy(paramView2, sOptimizely);
      this.optimizelyScreenshot.sendScreenShotToEditor();
    }
    if (this.listener != null) {
      this.listener.onChildViewRemoved(paramView1, paramView2);
    }
    this.mRemoveInProcess = false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/View/OptimizelyOnHierarchyChangeListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */