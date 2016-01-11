package com.google.android.gms.maps.model.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class MarkerOptionsParcelable
  implements SafeParcelable
{
  public static final zzm CREATOR = new zzm();
  private final int zzCY;
  private BitmapDescriptorParcelable zzaDQ;
  
  public MarkerOptionsParcelable()
  {
    this.zzCY = 1;
  }
  
  MarkerOptionsParcelable(int paramInt, BitmapDescriptorParcelable paramBitmapDescriptorParcelable)
  {
    this.zzCY = paramInt;
    this.zzaDQ = paramBitmapDescriptorParcelable;
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
    zzm.zza(this, paramParcel, paramInt);
  }
  
  public BitmapDescriptorParcelable zzvO()
  {
    return this.zzaDQ;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/maps/model/internal/MarkerOptionsParcelable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */