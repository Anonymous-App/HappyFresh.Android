package com.optimizely.View;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.View;
import com.optimizely.Network.OptimizelyScreenshot;
import com.optimizely.Optimizely;
import java.lang.reflect.Field;

class OptimizelyDrawerListener
  implements DrawerLayout.DrawerListener
{
  private static final String DRAWER_LISTENER = "OptimizelyDrawerListener";
  private static Field sDrawerListenerField;
  private static Optimizely sOptimizely;
  private int mCurrentState;
  private boolean mIsOpen;
  private float mLastSlideEvent;
  private final DrawerLayout.DrawerListener mWrappedListener;
  @NonNull
  private final OptimizelyScreenshot optimizelyScreenshot;
  
  static
  {
    if (!OptimizelyDrawerListener.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  private OptimizelyDrawerListener(@Nullable DrawerLayout.DrawerListener paramDrawerListener, @NonNull OptimizelyScreenshot paramOptimizelyScreenshot)
  {
    this.mWrappedListener = paramDrawerListener;
    this.optimizelyScreenshot = paramOptimizelyScreenshot;
  }
  
  @Nullable
  private static DrawerLayout.DrawerListener findDrawerListener(@NonNull DrawerLayout paramDrawerLayout)
  {
    try
    {
      if ((!$assertionsDisabled) && (sDrawerListenerField == null)) {
        throw new AssertionError();
      }
    }
    catch (Exception localException)
    {
      if (sOptimizely != null) {
        sOptimizely.verboseLog(true, "OptimizelyDrawerListener", "Failure in finding DrawerListener for view {%s} ", new Object[] { paramDrawerLayout });
      }
      return null;
    }
    DrawerLayout.DrawerListener localDrawerListener = (DrawerLayout.DrawerListener)sDrawerListenerField.get(paramDrawerLayout);
    return localDrawerListener;
  }
  
  public static void wrap(@NonNull DrawerLayout paramDrawerLayout, @NonNull OptimizelyScreenshot paramOptimizelyScreenshot, Optimizely paramOptimizely)
  {
    if (sOptimizely == null) {
      sOptimizely = paramOptimizely;
    }
    if (sDrawerListenerField == null) {}
    for (;;)
    {
      int i;
      try
      {
        paramOptimizely = DrawerLayout.class.getDeclaredFields();
        if (paramOptimizely != null)
        {
          int j = paramOptimizely.length;
          i = 0;
          if (i < j)
          {
            Field localField = paramOptimizely[i];
            if (localField.getType() != DrawerLayout.DrawerListener.class) {
              break label119;
            }
            sDrawerListenerField = localField;
          }
        }
        if (sDrawerListenerField == null) {
          return;
        }
        sDrawerListenerField.setAccessible(true);
        paramOptimizely = findDrawerListener(paramDrawerLayout);
        if (!(paramOptimizely instanceof OptimizelyDrawerListener))
        {
          paramDrawerLayout.setDrawerListener(new OptimizelyDrawerListener(paramOptimizely, paramOptimizelyScreenshot));
          return;
        }
      }
      catch (Exception paramOptimizelyScreenshot)
      {
        sOptimizely.verboseLog(true, "OptimizelyDrawerListener", "Failure in finding DrawerListener for view {%s} ", new Object[] { paramDrawerLayout });
      }
      return;
      label119:
      i += 1;
    }
  }
  
  public void onDrawerClosed(View paramView)
  {
    if ((sOptimizely != null) && (sOptimizely.isActive())) {
      this.optimizelyScreenshot.sendScreenShotToEditor();
    }
    if ((this.mWrappedListener != null) && (this.mIsOpen))
    {
      this.mIsOpen = false;
      this.mWrappedListener.onDrawerClosed(paramView);
    }
  }
  
  public void onDrawerOpened(View paramView)
  {
    if ((sOptimizely != null) && (sOptimizely.isActive())) {
      this.optimizelyScreenshot.sendScreenShotToEditor();
    }
    if ((this.mWrappedListener != null) && (!this.mIsOpen))
    {
      this.mIsOpen = true;
      this.mWrappedListener.onDrawerOpened(paramView);
    }
  }
  
  public void onDrawerSlide(View paramView, float paramFloat)
  {
    if ((this.mWrappedListener != null) && (paramFloat != this.mLastSlideEvent))
    {
      this.mLastSlideEvent = paramFloat;
      this.mWrappedListener.onDrawerSlide(paramView, paramFloat);
    }
  }
  
  public void onDrawerStateChanged(int paramInt)
  {
    if ((this.mWrappedListener != null) && (this.mCurrentState != paramInt))
    {
      this.mCurrentState = paramInt;
      this.mWrappedListener.onDrawerStateChanged(paramInt);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/View/OptimizelyDrawerListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */