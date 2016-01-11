package me.zhanghai.android.materialprogressbar;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Animatable;

abstract class IndeterminateProgressDrawableBase
  extends ProgressDrawableBase
  implements Animatable
{
  protected Animator[] mAnimators;
  
  public IndeterminateProgressDrawableBase(Context paramContext)
  {
    super(paramContext);
  }
  
  private boolean isStarted()
  {
    boolean bool2 = false;
    Animator[] arrayOfAnimator = this.mAnimators;
    int j = arrayOfAnimator.length;
    int i = 0;
    for (;;)
    {
      boolean bool1 = bool2;
      if (i < j)
      {
        if (arrayOfAnimator[i].isStarted()) {
          bool1 = true;
        }
      }
      else {
        return bool1;
      }
      i += 1;
    }
  }
  
  public void draw(Canvas paramCanvas)
  {
    super.draw(paramCanvas);
    if (isStarted()) {
      invalidateSelf();
    }
  }
  
  public boolean isRunning()
  {
    boolean bool2 = false;
    Animator[] arrayOfAnimator = this.mAnimators;
    int j = arrayOfAnimator.length;
    int i = 0;
    for (;;)
    {
      boolean bool1 = bool2;
      if (i < j)
      {
        if (arrayOfAnimator[i].isRunning()) {
          bool1 = true;
        }
      }
      else {
        return bool1;
      }
      i += 1;
    }
  }
  
  public void start()
  {
    if (isStarted()) {
      return;
    }
    Animator[] arrayOfAnimator = this.mAnimators;
    int j = arrayOfAnimator.length;
    int i = 0;
    while (i < j)
    {
      arrayOfAnimator[i].start();
      i += 1;
    }
    invalidateSelf();
  }
  
  public void stop()
  {
    Animator[] arrayOfAnimator = this.mAnimators;
    int j = arrayOfAnimator.length;
    int i = 0;
    while (i < j)
    {
      arrayOfAnimator[i].end();
      i += 1;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/me/zhanghai/android/materialprogressbar/IndeterminateProgressDrawableBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */