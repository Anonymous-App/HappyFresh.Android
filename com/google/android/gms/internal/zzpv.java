package com.google.android.gms.internal;

public final class zzpv
{
  private final boolean zzaOS;
  private final boolean zzaOT;
  private final String zzaOU;
  private final String zztd;
  
  private zzpv(zza paramzza)
  {
    this.zzaOU = zza.zza(paramzza);
    this.zzaOS = zza.zzb(paramzza);
    this.zzaOT = zza.zzc(paramzza);
    this.zztd = zza.zzd(paramzza);
  }
  
  public String getTrackingId()
  {
    return this.zztd;
  }
  
  public String zzzT()
  {
    return this.zzaOU;
  }
  
  public boolean zzzU()
  {
    return this.zzaOS;
  }
  
  public boolean zzzV()
  {
    return this.zzaOT;
  }
  
  public static final class zza
  {
    private boolean zzaOS = true;
    private boolean zzaOT = false;
    private final String zzaOU;
    private String zztd;
    
    public zza(String paramString)
    {
      this.zzaOU = paramString;
    }
    
    public zza zzap(boolean paramBoolean)
    {
      this.zzaOS = paramBoolean;
      return this;
    }
    
    public zza zzaq(boolean paramBoolean)
    {
      this.zzaOT = paramBoolean;
      return this;
    }
    
    public zza zzeS(String paramString)
    {
      this.zztd = paramString;
      return this;
    }
    
    public zzpv zzzW()
    {
      return new zzpv(this, null);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzpv.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */