package com.google.android.gms.internal;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

public final class zzof
  extends zznq<zzof>
{
  public String mCategory;
  public String zzaEH;
  public String zzaET;
  public long zzaEU;
  
  public String getLabel()
  {
    return this.zzaEH;
  }
  
  public long getTimeInMillis()
  {
    return this.zzaEU;
  }
  
  public void setTimeInMillis(long paramLong)
  {
    this.zzaEU = paramLong;
  }
  
  public String toString()
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("variableName", this.zzaET);
    localHashMap.put("timeInMillis", Long.valueOf(this.zzaEU));
    localHashMap.put("category", this.mCategory);
    localHashMap.put("label", this.zzaEH);
    return zzy(localHashMap);
  }
  
  public void zza(zzof paramzzof)
  {
    if (!TextUtils.isEmpty(this.zzaET)) {
      paramzzof.zzdN(this.zzaET);
    }
    if (this.zzaEU != 0L) {
      paramzzof.setTimeInMillis(this.zzaEU);
    }
    if (!TextUtils.isEmpty(this.mCategory)) {
      paramzzof.zzdG(this.mCategory);
    }
    if (!TextUtils.isEmpty(this.zzaEH)) {
      paramzzof.zzdI(this.zzaEH);
    }
  }
  
  public void zzdG(String paramString)
  {
    this.mCategory = paramString;
  }
  
  public void zzdI(String paramString)
  {
    this.zzaEH = paramString;
  }
  
  public void zzdN(String paramString)
  {
    this.zzaET = paramString;
  }
  
  public String zzwJ()
  {
    return this.zzaET;
  }
  
  public String zzwy()
  {
    return this.mCategory;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzof.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */