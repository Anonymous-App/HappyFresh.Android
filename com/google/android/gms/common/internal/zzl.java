package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.IBinder;
import android.os.Message;
import com.google.android.gms.common.stats.zzb;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

final class zzl
  extends zzk
  implements Handler.Callback
{
  private final Handler mHandler;
  private final HashMap<zza, zzb> zzaaL = new HashMap();
  private final zzb zzaaM;
  private final long zzaaN;
  private final Context zzqw;
  
  zzl(Context paramContext)
  {
    this.zzqw = paramContext.getApplicationContext();
    this.mHandler = new Handler(paramContext.getMainLooper(), this);
    this.zzaaM = zzb.zzoO();
    this.zzaaN = 5000L;
  }
  
  private boolean zza(zza paramzza, ServiceConnection paramServiceConnection, String paramString)
  {
    zzu.zzb(paramServiceConnection, "ServiceConnection must not be null");
    for (;;)
    {
      zzb localzzb;
      synchronized (this.zzaaL)
      {
        localzzb = (zzb)this.zzaaL.get(paramzza);
        if (localzzb == null)
        {
          localzzb = new zzb(paramzza);
          localzzb.zza(paramServiceConnection, paramString);
          localzzb.zzcc(paramString);
          this.zzaaL.put(paramzza, localzzb);
          paramzza = localzzb;
          boolean bool = paramzza.isBound();
          return bool;
        }
        this.mHandler.removeMessages(0, localzzb);
        if (localzzb.zza(paramServiceConnection)) {
          throw new IllegalStateException("Trying to bind a GmsServiceConnection that was already connected before.  config=" + paramzza);
        }
      }
      localzzb.zza(paramServiceConnection, paramString);
      switch (localzzb.getState())
      {
      case 1: 
        paramServiceConnection.onServiceConnected(localzzb.getComponentName(), localzzb.getBinder());
        paramzza = localzzb;
        break;
      case 2: 
        localzzb.zzcc(paramString);
        paramzza = localzzb;
        break;
      default: 
        paramzza = localzzb;
      }
    }
  }
  
  private void zzb(zza paramzza, ServiceConnection paramServiceConnection, String paramString)
  {
    zzu.zzb(paramServiceConnection, "ServiceConnection must not be null");
    zzb localzzb;
    synchronized (this.zzaaL)
    {
      localzzb = (zzb)this.zzaaL.get(paramzza);
      if (localzzb == null) {
        throw new IllegalStateException("Nonexistent connection status for service config: " + paramzza);
      }
    }
    if (!localzzb.zza(paramServiceConnection)) {
      throw new IllegalStateException("Trying to unbind a GmsServiceConnection  that was not bound before.  config=" + paramzza);
    }
    localzzb.zzb(paramServiceConnection, paramString);
    if (localzzb.zznW())
    {
      paramzza = this.mHandler.obtainMessage(0, localzzb);
      this.mHandler.sendMessageDelayed(paramzza, this.zzaaN);
    }
  }
  
  public boolean handleMessage(Message arg1)
  {
    switch (???.what)
    {
    default: 
      return false;
    }
    zzb localzzb = (zzb)???.obj;
    synchronized (this.zzaaL)
    {
      if (localzzb.zznW())
      {
        if (localzzb.isBound()) {
          localzzb.zzcd("GmsClientSupervisor");
        }
        this.zzaaL.remove(zzb.zza(localzzb));
      }
      return true;
    }
  }
  
  public boolean zza(ComponentName paramComponentName, ServiceConnection paramServiceConnection, String paramString)
  {
    return zza(new zza(paramComponentName), paramServiceConnection, paramString);
  }
  
  public boolean zza(String paramString1, ServiceConnection paramServiceConnection, String paramString2)
  {
    return zza(new zza(paramString1), paramServiceConnection, paramString2);
  }
  
  public void zzb(ComponentName paramComponentName, ServiceConnection paramServiceConnection, String paramString)
  {
    zzb(new zza(paramComponentName), paramServiceConnection, paramString);
  }
  
  public void zzb(String paramString1, ServiceConnection paramServiceConnection, String paramString2)
  {
    zzb(new zza(paramString1), paramServiceConnection, paramString2);
  }
  
  private static final class zza
  {
    private final ComponentName zzaaO;
    private final String zzuO;
    
    public zza(ComponentName paramComponentName)
    {
      this.zzuO = null;
      this.zzaaO = ((ComponentName)zzu.zzu(paramComponentName));
    }
    
    public zza(String paramString)
    {
      this.zzuO = zzu.zzcj(paramString);
      this.zzaaO = null;
    }
    
    public boolean equals(Object paramObject)
    {
      if (this == paramObject) {}
      do
      {
        return true;
        if (!(paramObject instanceof zza)) {
          return false;
        }
        paramObject = (zza)paramObject;
      } while ((zzt.equal(this.zzuO, ((zza)paramObject).zzuO)) && (zzt.equal(this.zzaaO, ((zza)paramObject).zzaaO)));
      return false;
    }
    
    public int hashCode()
    {
      return zzt.hashCode(new Object[] { this.zzuO, this.zzaaO });
    }
    
    public String toString()
    {
      if (this.zzuO == null) {
        return this.zzaaO.flattenToString();
      }
      return this.zzuO;
    }
    
    public Intent zznV()
    {
      if (this.zzuO != null) {
        return new Intent(this.zzuO).setPackage("com.google.android.gms");
      }
      return new Intent().setComponent(this.zzaaO);
    }
  }
  
  private final class zzb
  {
    private int mState;
    private IBinder zzZQ;
    private ComponentName zzaaO;
    private final zza zzaaP;
    private final Set<ServiceConnection> zzaaQ;
    private boolean zzaaR;
    private final zzl.zza zzaaS;
    
    public zzb(zzl.zza paramzza)
    {
      this.zzaaS = paramzza;
      this.zzaaP = new zza();
      this.zzaaQ = new HashSet();
      this.mState = 2;
    }
    
    public IBinder getBinder()
    {
      return this.zzZQ;
    }
    
    public ComponentName getComponentName()
    {
      return this.zzaaO;
    }
    
    public int getState()
    {
      return this.mState;
    }
    
    public boolean isBound()
    {
      return this.zzaaR;
    }
    
    public void zza(ServiceConnection paramServiceConnection, String paramString)
    {
      zzl.zzc(zzl.this).zza(zzl.zzb(zzl.this), paramServiceConnection, paramString, this.zzaaS.zznV());
      this.zzaaQ.add(paramServiceConnection);
    }
    
    public boolean zza(ServiceConnection paramServiceConnection)
    {
      return this.zzaaQ.contains(paramServiceConnection);
    }
    
    public void zzb(ServiceConnection paramServiceConnection, String paramString)
    {
      zzl.zzc(zzl.this).zzb(zzl.zzb(zzl.this), paramServiceConnection);
      this.zzaaQ.remove(paramServiceConnection);
    }
    
    public void zzcc(String paramString)
    {
      this.zzaaR = zzl.zzc(zzl.this).zza(zzl.zzb(zzl.this), paramString, this.zzaaS.zznV(), this.zzaaP, 129);
      if (this.zzaaR)
      {
        this.mState = 3;
        return;
      }
      try
      {
        zzl.zzc(zzl.this).zza(zzl.zzb(zzl.this), this.zzaaP);
        return;
      }
      catch (IllegalArgumentException paramString) {}
    }
    
    public void zzcd(String paramString)
    {
      zzl.zzc(zzl.this).zza(zzl.zzb(zzl.this), this.zzaaP);
      this.zzaaR = false;
      this.mState = 2;
    }
    
    public boolean zznW()
    {
      return this.zzaaQ.isEmpty();
    }
    
    public class zza
      implements ServiceConnection
    {
      public zza() {}
      
      public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
      {
        synchronized (zzl.zza(zzl.this))
        {
          zzl.zzb.zza(zzl.zzb.this, paramIBinder);
          zzl.zzb.zza(zzl.zzb.this, paramComponentName);
          Iterator localIterator = zzl.zzb.zzb(zzl.zzb.this).iterator();
          if (localIterator.hasNext()) {
            ((ServiceConnection)localIterator.next()).onServiceConnected(paramComponentName, paramIBinder);
          }
        }
        zzl.zzb.zza(zzl.zzb.this, 1);
      }
      
      public void onServiceDisconnected(ComponentName paramComponentName)
      {
        synchronized (zzl.zza(zzl.this))
        {
          zzl.zzb.zza(zzl.zzb.this, null);
          zzl.zzb.zza(zzl.zzb.this, paramComponentName);
          Iterator localIterator = zzl.zzb.zzb(zzl.zzb.this).iterator();
          if (localIterator.hasNext()) {
            ((ServiceConnection)localIterator.next()).onServiceDisconnected(paramComponentName);
          }
        }
        zzl.zzb.zza(zzl.zzb.this, 2);
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/internal/zzl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */