package com.optimizely.View;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import com.optimizely.Network.OptimizelyScreenshot;
import com.optimizely.Optimizely;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

class OptimizelyListScrollListener
  implements AbsListView.OnScrollListener
{
  private static final String LOG_TAG = "OptimizelyScrollViewListener";
  private static Optimizely sOptimizely;
  private static Field sScrollListenerField;
  private int firstVisibleItem;
  private final WeakReference<AbsListView> listViewReference;
  private final OptimizelyScreenshot mScreenshot;
  @NonNull
  private final Handler mSendHandler = new Handler();
  @NonNull
  private final Runnable mSendViewsRunnable = new Runnable()
  {
    public void run()
    {
      if ((OptimizelyListScrollListener.sOptimizely != null) && (OptimizelyListScrollListener.sOptimizely.isActive()) && (OptimizelyListScrollListener.sOptimizely.isEditorEnabled().booleanValue()))
      {
        AbsListView localAbsListView = (AbsListView)OptimizelyListScrollListener.this.listViewReference.get();
        OptimizelyListScrollListener.sOptimizely.getViews().onViewsAdded(ViewUtils.findAllChildViews(localAbsListView));
        ViewUtils.sendViewHierarchy(localAbsListView, OptimizelyListScrollListener.sOptimizely);
      }
    }
  };
  private final AbsListView.OnScrollListener mWrappedListener;
  private int scrollState;
  
  static
  {
    if (!OptimizelyListScrollListener.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  private OptimizelyListScrollListener(@Nullable AbsListView.OnScrollListener paramOnScrollListener, @NonNull AbsListView paramAbsListView, @NonNull OptimizelyScreenshot paramOptimizelyScreenshot)
  {
    this.mWrappedListener = paramOnScrollListener;
    this.listViewReference = new WeakReference(paramAbsListView);
    this.mScreenshot = paramOptimizelyScreenshot;
  }
  
  @Nullable
  private static AbsListView.OnScrollListener getOnScrollListener(@NonNull AbsListView paramAbsListView, @Nullable Optimizely paramOptimizely)
  {
    try
    {
      if ((!$assertionsDisabled) && (sScrollListenerField == null)) {
        throw new AssertionError();
      }
    }
    catch (Exception localException)
    {
      if (paramOptimizely != null) {
        paramOptimizely.verboseLog(true, "OptimizelyScrollViewListener", "Failure in finding on scroll listener for listview {%s} ", new Object[] { paramAbsListView });
      }
      return null;
    }
    Object localObject = sScrollListenerField.get(paramAbsListView);
    if (localObject == null) {
      return null;
    }
    localObject = (AbsListView.OnScrollListener)localObject;
    return (AbsListView.OnScrollListener)localObject;
  }
  
  public static void wrap(@Nullable AbsListView paramAbsListView, @NonNull OptimizelyScreenshot paramOptimizelyScreenshot, @NonNull Optimizely paramOptimizely)
  {
    if (sOptimizely == null) {
      sOptimizely = paramOptimizely;
    }
    if (sScrollListenerField == null) {}
    try
    {
      sScrollListenerField = AbsListView.class.getDeclaredField("mOnScrollListener");
      if (sScrollListenerField == null) {
        return;
      }
      sScrollListenerField.setAccessible(true);
      if (paramAbsListView != null)
      {
        paramOptimizely = getOnScrollListener(paramAbsListView, paramOptimizely);
        if (!(paramOptimizely instanceof OptimizelyListScrollListener))
        {
          paramAbsListView.setOnScrollListener(new OptimizelyListScrollListener(paramOptimizely, paramAbsListView, paramOptimizelyScreenshot));
          return;
        }
      }
    }
    catch (Exception paramOptimizelyScreenshot)
    {
      sOptimizely.verboseLog(true, "OptimizelyScrollViewListener", "Failure in finding on scroll listener for listview {%s} ", new Object[] { paramAbsListView });
    }
  }
  
  public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramAbsListView != this.listViewReference.get()) || (paramInt1 == this.firstVisibleItem)) {}
    do
    {
      return;
      this.firstVisibleItem = paramInt1;
      this.mSendHandler.removeCallbacks(this.mSendViewsRunnable);
      this.mSendHandler.postDelayed(this.mSendViewsRunnable, 200L);
      this.mScreenshot.sendScreenShotToEditor();
    } while (this.mWrappedListener == null);
    this.mWrappedListener.onScroll(paramAbsListView, paramInt1, paramInt2, paramInt3);
  }
  
  public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt)
  {
    if ((paramAbsListView != this.listViewReference.get()) || (paramInt == this.scrollState)) {}
    do
    {
      return;
      this.scrollState = paramInt;
    } while (this.mWrappedListener == null);
    this.mWrappedListener.onScrollStateChanged(paramAbsListView, paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/View/OptimizelyListScrollListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */