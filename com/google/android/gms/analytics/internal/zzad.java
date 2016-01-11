package com.google.android.gms.analytics.internal;

public class zzad
{
  private final long zzMf;
  private final int zzMg;
  private double zzMh;
  private long zzMi;
  private final Object zzMj = new Object();
  private final String zzuO;
  
  public zzad(int paramInt, long paramLong, String paramString)
  {
    this.zzMg = paramInt;
    this.zzMh = this.zzMg;
    this.zzMf = paramLong;
    this.zzuO = paramString;
  }
  
  public zzad(String paramString)
  {
    this(60, 2000L, paramString);
  }
  
  public boolean zzkb()
  {
    synchronized (this.zzMj)
    {
      long l = System.currentTimeMillis();
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
      zzae.zzaC("Excessive " + this.zzuO + " detected; call ignored.");
      return false;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/analytics/internal/zzad.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */