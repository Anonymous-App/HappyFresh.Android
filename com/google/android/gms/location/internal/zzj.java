package com.google.android.gms.location.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.location.Location;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.zza.zzb;
import com.google.android.gms.common.internal.zze;
import com.google.android.gms.common.internal.zzi.zzc;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationStatusCodes;
import com.google.android.gms.location.copresence.internal.CopresenceApiOptions;
import com.google.android.gms.location.zze.zza;
import com.google.android.gms.location.zze.zzb;
import java.util.List;

public class zzj
  extends zzb
{
  private final zzi zzayR = new zzi(paramContext, this.zzayq);
  private final com.google.android.gms.location.copresence.internal.zzb zzayS;
  
  public zzj(Context paramContext, Looper paramLooper, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener, String paramString)
  {
    this(paramContext, paramLooper, paramConnectionCallbacks, paramOnConnectionFailedListener, paramString, new GoogleApiClient.Builder(paramContext).zzmx());
  }
  
  public zzj(Context paramContext, Looper paramLooper, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener, String paramString, zze paramzze)
  {
    this(paramContext, paramLooper, paramConnectionCallbacks, paramOnConnectionFailedListener, paramString, paramzze, CopresenceApiOptions.zzayn);
  }
  
  public zzj(Context paramContext, Looper paramLooper, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener, String paramString, zze paramzze, CopresenceApiOptions paramCopresenceApiOptions)
  {
    super(paramContext, paramLooper, paramConnectionCallbacks, paramOnConnectionFailedListener, paramString, paramzze);
    this.zzayS = com.google.android.gms.location.copresence.internal.zzb.zza(paramContext, paramzze.getAccountName(), paramzze.zzny(), this.zzayq, paramCopresenceApiOptions);
  }
  
  public void disconnect()
  {
    synchronized (this.zzayR)
    {
      boolean bool = isConnected();
      if (bool) {}
      try
      {
        this.zzayR.removeAllListeners();
        this.zzayR.zzux();
        super.disconnect();
        return;
      }
      catch (Exception localException)
      {
        for (;;)
        {
          Log.e("LocationClientImpl", "Client disconnected before listeners could be cleaned up", localException);
        }
      }
    }
  }
  
  public Location getLastLocation()
  {
    return this.zzayR.getLastLocation();
  }
  
  public boolean requiresAccount()
  {
    return true;
  }
  
  public void zza(long paramLong, PendingIntent paramPendingIntent)
    throws RemoteException
  {
    zznL();
    zzu.zzu(paramPendingIntent);
    if (paramLong >= 0L) {}
    for (boolean bool = true;; bool = false)
    {
      zzu.zzb(bool, "detectionIntervalMillis must be >= 0");
      ((zzg)zznM()).zza(paramLong, true, paramPendingIntent);
      return;
    }
  }
  
  public void zza(PendingIntent paramPendingIntent)
    throws RemoteException
  {
    zznL();
    zzu.zzu(paramPendingIntent);
    ((zzg)zznM()).zza(paramPendingIntent);
  }
  
  public void zza(PendingIntent paramPendingIntent, zze.zzb paramzzb)
    throws RemoteException
  {
    zznL();
    zzu.zzb(paramPendingIntent, "PendingIntent must be specified.");
    zzu.zzb(paramzzb, "OnRemoveGeofencesResultListener not provided.");
    if (paramzzb == null) {}
    for (paramzzb = null;; paramzzb = new zzb(paramzzb, this))
    {
      ((zzg)zznM()).zza(paramPendingIntent, paramzzb, getContext().getPackageName());
      return;
    }
  }
  
  public void zza(GeofencingRequest paramGeofencingRequest, PendingIntent paramPendingIntent, zze.zza paramzza)
    throws RemoteException
  {
    zznL();
    zzu.zzb(paramGeofencingRequest, "geofencingRequest can't be null.");
    zzu.zzb(paramPendingIntent, "PendingIntent must be specified.");
    zzu.zzb(paramzza, "OnAddGeofencesResultListener not provided.");
    if (paramzza == null) {}
    for (paramzza = null;; paramzza = new zzb(paramzza, this))
    {
      ((zzg)zznM()).zza(paramGeofencingRequest, paramPendingIntent, paramzza);
      return;
    }
  }
  
  public void zza(LocationCallback paramLocationCallback)
    throws RemoteException
  {
    this.zzayR.zza(paramLocationCallback);
  }
  
  public void zza(LocationListener paramLocationListener)
    throws RemoteException
  {
    this.zzayR.zza(paramLocationListener);
  }
  
  public void zza(LocationRequest paramLocationRequest, LocationListener paramLocationListener, Looper paramLooper)
    throws RemoteException
  {
    synchronized (this.zzayR)
    {
      this.zzayR.zza(paramLocationRequest, paramLocationListener, paramLooper);
      return;
    }
  }
  
  public void zza(LocationSettingsRequest paramLocationSettingsRequest, zza.zzb<LocationSettingsResult> paramzzb, String paramString)
    throws RemoteException
  {
    boolean bool2 = true;
    zznL();
    if (paramLocationSettingsRequest != null)
    {
      bool1 = true;
      zzu.zzb(bool1, "locationSettingsRequest can't be null nor empty.");
      if (paramzzb == null) {
        break label67;
      }
    }
    label67:
    for (boolean bool1 = bool2;; bool1 = false)
    {
      zzu.zzb(bool1, "listener can't be null.");
      paramzzb = new zzd(paramzzb);
      ((zzg)zznM()).zza(paramLocationSettingsRequest, paramzzb, paramString);
      return;
      bool1 = false;
      break;
    }
  }
  
  public void zza(LocationRequestInternal paramLocationRequestInternal, LocationCallback paramLocationCallback, Looper paramLooper)
    throws RemoteException
  {
    synchronized (this.zzayR)
    {
      this.zzayR.zza(paramLocationRequestInternal, paramLocationCallback, paramLooper);
      return;
    }
  }
  
  public void zza(List<String> paramList, zze.zzb paramzzb)
    throws RemoteException
  {
    zznL();
    boolean bool;
    String[] arrayOfString;
    if ((paramList != null) && (paramList.size() > 0))
    {
      bool = true;
      zzu.zzb(bool, "geofenceRequestIds can't be null nor empty.");
      zzu.zzb(paramzzb, "OnRemoveGeofencesResultListener not provided.");
      arrayOfString = (String[])paramList.toArray(new String[0]);
      if (paramzzb != null) {
        break label81;
      }
    }
    label81:
    for (paramList = null;; paramList = new zzb(paramzzb, this))
    {
      ((zzg)zznM()).zza(arrayOfString, paramList, getContext().getPackageName());
      return;
      bool = false;
      break;
    }
  }
  
  public void zzac(boolean paramBoolean)
    throws RemoteException
  {
    this.zzayR.zzac(paramBoolean);
  }
  
  public void zzb(Location paramLocation)
    throws RemoteException
  {
    this.zzayR.zzb(paramLocation);
  }
  
  public void zzb(LocationRequest paramLocationRequest, PendingIntent paramPendingIntent)
    throws RemoteException
  {
    this.zzayR.zzb(paramLocationRequest, paramPendingIntent);
  }
  
  public void zzd(PendingIntent paramPendingIntent)
    throws RemoteException
  {
    this.zzayR.zzd(paramPendingIntent);
  }
  
  public LocationAvailability zzuw()
  {
    return this.zzayR.zzuw();
  }
  
  private final class zza
    extends com.google.android.gms.common.internal.zzi<zzg>.zzc<zze.zza>
  {
    private final int zzTS;
    private final String[] zzayT;
    
    public zza(zze.zza paramzza, int paramInt, String[] paramArrayOfString)
    {
      super(paramzza);
      this.zzTS = LocationStatusCodes.zzgA(paramInt);
      this.zzayT = paramArrayOfString;
    }
    
    protected void zza(zze.zza paramzza)
    {
      if (paramzza != null) {
        paramzza.zza(this.zzTS, this.zzayT);
      }
    }
    
    protected void zznP() {}
  }
  
  private static final class zzb
    extends zzf.zza
  {
    private zze.zza zzayV;
    private zze.zzb zzayW;
    private zzj zzayX;
    
    public zzb(zze.zza paramzza, zzj paramzzj)
    {
      this.zzayV = paramzza;
      this.zzayW = null;
      this.zzayX = paramzzj;
    }
    
    public zzb(zze.zzb paramzzb, zzj paramzzj)
    {
      this.zzayW = paramzzb;
      this.zzayV = null;
      this.zzayX = paramzzj;
    }
    
    public void zza(int paramInt, PendingIntent paramPendingIntent)
    {
      if (this.zzayX == null)
      {
        Log.wtf("LocationClientImpl", "onRemoveGeofencesByPendingIntentResult called multiple times");
        return;
      }
      zzj localzzj1 = this.zzayX;
      zzj localzzj2 = this.zzayX;
      localzzj2.getClass();
      localzzj1.zza(new zzj.zzc(localzzj2, 1, this.zzayW, paramInt, paramPendingIntent));
      this.zzayX = null;
      this.zzayV = null;
      this.zzayW = null;
    }
    
    public void zza(int paramInt, String[] paramArrayOfString)
      throws RemoteException
    {
      if (this.zzayX == null)
      {
        Log.wtf("LocationClientImpl", "onAddGeofenceResult called multiple times");
        return;
      }
      zzj localzzj1 = this.zzayX;
      zzj localzzj2 = this.zzayX;
      localzzj2.getClass();
      localzzj1.zza(new zzj.zza(localzzj2, this.zzayV, paramInt, paramArrayOfString));
      this.zzayX = null;
      this.zzayV = null;
      this.zzayW = null;
    }
    
    public void zzb(int paramInt, String[] paramArrayOfString)
    {
      if (this.zzayX == null)
      {
        Log.wtf("LocationClientImpl", "onRemoveGeofencesByRequestIdsResult called multiple times");
        return;
      }
      zzj localzzj1 = this.zzayX;
      zzj localzzj2 = this.zzayX;
      localzzj2.getClass();
      localzzj1.zza(new zzj.zzc(localzzj2, 2, this.zzayW, paramInt, paramArrayOfString));
      this.zzayX = null;
      this.zzayV = null;
      this.zzayW = null;
    }
  }
  
  private final class zzc
    extends com.google.android.gms.common.internal.zzi<zzg>.zzc<zze.zzb>
  {
    private final PendingIntent mPendingIntent;
    private final int zzTS;
    private final String[] zzayT;
    private final int zzayY;
    
    public zzc(int paramInt1, zze.zzb paramzzb, int paramInt2, PendingIntent paramPendingIntent)
    {
      super(paramzzb);
      if (paramInt1 == 1) {}
      for (;;)
      {
        com.google.android.gms.common.internal.zzb.zzU(bool);
        this.zzayY = paramInt1;
        this.zzTS = LocationStatusCodes.zzgA(paramInt2);
        this.mPendingIntent = paramPendingIntent;
        this.zzayT = null;
        return;
        bool = false;
      }
    }
    
    public zzc(int paramInt1, zze.zzb paramzzb, int paramInt2, String[] paramArrayOfString)
    {
      super(paramzzb);
      if (paramInt1 == 2) {}
      for (boolean bool = true;; bool = false)
      {
        com.google.android.gms.common.internal.zzb.zzU(bool);
        this.zzayY = paramInt1;
        this.zzTS = LocationStatusCodes.zzgA(paramInt2);
        this.zzayT = paramArrayOfString;
        this.mPendingIntent = null;
        return;
      }
    }
    
    protected void zza(zze.zzb paramzzb)
    {
      if (paramzzb != null) {}
      switch (this.zzayY)
      {
      default: 
        Log.wtf("LocationClientImpl", "Unsupported action: " + this.zzayY);
        return;
      case 1: 
        paramzzb.zza(this.zzTS, this.mPendingIntent);
        return;
      }
      paramzzb.zzb(this.zzTS, this.zzayT);
    }
    
    protected void zznP() {}
  }
  
  private static final class zzd
    extends zzh.zza
  {
    private zza.zzb<LocationSettingsResult> zzayZ;
    
    public zzd(zza.zzb<LocationSettingsResult> paramzzb)
    {
      if (paramzzb != null) {}
      for (boolean bool = true;; bool = false)
      {
        zzu.zzb(bool, "listener can't be null.");
        this.zzayZ = paramzzb;
        return;
      }
    }
    
    public void zza(LocationSettingsResult paramLocationSettingsResult)
      throws RemoteException
    {
      this.zzayZ.zzm(paramLocationSettingsResult);
      this.zzayZ = null;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/internal/zzj.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */