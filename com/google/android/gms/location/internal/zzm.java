package com.google.android.gms.location.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzm
  implements Parcelable.Creator<ParcelableGeofence>
{
  static void zza(ParcelableGeofence paramParcelableGeofence, Parcel paramParcel, int paramInt)
  {
    paramInt = zzb.zzac(paramParcel);
    zzb.zza(paramParcel, 1, paramParcelableGeofence.getRequestId(), false);
    zzb.zzc(paramParcel, 1000, paramParcelableGeofence.getVersionCode());
    zzb.zza(paramParcel, 2, paramParcelableGeofence.getExpirationTime());
    zzb.zza(paramParcel, 3, paramParcelableGeofence.zzuA());
    zzb.zza(paramParcel, 4, paramParcelableGeofence.getLatitude());
    zzb.zza(paramParcel, 5, paramParcelableGeofence.getLongitude());
    zzb.zza(paramParcel, 6, paramParcelableGeofence.zzuB());
    zzb.zzc(paramParcel, 7, paramParcelableGeofence.zzuC());
    zzb.zzc(paramParcel, 8, paramParcelableGeofence.getNotificationResponsiveness());
    zzb.zzc(paramParcel, 9, paramParcelableGeofence.zzuD());
    zzb.zzH(paramParcel, paramInt);
  }
  
  public ParcelableGeofence zzem(Parcel paramParcel)
  {
    int n = zza.zzab(paramParcel);
    int m = 0;
    String str = null;
    int k = 0;
    short s = 0;
    double d2 = 0.0D;
    double d1 = 0.0D;
    float f = 0.0F;
    long l = 0L;
    int j = 0;
    int i = -1;
    while (paramParcel.dataPosition() < n)
    {
      int i1 = zza.zzaa(paramParcel);
      switch (zza.zzbA(i1))
      {
      default: 
        zza.zzb(paramParcel, i1);
        break;
      case 1: 
        str = zza.zzo(paramParcel, i1);
        break;
      case 1000: 
        m = zza.zzg(paramParcel, i1);
        break;
      case 2: 
        l = zza.zzi(paramParcel, i1);
        break;
      case 3: 
        s = zza.zzf(paramParcel, i1);
        break;
      case 4: 
        d2 = zza.zzm(paramParcel, i1);
        break;
      case 5: 
        d1 = zza.zzm(paramParcel, i1);
        break;
      case 6: 
        f = zza.zzl(paramParcel, i1);
        break;
      case 7: 
        k = zza.zzg(paramParcel, i1);
        break;
      case 8: 
        j = zza.zzg(paramParcel, i1);
        break;
      case 9: 
        i = zza.zzg(paramParcel, i1);
      }
    }
    if (paramParcel.dataPosition() != n) {
      throw new zza.zza("Overread allowed size end=" + n, paramParcel);
    }
    return new ParcelableGeofence(m, str, k, s, d2, d1, f, l, j, i);
  }
  
  public ParcelableGeofence[] zzgI(int paramInt)
  {
    return new ParcelableGeofence[paramInt];
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/internal/zzm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */