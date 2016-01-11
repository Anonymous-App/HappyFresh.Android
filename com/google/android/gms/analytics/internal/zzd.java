package com.google.android.gms.analytics.internal;

public abstract class zzd
  extends zzc
{
  private boolean zzJA;
  private boolean zzJz;
  
  protected zzd(zzf paramzzf)
  {
    super(paramzzf);
  }
  
  public boolean isInitialized()
  {
    return (this.zzJz) && (!this.zzJA);
  }
  
  public void zza()
  {
    zzhn();
    this.zzJz = true;
  }
  
  protected abstract void zzhn();
  
  protected void zzia()
  {
    if (!isInitialized()) {
      throw new IllegalStateException("Not initialized");
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/analytics/internal/zzd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */