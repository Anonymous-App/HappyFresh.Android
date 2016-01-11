package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzi
  implements Parcelable.Creator<PlacePhotoMetadataResult>
{
  static void zza(PlacePhotoMetadataResult paramPlacePhotoMetadataResult, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzac(paramParcel);
    zzb.zza(paramParcel, 1, paramPlacePhotoMetadataResult.getStatus(), paramInt, false);
    zzb.zzc(paramParcel, 1000, paramPlacePhotoMetadataResult.zzCY);
    zzb.zza(paramParcel, 2, paramPlacePhotoMetadataResult.zzazH, paramInt, false);
    zzb.zzH(paramParcel, i);
  }
  
  public PlacePhotoMetadataResult zzes(Parcel paramParcel)
  {
    DataHolder localDataHolder = null;
    int j = zza.zzab(paramParcel);
    int i = 0;
    Status localStatus = null;
    if (paramParcel.dataPosition() < j)
    {
      int k = zza.zzaa(paramParcel);
      switch (zza.zzbA(k))
      {
      default: 
        zza.zzb(paramParcel, k);
      }
      for (;;)
      {
        break;
        localStatus = (Status)zza.zza(paramParcel, k, Status.CREATOR);
        continue;
        i = zza.zzg(paramParcel, k);
        continue;
        localDataHolder = (DataHolder)zza.zza(paramParcel, k, DataHolder.CREATOR);
      }
    }
    if (paramParcel.dataPosition() != j) {
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    }
    return new PlacePhotoMetadataResult(i, localStatus, localDataHolder);
  }
  
  public PlacePhotoMetadataResult[] zzgQ(int paramInt)
  {
    return new PlacePhotoMetadataResult[paramInt];
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/zzi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */