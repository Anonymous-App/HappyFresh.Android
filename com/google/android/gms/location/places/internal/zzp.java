package com.google.android.gms.location.places.internal;

import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.location.places.zzg;

public class zzp
  implements zzg
{
  private int mIndex;
  private final String zzaAW;
  private final CharSequence zzaAX;
  private final int zzyZ;
  private final int zzza;
  
  public zzp(String paramString, int paramInt1, int paramInt2, CharSequence paramCharSequence, int paramInt3)
  {
    this.zzaAW = paramString;
    this.zzyZ = paramInt1;
    this.zzza = paramInt2;
    this.zzaAX = paramCharSequence;
    this.mIndex = paramInt3;
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {}
    do
    {
      return true;
      if (!(paramObject instanceof zzp)) {
        return false;
      }
      paramObject = (zzp)paramObject;
    } while ((((zzp)paramObject).zzyZ == this.zzyZ) && (((zzp)paramObject).zzza == this.zzza) && (zzt.equal(((zzp)paramObject).zzaAW, this.zzaAW)) && (zzt.equal(((zzp)paramObject).zzaAX, this.zzaAX)));
    return false;
  }
  
  public int hashCode()
  {
    return zzt.hashCode(new Object[] { Integer.valueOf(this.zzyZ), Integer.valueOf(this.zzza), this.zzaAW, this.zzaAX });
  }
  
  public boolean isDataValid()
  {
    return true;
  }
  
  public zzg zzuZ()
  {
    return this;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/internal/zzp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */