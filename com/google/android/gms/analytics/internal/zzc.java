package com.google.android.gms.analytics.internal;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.internal.zzlb;
import com.google.android.gms.internal.zzns;

public class zzc
{
  private final zzf zzJy;
  
  protected zzc(zzf paramzzf)
  {
    com.google.android.gms.common.internal.zzu.zzu(paramzzf);
    this.zzJy = paramzzf;
  }
  
  private void zza(int paramInt, String paramString, Object paramObject1, Object paramObject2, Object paramObject3)
  {
    Object localObject = null;
    if (this.zzJy != null) {
      localObject = this.zzJy.zzid();
    }
    if (localObject != null) {
      ((zzaf)localObject).zza(paramInt, paramString, paramObject1, paramObject2, paramObject3);
    }
    do
    {
      return;
      localObject = (String)zzy.zzLb.get();
    } while (!Log.isLoggable((String)localObject, paramInt));
    Log.println(paramInt, (String)localObject, zzc(paramString, paramObject1, paramObject2, paramObject3));
  }
  
  protected static String zzc(String paramString, Object paramObject1, Object paramObject2, Object paramObject3)
  {
    String str1 = paramString;
    if (paramString == null) {
      str1 = "";
    }
    String str2 = zzi(paramObject1);
    paramObject2 = zzi(paramObject2);
    paramObject3 = zzi(paramObject3);
    StringBuilder localStringBuilder = new StringBuilder();
    paramString = "";
    if (!TextUtils.isEmpty(str1))
    {
      localStringBuilder.append(str1);
      paramString = ": ";
    }
    paramObject1 = paramString;
    if (!TextUtils.isEmpty(str2))
    {
      localStringBuilder.append(paramString);
      localStringBuilder.append(str2);
      paramObject1 = ", ";
    }
    paramString = (String)paramObject1;
    if (!TextUtils.isEmpty((CharSequence)paramObject2))
    {
      localStringBuilder.append((String)paramObject1);
      localStringBuilder.append((String)paramObject2);
      paramString = ", ";
    }
    if (!TextUtils.isEmpty((CharSequence)paramObject3))
    {
      localStringBuilder.append(paramString);
      localStringBuilder.append((String)paramObject3);
    }
    return localStringBuilder.toString();
  }
  
  private static String zzi(Object paramObject)
  {
    if (paramObject == null) {
      return "";
    }
    if ((paramObject instanceof String)) {
      return (String)paramObject;
    }
    if ((paramObject instanceof Boolean))
    {
      if (paramObject == Boolean.TRUE) {}
      for (paramObject = "true";; paramObject = "false") {
        return (String)paramObject;
      }
    }
    if ((paramObject instanceof Throwable)) {
      return ((Throwable)paramObject).toString();
    }
    return paramObject.toString();
  }
  
  protected Context getContext()
  {
    return this.zzJy.getContext();
  }
  
  public void zza(String paramString, Object paramObject)
  {
    zza(2, paramString, paramObject, null, null);
  }
  
  public void zza(String paramString, Object paramObject1, Object paramObject2)
  {
    zza(2, paramString, paramObject1, paramObject2, null);
  }
  
  public void zza(String paramString, Object paramObject1, Object paramObject2, Object paramObject3)
  {
    zza(3, paramString, paramObject1, paramObject2, paramObject3);
  }
  
  public void zzaT(String paramString)
  {
    zza(2, paramString, null, null, null);
  }
  
  public void zzaU(String paramString)
  {
    zza(3, paramString, null, null, null);
  }
  
  public void zzaV(String paramString)
  {
    zza(4, paramString, null, null, null);
  }
  
  public void zzaW(String paramString)
  {
    zza(5, paramString, null, null, null);
  }
  
  public void zzaX(String paramString)
  {
    zza(6, paramString, null, null, null);
  }
  
  public void zzb(String paramString, Object paramObject)
  {
    zza(3, paramString, paramObject, null, null);
  }
  
  public void zzb(String paramString, Object paramObject1, Object paramObject2)
  {
    zza(3, paramString, paramObject1, paramObject2, null);
  }
  
  public void zzb(String paramString, Object paramObject1, Object paramObject2, Object paramObject3)
  {
    zza(5, paramString, paramObject1, paramObject2, paramObject3);
  }
  
  public void zzc(String paramString, Object paramObject)
  {
    zza(4, paramString, paramObject, null, null);
  }
  
  public void zzc(String paramString, Object paramObject1, Object paramObject2)
  {
    zza(5, paramString, paramObject1, paramObject2, null);
  }
  
  public void zzd(String paramString, Object paramObject)
  {
    zza(5, paramString, paramObject, null, null);
  }
  
  public void zzd(String paramString, Object paramObject1, Object paramObject2)
  {
    zza(6, paramString, paramObject1, paramObject2, null);
  }
  
  public void zze(String paramString, Object paramObject)
  {
    zza(6, paramString, paramObject, null, null);
  }
  
  public zzf zzhM()
  {
    return this.zzJy;
  }
  
  protected void zzhN()
  {
    if (zzhR().zziW()) {
      throw new IllegalStateException("Call only supported on the client side");
    }
  }
  
  protected void zzhO()
  {
    this.zzJy.zzhO();
  }
  
  protected zzlb zzhP()
  {
    return this.zzJy.zzhP();
  }
  
  protected zzaf zzhQ()
  {
    return this.zzJy.zzhQ();
  }
  
  protected zzr zzhR()
  {
    return this.zzJy.zzhR();
  }
  
  protected zzns zzhS()
  {
    return this.zzJy.zzhS();
  }
  
  protected zzv zzhT()
  {
    return this.zzJy.zzhT();
  }
  
  protected zzai zzhU()
  {
    return this.zzJy.zzhU();
  }
  
  protected zzn zzhV()
  {
    return this.zzJy.zzih();
  }
  
  protected zza zzhW()
  {
    return this.zzJy.zzig();
  }
  
  protected zzk zzhX()
  {
    return this.zzJy.zzhX();
  }
  
  protected zzu zzhY()
  {
    return this.zzJy.zzhY();
  }
  
  public boolean zzhZ()
  {
    return Log.isLoggable((String)zzy.zzLb.get(), 2);
  }
  
  public GoogleAnalytics zzhg()
  {
    return this.zzJy.zzie();
  }
  
  protected zzb zzhl()
  {
    return this.zzJy.zzhl();
  }
  
  protected zzan zzhm()
  {
    return this.zzJy.zzhm();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/analytics/internal/zzc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */