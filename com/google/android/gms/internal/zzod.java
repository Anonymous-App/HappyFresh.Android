package com.google.android.gms.internal;

import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.zzu;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class zzod
  extends zznq<zzod>
{
  private String zzaEJ;
  private int zzaEK;
  private int zzaEL;
  private String zzaEM;
  private String zzaEN;
  private boolean zzaEO;
  private boolean zzaEP;
  private boolean zzaEQ;
  
  public zzod()
  {
    this(false);
  }
  
  public zzod(boolean paramBoolean)
  {
    this(paramBoolean, zzwA());
  }
  
  public zzod(boolean paramBoolean, int paramInt)
  {
    zzu.zzbw(paramInt);
    this.zzaEK = paramInt;
    this.zzaEP = paramBoolean;
  }
  
  static int zzwA()
  {
    UUID localUUID = UUID.randomUUID();
    int i = (int)(localUUID.getLeastSignificantBits() & 0x7FFFFFFF);
    if (i != 0) {}
    int j;
    do
    {
      return i;
      j = (int)(localUUID.getMostSignificantBits() & 0x7FFFFFFF);
      i = j;
    } while (j != 0);
    Log.e("GAv4", "UUID.randomUUID() returned 0.");
    return Integer.MAX_VALUE;
  }
  
  private void zzwH()
  {
    if (this.zzaEQ) {
      throw new IllegalStateException("ScreenViewInfo is immutable");
    }
  }
  
  public boolean isMutable()
  {
    return !this.zzaEQ;
  }
  
  public void setScreenName(String paramString)
  {
    zzwH();
    this.zzaEJ = paramString;
  }
  
  public String toString()
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("screenName", this.zzaEJ);
    localHashMap.put("interstitial", Boolean.valueOf(this.zzaEO));
    localHashMap.put("automatic", Boolean.valueOf(this.zzaEP));
    localHashMap.put("screenId", Integer.valueOf(this.zzaEK));
    localHashMap.put("referrerScreenId", Integer.valueOf(this.zzaEL));
    localHashMap.put("referrerScreenName", this.zzaEM);
    localHashMap.put("referrerUri", this.zzaEN);
    return zzy(localHashMap);
  }
  
  public void zzah(boolean paramBoolean)
  {
    zzwH();
    this.zzaEP = paramBoolean;
  }
  
  public void zzai(boolean paramBoolean)
  {
    zzwH();
    this.zzaEO = paramBoolean;
  }
  
  public int zzbn()
  {
    return this.zzaEK;
  }
  
  public void zzc(zzod paramzzod)
  {
    if (!TextUtils.isEmpty(this.zzaEJ)) {
      paramzzod.setScreenName(this.zzaEJ);
    }
    if (this.zzaEK != 0) {
      paramzzod.zzhK(this.zzaEK);
    }
    if (this.zzaEL != 0) {
      paramzzod.zzhL(this.zzaEL);
    }
    if (!TextUtils.isEmpty(this.zzaEM)) {
      paramzzod.zzdJ(this.zzaEM);
    }
    if (!TextUtils.isEmpty(this.zzaEN)) {
      paramzzod.zzdK(this.zzaEN);
    }
    if (this.zzaEO) {
      paramzzod.zzai(this.zzaEO);
    }
    if (this.zzaEP) {
      paramzzod.zzah(this.zzaEP);
    }
  }
  
  public void zzdJ(String paramString)
  {
    zzwH();
    this.zzaEM = paramString;
  }
  
  public void zzdK(String paramString)
  {
    zzwH();
    if (TextUtils.isEmpty(paramString))
    {
      this.zzaEN = null;
      return;
    }
    this.zzaEN = paramString;
  }
  
  public void zzhK(int paramInt)
  {
    zzwH();
    this.zzaEK = paramInt;
  }
  
  public void zzhL(int paramInt)
  {
    zzwH();
    this.zzaEL = paramInt;
  }
  
  public String zzwB()
  {
    return this.zzaEJ;
  }
  
  public int zzwC()
  {
    return this.zzaEL;
  }
  
  public String zzwD()
  {
    return this.zzaEM;
  }
  
  public String zzwE()
  {
    return this.zzaEN;
  }
  
  public void zzwF()
  {
    this.zzaEQ = true;
  }
  
  public boolean zzwG()
  {
    return this.zzaEO;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */