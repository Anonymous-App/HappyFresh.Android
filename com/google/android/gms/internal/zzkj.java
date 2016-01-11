package com.google.android.gms.internal;

import android.graphics.drawable.Drawable;
import com.google.android.gms.common.internal.zzt;

public final class zzkj
  extends zzku<zza, Drawable>
{
  public zzkj()
  {
    super(10);
  }
  
  public static final class zza
  {
    public final int zzZL;
    public final int zzZM;
    
    public zza(int paramInt1, int paramInt2)
    {
      this.zzZL = paramInt1;
      this.zzZM = paramInt2;
    }
    
    public boolean equals(Object paramObject)
    {
      boolean bool2 = true;
      boolean bool1;
      if (!(paramObject instanceof zza)) {
        bool1 = false;
      }
      do
      {
        do
        {
          return bool1;
          bool1 = bool2;
        } while (this == paramObject);
        paramObject = (zza)paramObject;
        if (((zza)paramObject).zzZL != this.zzZL) {
          break;
        }
        bool1 = bool2;
      } while (((zza)paramObject).zzZM == this.zzZM);
      return false;
    }
    
    public int hashCode()
    {
      return zzt.hashCode(new Object[] { Integer.valueOf(this.zzZL), Integer.valueOf(this.zzZM) });
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzkj.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */