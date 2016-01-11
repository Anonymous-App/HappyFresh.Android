package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzn
  implements Parcelable.Creator<TileOverlayOptions>
{
  static void zza(TileOverlayOptions paramTileOverlayOptions, Parcel paramParcel, int paramInt)
  {
    paramInt = zzb.zzac(paramParcel);
    zzb.zzc(paramParcel, 1, paramTileOverlayOptions.getVersionCode());
    zzb.zza(paramParcel, 2, paramTileOverlayOptions.zzvL(), false);
    zzb.zza(paramParcel, 3, paramTileOverlayOptions.isVisible());
    zzb.zza(paramParcel, 4, paramTileOverlayOptions.getZIndex());
    zzb.zza(paramParcel, 5, paramTileOverlayOptions.getFadeIn());
    zzb.zzH(paramParcel, paramInt);
  }
  
  public TileOverlayOptions zzeY(Parcel paramParcel)
  {
    boolean bool2 = false;
    int j = zza.zzab(paramParcel);
    IBinder localIBinder = null;
    float f = 0.0F;
    boolean bool1 = true;
    int i = 0;
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
        localIBinder = zza.zzp(paramParcel, k);
        break;
      case 3: 
        bool2 = zza.zzc(paramParcel, k);
        break;
      case 4: 
        f = zza.zzl(paramParcel, k);
        break;
      case 5: 
        bool1 = zza.zzc(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != j) {
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    }
    return new TileOverlayOptions(i, localIBinder, bool2, f, bool1);
  }
  
  public TileOverlayOptions[] zzhy(int paramInt)
  {
    return new TileOverlayOptions[paramInt];
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/maps/model/zzn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */