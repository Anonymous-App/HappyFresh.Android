package com.google.android.gms.location.places.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zze;
import com.google.android.gms.common.internal.zzi;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.location.places.AddPlaceRequest;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.PlacesOptions;
import com.google.android.gms.location.places.PlacesOptions.Builder;
import com.google.android.gms.location.places.zzm;
import com.google.android.gms.maps.model.LatLngBounds;
import java.util.List;
import java.util.Locale;

public class zzd
  extends zzi<zzf>
{
  private final PlacesParams zzaAl;
  private final Locale zzaAm = Locale.getDefault();
  
  public zzd(Context paramContext, Looper paramLooper, zze paramzze, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener, String paramString1, String paramString2, PlacesOptions paramPlacesOptions)
  {
    super(paramContext, paramLooper, 65, paramConnectionCallbacks, paramOnConnectionFailedListener, paramzze);
    paramContext = null;
    if (paramzze.getAccount() != null) {
      paramContext = paramzze.getAccount().name;
    }
    this.zzaAl = new PlacesParams(paramString1, this.zzaAm, paramContext, paramPlacesOptions.zzazX, paramString2);
  }
  
  protected String getServiceDescriptor()
  {
    return "com.google.android.gms.location.places.internal.IGooglePlacesService";
  }
  
  protected String getStartServiceAction()
  {
    return "com.google.android.gms.location.places.GeoDataApi";
  }
  
  public void zza(zzm paramzzm, AddPlaceRequest paramAddPlaceRequest)
    throws RemoteException
  {
    zzu.zzb(paramAddPlaceRequest, "userAddedPlace == null");
    ((zzf)zznM()).zza(paramAddPlaceRequest, this.zzaAl, paramzzm);
  }
  
  public void zza(zzm paramzzm, String paramString, LatLngBounds paramLatLngBounds, AutocompleteFilter paramAutocompleteFilter)
    throws RemoteException
  {
    zzu.zzb(paramString, "query == null");
    zzu.zzb(paramLatLngBounds, "bounds == null");
    zzu.zzb(paramzzm, "callback == null");
    if (paramAutocompleteFilter == null) {
      paramAutocompleteFilter = AutocompleteFilter.create(null);
    }
    for (;;)
    {
      ((zzf)zznM()).zza(paramString, paramLatLngBounds, paramAutocompleteFilter, this.zzaAl, paramzzm);
      return;
    }
  }
  
  public void zza(zzm paramzzm, List<String> paramList)
    throws RemoteException
  {
    ((zzf)zznM()).zzb(paramList, this.zzaAl, paramzzm);
  }
  
  protected zzf zzbY(IBinder paramIBinder)
  {
    return zzf.zza.zzca(paramIBinder);
  }
  
  public static class zza
    implements Api.zza<zzd, PlacesOptions>
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
    
    public zzd zza(Context paramContext, Looper paramLooper, zze paramzze, PlacesOptions paramPlacesOptions, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
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
        return new zzd(paramContext, paramLooper, paramzze, paramConnectionCallbacks, paramOnConnectionFailedListener, str1, str2, paramPlacesOptions);
        str1 = paramContext.getPackageName();
        break;
        str2 = paramContext.getPackageName();
        break label26;
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/internal/zzd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */