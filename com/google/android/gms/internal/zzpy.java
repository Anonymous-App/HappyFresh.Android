package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzu;

public class zzpy
{
  private final String zzaKy;
  private final String zzaMW;
  private final Integer zzaPb;
  private final String zzaPc;
  private final boolean zzaPd;
  
  public zzpy(String paramString1, Integer paramInteger, String paramString2, boolean paramBoolean)
  {
    this(paramString1, paramInteger, paramString2, paramBoolean, "");
  }
  
  public zzpy(String paramString1, Integer paramInteger, String paramString2, boolean paramBoolean, String paramString3)
  {
    zzu.zzu(paramString1);
    zzu.zzu(paramString3);
    this.zzaKy = paramString1;
    this.zzaPb = paramInteger;
    this.zzaPc = paramString2;
    this.zzaPd = paramBoolean;
    this.zzaMW = paramString3;
  }
  
  public String getContainerId()
  {
    return this.zzaKy;
  }
  
  public String zzAa()
  {
    return this.zzaPc;
  }
  
  public String zzAb()
  {
    if (this.zzaPc != null) {
      return this.zzaPc + "_" + this.zzaKy;
    }
    return this.zzaKy;
  }
  
  public boolean zzAc()
  {
    return this.zzaPd;
  }
  
  public String zzAd()
  {
    return this.zzaMW;
  }
  
  public Integer zzzZ()
  {
    return this.zzaPb;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzpy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */