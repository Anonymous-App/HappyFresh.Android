package com.google.android.gms.common.stats;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Debug;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;
import com.google.android.gms.internal.zzkf;
import com.google.android.gms.internal.zzla;
import com.google.android.gms.internal.zzll;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class zzb
{
  private static final Object zzaaJ = new Object();
  private static zzb zzack;
  private static final ComponentName zzacp = new ComponentName("com.google.android.gms", "com.google.android.gms.common.stats.GmsCoreStatsService");
  private final List<String> zzacl;
  private final List<String> zzacm;
  private final List<String> zzacn;
  private final List<String> zzaco;
  private zze zzacq;
  
  private zzb()
  {
    if (getLogLevel() == zzd.zzacz)
    {
      this.zzacl = Collections.EMPTY_LIST;
      this.zzacm = Collections.EMPTY_LIST;
      this.zzacn = Collections.EMPTY_LIST;
      this.zzaco = Collections.EMPTY_LIST;
      return;
    }
    Object localObject = (String)zzc.zza.zzacu.get();
    if (localObject == null)
    {
      localObject = Collections.EMPTY_LIST;
      this.zzacl = ((List)localObject);
      localObject = (String)zzc.zza.zzacv.get();
      if (localObject != null) {
        break label175;
      }
      localObject = Collections.EMPTY_LIST;
      label84:
      this.zzacm = ((List)localObject);
      localObject = (String)zzc.zza.zzacw.get();
      if (localObject != null) {
        break label188;
      }
      localObject = Collections.EMPTY_LIST;
      label107:
      this.zzacn = ((List)localObject);
      localObject = (String)zzc.zza.zzacx.get();
      if (localObject != null) {
        break label201;
      }
    }
    label175:
    label188:
    label201:
    for (localObject = Collections.EMPTY_LIST;; localObject = Arrays.asList(((String)localObject).split(",")))
    {
      this.zzaco = ((List)localObject);
      this.zzacq = new zze(1024, ((Long)zzc.zza.zzacy.get()).longValue());
      return;
      localObject = Arrays.asList(((String)localObject).split(","));
      break;
      localObject = Arrays.asList(((String)localObject).split(","));
      break label84;
      localObject = Arrays.asList(((String)localObject).split(","));
      break label107;
    }
  }
  
  private int getLogLevel()
  {
    try
    {
      if (zzla.zziW()) {
        return ((Integer)zzc.zza.zzact.get()).intValue();
      }
      int i = zzd.zzacz;
      return i;
    }
    catch (SecurityException localSecurityException) {}
    return zzd.zzacz;
  }
  
  private void zza(Context paramContext, ServiceConnection paramServiceConnection, String paramString, Intent paramIntent, int paramInt)
  {
    if (!com.google.android.gms.common.internal.zzd.zzZR) {}
    String str;
    do
    {
      return;
      str = zzb(paramServiceConnection);
    } while (!zza(paramContext, paramString, paramIntent, str, paramInt));
    long l2 = System.currentTimeMillis();
    paramServiceConnection = null;
    if ((getLogLevel() & zzd.zzacD) != 0) {
      paramServiceConnection = zzll.zzl(3, 5);
    }
    long l1 = 0L;
    if ((getLogLevel() & zzd.zzacF) != 0) {
      l1 = Debug.getNativeHeapAllocatedSize();
    }
    if ((paramInt == 1) || (paramInt == 4)) {}
    for (paramServiceConnection = new ConnectionEvent(l2, paramInt, null, null, null, null, paramServiceConnection, str, SystemClock.elapsedRealtime(), l1);; paramServiceConnection = new ConnectionEvent(l2, paramInt, zzll.zzaj(paramContext), paramString, paramIntent.processName, paramIntent.name, paramServiceConnection, str, SystemClock.elapsedRealtime(), l1))
    {
      paramContext.startService(new Intent().setComponent(zzacp).putExtra("com.google.android.gms.common.stats.EXTRA_LOG_EVENT", paramServiceConnection));
      return;
      paramIntent = zzb(paramContext, paramIntent);
    }
  }
  
  private boolean zza(Context paramContext, Intent paramIntent)
  {
    paramIntent = paramIntent.getComponent();
    if ((paramIntent == null) || ((com.google.android.gms.common.internal.zzd.zzZR) && ("com.google.android.gms".equals(paramIntent.getPackageName())))) {
      return false;
    }
    return zzla.zzi(paramContext, paramIntent.getPackageName());
  }
  
  private boolean zza(Context paramContext, String paramString1, Intent paramIntent, String paramString2, int paramInt)
  {
    int i = getLogLevel();
    if ((i == zzd.zzacz) || (this.zzacq == null)) {}
    Object localObject;
    do
    {
      do
      {
        return false;
        if ((paramInt != 4) && (paramInt != 1)) {
          break;
        }
      } while (!this.zzacq.zzcq(paramString2));
      return true;
      localObject = zzb(paramContext, paramIntent);
      if (localObject == null)
      {
        Log.w("ConnectionTracker", String.format("Client %s made an invalid request %s", new Object[] { paramString1, paramIntent.toUri(0) }));
        return false;
      }
      paramContext = zzll.zzaj(paramContext);
      paramIntent = ((ServiceInfo)localObject).processName;
      localObject = ((ServiceInfo)localObject).name;
    } while ((this.zzacl.contains(paramContext)) || (this.zzacm.contains(paramString1)) || (this.zzacn.contains(paramIntent)) || (this.zzaco.contains(localObject)) || ((paramIntent.equals(paramContext)) && ((i & zzd.zzacE) != 0)));
    this.zzacq.zzcp(paramString2);
    return true;
  }
  
  private static ServiceInfo zzb(Context paramContext, Intent paramIntent)
  {
    paramContext = paramContext.getPackageManager().queryIntentServices(paramIntent, 128);
    if ((paramContext == null) || (paramContext.size() == 0))
    {
      Log.w("ConnectionTracker", String.format("There are no handler of this intent: %s\n Stack trace: %s", new Object[] { paramIntent.toUri(0), zzll.zzl(3, 20) }));
      return null;
    }
    if (paramContext.size() > 1)
    {
      Log.w("ConnectionTracker", String.format("Multiple handlers found for this intent: %s\n Stack trace: %s", new Object[] { paramIntent.toUri(0), zzll.zzl(3, 20) }));
      paramIntent = paramContext.iterator();
      if (paramIntent.hasNext())
      {
        Log.w("ConnectionTracker", ((ResolveInfo)paramIntent.next()).serviceInfo.name);
        return null;
      }
    }
    return ((ResolveInfo)paramContext.get(0)).serviceInfo;
  }
  
  private String zzb(ServiceConnection paramServiceConnection)
  {
    return String.valueOf(Process.myPid() << 32 | System.identityHashCode(paramServiceConnection));
  }
  
  public static zzb zzoO()
  {
    synchronized (zzaaJ)
    {
      if (zzack == null) {
        zzack = new zzb();
      }
      return zzack;
    }
  }
  
  public void zza(Context paramContext, ServiceConnection paramServiceConnection)
  {
    zza(paramContext, paramServiceConnection, null, null, 1);
    paramContext.unbindService(paramServiceConnection);
  }
  
  public void zza(Context paramContext, ServiceConnection paramServiceConnection, String paramString, Intent paramIntent)
  {
    zza(paramContext, paramServiceConnection, paramString, paramIntent, 3);
  }
  
  public boolean zza(Context paramContext, Intent paramIntent, ServiceConnection paramServiceConnection, int paramInt)
  {
    return zza(paramContext, paramContext.getClass().getName(), paramIntent, paramServiceConnection, paramInt);
  }
  
  public boolean zza(Context paramContext, String paramString, Intent paramIntent, ServiceConnection paramServiceConnection, int paramInt)
  {
    if (zza(paramContext, paramIntent))
    {
      Log.w("ConnectionTracker", "Attempted to bind to a service in a STOPPED package.");
      return false;
    }
    zza(paramContext, paramServiceConnection, paramString, paramIntent, 2);
    return paramContext.bindService(paramIntent, paramServiceConnection, paramInt);
  }
  
  public void zzb(Context paramContext, ServiceConnection paramServiceConnection)
  {
    zza(paramContext, paramServiceConnection, null, null, 4);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/stats/zzb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */