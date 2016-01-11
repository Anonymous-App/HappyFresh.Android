package com.google.android.gms.maps.model.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzc
  implements Parcelable.Creator<GroundOverlayOptionsParcelable>
{
  static void zza(GroundOverlayOptionsParcelable paramGroundOverlayOptionsParcelable, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzac(paramParcel);
    zzb.zzc(paramParcel, 1, paramGroundOverlayOptionsParcelable.getVersionCode());
    zzb.zza(paramParcel, 2, paramGroundOverlayOptionsParcelable.zzvM(), paramInt, false);
    zzb.zzH(paramParcel, i);
  }
  
  public GroundOverlayOptionsParcelable zzfc(Parcel paramParcel)
  {
    int j = zza.zzab(paramParcel);
    int i = 0;
    BitmapDescriptorParcelable localBitmapDescriptorParcelable = null;
    while (paramParcel.dataPosition() < j)
    {
      int k = zza.zzaa(paramParcel);
      switch (zza.zzbA(k))
      {
      default: 
        zza.zzb(paramParcel, k);
        break;
      case 1: 
        i = zza.zzg(paramParcel, k);
        break;
      case 2: 
        localBitmapDescriptorParcelable = (BitmapDescriptorParcelable)zza.zza(paramParcel, k, BitmapDescriptorParcelable.CREATOR);
      }
    }
    if (paramParcel.dataPosition() != j) {
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    }
    return new GroundOverlayOptionsParcelable(i, localBitmapDescriptorParcelable);
  }
  
  public GroundOverlayOptionsParcelable[] zzhC(int paramInt)
  {
    return new GroundOverlayOptionsParcelable[paramInt];
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/maps/model/internal/zzc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */