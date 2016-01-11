package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class PlacePhotoMetadataResult
  implements Result, SafeParcelable
{
  public static final Parcelable.Creator<PlacePhotoMetadataResult> CREATOR = new zzi();
  final int zzCY;
  private final Status zzOt;
  final DataHolder zzazH;
  private final zzh zzazI;
  
  PlacePhotoMetadataResult(int paramInt, Status paramStatus, DataHolder paramDataHolder)
  {
    this.zzCY = paramInt;
    this.zzOt = paramStatus;
    this.zzazH = paramDataHolder;
    if (paramDataHolder == null)
    {
      this.zzazI = null;
      return;
    }
    this.zzazI = new zzh(this.zzazH);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public Status getStatus()
  {
    return this.zzOt;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzi.zza(this, paramParcel, paramInt);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/PlacePhotoMetadataResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */