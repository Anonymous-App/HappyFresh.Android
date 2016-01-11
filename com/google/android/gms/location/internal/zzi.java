package com.google.android.gms.location.internal;

import android.app.PendingIntent;
import android.content.ContentProviderClient;
import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.zzc;
import com.google.android.gms.location.zzc.zza;
import com.google.android.gms.location.zzd;
import com.google.android.gms.location.zzd.zza;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class zzi
{
  private final Context mContext;
  private Map<LocationListener, zzc> zzakE = new HashMap();
  private ContentProviderClient zzayL = null;
  private boolean zzayM = false;
  private Map<LocationCallback, zza> zzayN = new HashMap();
  private final zzn<zzg> zzayq;
  
  public zzi(Context paramContext, zzn<zzg> paramzzn)
  {
    this.mContext = paramContext;
    this.zzayq = paramzzn;
  }
  
  private zza zza(LocationCallback paramLocationCallback, Looper paramLooper)
  {
    synchronized (this.zzakE)
    {
      zza localzza2 = (zza)this.zzayN.get(paramLocationCallback);
      zza localzza1 = localzza2;
      if (localzza2 == null) {
        localzza1 = new zza(paramLocationCallback, paramLooper);
      }
      this.zzayN.put(paramLocationCallback, localzza1);
      return localzza1;
    }
  }
  
  private zzc zza(LocationListener paramLocationListener, Looper paramLooper)
  {
    synchronized (this.zzakE)
    {
      zzc localzzc2 = (zzc)this.zzakE.get(paramLocationListener);
      zzc localzzc1 = localzzc2;
      if (localzzc2 == null) {
        localzzc1 = new zzc(paramLocationListener, paramLooper);
      }
      this.zzakE.put(paramLocationListener, localzzc1);
      return localzzc1;
    }
  }
  
  public Location getLastLocation()
  {
    this.zzayq.zznL();
    try
    {
      Location localLocation = ((zzg)this.zzayq.zznM()).zzdl(this.mContext.getPackageName());
      return localLocation;
    }
    catch (RemoteException localRemoteException)
    {
      throw new IllegalStateException(localRemoteException);
    }
  }
  
  public void removeAllListeners()
  {
    Object localObject2;
    try
    {
      synchronized (this.zzakE)
      {
        Iterator localIterator1 = this.zzakE.values().iterator();
        while (localIterator1.hasNext())
        {
          localObject2 = (zzc)localIterator1.next();
          if (localObject2 != null) {
            ((zzg)this.zzayq.zznM()).zza(LocationRequestUpdateData.zzb((zzd)localObject2));
          }
        }
      }
      this.zzakE.clear();
    }
    catch (RemoteException localRemoteException)
    {
      throw new IllegalStateException(localRemoteException);
    }
    Iterator localIterator2 = this.zzayN.values().iterator();
    while (localIterator2.hasNext())
    {
      localObject2 = (zza)localIterator2.next();
      if (localObject2 != null) {
        ((zzg)this.zzayq.zznM()).zza(LocationRequestUpdateData.zza((zzc)localObject2));
      }
    }
    this.zzayN.clear();
  }
  
  public void zza(LocationCallback paramLocationCallback)
    throws RemoteException
  {
    this.zzayq.zznL();
    zzu.zzb(paramLocationCallback, "Invalid null callback");
    synchronized (this.zzayN)
    {
      paramLocationCallback = (zza)this.zzayN.remove(paramLocationCallback);
      if (paramLocationCallback != null)
      {
        paramLocationCallback.release();
        ((zzg)this.zzayq.zznM()).zza(LocationRequestUpdateData.zza(paramLocationCallback));
      }
      return;
    }
  }
  
  public void zza(LocationListener paramLocationListener)
    throws RemoteException
  {
    this.zzayq.zznL();
    zzu.zzb(paramLocationListener, "Invalid null listener");
    synchronized (this.zzakE)
    {
      paramLocationListener = (zzc)this.zzakE.remove(paramLocationListener);
      if ((this.zzayL != null) && (this.zzakE.isEmpty()))
      {
        this.zzayL.release();
        this.zzayL = null;
      }
      if (paramLocationListener != null)
      {
        paramLocationListener.release();
        ((zzg)this.zzayq.zznM()).zza(LocationRequestUpdateData.zzb(paramLocationListener));
      }
      return;
    }
  }
  
  public void zza(LocationRequest paramLocationRequest, LocationListener paramLocationListener, Looper paramLooper)
    throws RemoteException
  {
    this.zzayq.zznL();
    paramLocationListener = zza(paramLocationListener, paramLooper);
    ((zzg)this.zzayq.zznM()).zza(LocationRequestUpdateData.zzb(LocationRequestInternal.zzb(paramLocationRequest), paramLocationListener));
  }
  
  public void zza(LocationRequestInternal paramLocationRequestInternal, LocationCallback paramLocationCallback, Looper paramLooper)
    throws RemoteException
  {
    this.zzayq.zznL();
    paramLocationCallback = zza(paramLocationCallback, paramLooper);
    ((zzg)this.zzayq.zznM()).zza(LocationRequestUpdateData.zza(paramLocationRequestInternal, paramLocationCallback));
  }
  
  public void zzac(boolean paramBoolean)
    throws RemoteException
  {
    this.zzayq.zznL();
    ((zzg)this.zzayq.zznM()).zzac(paramBoolean);
    this.zzayM = paramBoolean;
  }
  
  public void zzb(Location paramLocation)
    throws RemoteException
  {
    this.zzayq.zznL();
    ((zzg)this.zzayq.zznM()).zzb(paramLocation);
  }
  
  public void zzb(LocationRequest paramLocationRequest, PendingIntent paramPendingIntent)
    throws RemoteException
  {
    this.zzayq.zznL();
    ((zzg)this.zzayq.zznM()).zza(LocationRequestUpdateData.zzb(LocationRequestInternal.zzb(paramLocationRequest), paramPendingIntent));
  }
  
  public void zzd(PendingIntent paramPendingIntent)
    throws RemoteException
  {
    this.zzayq.zznL();
    ((zzg)this.zzayq.zznM()).zza(LocationRequestUpdateData.zze(paramPendingIntent));
  }
  
  public LocationAvailability zzuw()
  {
    this.zzayq.zznL();
    try
    {
      LocationAvailability localLocationAvailability = ((zzg)this.zzayq.zznM()).zzdm(this.mContext.getPackageName());
      return localLocationAvailability;
    }
    catch (RemoteException localRemoteException)
    {
      throw new IllegalStateException(localRemoteException);
    }
  }
  
  public void zzux()
  {
    if (this.zzayM) {}
    try
    {
      zzac(false);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new IllegalStateException(localRemoteException);
    }
  }
  
  private static class zza
    extends zzc.zza
  {
    private Handler zzayO;
    
    zza(final LocationCallback paramLocationCallback, Looper paramLooper)
    {
      Looper localLooper = paramLooper;
      if (paramLooper == null)
      {
        localLooper = Looper.myLooper();
        if (localLooper == null) {
          break label45;
        }
      }
      label45:
      for (boolean bool = true;; bool = false)
      {
        zzu.zza(bool, "Can't create handler inside thread that has not called Looper.prepare()");
        this.zzayO = new Handler(localLooper)
        {
          public void handleMessage(Message paramAnonymousMessage)
          {
            switch (paramAnonymousMessage.what)
            {
            default: 
              return;
            case 0: 
              paramLocationCallback.onLocationResult((LocationResult)paramAnonymousMessage.obj);
              return;
            }
            paramLocationCallback.onLocationAvailability((LocationAvailability)paramAnonymousMessage.obj);
          }
        };
        return;
      }
    }
    
    private void zzb(int paramInt, Object paramObject)
    {
      if (this.zzayO == null)
      {
        Log.e("LocationClientHelper", "Received a data in client after calling removeLocationUpdates.");
        return;
      }
      Message localMessage = Message.obtain();
      localMessage.what = paramInt;
      localMessage.obj = paramObject;
      this.zzayO.sendMessage(localMessage);
    }
    
    public void onLocationAvailability(LocationAvailability paramLocationAvailability)
    {
      zzb(1, paramLocationAvailability);
    }
    
    public void onLocationResult(LocationResult paramLocationResult)
    {
      zzb(0, paramLocationResult);
    }
    
    public void release()
    {
      this.zzayO = null;
    }
  }
  
  private static class zzb
    extends Handler
  {
    private final LocationListener zzayQ;
    
    public zzb(LocationListener paramLocationListener)
    {
      this.zzayQ = paramLocationListener;
    }
    
    public zzb(LocationListener paramLocationListener, Looper paramLooper)
    {
      super();
      this.zzayQ = paramLocationListener;
    }
    
    public void handleMessage(Message paramMessage)
    {
      switch (paramMessage.what)
      {
      default: 
        Log.e("LocationClientHelper", "unknown message in LocationHandler.handleMessage");
        return;
      }
      paramMessage = new Location((Location)paramMessage.obj);
      this.zzayQ.onLocationChanged(paramMessage);
    }
  }
  
  private static class zzc
    extends zzd.zza
  {
    private Handler zzayO;
    
    zzc(LocationListener paramLocationListener, Looper paramLooper)
    {
      boolean bool;
      if (paramLooper == null)
      {
        if (Looper.myLooper() != null)
        {
          bool = true;
          zzu.zza(bool, "Can't create handler inside thread that has not called Looper.prepare()");
        }
      }
      else {
        if (paramLooper != null) {
          break label46;
        }
      }
      label46:
      for (paramLocationListener = new zzi.zzb(paramLocationListener);; paramLocationListener = new zzi.zzb(paramLocationListener, paramLooper))
      {
        this.zzayO = paramLocationListener;
        return;
        bool = false;
        break;
      }
    }
    
    public void onLocationChanged(Location paramLocation)
    {
      if (this.zzayO == null)
      {
        Log.e("LocationClientHelper", "Received a location in client after calling removeLocationUpdates.");
        return;
      }
      Message localMessage = Message.obtain();
      localMessage.what = 1;
      localMessage.obj = paramLocation;
      this.zzayO.sendMessage(localMessage);
    }
    
    public void release()
    {
      this.zzayO = null;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/internal/zzi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */