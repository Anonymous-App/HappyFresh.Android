package com.google.android.gms.common.stats;

import com.google.android.gms.internal.zzkf;

public final class zzc
{
  public static zzkf<Boolean> zzacr = zzkf.zzg("gms:common:stats:debug", false);
  public static zzkf<Integer> zzacs = zzkf.zza("gms:common:stats:max_num_of_events", Integer.valueOf(100));
  
  public static final class zza
  {
    public static zzkf<Integer> zzact = zzkf.zza("gms:common:stats:connections:level", Integer.valueOf(zzd.zzacz));
    public static zzkf<String> zzacu = zzkf.zzs("gms:common:stats:connections:ignored_calling_processes", "");
    public static zzkf<String> zzacv = zzkf.zzs("gms:common:stats:connections:ignored_calling_services", "");
    public static zzkf<String> zzacw = zzkf.zzs("gms:common:stats:connections:ignored_target_processes", "");
    public static zzkf<String> zzacx = zzkf.zzs("gms:common:stats:connections:ignored_target_services", "com.google.android.gms.auth.GetToken");
    public static zzkf<Long> zzacy = zzkf.zza("gms:common:stats:connections:time_out_duration", Long.valueOf(600000L));
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/stats/zzc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */