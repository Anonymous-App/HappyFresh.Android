package com.google.android.gms.analytics.internal;

public class zzz
  extends zzq<zzaa>
{
  public zzz(zzf paramzzf)
  {
    super(paramzzf, new zza(paramzzf));
  }
  
  private static class zza
    implements zzq.zza<zzaa>
  {
    private final zzf zzJy;
    private final zzaa zzLT;
    
    public zza(zzf paramzzf)
    {
      this.zzJy = paramzzf;
      this.zzLT = new zzaa();
    }
    
    public void zzc(String paramString, boolean paramBoolean)
    {
      if ("ga_dryRun".equals(paramString))
      {
        paramString = this.zzLT;
        if (paramBoolean) {}
        for (int i = 1;; i = 0)
        {
          paramString.zzLY = i;
          return;
        }
      }
      this.zzJy.zzhQ().zzd("Bool xml configuration name not recognized", paramString);
    }
    
    public void zzd(String paramString, int paramInt)
    {
      if ("ga_dispatchPeriod".equals(paramString))
      {
        this.zzLT.zzLX = paramInt;
        return;
      }
      this.zzJy.zzhQ().zzd("Int xml configuration name not recognized", paramString);
    }
    
    public zzaa zzjJ()
    {
      return this.zzLT;
    }
    
    public void zzk(String paramString1, String paramString2) {}
    
    public void zzl(String paramString1, String paramString2)
    {
      if ("ga_appName".equals(paramString1))
      {
        this.zzLT.zzLU = paramString2;
        return;
      }
      if ("ga_appVersion".equals(paramString1))
      {
        this.zzLT.zzLV = paramString2;
        return;
      }
      if ("ga_logLevel".equals(paramString1))
      {
        this.zzLT.zzLW = paramString2;
        return;
      }
      this.zzJy.zzhQ().zzd("String xml configuration name not recognized", paramString1);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/analytics/internal/zzz.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */