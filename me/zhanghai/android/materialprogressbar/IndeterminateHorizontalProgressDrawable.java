package me.zhanghai.android.materialprogressbar;

import android.animation.Animator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.support.annotation.Keep;
import android.util.DisplayMetrics;
import me.zhanghai.android.materialprogressbar.internal.ThemeUtils;

public class IndeterminateHorizontalProgressDrawable
  extends IndeterminateProgressDrawableBase
  implements ShowTrackDrawable
{
  private static final float PADDED_INTRINSIC_HEIGHT_DP = 16.0F;
  private static final float PROGRESS_INTRINSIC_HEIGHT_DP = 3.2F;
  private static final RectTransformX RECT_1_TRANSFORM_X = new RectTransformX(-522.6F, 0.1F);
  private static final RectTransformX RECT_2_TRANSFORM_X = new RectTransformX(-197.6F, 0.1F);
  private static final RectF RECT_BOUND = new RectF(-180.0F, -1.0F, 180.0F, 1.0F);
  private static final RectF RECT_PADDED_BOUND = new RectF(-180.0F, -5.0F, 180.0F, 5.0F);
  private static final RectF RECT_PROGRESS = new RectF(-144.0F, -1.0F, 144.0F, 1.0F);
  private int mPaddedIntrinsicHeight;
  private int mProgressIntrinsicHeight;
  private RectTransformX mRect1TransformX = new RectTransformX(RECT_1_TRANSFORM_X);
  private RectTransformX mRect2TransformX = new RectTransformX(RECT_2_TRANSFORM_X);
  private boolean mShowTrack = true;
  private float mTrackAlpha;
  
  public IndeterminateHorizontalProgressDrawable(Context paramContext)
  {
    super(paramContext);
    float f = paramContext.getResources().getDisplayMetrics().density;
    this.mProgressIntrinsicHeight = Math.round(3.2F * f);
    this.mPaddedIntrinsicHeight = Math.round(16.0F * f);
    this.mTrackAlpha = ThemeUtils.getFloatFromAttrRes(16842803, paramContext);
    this.mAnimators = new Animator[] { Animators.createIndeterminateHorizontalRect1(this.mRect1TransformX), Animators.createIndeterminateHorizontalRect2(this.mRect2TransformX) };
  }
  
  private static void drawProgressRect(Canvas paramCanvas, RectTransformX paramRectTransformX, Paint paramPaint)
  {
    int i = paramCanvas.save();
    paramCanvas.translate(paramRectTransformX.mTranslateX, 0.0F);
    paramCanvas.scale(paramRectTransformX.mScaleX, 1.0F);
    paramCanvas.drawRect(RECT_PROGRESS, paramPaint);
    paramCanvas.restoreToCount(i);
  }
  
  private static void drawTrackRect(Canvas paramCanvas, Paint paramPaint)
  {
    paramCanvas.drawRect(RECT_BOUND, paramPaint);
  }
  
  public int getIntrinsicHeight()
  {
    if (this.mUseIntrinsicPadding) {
      return this.mPaddedIntrinsicHeight;
    }
    return this.mProgressIntrinsicHeight;
  }
  
  public int getOpacity()
  {
    if (this.mAlpha == 0) {
      return -2;
    }
    if ((this.mAlpha == 255) && ((!this.mShowTrack) || (this.mTrackAlpha == 1.0F))) {
      return -1;
    }
    return -3;
  }
  
  public boolean getShowTrack()
  {
    return this.mShowTrack;
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
      if (this.mShowTrack)
      {
        paramPaint.setAlpha(Math.round(this.mAlpha * this.mTrackAlpha));
        drawTrackRect(paramCanvas, paramPaint);
        paramPaint.setAlpha(this.mAlpha);
      }
      drawProgressRect(paramCanvas, this.mRect2TransformX, paramPaint);
      drawProgressRect(paramCanvas, this.mRect1TransformX, paramPaint);
      return;
      paramCanvas.scale(paramInt1 / RECT_BOUND.width(), paramInt2 / RECT_BOUND.height());
      paramCanvas.translate(RECT_BOUND.width() / 2.0F, RECT_BOUND.height() / 2.0F);
    }
  }
  
  protected void onPreparePaint(Paint paramPaint)
  {
    paramPaint.setStyle(Paint.Style.FILL);
  }
  
  public void setShowTrack(boolean paramBoolean)
  {
    if (this.mShowTrack != paramBoolean)
    {
      this.mShowTrack = paramBoolean;
      invalidateSelf();
    }
  }
  
  private static class RectTransformX
  {
    public float mScaleX;
    public float mTranslateX;
    
    public RectTransformX(float paramFloat1, float paramFloat2)
    {
      this.mTranslateX = paramFloat1;
      this.mScaleX = paramFloat2;
    }
    
    public RectTransformX(RectTransformX paramRectTransformX)
    {
      this.mTranslateX = paramRectTransformX.mTranslateX;
      this.mScaleX = paramRectTransformX.mScaleX;
    }
    
    @Keep
    public void setScaleX(float paramFloat)
    {
      this.mScaleX = paramFloat;
    }
    
    @Keep
    public void setTranslateX(float paramFloat)
    {
      this.mTranslateX = paramFloat;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/me/zhanghai/android/materialprogressbar/IndeterminateHorizontalProgressDrawable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */