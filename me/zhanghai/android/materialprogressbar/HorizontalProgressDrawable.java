package me.zhanghai.android.materialprogressbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import me.zhanghai.android.materialprogressbar.internal.ThemeUtils;

public class HorizontalProgressDrawable
  extends LayerDrawable
  implements IntrinsicPaddingDrawable, ShowTrackDrawable, TintableDrawable
{
  private SingleHorizontalProgressDrawable mProgressDrawable;
  private int mSecondaryAlpha;
  private SingleHorizontalProgressDrawable mSecondaryProgressDrawable;
  private SingleHorizontalProgressDrawable mTrackDrawable;
  
  public HorizontalProgressDrawable(Context paramContext)
  {
    super(new Drawable[] { new SingleHorizontalProgressDrawable(paramContext), new SingleHorizontalProgressDrawable(paramContext), new SingleHorizontalProgressDrawable(paramContext) });
    setId(0, 16908288);
    this.mTrackDrawable = ((SingleHorizontalProgressDrawable)getDrawable(0));
    setId(1, 16908303);
    this.mSecondaryProgressDrawable = ((SingleHorizontalProgressDrawable)getDrawable(1));
    this.mSecondaryAlpha = Math.round(255.0F * ThemeUtils.getFloatFromAttrRes(16842803, paramContext));
    this.mSecondaryProgressDrawable.setAlpha(this.mSecondaryAlpha);
    this.mSecondaryProgressDrawable.setShowTrack(false);
    setId(2, 16908301);
    this.mProgressDrawable = ((SingleHorizontalProgressDrawable)getDrawable(2));
    this.mProgressDrawable.setShowTrack(false);
  }
  
  public boolean getShowTrack()
  {
    return this.mTrackDrawable.getShowTrack();
  }
  
  public boolean getUseIntrinsicPadding()
  {
    return this.mTrackDrawable.getUseIntrinsicPadding();
  }
  
  public void setShowTrack(boolean paramBoolean)
  {
    SingleHorizontalProgressDrawable localSingleHorizontalProgressDrawable;
    if (this.mTrackDrawable.getShowTrack() != paramBoolean)
    {
      this.mTrackDrawable.setShowTrack(paramBoolean);
      localSingleHorizontalProgressDrawable = this.mSecondaryProgressDrawable;
      if (!paramBoolean) {
        break label39;
      }
    }
    label39:
    for (int i = this.mSecondaryAlpha;; i = this.mSecondaryAlpha * 2)
    {
      localSingleHorizontalProgressDrawable.setAlpha(i);
      return;
    }
  }
  
  @SuppressLint({"NewApi"})
  public void setTint(@ColorInt int paramInt)
  {
    this.mTrackDrawable.setTint(paramInt);
    this.mSecondaryProgressDrawable.setTint(paramInt);
    this.mProgressDrawable.setTint(paramInt);
  }
  
  @SuppressLint({"NewApi"})
  public void setTintList(@Nullable ColorStateList paramColorStateList)
  {
    this.mTrackDrawable.setTintList(paramColorStateList);
    this.mSecondaryProgressDrawable.setTintList(paramColorStateList);
    this.mProgressDrawable.setTintList(paramColorStateList);
  }
  
  @SuppressLint({"NewApi"})
  public void setTintMode(@NonNull PorterDuff.Mode paramMode)
  {
    this.mTrackDrawable.setTintMode(paramMode);
    this.mSecondaryProgressDrawable.setTintMode(paramMode);
    this.mProgressDrawable.setTintMode(paramMode);
  }
  
  public void setUseIntrinsicPadding(boolean paramBoolean)
  {
    this.mTrackDrawable.setUseIntrinsicPadding(paramBoolean);
    this.mSecondaryProgressDrawable.setUseIntrinsicPadding(paramBoolean);
    this.mProgressDrawable.setUseIntrinsicPadding(paramBoolean);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/me/zhanghai/android/materialprogressbar/HorizontalProgressDrawable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */