package com.google.android.gms.location.places.personalized;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.location.places.personalized.internal.TestDataImpl;
import java.util.ArrayList;

public class zze
  implements Parcelable.Creator<PlaceUserData>
{
  static void zza(PlaceUserData paramPlaceUserData, Parcel paramParcel, int paramInt)
  {
    paramInt = zzb.zzac(paramParcel);
    zzb.zza(paramParcel, 1, paramPlaceUserData.zzvb(), false);
    zzb.zzc(paramParcel, 1000, paramPlaceUserData.zzCY);
    zzb.zza(paramParcel, 2, paramPlaceUserData.getPlaceId(), false);
    zzb.zzc(paramParcel, 5, paramPlaceUserData.zzve(), false);
    zzb.zzc(paramParcel, 6, paramPlaceUserData.zzvc(), false);
    zzb.zzc(paramParcel, 7, paramPlaceUserData.zzvd(), false);
    zzb.zzH(paramParcel, paramInt);
  }
  
  public PlaceUserData zzeG(Parcel paramParcel)
  {
    ArrayList localArrayList1 = null;
    int j = zza.zzab(paramParcel);
    int i = 0;
    ArrayList localArrayList2 = null;
    ArrayList localArrayList3 = null;
    String str1 = null;
    String str2 = null;
    while (paramParcel.dataPosition() < j)
    {
      int k = zza.zzaa(paramParcel);
      switch (zza.zzbA(k))
      {
      default: 
        zza.zzb(paramParcel, k);
        break;
      case 1: 
        str2 = zza.zzo(paramParcel, k);
        break;
      case 1000: 
        i = zza.zzg(paramParcel, k);
        break;
      case 2: 
        str1 = zza.zzo(paramParcel, k);
        break;
      case 5: 
        localArrayList3 = zza.zzc(paramParcel, k, TestDataImpl.CREATOR);
        break;
      case 6: 
        localArrayList2 = zza.zzc(paramParcel, k, PlaceAlias.CREATOR);
        break;
      case 7: 
        localArrayList1 = zza.zzc(paramParcel, k, HereContent.CREATOR);
      }
    }
    if (paramParcel.dataPosition() != j) {
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    }
    return new PlaceUserData(i, str2, str1, localArrayList3, localArrayList2, localArrayList1);
  }
  
  public PlaceUserData[] zzhg(int paramInt)
  {
    return new PlaceUserData[paramInt];
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/personalized/zze.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */