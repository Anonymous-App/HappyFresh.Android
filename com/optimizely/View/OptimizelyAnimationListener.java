package com.optimizely.View;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import com.optimizely.Network.OptimizelyScreenshot;
import com.optimizely.Optimizely;
import java.lang.ref.WeakReference;

class OptimizelyAnimationListener
  implements Animation.AnimationListener
{
  private static Optimizely sOptimizely;
  @NonNull
  private final OptimizelyScreenshot mOptimizelyScreenshot;
  private final Animation.AnimationListener mWrappedListener;
  @NonNull
  private final WeakReference<ViewGroup> viewGroupWeakReference;
  
  private OptimizelyAnimationListener(@Nullable Animation.AnimationListener paramAnimationListener, ViewGroup paramViewGroup, @NonNull OptimizelyScreenshot paramOptimizelyScreenshot)
  {
    this.mWrappedListener = paramAnimationListener;
    this.mOptimizelyScreenshot = paramOptimizelyScreenshot;
    this.viewGroupWeakReference = new WeakReference(paramViewGroup);
  }
  
  private boolean isOnScreen()
  {
    ViewGroup localViewGroup = (ViewGroup)this.viewGroupWeakReference.get();
    return (localViewGroup != null) && (ViewUtils.isViewOnScreen(localViewGroup, sOptimizely));
  }
  
  public static void wrap(@NonNull ViewGroup paramViewGroup, @NonNull OptimizelyScreenshot paramOptimizelyScreenshot, Optimizely paramOptimizely)
  {
    if (sOptimizely == null) {
      sOptimizely = paramOptimizely;
    }
    paramOptimizely = paramViewGroup.getLayoutAnimationListener();
    if (!(paramOptimizely instanceof OptimizelyAnimationListener)) {
      paramViewGroup.setLayoutAnimationListener(new OptimizelyAnimationListener(paramOptimizely, paramViewGroup, paramOptimizelyScreenshot));
    }
  }
  
  public void onAnimationEnd(Animation paramAnimation)
  {
    if ((isOnScreen()) && (sOptimizely != null) && (sOptimizely.isActive())) {
      this.mOptimizelyScreenshot.sendScreenShotToEditor();
    }
    if (this.mWrappedListener != null) {
      this.mWrappedListener.onAnimationEnd(paramAnimation);
    }
  }
  
  public void onAnimationRepeat(Animation paramAnimation)
  {
    if (this.mWrappedListener != null) {
      this.mWrappedListener.onAnimationRepeat(paramAnimation);
    }
  }
  
  public void onAnimationStart(Animation paramAnimation)
  {
    if (this.mWrappedListener != null) {
      this.mWrappedListener.onAnimationStart(paramAnimation);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/optimizely/View/OptimizelyAnimationListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */