package com.google.android.gms.internal;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.Drawable.ConstantState;
import android.os.SystemClock;

public final class zzkg
  extends Drawable
  implements Drawable.Callback
{
  private int mFrom;
  private long zzKT;
  private int zzZA;
  private boolean zzZh = true;
  private int zzZo = 0;
  private int zzZp;
  private int zzZq = 255;
  private int zzZr;
  private int zzZs = 0;
  private boolean zzZt;
  private zzb zzZu;
  private Drawable zzZv;
  private Drawable zzZw;
  private boolean zzZx;
  private boolean zzZy;
  private boolean zzZz;
  
  public zzkg(Drawable paramDrawable1, Drawable paramDrawable2)
  {
    this(null);
    Object localObject = paramDrawable1;
    if (paramDrawable1 == null) {
      localObject = zza.zznq();
    }
    this.zzZv = ((Drawable)localObject);
    ((Drawable)localObject).setCallback(this);
    paramDrawable1 = this.zzZu;
    paramDrawable1.zzZE |= ((Drawable)localObject).getChangingConfigurations();
    paramDrawable1 = paramDrawable2;
    if (paramDrawable2 == null) {
      paramDrawable1 = zza.zznq();
    }
    this.zzZw = paramDrawable1;
    paramDrawable1.setCallback(this);
    paramDrawable2 = this.zzZu;
    paramDrawable2.zzZE |= paramDrawable1.getChangingConfigurations();
  }
  
  zzkg(zzb paramzzb)
  {
    this.zzZu = new zzb(paramzzb);
  }
  
  public boolean canConstantState()
  {
    if (!this.zzZx) {
      if ((this.zzZv.getConstantState() == null) || (this.zzZw.getConstantState() == null)) {
        break label44;
      }
    }
    label44:
    for (boolean bool = true;; bool = false)
    {
      this.zzZy = bool;
      this.zzZx = true;
      return this.zzZy;
    }
  }
  
  public void draw(Canvas paramCanvas)
  {
    int j = 1;
    int i = 1;
    int k = 0;
    switch (this.zzZo)
    {
    }
    boolean bool;
    Drawable localDrawable1;
    Drawable localDrawable2;
    do
    {
      for (;;)
      {
        j = this.zzZs;
        bool = this.zzZh;
        localDrawable1 = this.zzZv;
        localDrawable2 = this.zzZw;
        if (i == 0) {
          break;
        }
        if ((!bool) || (j == 0)) {
          localDrawable1.draw(paramCanvas);
        }
        if (j == this.zzZq)
        {
          localDrawable2.setAlpha(this.zzZq);
          localDrawable2.draw(paramCanvas);
        }
        return;
        this.zzKT = SystemClock.uptimeMillis();
        this.zzZo = 2;
        i = k;
      }
    } while (this.zzKT < 0L);
    float f1 = (float)(SystemClock.uptimeMillis() - this.zzKT) / this.zzZr;
    if (f1 >= 1.0F) {}
    for (i = j;; i = 0)
    {
      if (i != 0) {
        this.zzZo = 0;
      }
      f1 = Math.min(f1, 1.0F);
      float f2 = this.mFrom;
      this.zzZs = ((int)(f1 * (this.zzZp - this.mFrom) + f2));
      break;
    }
    if (bool) {
      localDrawable1.setAlpha(this.zzZq - j);
    }
    localDrawable1.draw(paramCanvas);
    if (bool) {
      localDrawable1.setAlpha(this.zzZq);
    }
    if (j > 0)
    {
      localDrawable2.setAlpha(j);
      localDrawable2.draw(paramCanvas);
      localDrawable2.setAlpha(this.zzZq);
    }
    invalidateSelf();
  }
  
  public int getChangingConfigurations()
  {
    return super.getChangingConfigurations() | this.zzZu.zzZD | this.zzZu.zzZE;
  }
  
  public Drawable.ConstantState getConstantState()
  {
    if (canConstantState())
    {
      this.zzZu.zzZD = getChangingConfigurations();
      return this.zzZu;
    }
    return null;
  }
  
  public int getIntrinsicHeight()
  {
    return Math.max(this.zzZv.getIntrinsicHeight(), this.zzZw.getIntrinsicHeight());
  }
  
  public int getIntrinsicWidth()
  {
    return Math.max(this.zzZv.getIntrinsicWidth(), this.zzZw.getIntrinsicWidth());
  }
  
  public int getOpacity()
  {
    if (!this.zzZz)
    {
      this.zzZA = Drawable.resolveOpacity(this.zzZv.getOpacity(), this.zzZw.getOpacity());
      this.zzZz = true;
    }
    return this.zzZA;
  }
  
  public void invalidateDrawable(Drawable paramDrawable)
  {
    if (zzlk.zzoR())
    {
      paramDrawable = getCallback();
      if (paramDrawable != null) {
        paramDrawable.invalidateDrawable(this);
      }
    }
  }
  
  public Drawable mutate()
  {
    if ((!this.zzZt) && (super.mutate() == this))
    {
      if (!canConstantState()) {
        throw new IllegalStateException("One or more children of this LayerDrawable does not have constant state; this drawable cannot be mutated.");
      }
      this.zzZv.mutate();
      this.zzZw.mutate();
      this.zzZt = true;
    }
    return this;
  }
  
  protected void onBoundsChange(Rect paramRect)
  {
    this.zzZv.setBounds(paramRect);
    this.zzZw.setBounds(paramRect);
  }
  
  public void scheduleDrawable(Drawable paramDrawable, Runnable paramRunnable, long paramLong)
  {
    if (zzlk.zzoR())
    {
      paramDrawable = getCallback();
      if (paramDrawable != null) {
        paramDrawable.scheduleDrawable(this, paramRunnable, paramLong);
      }
    }
  }
  
  public void setAlpha(int paramInt)
  {
    if (this.zzZs == this.zzZq) {
      this.zzZs = paramInt;
    }
    this.zzZq = paramInt;
    invalidateSelf();
  }
  
  public void setColorFilter(ColorFilter paramColorFilter)
  {
    this.zzZv.setColorFilter(paramColorFilter);
    this.zzZw.setColorFilter(paramColorFilter);
  }
  
  public void startTransition(int paramInt)
  {
    this.mFrom = 0;
    this.zzZp = this.zzZq;
    this.zzZs = 0;
    this.zzZr = paramInt;
    this.zzZo = 1;
    invalidateSelf();
  }
  
  public void unscheduleDrawable(Drawable paramDrawable, Runnable paramRunnable)
  {
    if (zzlk.zzoR())
    {
      paramDrawable = getCallback();
      if (paramDrawable != null) {
        paramDrawable.unscheduleDrawable(this, paramRunnable);
      }
    }
  }
  
  public Drawable zznp()
  {
    return this.zzZw;
  }
  
  private static final class zza
    extends Drawable
  {
    private static final zza zzZB = new zza();
    private static final zza zzZC = new zza(null);
    
    public void draw(Canvas paramCanvas) {}
    
    public Drawable.ConstantState getConstantState()
    {
      return zzZC;
    }
    
    public int getOpacity()
    {
      return -2;
    }
    
    public void setAlpha(int paramInt) {}
    
    public void setColorFilter(ColorFilter paramColorFilter) {}
    
    private static final class zza
      extends Drawable.ConstantState
    {
      public int getChangingConfigurations()
      {
        return 0;
      }
      
      public Drawable newDrawable()
      {
        return zzkg.zza.zznq();
      }
    }
  }
  
  static final class zzb
    extends Drawable.ConstantState
  {
    int zzZD;
    int zzZE;
    
    zzb(zzb paramzzb)
    {
      if (paramzzb != null)
      {
        this.zzZD = paramzzb.zzZD;
        this.zzZE = paramzzb.zzZE;
      }
    }
    
    public int getChangingConfigurations()
    {
      return this.zzZD;
    }
    
    public Drawable newDrawable()
    {
      return new zzkg(this);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzkg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */