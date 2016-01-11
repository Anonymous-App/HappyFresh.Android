package com.google.android.gms.analytics.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.internal.zzlb;
import java.util.UUID;

public class zzai
  extends zzd
{
  private SharedPreferences zzMw;
  private long zzMx;
  private long zzMy = -1L;
  private final zza zzMz = new zza("monitoring", zzhR().zzjC(), null);
  
  protected zzai(zzf paramzzf)
  {
    super(paramzzf);
  }
  
  public void zzbf(String paramString)
  {
    zzhO();
    zzia();
    SharedPreferences.Editor localEditor = this.zzMw.edit();
    if (TextUtils.isEmpty(paramString)) {
      localEditor.remove("installation_campaign");
    }
    for (;;)
    {
      if (!localEditor.commit()) {
        zzaW("Failed to commit campaign data");
      }
      return;
      localEditor.putString("installation_campaign", paramString);
    }
  }
  
  protected void zzhn()
  {
    this.zzMw = getContext().getSharedPreferences("com.google.android.gms.analytics.prefs", 0);
  }
  
  public long zzkk()
  {
    zzhO();
    zzia();
    long l;
    if (this.zzMx == 0L)
    {
      l = this.zzMw.getLong("first_run", 0L);
      if (l == 0L) {
        break label46;
      }
    }
    for (this.zzMx = l;; this.zzMx = l)
    {
      return this.zzMx;
      label46:
      l = zzhP().currentTimeMillis();
      SharedPreferences.Editor localEditor = this.zzMw.edit();
      localEditor.putLong("first_run", l);
      if (!localEditor.commit()) {
        zzaW("Failed to commit first run time");
      }
    }
  }
  
  public zzaj zzkl()
  {
    return new zzaj(zzhP(), zzkk());
  }
  
  public long zzkm()
  {
    zzhO();
    zzia();
    if (this.zzMy == -1L) {
      this.zzMy = this.zzMw.getLong("last_dispatch", 0L);
    }
    return this.zzMy;
  }
  
  public void zzkn()
  {
    zzhO();
    zzia();
    long l = zzhP().currentTimeMillis();
    SharedPreferences.Editor localEditor = this.zzMw.edit();
    localEditor.putLong("last_dispatch", l);
    localEditor.apply();
    this.zzMy = l;
  }
  
  public String zzko()
  {
    zzhO();
    zzia();
    String str = this.zzMw.getString("installation_campaign", null);
    if (TextUtils.isEmpty(str)) {
      return null;
    }
    return str;
  }
  
  public zza zzkp()
  {
    return this.zzMz;
  }
  
  public final class zza
  {
    private final String mName;
    private final long zzMA;
    
    private zza(String paramString, long paramLong)
    {
      zzu.zzcj(paramString);
      if (paramLong > 0L) {}
      for (boolean bool = true;; bool = false)
      {
        zzu.zzV(bool);
        this.mName = paramString;
        this.zzMA = paramLong;
        return;
      }
    }
    
    private void zzkq()
    {
      long l = zzai.this.zzhP().currentTimeMillis();
      SharedPreferences.Editor localEditor = zzai.zza(zzai.this).edit();
      localEditor.remove(zzkv());
      localEditor.remove(zzkw());
      localEditor.putLong(zzku(), l);
      localEditor.commit();
    }
    
    private long zzkr()
    {
      long l = zzkt();
      if (l == 0L) {
        return 0L;
      }
      return Math.abs(l - zzai.this.zzhP().currentTimeMillis());
    }
    
    private long zzkt()
    {
      return zzai.zza(zzai.this).getLong(zzku(), 0L);
    }
    
    private String zzku()
    {
      return this.mName + ":start";
    }
    
    private String zzkv()
    {
      return this.mName + ":count";
    }
    
    public void zzbg(String paramString)
    {
      if (zzkt() == 0L) {
        zzkq();
      }
      String str = paramString;
      if (paramString == null) {
        str = "";
      }
      for (;;)
      {
        try
        {
          long l = zzai.zza(zzai.this).getLong(zzkv(), 0L);
          if (l <= 0L)
          {
            paramString = zzai.zza(zzai.this).edit();
            paramString.putString(zzkw(), str);
            paramString.putLong(zzkv(), 1L);
            paramString.apply();
            return;
          }
          if ((UUID.randomUUID().getLeastSignificantBits() & 0x7FFFFFFFFFFFFFFF) < Long.MAX_VALUE / (l + 1L))
          {
            i = 1;
            paramString = zzai.zza(zzai.this).edit();
            if (i != 0) {
              paramString.putString(zzkw(), str);
            }
            paramString.putLong(zzkv(), l + 1L);
            paramString.apply();
            return;
          }
        }
        finally {}
        int i = 0;
      }
    }
    
    public Pair<String, Long> zzks()
    {
      long l = zzkr();
      if (l < this.zzMA) {}
      String str;
      do
      {
        return null;
        if (l > this.zzMA * 2L)
        {
          zzkq();
          return null;
        }
        str = zzai.zza(zzai.this).getString(zzkw(), null);
        l = zzai.zza(zzai.this).getLong(zzkv(), 0L);
        zzkq();
      } while ((str == null) || (l <= 0L));
      return new Pair(str, Long.valueOf(l));
    }
    
    protected String zzkw()
    {
      return this.mName + ":value";
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/analytics/internal/zzai.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */