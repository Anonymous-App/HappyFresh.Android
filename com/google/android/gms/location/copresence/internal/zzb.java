package com.google.android.gms.location.copresence.internal;

import android.content.Context;
import com.google.android.gms.location.internal.zzg;
import com.google.android.gms.location.internal.zzn;

public class zzb
{
  private final Context mContext;
  private final String zzOe;
  private final String zzOx;
  private final zzn<zzg> zzayq;
  private final CopresenceApiOptions zzayr;
  private zzc zzays;
  
  private zzb(Context paramContext, String paramString1, String paramString2, zzn<zzg> paramzzn, CopresenceApiOptions paramCopresenceApiOptions)
  {
    this.mContext = paramContext;
    this.zzOx = paramString1;
    this.zzayq = paramzzn;
    this.zzays = null;
    this.zzOe = paramString2;
    this.zzayr = paramCopresenceApiOptions;
  }
  
  public static zzb zza(Context paramContext, String paramString1, String paramString2, zzn<zzg> paramzzn, CopresenceApiOptions paramCopresenceApiOptions)
  {
    return new zzb(paramContext, paramString1, paramString2, paramzzn, paramCopresenceApiOptions);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/copresence/internal/zzb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */