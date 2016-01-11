package me.zhanghai.android.materialprogressbar;

import android.animation.Animator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.support.annotation.Keep;
import android.util.DisplayMetrics;

public class IndeterminateProgressDrawable
  extends IndeterminateProgressDrawableBase
{
  private static final float PADDED_INTRINSIC_SIZE_DP = 16.0F;
  private static final float PROGRESS_INTRINSIC_SIZE_DP = 3.2F;
  private static final RectF RECT_BOUND = new RectF(-21.0F, -21.0F, 21.0F, 21.0F);
  private static final RectF RECT_PADDED_BOUND = new RectF(-24.0F, -24.0F, 24.0F, 24.0F);
  private static final RectF RECT_PROGRESS = new RectF(-19.0F, -19.0F, 19.0F, 19.0F);
  private int mPaddedIntrinsicSize;
  private int mProgressIntrinsicSize;
  private RingPathTransform mRingPathTransform = new RingPathTransform(null);
  private RingRotation mRingRotation = new RingRotation(null);
  
  public IndeterminateProgressDrawable(Context paramContext)
  {
    super(paramContext);
    float f = paramContext.getResources().getDisplayMetrics().density;
    this.mProgressIntrinsicSize = Math.round(3.2F * f);
    this.mPaddedIntrinsicSize = Math.round(16.0F * f);
    this.mAnimators = new Animator[] { Animators.createIndeterminate(this.mRingPathTransform), Animators.createIndeterminateRotation(this.mRingRotation) };
  }
  
  private void drawRing(Canvas paramCanvas, Paint paramPaint)
  {
    int i = paramCanvas.save();
    paramCanvas.rotate(this.mRingRotation.mRotation);
    float f1 = this.mRingPathTransform.mTrimPathOffset;
    float f2 = this.mRingPathTransform.mTrimPathStart;
    float f3 = this.mRingPathTransform.mTrimPathEnd;
    float f4 = this.mRingPathTransform.mTrimPathStart;
    paramCanvas.drawArc(RECT_PROGRESS, -90.0F + (f1 + f2) * 360.0F, 360.0F * (f3 - f4), false, paramPaint);
    paramCanvas.restoreToCount(i);
  }
  
  private int getIntrinsicSize()
  {
    if (this.mUseIntrinsicPadding) {
      return this.mPaddedIntrinsicSize;
    }
    return this.mProgressIntrinsicSize;
  }
  
  public int getIntrinsicHeight()
  {
    return getIntrinsicSize();
  }
  
  public int getIntrinsicWidth()
  {
    return getIntrinsicSize();
  }
  
  public int getOpacity()
  {
    if (this.mAlpha == 0) {
      return -2;
    }
    if (this.mAlpha == 255) {
      return -1;
    }
    return -3;
  }
  
  protected void onDraw(Canvas paramCanvas, int paramInt1, int paramInt2, Paint paramPaint)
  {
    if (this.mUseIntrinsicPadding)
    {
      paramCanvas.scale(paramInt1 / RECT_PADDED_BOUND.width(), paramInt2 / RECT_PADDED_BOUND.height());
      paramCanvas.translate(RECT_PADDED_BOUND.width() / 2.0F, RECT_PADDED_BOUND.height() / 2.0F);
    }
    for (;;)
    {
      drawRing(paramCanvas, paramPaint);
      return;
      paramCanvas.scale(paramInt1 / RECT_BOUND.width(), paramInt2 / RECT_BOUND.height());
      paramCanvas.translate(RECT_BOUND.width() / 2.0F, RECT_BOUND.height() / 2.0F);
    }
  }
  
  protected void onPreparePaint(Paint paramPaint)
  {
    paramPaint.setStyle(Paint.Style.STROKE);
    paramPaint.setStrokeWidth(4.0F);
    paramPaint.setStrokeCap(Paint.Cap.SQUARE);
    paramPaint.setStrokeJoin(Paint.Join.MITER);
  }
  
  private static class RingPathTransform
  {
    public float mTrimPathEnd;
    public float mTrimPathOffset;
    public float mTrimPathStart;
    
    @Keep
    public void setTrimPathEnd(float paramFloat)
    {
      this.mTrimPathEnd = paramFloat;
    }
    
    @Keep
    public void setTrimPathOffset(float paramFloat)
    {
      this.mTrimPathOffset = paramFloat;
    }
    
    @Keep
    public void setTrimPathStart(float paramFloat)
    {
      this.mTrimPathStart = paramFloat;
    }
  }
  
  private static class RingRotation
  {
    private float mRotation;
    
    @Keep
    public void setRotation(float paramFloat)
    {
      this.mRotation = paramFloat;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/me/zhanghai/android/materialprogressbar/IndeterminateProgressDrawable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */