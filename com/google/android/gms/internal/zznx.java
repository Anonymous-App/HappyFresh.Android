package com.google.android.gms.internal;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

public final class zznx
  extends zznq<zznx>
{
  private String zzLU;
  private String zzLV;
  private String zzaEw;
  private String zzaEx;
  
  public void setAppId(String paramString)
  {
    this.zzaEw = paramString;
  }
  
  public void setAppInstallerId(String paramString)
  {
    this.zzaEx = paramString;
  }
  
  public void setAppName(String paramString)
  {
    this.zzLU = paramString;
  }
  
  public void setAppVersion(String paramString)
  {
    this.zzLV = paramString;
  }
  
  public String toString()
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("appName", this.zzLU);
    localHashMap.put("appVersion", this.zzLV);
    localHashMap.put("appId", this.zzaEw);
    localHashMap.put("appInstallerId", this.zzaEx);
    return zzy(localHashMap);
  }
  
  public void zza(zznx paramzznx)
  {
    if (!TextUtils.isEmpty(this.zzLU)) {
      paramzznx.setAppName(this.zzLU);
    }
    if (!TextUtils.isEmpty(this.zzLV)) {
      paramzznx.setAppVersion(this.zzLV);
    }
    if (!TextUtils.isEmpty(this.zzaEw)) {
      paramzznx.setAppId(this.zzaEw);
    }
    if (!TextUtils.isEmpty(this.zzaEx)) {
      paramzznx.setAppInstallerId(this.zzaEx);
    }
  }
  
  public String zzjL()
  {
    return this.zzLU;
  }
  
  public String zzjN()
  {
    return this.zzLV;
  }
  
  public String zzsK()
  {
    return this.zzaEw;
  }
  
  public String zzwi()
  {
    return this.zzaEx;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zznx.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */