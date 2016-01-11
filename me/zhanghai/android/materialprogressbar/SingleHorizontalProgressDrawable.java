package me.zhanghai.android.materialprogressbar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import me.zhanghai.android.materialprogressbar.internal.ThemeUtils;

class SingleHorizontalProgressDrawable
  extends ProgressDrawableBase
{
  private static final int LEVEL_MAX = 10000;
  private static final float PADDED_INTRINSIC_HEIGHT_DP = 16.0F;
  private static final float PROGRESS_INTRINSIC_HEIGHT_DP = 3.2F;
  private static final RectF RECT_BOUND = new RectF(-180.0F, -1.0F, 180.0F, 1.0F);
  private static final RectF RECT_PADDED_BOUND = new RectF(-180.0F, -5.0F, 180.0F, 5.0F);
  private int mPaddedIntrinsicHeight;
  private int mProgressIntrinsicHeight;
  private boolean mShowTrack = true;
  private float mTrackAlpha;
  
  public SingleHorizontalProgressDrawable(Context paramContext)
  {
    super(paramContext);
    float f = paramContext.getResources().getDisplayMetrics().density;
    this.mProgressIntrinsicHeight = Math.round(3.2F * f);
    this.mPaddedIntrinsicHeight = Math.round(16.0F * f);
    this.mTrackAlpha = ThemeUtils.getFloatFromAttrRes(16842803, paramContext);
  }
  
  private void drawProgressRect(Canvas paramCanvas, Paint paramPaint)
  {
    int i = getLevel();
    if (i == 0) {
      return;
    }
    int j = paramCanvas.save();
    paramCanvas.scale(i / 10000.0F, 1.0F, RECT_BOUND.left, 0.0F);
    paramCanvas.drawRect(RECT_BOUND, paramPaint);
    paramCanvas.restoreToCount(j);
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
      drawProgressRect(paramCanvas, paramPaint);
      return;
      paramCanvas.scale(paramInt1 / RECT_BOUND.width(), paramInt2 / RECT_BOUND.height());
      paramCanvas.translate(RECT_BOUND.width() / 2.0F, RECT_BOUND.height() / 2.0F);
    }
  }
  
  protected boolean onLevelChange(int paramInt)
  {
    invalidateSelf();
    return true;
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
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/me/zhanghai/android/materialprogressbar/SingleHorizontalProgressDrawable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */