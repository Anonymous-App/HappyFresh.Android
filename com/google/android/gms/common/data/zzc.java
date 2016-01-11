package com.google.android.gms.common.data;

import android.database.CharArrayBuffer;
import android.net.Uri;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.common.internal.zzu;

public abstract class zzc
{
  protected final DataHolder zzWu;
  protected int zzYs;
  private int zzYt;
  
  public zzc(DataHolder paramDataHolder, int paramInt)
  {
    this.zzWu = ((DataHolder)zzu.zzu(paramDataHolder));
    zzbf(paramInt);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if ((paramObject instanceof zzc))
    {
      paramObject = (zzc)paramObject;
      bool1 = bool2;
      if (zzt.equal(Integer.valueOf(((zzc)paramObject).zzYs), Integer.valueOf(this.zzYs)))
      {
        bool1 = bool2;
        if (zzt.equal(Integer.valueOf(((zzc)paramObject).zzYt), Integer.valueOf(this.zzYt)))
        {
          bool1 = bool2;
          if (((zzc)paramObject).zzWu == this.zzWu) {
            bool1 = true;
          }
        }
      }
    }
    return bool1;
  }
  
  protected boolean getBoolean(String paramString)
  {
    return this.zzWu.zze(paramString, this.zzYs, this.zzYt);
  }
  
  protected byte[] getByteArray(String paramString)
  {
    return this.zzWu.zzg(paramString, this.zzYs, this.zzYt);
  }
  
  protected float getFloat(String paramString)
  {
    return this.zzWu.zzf(paramString, this.zzYs, this.zzYt);
  }
  
  protected int getInteger(String paramString)
  {
    return this.zzWu.zzc(paramString, this.zzYs, this.zzYt);
  }
  
  protected long getLong(String paramString)
  {
    return this.zzWu.zzb(paramString, this.zzYs, this.zzYt);
  }
  
  protected String getString(String paramString)
  {
    return this.zzWu.zzd(paramString, this.zzYs, this.zzYt);
  }
  
  public int hashCode()
  {
    return zzt.hashCode(new Object[] { Integer.valueOf(this.zzYs), Integer.valueOf(this.zzYt), this.zzWu });
  }
  
  public boolean isDataValid()
  {
    return !this.zzWu.isClosed();
  }
  
  protected void zza(String paramString, CharArrayBuffer paramCharArrayBuffer)
  {
    this.zzWu.zza(paramString, this.zzYs, this.zzYt, paramCharArrayBuffer);
  }
  
  public boolean zzbV(String paramString)
  {
    return this.zzWu.zzbV(paramString);
  }
  
  protected Uri zzbW(String paramString)
  {
    return this.zzWu.zzh(paramString, this.zzYs, this.zzYt);
  }
  
  protected boolean zzbX(String paramString)
  {
    return this.zzWu.zzi(paramString, this.zzYs, this.zzYt);
  }
  
  protected void zzbf(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < this.zzWu.getCount())) {}
    for (boolean bool = true;; bool = false)
    {
      zzu.zzU(bool);
      this.zzYs = paramInt;
      this.zzYt = this.zzWu.zzbh(this.zzYs);
      return;
    }
  }
  
  protected int zzne()
  {
    return this.zzYs;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/data/zzc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */