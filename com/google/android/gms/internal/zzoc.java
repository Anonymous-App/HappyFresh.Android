package com.google.android.gms.internal;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

public final class zzoc
  extends zznq<zzoc>
{
  public boolean zzaEI;
  public String zzakM;
  
  public String getDescription()
  {
    return this.zzakM;
  }
  
  public void setDescription(String paramString)
  {
    this.zzakM = paramString;
  }
  
  public String toString()
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("description", this.zzakM);
    localHashMap.put("fatal", Boolean.valueOf(this.zzaEI));
    return zzy(localHashMap);
  }
  
  public void zza(zzoc paramzzoc)
  {
    if (!TextUtils.isEmpty(this.zzakM)) {
      paramzzoc.setDescription(this.zzakM);
    }
    if (this.zzaEI) {
      paramzzoc.zzag(this.zzaEI);
    }
  }
  
  public void zzag(boolean paramBoolean)
  {
    this.zzaEI = paramBoolean;
  }
  
  public boolean zzwz()
  {
    return this.zzaEI;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzoc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */