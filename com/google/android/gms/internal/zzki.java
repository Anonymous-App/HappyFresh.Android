package com.google.android.gms.internal;

import android.graphics.Canvas;
import android.graphics.Path;
import android.net.Uri;
import android.widget.ImageView;

public final class zzki
  extends ImageView
{
  private Uri zzZF;
  private int zzZG;
  private int zzZH;
  private zza zzZI;
  private int zzZJ;
  private float zzZK;
  
  protected void onDraw(Canvas paramCanvas)
  {
    if (this.zzZI != null) {
      paramCanvas.clipPath(this.zzZI.zzk(getWidth(), getHeight()));
    }
    super.onDraw(paramCanvas);
    if (this.zzZH != 0) {
      paramCanvas.drawColor(this.zzZH);
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    switch (this.zzZJ)
    {
    default: 
      return;
    case 1: 
      paramInt1 = getMeasuredHeight();
      paramInt2 = (int)(paramInt1 * this.zzZK);
    }
    for (;;)
    {
      setMeasuredDimension(paramInt2, paramInt1);
      return;
      paramInt2 = getMeasuredWidth();
      paramInt1 = (int)(paramInt2 / this.zzZK);
    }
  }
  
  public void zzbo(int paramInt)
  {
    this.zzZG = paramInt;
  }
  
  public void zzi(Uri paramUri)
  {
    this.zzZF = paramUri;
  }
  
  public int zznr()
  {
    return this.zzZG;
  }
  
  public static abstract interface zza
  {
    public abstract Path zzk(int paramInt1, int paramInt2);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzki.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */