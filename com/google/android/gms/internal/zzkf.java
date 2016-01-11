package com.google.android.gms.internal;

import android.os.Binder;

public abstract class zzkf<T>
{
  private static zza zzYj = null;
  private static int zzYk = 0;
  private static String zzYl = "com.google.android.providers.gsf.permission.READ_GSERVICES";
  private static final Object zzoW = new Object();
  private T zzLS = null;
  protected final String zztw;
  protected final T zztx;
  
  protected zzkf(String paramString, T paramT)
  {
    this.zztw = paramString;
    this.zztx = paramT;
  }
  
  public static boolean isInitialized()
  {
    return zzYj != null;
  }
  
  public static zzkf<Float> zza(String paramString, Float paramFloat)
  {
    new zzkf(paramString, paramFloat)
    {
      protected Float zzbT(String paramAnonymousString)
      {
        return zzkf.zzna().zzb(this.zztw, (Float)this.zztx);
      }
    };
  }
  
  public static zzkf<Integer> zza(String paramString, Integer paramInteger)
  {
    new zzkf(paramString, paramInteger)
    {
      protected Integer zzbS(String paramAnonymousString)
      {
        return zzkf.zzna().zzb(this.zztw, (Integer)this.zztx);
      }
    };
  }
  
  public static zzkf<Long> zza(String paramString, Long paramLong)
  {
    new zzkf(paramString, paramLong)
    {
      protected Long zzbR(String paramAnonymousString)
      {
        return zzkf.zzna().getLong(this.zztw, (Long)this.zztx);
      }
    };
  }
  
  public static zzkf<Boolean> zzg(String paramString, boolean paramBoolean)
  {
    new zzkf(paramString, Boolean.valueOf(paramBoolean))
    {
      protected Boolean zzbQ(String paramAnonymousString)
      {
        return zzkf.zzna().zzb(this.zztw, (Boolean)this.zztx);
      }
    };
  }
  
  public static int zzmY()
  {
    return zzYk;
  }
  
  public static zzkf<String> zzs(String paramString1, String paramString2)
  {
    new zzkf(paramString1, paramString2)
    {
      protected String zzbU(String paramAnonymousString)
      {
        return zzkf.zzna().getString(this.zztw, (String)this.zztx);
      }
    };
  }
  
  public final T get()
  {
    if (this.zzLS != null) {
      return (T)this.zzLS;
    }
    return (T)zzbP(this.zztw);
  }
  
  protected abstract T zzbP(String paramString);
  
  public final T zzmZ()
  {
    long l = Binder.clearCallingIdentity();
    try
    {
      Object localObject1 = get();
      return (T)localObject1;
    }
    finally
    {
      Binder.restoreCallingIdentity(l);
    }
  }
  
  private static abstract interface zza
  {
    public abstract Long getLong(String paramString, Long paramLong);
    
    public abstract String getString(String paramString1, String paramString2);
    
    public abstract Boolean zzb(String paramString, Boolean paramBoolean);
    
    public abstract Float zzb(String paramString, Float paramFloat);
    
    public abstract Integer zzb(String paramString, Integer paramInteger);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzkf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */