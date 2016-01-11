package com.google.android.gms.location.places.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzs
  implements Parcelable.Creator<PlacesParams>
{
  static void zza(PlacesParams paramPlacesParams, Parcel paramParcel, int paramInt)
  {
    paramInt = zzb.zzac(paramParcel);
    zzb.zza(paramParcel, 1, paramPlacesParams.zzaAZ, false);
    zzb.zzc(paramParcel, 1000, paramPlacesParams.versionCode);
    zzb.zza(paramParcel, 2, paramPlacesParams.zzaBa, false);
    zzb.zza(paramParcel, 3, paramPlacesParams.zzaBb, false);
    zzb.zza(paramParcel, 4, paramPlacesParams.zzazX, false);
    zzb.zza(paramParcel, 5, paramPlacesParams.zzaBc, false);
    zzb.zzc(paramParcel, 6, paramPlacesParams.zzaBd);
    zzb.zzH(paramParcel, paramInt);
  }
  
  public PlacesParams zzeB(Parcel paramParcel)
  {
    int i = 0;
    String str1 = null;
    int k = zza.zzab(paramParcel);
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    int j = 0;
    while (paramParcel.dataPosition() < k)
    {
      int m = zza.zzaa(paramParcel);
      switch (zza.zzbA(m))
      {
      default: 
        zza.zzb(paramParcel, m);
        break;
      case 1: 
        str5 = zza.zzo(paramParcel, m);
        break;
      case 1000: 
        j = zza.zzg(paramParcel, m);
        break;
      case 2: 
        str4 = zza.zzo(paramParcel, m);
        break;
      case 3: 
        str3 = zza.zzo(paramParcel, m);
        break;
      case 4: 
        str2 = zza.zzo(paramParcel, m);
        break;
      case 5: 
        str1 = zza.zzo(paramParcel, m);
        break;
      case 6: 
        i = zza.zzg(paramParcel, m);
      }
    }
    if (paramParcel.dataPosition() != k) {
      throw new zza.zza("Overread allowed size end=" + k, paramParcel);
    }
    return new PlacesParams(j, str5, str4, str3, str2, str1, i);
  }
  
  public PlacesParams[] zzhb(int paramInt)
  {
    return new PlacesParams[paramInt];
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/internal/zzs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */