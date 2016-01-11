package com.google.android.gms.tagmanager;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.PowerManager;
import android.os.Process;
import android.text.TextUtils;
import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzae;
import com.google.android.gms.internal.zzag.zza;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

class zzdb
  extends zzak
{
  private static final String ID = zzad.zzcN.toString();
  private static final String NAME = zzae.zzfO.toString();
  private static final String zzaOe = zzae.zzft.toString();
  private static final String zzaOf = zzae.zzfB.toString();
  private static final String zzaOg = zzae.zzhs.toString();
  private final Context mContext;
  private Handler mHandler;
  private DataLayer zzaKz;
  private boolean zzaOh;
  private boolean zzaOi;
  private final HandlerThread zzaOj;
  private final Set<String> zzaOk = new HashSet();
  
  public zzdb(Context paramContext, DataLayer paramDataLayer)
  {
    super(ID, new String[] { zzaOe, NAME });
    this.mContext = paramContext;
    this.zzaKz = paramDataLayer;
    this.zzaOj = new HandlerThread("Google GTM SDK Timer", 10);
    this.zzaOj.start();
    this.mHandler = new Handler(this.zzaOj.getLooper());
  }
  
  public zzag.zza zzE(Map<String, zzag.zza> paramMap)
  {
    String str2 = zzdf.zzg((zzag.zza)paramMap.get(NAME));
    String str1 = zzdf.zzg((zzag.zza)paramMap.get(zzaOg));
    String str3 = zzdf.zzg((zzag.zza)paramMap.get(zzaOe));
    paramMap = zzdf.zzg((zzag.zza)paramMap.get(zzaOf));
    try
    {
      l1 = Long.parseLong(str3);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      try
      {
        for (;;)
        {
          l2 = Long.parseLong(paramMap);
          if ((l1 > 0L) && (!TextUtils.isEmpty(str2)))
          {
            if (str1 != null)
            {
              paramMap = str1;
              if (!str1.isEmpty()) {}
            }
            else
            {
              paramMap = "0";
            }
            if (!this.zzaOk.contains(paramMap))
            {
              if (!"0".equals(paramMap)) {
                this.zzaOk.add(paramMap);
              }
              this.mHandler.postDelayed(new zza(str2, paramMap, l1, l2), l1);
            }
          }
          return zzdf.zzzQ();
          localNumberFormatException = localNumberFormatException;
          long l1 = 0L;
        }
      }
      catch (NumberFormatException paramMap)
      {
        for (;;)
        {
          long l2 = 0L;
        }
      }
    }
  }
  
  public boolean zzyh()
  {
    return false;
  }
  
  private final class zza
    implements Runnable
  {
    private final long zzMC;
    private final String zzaOl;
    private final String zzaOm;
    private final long zzaOn;
    private long zzaOo;
    private final long zzaxU;
    
    zza(String paramString1, String paramString2, long paramLong1, long paramLong2)
    {
      this.zzaOl = paramString1;
      this.zzaOm = paramString2;
      this.zzaxU = paramLong1;
      this.zzaOn = paramLong2;
      this.zzMC = System.currentTimeMillis();
    }
    
    public void run()
    {
      if ((this.zzaOn > 0L) && (this.zzaOo >= this.zzaOn))
      {
        if (!"0".equals(this.zzaOm)) {
          zzdb.zza(zzdb.this).remove(this.zzaOm);
        }
        return;
      }
      this.zzaOo += 1L;
      if (zzcq())
      {
        long l = System.currentTimeMillis();
        zzdb.zzb(zzdb.this).push(DataLayer.mapOf(new Object[] { "event", this.zzaOl, "gtm.timerInterval", String.valueOf(this.zzaxU), "gtm.timerLimit", String.valueOf(this.zzaOn), "gtm.timerStartTime", String.valueOf(this.zzMC), "gtm.timerCurrentTime", String.valueOf(l), "gtm.timerElapsedTime", String.valueOf(l - this.zzMC), "gtm.timerEventNumber", String.valueOf(this.zzaOo), "gtm.triggers", this.zzaOm }));
      }
      zzdb.zzc(zzdb.this).postDelayed(this, this.zzaxU);
    }
    
    protected boolean zzcq()
    {
      if (zzdb.zzd(zzdb.this)) {
        return zzdb.zze(zzdb.this);
      }
      Object localObject = (ActivityManager)zzdb.zzf(zzdb.this).getSystemService("activity");
      KeyguardManager localKeyguardManager = (KeyguardManager)zzdb.zzf(zzdb.this).getSystemService("keyguard");
      PowerManager localPowerManager = (PowerManager)zzdb.zzf(zzdb.this).getSystemService("power");
      localObject = ((ActivityManager)localObject).getRunningAppProcesses().iterator();
      while (((Iterator)localObject).hasNext())
      {
        ActivityManager.RunningAppProcessInfo localRunningAppProcessInfo = (ActivityManager.RunningAppProcessInfo)((Iterator)localObject).next();
        if ((Process.myPid() == localRunningAppProcessInfo.pid) && (localRunningAppProcessInfo.importance == 100) && (!localKeyguardManager.inKeyguardRestrictedInputMode()) && (localPowerManager.isScreenOn())) {
          return true;
        }
      }
      return false;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/tagmanager/zzdb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */