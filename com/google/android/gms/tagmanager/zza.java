package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Process;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.internal.zzlb;
import com.google.android.gms.internal.zzld;
import java.io.IOException;

class zza
{
  private static Object zzaKl = new Object();
  private static zza zzaKm;
  private volatile boolean mClosed = false;
  private final Context mContext;
  private final Thread zzFZ;
  private volatile AdvertisingIdClient.Info zzJl;
  private volatile long zzaKh = 900000L;
  private volatile long zzaKi = 30000L;
  private volatile long zzaKj;
  private zza zzaKk = new zza()
  {
    public AdvertisingIdClient.Info zzyg()
    {
      try
      {
        AdvertisingIdClient.Info localInfo = AdvertisingIdClient.getAdvertisingIdInfo(zza.zza(zza.this));
        return localInfo;
      }
      catch (IllegalStateException localIllegalStateException)
      {
        zzbg.zzaC("IllegalStateException getting Advertising Id Info");
        return null;
      }
      catch (GooglePlayServicesRepairableException localGooglePlayServicesRepairableException)
      {
        zzbg.zzaC("GooglePlayServicesRepairableException getting Advertising Id Info");
        return null;
      }
      catch (IOException localIOException)
      {
        zzbg.zzaC("IOException getting Ad Id Info");
        return null;
      }
      catch (GooglePlayServicesNotAvailableException localGooglePlayServicesNotAvailableException)
      {
        zzbg.zzaC("GooglePlayServicesNotAvailableException getting Advertising Id Info");
        return null;
      }
      catch (Exception localException)
      {
        zzbg.zzaC("Unknown exception. Could not get the Advertising Id Info.");
      }
      return null;
    }
  };
  private final zzlb zzpw;
  
  private zza(Context paramContext)
  {
    this(paramContext, null, zzld.zzoQ());
  }
  
  zza(Context paramContext, zza paramzza, zzlb paramzzlb)
  {
    this.zzpw = paramzzlb;
    if (paramContext != null) {}
    for (this.mContext = paramContext.getApplicationContext();; this.mContext = paramContext)
    {
      if (paramzza != null) {
        this.zzaKk = paramzza;
      }
      this.zzFZ = new Thread(new Runnable()
      {
        public void run()
        {
          zza.zzb(zza.this);
        }
      });
      return;
    }
  }
  
  static zza zzaE(Context paramContext)
  {
    if (zzaKm == null) {}
    synchronized (zzaKl)
    {
      if (zzaKm == null)
      {
        zzaKm = new zza(paramContext);
        zzaKm.start();
      }
      return zzaKm;
    }
  }
  
  private void zzye()
  {
    Process.setThreadPriority(10);
    while (!this.mClosed) {
      try
      {
        this.zzJl = this.zzaKk.zzyg();
        Thread.sleep(this.zzaKh);
      }
      catch (InterruptedException localInterruptedException)
      {
        zzbg.zzaA("sleep interrupted in AdvertiserDataPoller thread; continuing");
      }
    }
  }
  
  private void zzyf()
  {
    if (this.zzpw.currentTimeMillis() - this.zzaKj < this.zzaKi) {
      return;
    }
    interrupt();
    this.zzaKj = this.zzpw.currentTimeMillis();
  }
  
  void interrupt()
  {
    this.zzFZ.interrupt();
  }
  
  public boolean isLimitAdTrackingEnabled()
  {
    zzyf();
    if (this.zzJl == null) {
      return true;
    }
    return this.zzJl.isLimitAdTrackingEnabled();
  }
  
  void start()
  {
    this.zzFZ.start();
  }
  
  public String zzyd()
  {
    zzyf();
    if (this.zzJl == null) {
      return null;
    }
    return this.zzJl.getId();
  }
  
  public static abstract interface zza
  {
    public abstract AdvertisingIdClient.Info zzyg();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zza.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */