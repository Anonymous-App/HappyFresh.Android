package com.google.android.gms.analytics.internal;

import com.google.android.gms.common.internal.zzd;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.internal.zzkf;

public final class zzy
{
  public static zza<Boolean> zzKZ = zza.zzd("analytics.service_enabled", false);
  public static zza<String> zzLA = zza.zzm("analytics.fallback_responses.k", "404,502");
  public static zza<Integer> zzLB = zza.zze("analytics.batch_retry_interval.seconds.k", 3600);
  public static zza<Long> zzLC = zza.zzc("analytics.service_monitor_interval", 86400000L);
  public static zza<Integer> zzLD = zza.zze("analytics.http_connection.connect_timeout_millis", 60000);
  public static zza<Integer> zzLE = zza.zze("analytics.http_connection.read_timeout_millis", 61000);
  public static zza<Long> zzLF = zza.zzc("analytics.campaigns.time_limit", 86400000L);
  public static zza<String> zzLG = zza.zzm("analytics.first_party_experiment_id", "");
  public static zza<Integer> zzLH = zza.zze("analytics.first_party_experiment_variant", 0);
  public static zza<Boolean> zzLI = zza.zzd("analytics.test.disable_receiver", false);
  public static zza<Long> zzLJ = zza.zza("analytics.service_client.idle_disconnect_millis", 10000L, 10000L);
  public static zza<Long> zzLK = zza.zzc("analytics.service_client.connect_timeout_millis", 5000L);
  public static zza<Long> zzLL = zza.zzc("analytics.service_client.second_connect_delay_millis", 5000L);
  public static zza<Long> zzLM = zza.zzc("analytics.service_client.unexpected_reconnect_millis", 60000L);
  public static zza<Long> zzLN = zza.zzc("analytics.service_client.reconnect_throttle_millis", 1800000L);
  public static zza<Long> zzLO = zza.zzc("analytics.monitoring.sample_period_millis", 86400000L);
  public static zza<Long> zzLP = zza.zzc("analytics.initialization_warning_threshold", 5000L);
  public static zza<Boolean> zzLa = zza.zzd("analytics.service_client_enabled", true);
  public static zza<String> zzLb = zza.zzd("analytics.log_tag", "GAv4", "GAv4-SVC");
  public static zza<Long> zzLc = zza.zzc("analytics.max_tokens", 60L);
  public static zza<Float> zzLd = zza.zza("analytics.tokens_per_sec", 0.5F);
  public static zza<Integer> zzLe = zza.zza("analytics.max_stored_hits", 2000, 20000);
  public static zza<Integer> zzLf = zza.zze("analytics.max_stored_hits_per_app", 2000);
  public static zza<Integer> zzLg = zza.zze("analytics.max_stored_properties_per_app", 100);
  public static zza<Long> zzLh = zza.zza("analytics.local_dispatch_millis", 1800000L, 120000L);
  public static zza<Long> zzLi = zza.zza("analytics.initial_local_dispatch_millis", 5000L, 5000L);
  public static zza<Long> zzLj = zza.zzc("analytics.min_local_dispatch_millis", 120000L);
  public static zza<Long> zzLk = zza.zzc("analytics.max_local_dispatch_millis", 7200000L);
  public static zza<Long> zzLl = zza.zzc("analytics.dispatch_alarm_millis", 7200000L);
  public static zza<Long> zzLm = zza.zzc("analytics.max_dispatch_alarm_millis", 32400000L);
  public static zza<Integer> zzLn = zza.zze("analytics.max_hits_per_dispatch", 20);
  public static zza<Integer> zzLo = zza.zze("analytics.max_hits_per_batch", 20);
  public static zza<String> zzLp = zza.zzm("analytics.insecure_host", "http://www.google-analytics.com");
  public static zza<String> zzLq = zza.zzm("analytics.secure_host", "https://ssl.google-analytics.com");
  public static zza<String> zzLr = zza.zzm("analytics.simple_endpoint", "/collect");
  public static zza<String> zzLs = zza.zzm("analytics.batching_endpoint", "/batch");
  public static zza<Integer> zzLt = zza.zze("analytics.max_get_length", 2036);
  public static zza<String> zzLu = zza.zzd("analytics.batching_strategy.k", zzm.zzKD.name(), zzm.zzKD.name());
  public static zza<String> zzLv = zza.zzm("analytics.compression_strategy.k", zzo.zzKL.name());
  public static zza<Integer> zzLw = zza.zze("analytics.max_hits_per_request.k", 20);
  public static zza<Integer> zzLx = zza.zze("analytics.max_hit_length.k", 8192);
  public static zza<Integer> zzLy = zza.zze("analytics.max_post_length.k", 8192);
  public static zza<Integer> zzLz = zza.zze("analytics.max_batch_post_length", 8192);
  
  public static final class zza<V>
  {
    private final V zzLQ;
    private final zzkf<V> zzLR;
    private V zzLS;
    
    private zza(zzkf<V> paramzzkf, V paramV)
    {
      zzu.zzu(paramzzkf);
      this.zzLR = paramzzkf;
      this.zzLQ = paramV;
    }
    
    static zza<Float> zza(String paramString, float paramFloat)
    {
      return zza(paramString, paramFloat, paramFloat);
    }
    
    static zza<Float> zza(String paramString, float paramFloat1, float paramFloat2)
    {
      return new zza(zzkf.zza(paramString, Float.valueOf(paramFloat2)), Float.valueOf(paramFloat1));
    }
    
    static zza<Integer> zza(String paramString, int paramInt1, int paramInt2)
    {
      return new zza(zzkf.zza(paramString, Integer.valueOf(paramInt2)), Integer.valueOf(paramInt1));
    }
    
    static zza<Long> zza(String paramString, long paramLong1, long paramLong2)
    {
      return new zza(zzkf.zza(paramString, Long.valueOf(paramLong2)), Long.valueOf(paramLong1));
    }
    
    static zza<Boolean> zza(String paramString, boolean paramBoolean1, boolean paramBoolean2)
    {
      return new zza(zzkf.zzg(paramString, paramBoolean2), Boolean.valueOf(paramBoolean1));
    }
    
    static zza<Long> zzc(String paramString, long paramLong)
    {
      return zza(paramString, paramLong, paramLong);
    }
    
    static zza<String> zzd(String paramString1, String paramString2, String paramString3)
    {
      return new zza(zzkf.zzs(paramString1, paramString3), paramString2);
    }
    
    static zza<Boolean> zzd(String paramString, boolean paramBoolean)
    {
      return zza(paramString, paramBoolean, paramBoolean);
    }
    
    static zza<Integer> zze(String paramString, int paramInt)
    {
      return zza(paramString, paramInt, paramInt);
    }
    
    static zza<String> zzm(String paramString1, String paramString2)
    {
      return zzd(paramString1, paramString2, paramString2);
    }
    
    public V get()
    {
      if (this.zzLS != null) {
        return (V)this.zzLS;
      }
      if ((zzd.zzZR) && (zzkf.isInitialized())) {
        return (V)this.zzLR.zzmZ();
      }
      return (V)this.zzLQ;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/analytics/internal/zzy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */