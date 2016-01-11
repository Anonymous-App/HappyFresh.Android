package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzlb;

class zzbe
  implements zzcd
{
  private final long zzMf;
  private final int zzMg;
  private double zzMh;
  private long zzMi;
  private final Object zzMj = new Object();
  private final long zzaMh;
  private final zzlb zzpw;
  private final String zzuO;
  
  public zzbe(int paramInt, long paramLong1, long paramLong2, String paramString, zzlb paramzzlb)
  {
    this.zzMg = paramInt;
    this.zzMh = this.zzMg;
    this.zzMf = paramLong1;
    this.zzaMh = paramLong2;
    this.zzuO = paramString;
    this.zzpw = paramzzlb;
  }
  
  public boolean zzkb()
  {
    synchronized (this.zzMj)
    {
      long l = this.zzpw.currentTimeMillis();
      if (l - this.zzMi < this.zzaMh)
      {
        zzbg.zzaC("Excessive " + this.zzuO + " detected; call ignored.");
        return false;
      }
      if (this.zzMh < this.zzMg)
      {
        double d = (l - this.zzMi) / this.zzMf;
        if (d > 0.0D) {
          this.zzMh = Math.min(this.zzMg, d + this.zzMh);
        }
      }
      this.zzMi = l;
      if (this.zzMh >= 1.0D)
      {
        this.zzMh -= 1.0D;
        return true;
      }
    }
    zzbg.zzaC("Excessive " + this.zzuO + " detected; call ignored.");
    return false;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzbe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */