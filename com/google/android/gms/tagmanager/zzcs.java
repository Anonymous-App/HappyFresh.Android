package com.google.android.gms.tagmanager;

class zzcs
  implements zzcd
{
  private final long zzMf;
  private final int zzMg;
  private double zzMh;
  private final Object zzMj = new Object();
  private long zzaNC;
  
  public zzcs()
  {
    this(60, 2000L);
  }
  
  public zzcs(int paramInt, long paramLong)
  {
    this.zzMg = paramInt;
    this.zzMh = this.zzMg;
    this.zzMf = paramLong;
  }
  
  public boolean zzkb()
  {
    synchronized (this.zzMj)
    {
      long l = System.currentTimeMillis();
      if (this.zzMh < this.zzMg)
      {
        double d = (l - this.zzaNC) / this.zzMf;
        if (d > 0.0D) {
          this.zzMh = Math.min(this.zzMg, d + this.zzMh);
        }
      }
      this.zzaNC = l;
      if (this.zzMh >= 1.0D)
      {
        this.zzMh -= 1.0D;
        return true;
      }
      zzbg.zzaC("No more tokens available.");
      return false;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzcs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */