package com.google.android.gms.internal;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

public final class zznz
  extends zznq<zznz>
{
  private String zzRA;
  public int zzaEE;
  public int zzaEF;
  public int zzaEG;
  public int zzyW;
  public int zzyX;
  
  public String getLanguage()
  {
    return this.zzRA;
  }
  
  public void setLanguage(String paramString)
  {
    this.zzRA = paramString;
  }
  
  public String toString()
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("language", this.zzRA);
    localHashMap.put("screenColors", Integer.valueOf(this.zzaEE));
    localHashMap.put("screenWidth", Integer.valueOf(this.zzyW));
    localHashMap.put("screenHeight", Integer.valueOf(this.zzyX));
    localHashMap.put("viewportWidth", Integer.valueOf(this.zzaEF));
    localHashMap.put("viewportHeight", Integer.valueOf(this.zzaEG));
    return zzy(localHashMap);
  }
  
  public void zza(zznz paramzznz)
  {
    if (this.zzaEE != 0) {
      paramzznz.zzhF(this.zzaEE);
    }
    if (this.zzyW != 0) {
      paramzznz.zzhG(this.zzyW);
    }
    if (this.zzyX != 0) {
      paramzznz.zzhH(this.zzyX);
    }
    if (this.zzaEF != 0) {
      paramzznz.zzhI(this.zzaEF);
    }
    if (this.zzaEG != 0) {
      paramzznz.zzhJ(this.zzaEG);
    }
    if (!TextUtils.isEmpty(this.zzRA)) {
      paramzznz.setLanguage(this.zzRA);
    }
  }
  
  public void zzhF(int paramInt)
  {
    this.zzaEE = paramInt;
  }
  
  public void zzhG(int paramInt)
  {
    this.zzyW = paramInt;
  }
  
  public void zzhH(int paramInt)
  {
    this.zzyX = paramInt;
  }
  
  public void zzhI(int paramInt)
  {
    this.zzaEF = paramInt;
  }
  
  public void zzhJ(int paramInt)
  {
    this.zzaEG = paramInt;
  }
  
  public int zzwp()
  {
    return this.zzaEE;
  }
  
  public int zzwq()
  {
    return this.zzyW;
  }
  
  public int zzwr()
  {
    return this.zzyX;
  }
  
  public int zzws()
  {
    return this.zzaEF;
  }
  
  public int zzwt()
  {
    return this.zzaEG;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zznz.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */