package com.google.android.gms.location.places.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzi;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.PlaceReport;
import com.google.android.gms.location.places.PlacesOptions;
import com.google.android.gms.location.places.PlacesOptions.Builder;
import com.google.android.gms.location.places.zzm;
import java.util.Locale;

public class zzj
  extends zzi<zze>
{
  private final PlacesParams zzaAl;
  private final Locale zzaAm = Locale.getDefault();
  
  public zzj(Context paramContext, Looper paramLooper, com.google.android.gms.common.internal.zze paramzze, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener, String paramString1, String paramString2, PlacesOptions paramPlacesOptions)
  {
    super(paramContext, paramLooper, 67, paramConnectionCallbacks, paramOnConnectionFailedListener, paramzze);
    paramContext = null;
    if (paramzze.getAccount() != null) {
      paramContext = paramzze.getAccount().name;
    }
    this.zzaAl = new PlacesParams(paramString1, this.zzaAm, paramContext, paramPlacesOptions.zzazX, paramString2);
  }
  
  protected String getServiceDescriptor()
  {
    return "com.google.android.gms.location.places.internal.IGooglePlaceDetectionService";
  }
  
  protected String getStartServiceAction()
  {
    return "com.google.android.gms.location.places.PlaceDetectionApi";
  }
  
  public void zza(zzm paramzzm, PlaceFilter paramPlaceFilter)
    throws RemoteException
  {
    PlaceFilter localPlaceFilter = paramPlaceFilter;
    if (paramPlaceFilter == null) {
      localPlaceFilter = PlaceFilter.zzuJ();
    }
    ((zze)zznM()).zza(localPlaceFilter, this.zzaAl, paramzzm);
  }
  
  public void zza(zzm paramzzm, PlaceReport paramPlaceReport)
    throws RemoteException
  {
    zzu.zzu(paramPlaceReport);
    ((zze)zznM()).zza(paramPlaceReport, this.zzaAl, paramzzm);
  }
  
  protected zze zzcd(IBinder paramIBinder)
  {
    return zze.zza.zzbZ(paramIBinder);
  }
  
  public static class zza
    implements Api.zza<zzj, PlacesOptions>
  {
    private final String zzaAn;
    private final String zzaAo;
    
    public zza(String paramString1, String paramString2)
    {
      this.zzaAn = paramString1;
      this.zzaAo = paramString2;
    }
    
    public int getPriority()
    {
      return Integer.MAX_VALUE;
    }
    
    public zzj zzb(Context paramContext, Looper paramLooper, com.google.android.gms.common.internal.zze paramzze, PlacesOptions paramPlacesOptions, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
    {
      String str1;
      String str2;
      if (this.zzaAn != null)
      {
        str1 = this.zzaAn;
        if (this.zzaAo == null) {
          break label73;
        }
        str2 = this.zzaAo;
        label26:
        if (paramPlacesOptions != null) {
          break label82;
        }
        paramPlacesOptions = new PlacesOptions.Builder().build();
      }
      label73:
      label82:
      for (;;)
      {
        return new zzj(paramContext, paramLooper, paramzze, paramConnectionCallbacks, paramOnConnectionFailedListener, str1, str2, paramPlacesOptions);
        str1 = paramContext.getPackageName();
        break;
        str2 = paramContext.getPackageName();
        break label26;
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/internal/zzj.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */