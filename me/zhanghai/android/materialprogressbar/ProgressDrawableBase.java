package me.zhanghai.android.materialprogressbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import me.zhanghai.android.materialprogressbar.internal.ThemeUtils;

abstract class ProgressDrawableBase
  extends Drawable
  implements IntrinsicPaddingDrawable, TintableDrawable
{
  protected int mAlpha = 255;
  protected ColorFilter mColorFilter;
  private Paint mPaint;
  protected PorterDuffColorFilter mTintFilter;
  protected ColorStateList mTintList;
  protected PorterDuff.Mode mTintMode = PorterDuff.Mode.SRC_IN;
  protected boolean mUseIntrinsicPadding = true;
  
  public ProgressDrawableBase(Context paramContext)
  {
    setTint(ThemeUtils.getColorFromAttrRes(R.attr.colorControlActivated, paramContext));
  }
  
  private PorterDuffColorFilter makeTintFilter(ColorStateList paramColorStateList, PorterDuff.Mode paramMode)
  {
    if ((paramColorStateList == null) || (paramMode == null)) {
      return null;
    }
    return new PorterDuffColorFilter(paramColorStateList.getColorForState(getState(), 0), paramMode);
  }
  
  private boolean needMirroring()
  {
    return (DrawableCompat.isAutoMirrored(this)) && (DrawableCompat.getLayoutDirection(this) == 1);
  }
  
  public void draw(Canvas paramCanvas)
  {
    Rect localRect = getBounds();
    if ((localRect.width() == 0) || (localRect.height() == 0)) {
      return;
    }
    if (this.mPaint == null)
    {
      this.mPaint = new Paint();
      this.mPaint.setAntiAlias(true);
      this.mPaint.setColor(-16777216);
      onPreparePaint(this.mPaint);
    }
    this.mPaint.setAlpha(this.mAlpha);
    if (this.mColorFilter != null) {}
    for (Object localObject = this.mColorFilter;; localObject = this.mTintFilter)
    {
      this.mPaint.setColorFilter((ColorFilter)localObject);
      int i = paramCanvas.save();
      paramCanvas.translate(localRect.left, localRect.top);
      if (needMirroring())
      {
        paramCanvas.translate(localRect.width(), 0.0F);
        paramCanvas.scale(-1.0F, 1.0F);
      }
      onDraw(paramCanvas, localRect.width(), localRect.height(), this.mPaint);
      paramCanvas.restoreToCount(i);
      return;
    }
  }
  
  public ColorFilter getColorFilter()
  {
    return this.mColorFilter;
  }
  
  public boolean getUseIntrinsicPadding()
  {
    return this.mUseIntrinsicPadding;
  }
  
  protected abstract void onDraw(Canvas paramCanvas, int paramInt1, int paramInt2, Paint paramPaint);
  
  protected abstract void onPreparePaint(Paint paramPaint);
  
  public void setAlpha(int paramInt)
  {
    if (this.mAlpha != paramInt)
    {
      this.mAlpha = paramInt;
      invalidateSelf();
    }
  }
  
  public void setColorFilter(@Nullable ColorFilter paramColorFilter)
  {
    this.mColorFilter = paramColorFilter;
    invalidateSelf();
  }
  
  public void setTint(@ColorInt int paramInt)
  {
    setTintList(ColorStateList.valueOf(paramInt));
  }
  
  public void setTintList(@Nullable ColorStateList paramColorStateList)
  {
    this.mTintList = paramColorStateList;
    this.mTintFilter = makeTintFilter(this.mTintList, this.mTintMode);
    invalidateSelf();
  }
  
  public void setTintMode(@NonNull PorterDuff.Mode paramMode)
  {
    this.mTintMode = paramMode;
    this.mTintFilter = makeTintFilter(this.mTintList, this.mTintMode);
    invalidateSelf();
  }
  
  public void setUseIntrinsicPadding(boolean paramBoolean)
  {
    if (this.mUseIntrinsicPadding != paramBoolean)
    {
      this.mUseIntrinsicPadding = paramBoolean;
      invalidateSelf();
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/me/zhanghai/android/materialprogressbar/ProgressDrawableBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */