package com.google.android.gms.playlog.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.zze;
import com.google.android.gms.common.internal.zzi;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.internal.zzrn;
import java.util.ArrayList;
import java.util.Iterator;

public class zzf
  extends zzi<zza>
{
  private final String zzMZ;
  private final zzd zzaGW;
  private final zzb zzaGX;
  private boolean zzaGY;
  private final Object zzqt;
  
  public zzf(Context paramContext, Looper paramLooper, zzd paramzzd, zze paramzze)
  {
    super(paramContext, paramLooper, 24, paramzzd, paramzzd, paramzze);
    this.zzMZ = paramContext.getPackageName();
    this.zzaGW = ((zzd)zzu.zzu(paramzzd));
    this.zzaGW.zza(this);
    this.zzaGX = new zzb();
    this.zzqt = new Object();
    this.zzaGY = true;
  }
  
  private void zzc(PlayLoggerContext paramPlayLoggerContext, LogEvent paramLogEvent)
  {
    this.zzaGX.zza(paramPlayLoggerContext, paramLogEvent);
  }
  
  private void zzd(PlayLoggerContext paramPlayLoggerContext, LogEvent paramLogEvent)
  {
    try
    {
      zzxp();
      ((zza)zznM()).zza(this.zzMZ, paramPlayLoggerContext, paramLogEvent);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      Log.e("PlayLoggerImpl", "Couldn't send log event.  Will try caching.");
      zzc(paramPlayLoggerContext, paramLogEvent);
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      Log.e("PlayLoggerImpl", "Service was disconnected.  Will try caching.");
      zzc(paramPlayLoggerContext, paramLogEvent);
    }
  }
  
  private void zzxp()
  {
    boolean bool;
    if (!this.zzaGY)
    {
      bool = true;
      com.google.android.gms.common.internal.zzb.zzU(bool);
      if (!this.zzaGX.isEmpty()) {
        Object localObject = null;
      }
    }
    label122:
    label195:
    label228:
    for (;;)
    {
      ArrayList localArrayList;
      zzb.zza localzza;
      try
      {
        localArrayList = new ArrayList();
        Iterator localIterator = this.zzaGX.zzxn().iterator();
        if (!localIterator.hasNext()) {
          break label195;
        }
        localzza = (zzb.zza)localIterator.next();
        if (localzza.zzaGM == null) {
          break label122;
        }
        ((zza)zznM()).zza(this.zzMZ, localzza.zzaGK, zzrn.zzf(localzza.zzaGM));
        continue;
        return;
      }
      catch (RemoteException localRemoteException)
      {
        Log.e("PlayLoggerImpl", "Couldn't send cached log events to AndroidLog service.  Retaining in memory cache.");
      }
      bool = false;
      break;
      if (localzza.zzaGK.equals(localRemoteException))
      {
        localArrayList.add(localzza.zzaGL);
      }
      else
      {
        if (!localArrayList.isEmpty())
        {
          ((zza)zznM()).zza(this.zzMZ, localRemoteException, localArrayList);
          localArrayList.clear();
        }
        PlayLoggerContext localPlayLoggerContext = localzza.zzaGK;
        localArrayList.add(localzza.zzaGL);
        break label228;
        if (!localArrayList.isEmpty()) {
          ((zza)zznM()).zza(this.zzMZ, localPlayLoggerContext, localArrayList);
        }
        this.zzaGX.clear();
        return;
      }
    }
  }
  
  protected String getServiceDescriptor()
  {
    return "com.google.android.gms.playlog.internal.IPlayLogService";
  }
  
  protected String getStartServiceAction()
  {
    return "com.google.android.gms.playlog.service.START";
  }
  
  public void start()
  {
    synchronized (this.zzqt)
    {
      if ((isConnecting()) || (isConnected())) {
        return;
      }
      this.zzaGW.zzaj(true);
      zznJ();
      return;
    }
  }
  
  public void stop()
  {
    synchronized (this.zzqt)
    {
      this.zzaGW.zzaj(false);
      disconnect();
      return;
    }
  }
  
  void zzak(boolean paramBoolean)
  {
    synchronized (this.zzqt)
    {
      boolean bool = this.zzaGY;
      this.zzaGY = paramBoolean;
      if ((bool) && (!this.zzaGY)) {
        zzxp();
      }
      return;
    }
  }
  
  public void zzb(PlayLoggerContext paramPlayLoggerContext, LogEvent paramLogEvent)
  {
    synchronized (this.zzqt)
    {
      if (this.zzaGY)
      {
        zzc(paramPlayLoggerContext, paramLogEvent);
        return;
      }
      zzd(paramPlayLoggerContext, paramLogEvent);
    }
  }
  
  protected zza zzdq(IBinder paramIBinder)
  {
    return zza.zza.zzdp(paramIBinder);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/playlog/internal/zzf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */