package com.google.android.gms.maps.model.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class GroundOverlayOptionsParcelable
  implements SafeParcelable
{
  public static final zzc CREATOR = new zzc();
  private final int zzCY;
  private BitmapDescriptorParcelable zzaDP;
  
  public GroundOverlayOptionsParcelable()
  {
    this.zzCY = 1;
  }
  
  GroundOverlayOptionsParcelable(int paramInt, BitmapDescriptorParcelable paramBitmapDescriptorParcelable)
  {
    this.zzCY = paramInt;
    this.zzaDP = paramBitmapDescriptorParcelable;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  int getVersionCode()
  {
    return this.zzCY;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzc.zza(this, paramParcel, paramInt);
  }
  
  public BitmapDescriptorParcelable zzvM()
  {
    return this.zzaDP;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/maps/model/internal/GroundOverlayOptionsParcelable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */