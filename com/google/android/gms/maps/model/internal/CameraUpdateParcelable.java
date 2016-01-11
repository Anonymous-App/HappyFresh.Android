package com.google.android.gms.maps.model.internal;

import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class CameraUpdateParcelable
  implements SafeParcelable
{
  public static final zzb CREATOR = new zzb();
  private int type;
  private final int zzCY;
  private Bundle zzaDN;
  
  CameraUpdateParcelable(int paramInt1, int paramInt2, Bundle paramBundle)
  {
    this.zzCY = paramInt1;
    this.type = paramInt2;
    this.zzaDN = paramBundle;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public Bundle getParameters()
  {
    return this.zzaDN;
  }
  
  public int getType()
  {
    return this.type;
  }
  
  public int getVersionCode()
  {
    return this.zzCY;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzb.zza(this, paramParcel, paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/maps/model/internal/CameraUpdateParcelable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */