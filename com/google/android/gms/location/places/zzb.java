package com.google.android.gms.location.places;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;

public class zzb
  implements Parcelable.Creator<AddPlaceRequest>
{
  static void zza(AddPlaceRequest paramAddPlaceRequest, Parcel paramParcel, int paramInt)
  {
    int i = com.google.android.gms.common.internal.safeparcel.zzb.zzac(paramParcel);
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 1, paramAddPlaceRequest.getName(), false);
    com.google.android.gms.common.internal.safeparcel.zzb.zzc(paramParcel, 1000, paramAddPlaceRequest.zzCY);
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 2, paramAddPlaceRequest.getLatLng(), paramInt, false);
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 3, paramAddPlaceRequest.getAddress(), false);
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 4, paramAddPlaceRequest.getPlaceTypes(), false);
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 5, paramAddPlaceRequest.getPhoneNumber(), false);
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 6, paramAddPlaceRequest.getWebsiteUri(), paramInt, false);
    com.google.android.gms.common.internal.safeparcel.zzb.zzH(paramParcel, i);
  }
  
  public AddPlaceRequest zzen(Parcel paramParcel)
  {
    Uri localUri = null;
    int j = zza.zzab(paramParcel);
    int i = 0;
    String str1 = null;
    ArrayList localArrayList = null;
    String str2 = null;
    LatLng localLatLng = null;
    String str3 = null;
    while (paramParcel.dataPosition() < j)
    {
      int k = zza.zzaa(paramParcel);
      switch (zza.zzbA(k))
      {
      default: 
        zza.zzb(paramParcel, k);
        break;
      case 1: 
        str3 = zza.zzo(paramParcel, k);
        break;
      case 1000: 
        i = zza.zzg(paramParcel, k);
        break;
      case 2: 
        localLatLng = (LatLng)zza.zza(paramParcel, k, LatLng.CREATOR);
        break;
      case 3: 
        str2 = zza.zzo(paramParcel, k);
        break;
      case 4: 
        localArrayList = zza.zzB(paramParcel, k);
        break;
      case 5: 
        str1 = zza.zzo(paramParcel, k);
        break;
      case 6: 
        localUri = (Uri)zza.zza(paramParcel, k, Uri.CREATOR);
      }
    }
    if (paramParcel.dataPosition() != j) {
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    }
    return new AddPlaceRequest(i, str3, localLatLng, str2, localArrayList, str1, localUri);
  }
  
  public AddPlaceRequest[] zzgJ(int paramInt)
  {
    return new AddPlaceRequest[paramInt];
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/zzb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */