package com.google.android.gms.maps.model.internal;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zza
  implements Parcelable.Creator<BitmapDescriptorParcelable>
{
  static void zza(BitmapDescriptorParcelable paramBitmapDescriptorParcelable, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzac(paramParcel);
    zzb.zzc(paramParcel, 1, paramBitmapDescriptorParcelable.getVersionCode());
    zzb.zza(paramParcel, 2, paramBitmapDescriptorParcelable.getType());
    zzb.zza(paramParcel, 3, paramBitmapDescriptorParcelable.getParameters(), false);
    zzb.zza(paramParcel, 4, paramBitmapDescriptorParcelable.getBitmap(), paramInt, false);
    zzb.zzH(paramParcel, i);
  }
  
  public BitmapDescriptorParcelable zzfa(Parcel paramParcel)
  {
    Bitmap localBitmap = null;
    byte b = 0;
    int j = com.google.android.gms.common.internal.safeparcel.zza.zzab(paramParcel);
    Bundle localBundle = null;
    int i = 0;
    while (paramParcel.dataPosition() < j)
    {
      int k = com.google.android.gms.common.internal.safeparcel.zza.zzaa(paramParcel);
      switch (com.google.android.gms.common.internal.safeparcel.zza.zzbA(k))
      {
      default: 
        com.google.android.gms.common.internal.safeparcel.zza.zzb(paramParcel, k);
        break;
      case 1: 
        i = com.google.android.gms.common.internal.safeparcel.zza.zzg(paramParcel, k);
        break;
      case 2: 
        b = com.google.android.gms.common.internal.safeparcel.zza.zze(paramParcel, k);
        break;
      case 3: 
        localBundle = com.google.android.gms.common.internal.safeparcel.zza.zzq(paramParcel, k);
        break;
      case 4: 
        localBitmap = (Bitmap)com.google.android.gms.common.internal.safeparcel.zza.zza(paramParcel, k, Bitmap.CREATOR);
      }
    }
    if (paramParcel.dataPosition() != j) {
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    }
    return new BitmapDescriptorParcelable(i, b, localBundle, localBitmap);
  }
  
  public BitmapDescriptorParcelable[] zzhA(int paramInt)
  {
    return new BitmapDescriptorParcelable[paramInt];
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/maps/model/internal/zza.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */