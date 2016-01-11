package com.google.android.gms.analytics.internal;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.internal.zzns;
import java.util.Collections;
import java.util.List;

public class zzi
  extends zzd
{
  private final zza zzJW;
  private zzac zzJX;
  private final zzt zzJY;
  private zzaj zzJZ;
  
  protected zzi(zzf paramzzf)
  {
    super(paramzzf);
    this.zzJZ = new zzaj(paramzzf.zzhP());
    this.zzJW = new zza();
    this.zzJY = new zzt(paramzzf)
    {
      public void run()
      {
        zzi.zzb(zzi.this);
      }
    };
  }
  
  private void onDisconnect()
  {
    zzhl().zzhJ();
  }
  
  private void onServiceDisconnected(ComponentName paramComponentName)
  {
    zzhO();
    if (this.zzJX != null)
    {
      this.zzJX = null;
      zza("Disconnected from device AnalyticsService", paramComponentName);
      onDisconnect();
    }
  }
  
  private void zza(zzac paramzzac)
  {
    zzhO();
    this.zzJX = paramzzac;
    zzin();
    zzhl().onServiceConnected();
  }
  
  private void zzin()
  {
    this.zzJZ.start();
    this.zzJY.zzt(zzhR().zzjr());
  }
  
  private void zzio()
  {
    zzhO();
    if (!isConnected()) {
      return;
    }
    zzaT("Inactivity, disconnecting from device AnalyticsService");
    disconnect();
  }
  
  public boolean connect()
  {
    zzhO();
    zzia();
    if (this.zzJX != null) {
      return true;
    }
    zzac localzzac = this.zzJW.zzip();
    if (localzzac != null)
    {
      this.zzJX = localzzac;
      zzin();
      return true;
    }
    return false;
  }
  
  public void disconnect()
  {
    zzhO();
    zzia();
    try
    {
      com.google.android.gms.common.stats.zzb.zzoO().zza(getContext(), this.zzJW);
      if (this.zzJX != null)
      {
        this.zzJX = null;
        onDisconnect();
      }
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      for (;;) {}
    }
    catch (IllegalStateException localIllegalStateException)
    {
      for (;;) {}
    }
  }
  
  public boolean isConnected()
  {
    zzhO();
    zzia();
    return this.zzJX != null;
  }
  
  public boolean zzb(zzab paramzzab)
  {
    zzu.zzu(paramzzab);
    zzhO();
    zzia();
    zzac localzzac = this.zzJX;
    if (localzzac == null) {
      return false;
    }
    if (paramzzab.zzjY()) {}
    for (String str = zzhR().zzjk();; str = zzhR().zzjl())
    {
      List localList = Collections.emptyList();
      try
      {
        localzzac.zza(paramzzab.zzn(), paramzzab.zzjW(), str, localList);
        zzin();
        return true;
      }
      catch (RemoteException paramzzab)
      {
        zzaT("Failed to send hits to AnalyticsService");
      }
    }
    return false;
  }
  
  protected void zzhn() {}
  
  public boolean zzim()
  {
    zzhO();
    zzia();
    zzac localzzac = this.zzJX;
    if (localzzac == null) {
      return false;
    }
    try
    {
      localzzac.zzhG();
      zzin();
      return true;
    }
    catch (RemoteException localRemoteException)
    {
      zzaT("Failed to clear hits from AnalyticsService");
    }
    return false;
  }
  
  protected class zza
    implements ServiceConnection
  {
    private volatile zzac zzKb;
    private volatile boolean zzKc;
    
    protected zza() {}
    
    /* Error */
    public void onServiceConnected(final ComponentName paramComponentName, android.os.IBinder paramIBinder)
    {
      // Byte code:
      //   0: ldc 35
      //   2: invokestatic 41	com/google/android/gms/common/internal/zzu:zzbY	(Ljava/lang/String;)V
      //   5: aload_0
      //   6: monitorenter
      //   7: aload_2
      //   8: ifnonnull +19 -> 27
      //   11: aload_0
      //   12: getfield 23	com/google/android/gms/analytics/internal/zzi$zza:zzKa	Lcom/google/android/gms/analytics/internal/zzi;
      //   15: ldc 43
      //   17: invokevirtual 46	com/google/android/gms/analytics/internal/zzi:zzaX	(Ljava/lang/String;)V
      //   20: aload_0
      //   21: invokevirtual 49	java/lang/Object:notifyAll	()V
      //   24: aload_0
      //   25: monitorexit
      //   26: return
      //   27: aconst_null
      //   28: astore 4
      //   30: aconst_null
      //   31: astore_3
      //   32: aload 4
      //   34: astore_1
      //   35: aload_2
      //   36: invokeinterface 55 1 0
      //   41: astore 5
      //   43: aload 4
      //   45: astore_1
      //   46: ldc 57
      //   48: aload 5
      //   50: invokevirtual 63	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   53: ifeq +60 -> 113
      //   56: aload 4
      //   58: astore_1
      //   59: aload_2
      //   60: invokestatic 69	com/google/android/gms/analytics/internal/zzac$zza:zzac	(Landroid/os/IBinder;)Lcom/google/android/gms/analytics/internal/zzac;
      //   63: astore_2
      //   64: aload_2
      //   65: astore_1
      //   66: aload_0
      //   67: getfield 23	com/google/android/gms/analytics/internal/zzi$zza:zzKa	Lcom/google/android/gms/analytics/internal/zzi;
      //   70: ldc 71
      //   72: invokevirtual 74	com/google/android/gms/analytics/internal/zzi:zzaT	(Ljava/lang/String;)V
      //   75: aload_2
      //   76: astore_1
      //   77: aload_1
      //   78: ifnonnull +74 -> 152
      //   81: invokestatic 80	com/google/android/gms/common/stats/zzb:zzoO	()Lcom/google/android/gms/common/stats/zzb;
      //   84: aload_0
      //   85: getfield 23	com/google/android/gms/analytics/internal/zzi$zza:zzKa	Lcom/google/android/gms/analytics/internal/zzi;
      //   88: invokevirtual 84	com/google/android/gms/analytics/internal/zzi:getContext	()Landroid/content/Context;
      //   91: aload_0
      //   92: getfield 23	com/google/android/gms/analytics/internal/zzi$zza:zzKa	Lcom/google/android/gms/analytics/internal/zzi;
      //   95: invokestatic 87	com/google/android/gms/analytics/internal/zzi:zza	(Lcom/google/android/gms/analytics/internal/zzi;)Lcom/google/android/gms/analytics/internal/zzi$zza;
      //   98: invokevirtual 90	com/google/android/gms/common/stats/zzb:zza	(Landroid/content/Context;Landroid/content/ServiceConnection;)V
      //   101: aload_0
      //   102: invokevirtual 49	java/lang/Object:notifyAll	()V
      //   105: aload_0
      //   106: monitorexit
      //   107: return
      //   108: astore_1
      //   109: aload_0
      //   110: monitorexit
      //   111: aload_1
      //   112: athrow
      //   113: aload 4
      //   115: astore_1
      //   116: aload_0
      //   117: getfield 23	com/google/android/gms/analytics/internal/zzi$zza:zzKa	Lcom/google/android/gms/analytics/internal/zzi;
      //   120: ldc 92
      //   122: aload 5
      //   124: invokevirtual 96	com/google/android/gms/analytics/internal/zzi:zze	(Ljava/lang/String;Ljava/lang/Object;)V
      //   127: aload_3
      //   128: astore_1
      //   129: goto -52 -> 77
      //   132: astore_2
      //   133: aload_0
      //   134: getfield 23	com/google/android/gms/analytics/internal/zzi$zza:zzKa	Lcom/google/android/gms/analytics/internal/zzi;
      //   137: ldc 98
      //   139: invokevirtual 46	com/google/android/gms/analytics/internal/zzi:zzaX	(Ljava/lang/String;)V
      //   142: goto -65 -> 77
      //   145: astore_1
      //   146: aload_0
      //   147: invokevirtual 49	java/lang/Object:notifyAll	()V
      //   150: aload_1
      //   151: athrow
      //   152: aload_0
      //   153: getfield 100	com/google/android/gms/analytics/internal/zzi$zza:zzKc	Z
      //   156: ifne +34 -> 190
      //   159: aload_0
      //   160: getfield 23	com/google/android/gms/analytics/internal/zzi$zza:zzKa	Lcom/google/android/gms/analytics/internal/zzi;
      //   163: ldc 102
      //   165: invokevirtual 105	com/google/android/gms/analytics/internal/zzi:zzaW	(Ljava/lang/String;)V
      //   168: aload_0
      //   169: getfield 23	com/google/android/gms/analytics/internal/zzi$zza:zzKa	Lcom/google/android/gms/analytics/internal/zzi;
      //   172: invokevirtual 109	com/google/android/gms/analytics/internal/zzi:zzhS	()Lcom/google/android/gms/internal/zzns;
      //   175: new 11	com/google/android/gms/analytics/internal/zzi$zza$1
      //   178: dup
      //   179: aload_0
      //   180: aload_1
      //   181: invokespecial 112	com/google/android/gms/analytics/internal/zzi$zza$1:<init>	(Lcom/google/android/gms/analytics/internal/zzi$zza;Lcom/google/android/gms/analytics/internal/zzac;)V
      //   184: invokevirtual 117	com/google/android/gms/internal/zzns:zze	(Ljava/lang/Runnable;)V
      //   187: goto -86 -> 101
      //   190: aload_0
      //   191: aload_1
      //   192: putfield 119	com/google/android/gms/analytics/internal/zzi$zza:zzKb	Lcom/google/android/gms/analytics/internal/zzac;
      //   195: goto -94 -> 101
      //   198: astore_1
      //   199: goto -98 -> 101
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	202	0	this	zza
      //   0	202	1	paramComponentName	ComponentName
      //   0	202	2	paramIBinder	android.os.IBinder
      //   31	97	3	localObject1	Object
      //   28	86	4	localObject2	Object
      //   41	82	5	str	String
      // Exception table:
      //   from	to	target	type
      //   20	26	108	finally
      //   101	107	108	finally
      //   109	111	108	finally
      //   146	152	108	finally
      //   35	43	132	android/os/RemoteException
      //   46	56	132	android/os/RemoteException
      //   59	64	132	android/os/RemoteException
      //   66	75	132	android/os/RemoteException
      //   116	127	132	android/os/RemoteException
      //   11	20	145	finally
      //   35	43	145	finally
      //   46	56	145	finally
      //   59	64	145	finally
      //   66	75	145	finally
      //   81	101	145	finally
      //   116	127	145	finally
      //   133	142	145	finally
      //   152	187	145	finally
      //   190	195	145	finally
      //   81	101	198	java/lang/IllegalArgumentException
    }
    
    public void onServiceDisconnected(final ComponentName paramComponentName)
    {
      zzu.zzbY("AnalyticsServiceConnection.onServiceDisconnected");
      zzi.this.zzhS().zze(new Runnable()
      {
        public void run()
        {
          zzi.zza(zzi.this, paramComponentName);
        }
      });
    }
    
    /* Error */
    public zzac zzip()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 23	com/google/android/gms/analytics/internal/zzi$zza:zzKa	Lcom/google/android/gms/analytics/internal/zzi;
      //   4: invokevirtual 133	com/google/android/gms/analytics/internal/zzi:zzhO	()V
      //   7: new 135	android/content/Intent
      //   10: dup
      //   11: ldc -119
      //   13: invokespecial 139	android/content/Intent:<init>	(Ljava/lang/String;)V
      //   16: astore_2
      //   17: aload_2
      //   18: new 141	android/content/ComponentName
      //   21: dup
      //   22: ldc -113
      //   24: ldc -111
      //   26: invokespecial 148	android/content/ComponentName:<init>	(Ljava/lang/String;Ljava/lang/String;)V
      //   29: invokevirtual 152	android/content/Intent:setComponent	(Landroid/content/ComponentName;)Landroid/content/Intent;
      //   32: pop
      //   33: aload_0
      //   34: getfield 23	com/google/android/gms/analytics/internal/zzi$zza:zzKa	Lcom/google/android/gms/analytics/internal/zzi;
      //   37: invokevirtual 84	com/google/android/gms/analytics/internal/zzi:getContext	()Landroid/content/Context;
      //   40: astore_3
      //   41: aload_2
      //   42: ldc -102
      //   44: aload_3
      //   45: invokevirtual 159	android/content/Context:getPackageName	()Ljava/lang/String;
      //   48: invokevirtual 163	android/content/Intent:putExtra	(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;
      //   51: pop
      //   52: invokestatic 80	com/google/android/gms/common/stats/zzb:zzoO	()Lcom/google/android/gms/common/stats/zzb;
      //   55: astore 4
      //   57: aload_0
      //   58: monitorenter
      //   59: aload_0
      //   60: aconst_null
      //   61: putfield 119	com/google/android/gms/analytics/internal/zzi$zza:zzKb	Lcom/google/android/gms/analytics/internal/zzac;
      //   64: aload_0
      //   65: iconst_1
      //   66: putfield 100	com/google/android/gms/analytics/internal/zzi$zza:zzKc	Z
      //   69: aload 4
      //   71: aload_3
      //   72: aload_2
      //   73: aload_0
      //   74: getfield 23	com/google/android/gms/analytics/internal/zzi$zza:zzKa	Lcom/google/android/gms/analytics/internal/zzi;
      //   77: invokestatic 87	com/google/android/gms/analytics/internal/zzi:zza	(Lcom/google/android/gms/analytics/internal/zzi;)Lcom/google/android/gms/analytics/internal/zzi$zza;
      //   80: sipush 129
      //   83: invokevirtual 166	com/google/android/gms/common/stats/zzb:zza	(Landroid/content/Context;Landroid/content/Intent;Landroid/content/ServiceConnection;I)Z
      //   86: istore_1
      //   87: aload_0
      //   88: getfield 23	com/google/android/gms/analytics/internal/zzi$zza:zzKa	Lcom/google/android/gms/analytics/internal/zzi;
      //   91: ldc -88
      //   93: iload_1
      //   94: invokestatic 174	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
      //   97: invokevirtual 176	com/google/android/gms/analytics/internal/zzi:zza	(Ljava/lang/String;Ljava/lang/Object;)V
      //   100: iload_1
      //   101: ifne +12 -> 113
      //   104: aload_0
      //   105: iconst_0
      //   106: putfield 100	com/google/android/gms/analytics/internal/zzi$zza:zzKc	Z
      //   109: aload_0
      //   110: monitorexit
      //   111: aconst_null
      //   112: areturn
      //   113: aload_0
      //   114: aload_0
      //   115: getfield 23	com/google/android/gms/analytics/internal/zzi$zza:zzKa	Lcom/google/android/gms/analytics/internal/zzi;
      //   118: invokevirtual 180	com/google/android/gms/analytics/internal/zzi:zzhR	()Lcom/google/android/gms/analytics/internal/zzr;
      //   121: invokevirtual 186	com/google/android/gms/analytics/internal/zzr:zzjs	()J
      //   124: invokevirtual 190	java/lang/Object:wait	(J)V
      //   127: aload_0
      //   128: iconst_0
      //   129: putfield 100	com/google/android/gms/analytics/internal/zzi$zza:zzKc	Z
      //   132: aload_0
      //   133: getfield 119	com/google/android/gms/analytics/internal/zzi$zza:zzKb	Lcom/google/android/gms/analytics/internal/zzac;
      //   136: astore_2
      //   137: aload_0
      //   138: aconst_null
      //   139: putfield 119	com/google/android/gms/analytics/internal/zzi$zza:zzKb	Lcom/google/android/gms/analytics/internal/zzac;
      //   142: aload_2
      //   143: ifnonnull +12 -> 155
      //   146: aload_0
      //   147: getfield 23	com/google/android/gms/analytics/internal/zzi$zza:zzKa	Lcom/google/android/gms/analytics/internal/zzi;
      //   150: ldc -64
      //   152: invokevirtual 46	com/google/android/gms/analytics/internal/zzi:zzaX	(Ljava/lang/String;)V
      //   155: aload_0
      //   156: monitorexit
      //   157: aload_2
      //   158: areturn
      //   159: astore_2
      //   160: aload_0
      //   161: monitorexit
      //   162: aload_2
      //   163: athrow
      //   164: astore_2
      //   165: aload_0
      //   166: getfield 23	com/google/android/gms/analytics/internal/zzi$zza:zzKa	Lcom/google/android/gms/analytics/internal/zzi;
      //   169: ldc -62
      //   171: invokevirtual 105	com/google/android/gms/analytics/internal/zzi:zzaW	(Ljava/lang/String;)V
      //   174: goto -47 -> 127
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	177	0	this	zza
      //   86	15	1	bool	boolean
      //   16	142	2	localObject1	Object
      //   159	4	2	localObject2	Object
      //   164	1	2	localInterruptedException	InterruptedException
      //   40	32	3	localContext	android.content.Context
      //   55	15	4	localzzb	com.google.android.gms.common.stats.zzb
      // Exception table:
      //   from	to	target	type
      //   59	100	159	finally
      //   104	111	159	finally
      //   113	127	159	finally
      //   127	142	159	finally
      //   146	155	159	finally
      //   155	157	159	finally
      //   160	162	159	finally
      //   165	174	159	finally
      //   113	127	164	java/lang/InterruptedException
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/analytics/internal/zzi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */