package com.happyfresh.customs;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.util.Property;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

public class CircularProgressDrawable
  extends Drawable
  implements Animatable
{
  private static final int ANGLE_ANIMATOR_DURATION = 2000;
  private static final Interpolator ANGLE_INTERPOLATOR = new LinearInterpolator();
  private static final int MIN_SWEEP_ANGLE = 30;
  private static final int SWEEP_ANIMATOR_DURATION = 600;
  private static final Interpolator SWEEP_INTERPOLATOR = new DecelerateInterpolator();
  private final RectF fBounds = new RectF();
  private Property<CircularProgressDrawable, Float> mAngleProperty = new Property(Float.class, "angle")
  {
    public Float get(CircularProgressDrawable paramAnonymousCircularProgressDrawable)
    {
      return Float.valueOf(paramAnonymousCircularProgressDrawable.getCurrentGlobalAngle());
    }
    
    public void set(CircularProgressDrawable paramAnonymousCircularProgressDrawable, Float paramAnonymousFloat)
    {
      paramAnonymousCircularProgressDrawable.setCurrentGlobalAngle(paramAnonymousFloat.floatValue());
    }
  };
  private float mBorderWidth;
  private float mCurrentGlobalAngle;
  private float mCurrentGlobalAngleOffset;
  private float mCurrentSweepAngle;
  private boolean mModeAppearing;
  private ObjectAnimator mObjectAnimatorAngle;
  private ObjectAnimator mObjectAnimatorSweep;
  private Paint mPaint;
  private boolean mRunning;
  private Property<CircularProgressDrawable, Float> mSweepProperty = new Property(Float.class, "arc")
  {
    public Float get(CircularProgressDrawable paramAnonymousCircularProgressDrawable)
    {
      return Float.valueOf(paramAnonymousCircularProgressDrawable.getCurrentSweepAngle());
    }
    
    public void set(CircularProgressDrawable paramAnonymousCircularProgressDrawable, Float paramAnonymousFloat)
    {
      paramAnonymousCircularProgressDrawable.setCurrentSweepAngle(paramAnonymousFloat.floatValue());
    }
  };
  
  public CircularProgressDrawable(int paramInt, float paramFloat)
  {
    this.mBorderWidth = paramFloat;
    this.mPaint = new Paint();
    this.mPaint.setAntiAlias(true);
    this.mPaint.setStyle(Paint.Style.STROKE);
    this.mPaint.setStrokeWidth(paramFloat);
    this.mPaint.setColor(paramInt);
    setupAnimations();
  }
  
  private void setupAnimations()
  {
    this.mObjectAnimatorAngle = ObjectAnimator.ofFloat(this, this.mAngleProperty, new float[] { 360.0F });
    this.mObjectAnimatorAngle.setInterpolator(ANGLE_INTERPOLATOR);
    this.mObjectAnimatorAngle.setDuration(2000L);
    this.mObjectAnimatorAngle.setRepeatMode(1);
    this.mObjectAnimatorAngle.setRepeatCount(-1);
    this.mObjectAnimatorSweep = ObjectAnimator.ofFloat(this, this.mSweepProperty, new float[] { 300.0F });
    this.mObjectAnimatorSweep.setInterpolator(SWEEP_INTERPOLATOR);
    this.mObjectAnimatorSweep.setDuration(600L);
    this.mObjectAnimatorSweep.setRepeatMode(1);
    this.mObjectAnimatorSweep.setRepeatCount(-1);
    this.mObjectAnimatorSweep.addListener(new Animator.AnimatorListener()
    {
      public void onAnimationCancel(Animator paramAnonymousAnimator) {}
      
      public void onAnimationEnd(Animator paramAnonymousAnimator) {}
      
      public void onAnimationRepeat(Animator paramAnonymousAnimator)
      {
        CircularProgressDrawable.this.toggleAppearingMode();
      }
      
      public void onAnimationStart(Animator paramAnonymousAnimator) {}
    });
  }
  
  private void toggleAppearingMode()
  {
    if (!this.mModeAppearing) {}
    for (boolean bool = true;; bool = false)
    {
      this.mModeAppearing = bool;
      if (this.mModeAppearing) {
        this.mCurrentGlobalAngleOffset = ((this.mCurrentGlobalAngleOffset + 60.0F) % 360.0F);
      }
      return;
    }
  }
  
  public void draw(Canvas paramCanvas)
  {
    float f1 = this.mCurrentGlobalAngle - this.mCurrentGlobalAngleOffset;
    float f2 = this.mCurrentSweepAngle;
    if (!this.mModeAppearing) {
      f1 += f2;
    }
    for (f2 = 360.0F - f2 - 30.0F;; f2 += 30.0F)
    {
      paramCanvas.drawArc(this.fBounds, f1, f2, false, this.mPaint);
      return;
    }
  }
  
  public float getCurrentGlobalAngle()
  {
    return this.mCurrentGlobalAngle;
  }
  
  public float getCurrentSweepAngle()
  {
    return this.mCurrentSweepAngle;
  }
  
  public int getOpacity()
  {
    return -2;
  }
  
  public boolean isRunning()
  {
    return this.mRunning;
  }
  
  protected void onBoundsChange(Rect paramRect)
  {
    super.onBoundsChange(paramRect);
    this.fBounds.left = (paramRect.left + this.mBorderWidth / 2.0F + 0.5F);
    this.fBounds.right = (paramRect.right - this.mBorderWidth / 2.0F - 0.5F);
    this.fBounds.top = (paramRect.top + this.mBorderWidth / 2.0F + 0.5F);
    this.fBounds.bottom = (paramRect.bottom - this.mBorderWidth / 2.0F - 0.5F);
  }
  
  public void setAlpha(int paramInt)
  {
    this.mPaint.setAlpha(paramInt);
  }
  
  public void setColorFilter(ColorFilter paramColorFilter)
  {
    this.mPaint.setColorFilter(paramColorFilter);
  }
  
  public void setCurrentGlobalAngle(float paramFloat)
  {
    this.mCurrentGlobalAngle = paramFloat;
    invalidateSelf();
  }
  
  public void setCurrentSweepAngle(float paramFloat)
  {
    this.mCurrentSweepAngle = paramFloat;
    invalidateSelf();
  }
  
  public void start()
  {
    if (isRunning()) {
      return;
    }
    this.mRunning = true;
    this.mObjectAnimatorAngle.start();
    this.mObjectAnimatorSweep.start();
    invalidateSelf();
  }
  
  public void stop()
  {
    if (!isRunning()) {
      return;
    }
    this.mRunning = false;
    this.mObjectAnimatorAngle.cancel();
    this.mObjectAnimatorSweep.cancel();
    invalidateSelf();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/customs/CircularProgressDrawable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */