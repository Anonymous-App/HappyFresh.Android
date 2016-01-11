package com.google.android.gms.internal;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

public final class zzoe
  extends zznq<zzoe>
{
  public String zzaER;
  public String zzaES;
  public String zzuO;
  
  public String getAction()
  {
    return this.zzuO;
  }
  
  public String getTarget()
  {
    return this.zzaES;
  }
  
  public String toString()
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("network", this.zzaER);
    localHashMap.put("action", this.zzuO);
    localHashMap.put("target", this.zzaES);
    return zzy(localHashMap);
  }
  
  public void zza(zzoe paramzzoe)
  {
    if (!TextUtils.isEmpty(this.zzaER)) {
      paramzzoe.zzdL(this.zzaER);
    }
    if (!TextUtils.isEmpty(this.zzuO)) {
      paramzzoe.zzdH(this.zzuO);
    }
    if (!TextUtils.isEmpty(this.zzaES)) {
      paramzzoe.zzdM(this.zzaES);
    }
  }
  
  public void zzdH(String paramString)
  {
    this.zzuO = paramString;
  }
  
  public void zzdL(String paramString)
  {
    this.zzaER = paramString;
  }
  
  public void zzdM(String paramString)
  {
    this.zzaES = paramString;
  }
  
  public String zzwI()
  {
    return this.zzaER;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzoe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */