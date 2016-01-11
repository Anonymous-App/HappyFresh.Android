package com.google.android.gms.playlog.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zze
  implements Parcelable.Creator<PlayLoggerContext>
{
  static void zza(PlayLoggerContext paramPlayLoggerContext, Parcel paramParcel, int paramInt)
  {
    paramInt = zzb.zzac(paramParcel);
    zzb.zzc(paramParcel, 1, paramPlayLoggerContext.versionCode);
    zzb.zza(paramParcel, 2, paramPlayLoggerContext.packageName, false);
    zzb.zzc(paramParcel, 3, paramPlayLoggerContext.zzaGP);
    zzb.zzc(paramParcel, 4, paramPlayLoggerContext.zzaGQ);
    zzb.zza(paramParcel, 5, paramPlayLoggerContext.zzaGR, false);
    zzb.zza(paramParcel, 6, paramPlayLoggerContext.zzaGS, false);
    zzb.zza(paramParcel, 7, paramPlayLoggerContext.zzaGT);
    zzb.zza(paramParcel, 8, paramPlayLoggerContext.zzaGU, false);
    zzb.zza(paramParcel, 9, paramPlayLoggerContext.zzaGV);
    zzb.zzH(paramParcel, paramInt);
  }
  
  public PlayLoggerContext zzfH(Parcel paramParcel)
  {
    String str1 = null;
    boolean bool1 = false;
    int m = zza.zzab(paramParcel);
    boolean bool2 = true;
    String str2 = null;
    String str3 = null;
    int i = 0;
    int j = 0;
    String str4 = null;
    int k = 0;
    while (paramParcel.dataPosition() < m)
    {
      int n = zza.zzaa(paramParcel);
      switch (zza.zzbA(n))
      {
      default: 
        zza.zzb(paramParcel, n);
        break;
      case 1: 
        k = zza.zzg(paramParcel, n);
        break;
      case 2: 
        str4 = zza.zzo(paramParcel, n);
        break;
      case 3: 
        j = zza.zzg(paramParcel, n);
        break;
      case 4: 
        i = zza.zzg(paramParcel, n);
        break;
      case 5: 
        str3 = zza.zzo(paramParcel, n);
        break;
      case 6: 
        str2 = zza.zzo(paramParcel, n);
        break;
      case 7: 
        bool2 = zza.zzc(paramParcel, n);
        break;
      case 8: 
        str1 = zza.zzo(paramParcel, n);
        break;
      case 9: 
        bool1 = zza.zzc(paramParcel, n);
      }
    }
    if (paramParcel.dataPosition() != m) {
      throw new zza.zza("Overread allowed size end=" + m, paramParcel);
    }
    return new PlayLoggerContext(k, str4, j, i, str3, str2, bool2, str1, bool1);
  }
  
  public PlayLoggerContext[] zziw(int paramInt)
  {
    return new PlayLoggerContext[paramInt];
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/playlog/internal/zze.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */