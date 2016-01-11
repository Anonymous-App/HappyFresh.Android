package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzk
  implements Parcelable.Creator<PlaceReport>
{
  static void zza(PlaceReport paramPlaceReport, Parcel paramParcel, int paramInt)
  {
    paramInt = zzb.zzac(paramParcel);
    zzb.zzc(paramParcel, 1, paramPlaceReport.zzCY);
    zzb.zza(paramParcel, 2, paramPlaceReport.getPlaceId(), false);
    zzb.zza(paramParcel, 3, paramPlaceReport.getTag(), false);
    zzb.zza(paramParcel, 4, paramPlaceReport.getSource(), false);
    zzb.zzH(paramParcel, paramInt);
  }
  
  public PlaceReport zzeu(Parcel paramParcel)
  {
    String str3 = null;
    int j = zza.zzab(paramParcel);
    int i = 0;
    String str2 = null;
    String str1 = null;
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
        str1 = zza.zzo(paramParcel, k);
        break;
      case 3: 
        str2 = zza.zzo(paramParcel, k);
        break;
      case 4: 
        str3 = zza.zzo(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != j) {
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    }
    return new PlaceReport(i, str1, str2, str3);
  }
  
  public PlaceReport[] zzgS(int paramInt)
  {
    return new PlaceReport[paramInt];
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/zzk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */