package com.google.android.gms.common;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.common.internal.zzy;
import com.google.android.gms.dynamic.zzg.zza;

public final class SignInButton
  extends FrameLayout
  implements View.OnClickListener
{
  public static final int COLOR_DARK = 0;
  public static final int COLOR_LIGHT = 1;
  public static final int SIZE_ICON_ONLY = 2;
  public static final int SIZE_STANDARD = 0;
  public static final int SIZE_WIDE = 1;
  private int mColor;
  private int mSize;
  private View zzVZ;
  private View.OnClickListener zzWa = null;
  
  public SignInButton(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public SignInButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public SignInButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setStyle(0, 0);
  }
  
  private static Button zza(Context paramContext, int paramInt1, int paramInt2)
  {
    zzy localzzy = new zzy(paramContext);
    localzzy.zza(paramContext.getResources(), paramInt1, paramInt2);
    return localzzy;
  }
  
  private void zzaf(Context paramContext)
  {
    if (this.zzVZ != null) {
      removeView(this.zzVZ);
    }
    try
    {
      this.zzVZ = zzx.zzb(paramContext, this.mSize, this.mColor);
      addView(this.zzVZ);
      this.zzVZ.setEnabled(isEnabled());
      this.zzVZ.setOnClickListener(this);
      return;
    }
    catch (zzg.zza localzza)
    {
      for (;;)
      {
        Log.w("SignInButton", "Sign in button not found, using placeholder instead");
        this.zzVZ = zza(paramContext, this.mSize, this.mColor);
      }
    }
  }
  
  public void onClick(View paramView)
  {
    if ((this.zzWa != null) && (paramView == this.zzVZ)) {
      this.zzWa.onClick(this);
    }
  }
  
  public void setColorScheme(int paramInt)
  {
    setStyle(this.mSize, paramInt);
  }
  
  public void setEnabled(boolean paramBoolean)
  {
    super.setEnabled(paramBoolean);
    this.zzVZ.setEnabled(paramBoolean);
  }
  
  public void setOnClickListener(View.OnClickListener paramOnClickListener)
  {
    this.zzWa = paramOnClickListener;
    if (this.zzVZ != null) {
      this.zzVZ.setOnClickListener(this);
    }
  }
  
  public void setSize(int paramInt)
  {
    setStyle(paramInt, this.mColor);
  }
  
  public void setStyle(int paramInt1, int paramInt2)
  {
    if ((paramInt1 >= 0) && (paramInt1 < 3))
    {
      bool = true;
      zzu.zza(bool, "Unknown button size %d", new Object[] { Integer.valueOf(paramInt1) });
      if ((paramInt2 < 0) || (paramInt2 >= 2)) {
        break label80;
      }
    }
    label80:
    for (boolean bool = true;; bool = false)
    {
      zzu.zza(bool, "Unknown color scheme %s", new Object[] { Integer.valueOf(paramInt2) });
      this.mSize = paramInt1;
      this.mColor = paramInt2;
      zzaf(getContext());
      return;
      bool = false;
      break;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/SignInButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */