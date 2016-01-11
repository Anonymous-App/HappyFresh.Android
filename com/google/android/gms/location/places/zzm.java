package com.google.android.gms.location.places;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.Api.Client;
import com.google.android.gms.common.api.Api.ClientKey;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zza.zza;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.internal.zzlm;
import com.google.android.gms.location.places.internal.zzh.zza;
import com.google.android.gms.location.places.personalized.zzd;

public class zzm
  extends zzh.zza
{
  private static final String TAG = zzm.class.getSimpleName();
  private final Context mContext;
  private final zzd zzazS;
  private final zza zzazT;
  private final zze zzazU;
  private final zzf zzazV;
  private final zzc zzazW;
  
  public zzm(zza paramzza)
  {
    this.zzazS = null;
    this.zzazT = paramzza;
    this.zzazU = null;
    this.zzazV = null;
    this.zzazW = null;
    this.mContext = null;
  }
  
  public zzm(zzc paramzzc, Context paramContext)
  {
    this.zzazS = null;
    this.zzazT = null;
    this.zzazU = null;
    this.zzazV = null;
    this.zzazW = paramzzc;
    this.mContext = paramContext;
  }
  
  public zzm(zzd paramzzd, Context paramContext)
  {
    this.zzazS = paramzzd;
    this.zzazT = null;
    this.zzazU = null;
    this.zzazV = null;
    this.zzazW = null;
    this.mContext = paramContext;
  }
  
  public zzm(zzf paramzzf)
  {
    this.zzazS = null;
    this.zzazT = null;
    this.zzazU = null;
    this.zzazV = paramzzf;
    this.zzazW = null;
    this.mContext = null;
  }
  
  public void zzY(DataHolder paramDataHolder)
    throws RemoteException
  {
    if (this.zzazS != null) {}
    for (boolean bool = true;; bool = false)
    {
      zzu.zza(bool, "placeEstimator cannot be null");
      if (paramDataHolder != null) {
        break;
      }
      if (Log.isLoggable(TAG, 6)) {
        Log.e(TAG, "onPlaceEstimated received null DataHolder: " + zzlm.zzpa());
      }
      this.zzazS.zzr(Status.zzXR);
      return;
    }
    paramDataHolder = new PlaceLikelihoodBuffer(paramDataHolder, 100, this.mContext);
    this.zzazS.setResult(paramDataHolder);
  }
  
  public void zzZ(DataHolder paramDataHolder)
    throws RemoteException
  {
    if (paramDataHolder == null)
    {
      if (Log.isLoggable(TAG, 6)) {
        Log.e(TAG, "onAutocompletePrediction received null DataHolder: " + zzlm.zzpa());
      }
      this.zzazT.zzr(Status.zzXR);
      return;
    }
    this.zzazT.setResult(new AutocompletePredictionBuffer(paramDataHolder));
  }
  
  public void zzaF(Status paramStatus)
    throws RemoteException
  {
    this.zzazV.setResult(paramStatus);
  }
  
  public void zzaa(DataHolder paramDataHolder)
    throws RemoteException
  {
    if (paramDataHolder == null)
    {
      if (Log.isLoggable(TAG, 6)) {
        Log.e(TAG, "onPlaceUserDataFetched received null DataHolder: " + zzlm.zzpa());
      }
      this.zzazU.zzr(Status.zzXR);
      return;
    }
    this.zzazU.setResult(new zzd(paramDataHolder));
  }
  
  public void zzab(DataHolder paramDataHolder)
    throws RemoteException
  {
    paramDataHolder = new PlaceBuffer(paramDataHolder, this.mContext);
    this.zzazW.setResult(paramDataHolder);
  }
  
  public static abstract class zza<A extends Api.Client>
    extends zzm.zzb<AutocompletePredictionBuffer, A>
  {
    public zza(Api.ClientKey<A> paramClientKey, GoogleApiClient paramGoogleApiClient)
    {
      super(paramGoogleApiClient);
    }
    
    protected AutocompletePredictionBuffer zzaG(Status paramStatus)
    {
      return new AutocompletePredictionBuffer(DataHolder.zzbi(paramStatus.getStatusCode()));
    }
  }
  
  public static abstract class zzb<R extends Result, A extends Api.Client>
    extends zza.zza<R, A>
  {
    public zzb(Api.ClientKey<A> paramClientKey, GoogleApiClient paramGoogleApiClient)
    {
      super(paramGoogleApiClient);
    }
  }
  
  public static abstract class zzc<A extends Api.Client>
    extends zzm.zzb<PlaceBuffer, A>
  {
    public zzc(Api.ClientKey<A> paramClientKey, GoogleApiClient paramGoogleApiClient)
    {
      super(paramGoogleApiClient);
    }
    
    protected PlaceBuffer zzaH(Status paramStatus)
    {
      return new PlaceBuffer(DataHolder.zzbi(paramStatus.getStatusCode()), null);
    }
  }
  
  public static abstract class zzd<A extends Api.Client>
    extends zzm.zzb<PlaceLikelihoodBuffer, A>
  {
    public zzd(Api.ClientKey<A> paramClientKey, GoogleApiClient paramGoogleApiClient)
    {
      super(paramGoogleApiClient);
    }
    
    protected PlaceLikelihoodBuffer zzaI(Status paramStatus)
    {
      return new PlaceLikelihoodBuffer(DataHolder.zzbi(paramStatus.getStatusCode()), 100, null);
    }
  }
  
  public static abstract class zze<A extends Api.Client>
    extends zzm.zzb<zzd, A>
  {
    protected zzd zzaJ(Status paramStatus)
    {
      return zzd.zzaK(paramStatus);
    }
  }
  
  public static abstract class zzf<A extends Api.Client>
    extends zzm.zzb<Status, A>
  {
    public zzf(Api.ClientKey<A> paramClientKey, GoogleApiClient paramGoogleApiClient)
    {
      super(paramGoogleApiClient);
    }
    
    protected Status zzb(Status paramStatus)
    {
      return paramStatus;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/location/places/zzm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */