package com.google.android.gms.playlog.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzc
  implements Parcelable.Creator<LogEvent>
{
  static void zza(LogEvent paramLogEvent, Parcel paramParcel, int paramInt)
  {
    paramInt = zzb.zzac(paramParcel);
    zzb.zzc(paramParcel, 1, paramLogEvent.versionCode);
    zzb.zza(paramParcel, 2, paramLogEvent.zzaGF);
    zzb.zza(paramParcel, 3, paramLogEvent.tag, false);
    zzb.zza(paramParcel, 4, paramLogEvent.zzaGG, false);
    zzb.zza(paramParcel, 5, paramLogEvent.zzaGH, false);
    zzb.zzH(paramParcel, paramInt);
  }
  
  public LogEvent zzfG(Parcel paramParcel)
  {
    Bundle localBundle = null;
    int j = zza.zzab(paramParcel);
    int i = 0;
    long l = 0L;
    byte[] arrayOfByte = null;
    String str = null;
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
        l = zza.zzi(paramParcel, k);
        break;
      case 3: 
        str = zza.zzo(paramParcel, k);
        break;
      case 4: 
        arrayOfByte = zza.zzr(paramParcel, k);
        break;
      case 5: 
        localBundle = zza.zzq(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != j) {
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    }
    return new LogEvent(i, l, str, arrayOfByte, localBundle);
  }
  
  public LogEvent[] zziv(int paramInt)
  {
    return new LogEvent[paramInt];
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/playlog/internal/zzc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */