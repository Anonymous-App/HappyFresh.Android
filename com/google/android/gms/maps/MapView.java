package com.google.android.gms.maps;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.dynamic.zza;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.dynamic.zzf;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.internal.IMapViewDelegate;
import com.google.android.gms.maps.internal.MapLifecycleDelegate;
import com.google.android.gms.maps.internal.zzc;
import com.google.android.gms.maps.internal.zzm.zza;
import com.google.android.gms.maps.internal.zzy;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MapView
  extends FrameLayout
{
  private GoogleMap zzaBV;
  private final zzb zzaCb;
  
  public MapView(Context paramContext)
  {
    super(paramContext);
    this.zzaCb = new zzb(this, paramContext, null);
    init();
  }
  
  public MapView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.zzaCb = new zzb(this, paramContext, GoogleMapOptions.createFromAttributes(paramContext, paramAttributeSet));
    init();
  }
  
  public MapView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.zzaCb = new zzb(this, paramContext, GoogleMapOptions.createFromAttributes(paramContext, paramAttributeSet));
    init();
  }
  
  public MapView(Context paramContext, GoogleMapOptions paramGoogleMapOptions)
  {
    super(paramContext);
    this.zzaCb = new zzb(this, paramContext, paramGoogleMapOptions);
    init();
  }
  
  private void init()
  {
    setClickable(true);
  }
  
  @Deprecated
  public final GoogleMap getMap()
  {
    if (this.zzaBV != null) {
      return this.zzaBV;
    }
    this.zzaCb.zzvu();
    if (this.zzaCb.zzqj() == null) {
      return null;
    }
    try
    {
      this.zzaBV = new GoogleMap(((zza)this.zzaCb.zzqj()).zzvv().getMap());
      return this.zzaBV;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }
  
  public void getMapAsync(OnMapReadyCallback paramOnMapReadyCallback)
  {
    zzu.zzbY("getMapAsync() must be called on the main thread");
    this.zzaCb.getMapAsync(paramOnMapReadyCallback);
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    this.zzaCb.onCreate(paramBundle);
    if (this.zzaCb.zzqj() == null) {
      zza.zzb(this);
    }
  }
  
  public final void onDestroy()
  {
    this.zzaCb.onDestroy();
  }
  
  public final void onLowMemory()
  {
    this.zzaCb.onLowMemory();
  }
  
  public final void onPause()
  {
    this.zzaCb.onPause();
  }
  
  public final void onResume()
  {
    this.zzaCb.onResume();
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    this.zzaCb.onSaveInstanceState(paramBundle);
  }
  
  static class zza
    implements MapLifecycleDelegate
  {
    private final ViewGroup zzaCc;
    private final IMapViewDelegate zzaCd;
    private View zzaCe;
    
    public zza(ViewGroup paramViewGroup, IMapViewDelegate paramIMapViewDelegate)
    {
      this.zzaCd = ((IMapViewDelegate)zzu.zzu(paramIMapViewDelegate));
      this.zzaCc = ((ViewGroup)zzu.zzu(paramViewGroup));
    }
    
    public void getMapAsync(final OnMapReadyCallback paramOnMapReadyCallback)
    {
      try
      {
        this.zzaCd.getMapAsync(new zzm.zza()
        {
          public void zza(IGoogleMapDelegate paramAnonymousIGoogleMapDelegate)
            throws RemoteException
          {
            paramOnMapReadyCallback.onMapReady(new GoogleMap(paramAnonymousIGoogleMapDelegate));
          }
        });
        return;
      }
      catch (RemoteException paramOnMapReadyCallback)
      {
        throw new RuntimeRemoteException(paramOnMapReadyCallback);
      }
    }
    
    public void onCreate(Bundle paramBundle)
    {
      try
      {
        this.zzaCd.onCreate(paramBundle);
        this.zzaCe = ((View)zze.zzn(this.zzaCd.getView()));
        this.zzaCc.removeAllViews();
        this.zzaCc.addView(this.zzaCe);
        return;
      }
      catch (RemoteException paramBundle)
      {
        throw new RuntimeRemoteException(paramBundle);
      }
    }
    
    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
    {
      throw new UnsupportedOperationException("onCreateView not allowed on MapViewDelegate");
    }
    
    public void onDestroy()
    {
      try
      {
        this.zzaCd.onDestroy();
        return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
    }
    
    public void onDestroyView()
    {
      throw new UnsupportedOperationException("onDestroyView not allowed on MapViewDelegate");
    }
    
    public void onInflate(Activity paramActivity, Bundle paramBundle1, Bundle paramBundle2)
    {
      throw new UnsupportedOperationException("onInflate not allowed on MapViewDelegate");
    }
    
    public void onLowMemory()
    {
      try
      {
        this.zzaCd.onLowMemory();
        return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
    }
    
    public void onPause()
    {
      try
      {
        this.zzaCd.onPause();
        return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
    }
    
    public void onResume()
    {
      try
      {
        this.zzaCd.onResume();
        return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
    }
    
    public void onSaveInstanceState(Bundle paramBundle)
    {
      try
      {
        this.zzaCd.onSaveInstanceState(paramBundle);
        return;
      }
      catch (RemoteException paramBundle)
      {
        throw new RuntimeRemoteException(paramBundle);
      }
    }
    
    public void onStart() {}
    
    public void onStop() {}
    
    public IMapViewDelegate zzvv()
    {
      return this.zzaCd;
    }
  }
  
  static class zzb
    extends zza<MapView.zza>
  {
    private final Context mContext;
    protected zzf<MapView.zza> zzaBZ;
    private final List<OnMapReadyCallback> zzaCa = new ArrayList();
    private final ViewGroup zzaCg;
    private final GoogleMapOptions zzaCh;
    
    zzb(ViewGroup paramViewGroup, Context paramContext, GoogleMapOptions paramGoogleMapOptions)
    {
      this.zzaCg = paramViewGroup;
      this.mContext = paramContext;
      this.zzaCh = paramGoogleMapOptions;
    }
    
    public void getMapAsync(OnMapReadyCallback paramOnMapReadyCallback)
    {
      if (zzqj() != null)
      {
        ((MapView.zza)zzqj()).getMapAsync(paramOnMapReadyCallback);
        return;
      }
      this.zzaCa.add(paramOnMapReadyCallback);
    }
    
    protected void zza(zzf<MapView.zza> paramzzf)
    {
      this.zzaBZ = paramzzf;
      zzvu();
    }
    
    public void zzvu()
    {
      if ((this.zzaBZ != null) && (zzqj() == null)) {
        try
        {
          MapsInitializer.initialize(this.mContext);
          Object localObject = zzy.zzay(this.mContext).zza(zze.zzw(this.mContext), this.zzaCh);
          if (localObject == null) {
            return;
          }
          this.zzaBZ.zza(new MapView.zza(this.zzaCg, (IMapViewDelegate)localObject));
          localObject = this.zzaCa.iterator();
          while (((Iterator)localObject).hasNext())
          {
            OnMapReadyCallback localOnMapReadyCallback = (OnMapReadyCallback)((Iterator)localObject).next();
            ((MapView.zza)zzqj()).getMapAsync(localOnMapReadyCallback);
          }
          return;
        }
        catch (RemoteException localRemoteException)
        {
          throw new RuntimeRemoteException(localRemoteException);
          this.zzaCa.clear();
          return;
        }
        catch (GooglePlayServicesNotAvailableException localGooglePlayServicesNotAvailableException) {}
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/maps/MapView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */