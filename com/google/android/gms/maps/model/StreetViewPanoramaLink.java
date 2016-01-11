package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.common.internal.zzt.zza;

public class StreetViewPanoramaLink
  implements SafeParcelable
{
  public static final zzj CREATOR = new zzj();
  public final float bearing;
  public final String panoId;
  private final int zzCY;
  
  StreetViewPanoramaLink(int paramInt, String paramString, float paramFloat)
  {
    this.zzCY = paramInt;
    this.panoId = paramString;
    float f = paramFloat;
    if (paramFloat <= 0.0D) {
      f = paramFloat % 360.0F + 360.0F;
    }
    this.bearing = (f % 360.0F);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    do
    {
      return true;
      if (!(paramObject instanceof StreetViewPanoramaLink)) {
        return false;
      }
      paramObject = (StreetViewPanoramaLink)paramObject;
    } while ((this.panoId.equals(((StreetViewPanoramaLink)paramObject).panoId)) && (Float.floatToIntBits(this.bearing) == Float.floatToIntBits(((StreetViewPanoramaLink)paramObject).bearing)));
    return false;
  }
  
  int getVersionCode()
  {
    return this.zzCY;
  }
  
  public int hashCode()
  {
    return zzt.hashCode(new Object[] { this.panoId, Float.valueOf(this.bearing) });
  }
  
  public String toString()
  {
    return zzt.zzt(this).zzg("panoId", this.panoId).zzg("bearing", Float.valueOf(this.bearing)).toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzj.zza(this, paramParcel, paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/maps/model/StreetViewPanoramaLink.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */