package me.zhanghai.android.materialprogressbar;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public abstract interface TintableDrawable
{
  public abstract void setTint(@ColorInt int paramInt);
  
  public abstract void setTintList(@Nullable ColorStateList paramColorStateList);
  
  public abstract void setTintMode(@NonNull PorterDuff.Mode paramMode);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/me/zhanghai/android/materialprogressbar/TintableDrawable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */