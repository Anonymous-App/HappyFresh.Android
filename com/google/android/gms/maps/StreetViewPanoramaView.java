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
import com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate;
import com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate;
import com.google.android.gms.maps.internal.StreetViewLifecycleDelegate;
import com.google.android.gms.maps.internal.zzc;
import com.google.android.gms.maps.internal.zzv.zza;
import com.google.android.gms.maps.internal.zzy;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StreetViewPanoramaView
  extends FrameLayout
{
  private final zzb zzaCC;
  private StreetViewPanorama zzaCq;
  
  public StreetViewPanoramaView(Context paramContext)
  {
    super(paramContext);
    this.zzaCC = new zzb(this, paramContext, null);
  }
  
  public StreetViewPanoramaView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.zzaCC = new zzb(this, paramContext, null);
  }
  
  public StreetViewPanoramaView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.zzaCC = new zzb(this, paramContext, null);
  }
  
  public StreetViewPanoramaView(Context paramContext, StreetViewPanoramaOptions paramStreetViewPanoramaOptions)
  {
    super(paramContext);
    this.zzaCC = new zzb(this, paramContext, paramStreetViewPanoramaOptions);
  }
  
  @Deprecated
  public final StreetViewPanorama getStreetViewPanorama()
  {
    if (this.zzaCq != null) {
      return this.zzaCq;
    }
    this.zzaCC.zzvu();
    if (this.zzaCC.zzqj() == null) {
      return null;
    }
    try
    {
      this.zzaCq = new StreetViewPanorama(((zza)this.zzaCC.zzqj()).zzvB().getStreetViewPanorama());
      return this.zzaCq;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }
  
  public void getStreetViewPanoramaAsync(OnStreetViewPanoramaReadyCallback paramOnStreetViewPanoramaReadyCallback)
  {
    zzu.zzbY("getStreetViewPanoramaAsync() must be called on the main thread");
    this.zzaCC.getStreetViewPanoramaAsync(paramOnStreetViewPanoramaReadyCallback);
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    this.zzaCC.onCreate(paramBundle);
    if (this.zzaCC.zzqj() == null) {
      zza.zzb(this);
    }
  }
  
  public final void onDestroy()
  {
    this.zzaCC.onDestroy();
  }
  
  public final void onLowMemory()
  {
    this.zzaCC.onLowMemory();
  }
  
  public final void onPause()
  {
    this.zzaCC.onPause();
  }
  
  public final void onResume()
  {
    this.zzaCC.onResume();
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    this.zzaCC.onSaveInstanceState(paramBundle);
  }
  
  static class zza
    implements StreetViewLifecycleDelegate
  {
    private final IStreetViewPanoramaViewDelegate zzaCD;
    private View zzaCE;
    private final ViewGroup zzaCc;
    
    public zza(ViewGroup paramViewGroup, IStreetViewPanoramaViewDelegate paramIStreetViewPanoramaViewDelegate)
    {
      this.zzaCD = ((IStreetViewPanoramaViewDelegate)zzu.zzu(paramIStreetViewPanoramaViewDelegate));
      this.zzaCc = ((ViewGroup)zzu.zzu(paramViewGroup));
    }
    
    public void getStreetViewPanoramaAsync(final OnStreetViewPanoramaReadyCallback paramOnStreetViewPanoramaReadyCallback)
    {
      try
      {
        this.zzaCD.getStreetViewPanoramaAsync(new zzv.zza()
        {
          public void zza(IStreetViewPanoramaDelegate paramAnonymousIStreetViewPanoramaDelegate)
            throws RemoteException
          {
            paramOnStreetViewPanoramaReadyCallback.onStreetViewPanoramaReady(new StreetViewPanorama(paramAnonymousIStreetViewPanoramaDelegate));
          }
        });
        return;
      }
      catch (RemoteException paramOnStreetViewPanoramaReadyCallback)
      {
        throw new RuntimeRemoteException(paramOnStreetViewPanoramaReadyCallback);
      }
    }
    
    public void onCreate(Bundle paramBundle)
    {
      try
      {
        this.zzaCD.onCreate(paramBundle);
        this.zzaCE = ((View)zze.zzn(this.zzaCD.getView()));
        this.zzaCc.removeAllViews();
        this.zzaCc.addView(this.zzaCE);
        return;
      }
      catch (RemoteException paramBundle)
      {
        throw new RuntimeRemoteException(paramBundle);
      }
    }
    
    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
    {
      throw new UnsupportedOperationException("onCreateView not allowed on StreetViewPanoramaViewDelegate");
    }
    
    public void onDestroy()
    {
      try
      {
        this.zzaCD.onDestroy();
        return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
    }
    
    public void onDestroyView()
    {
      throw new UnsupportedOperationException("onDestroyView not allowed on StreetViewPanoramaViewDelegate");
    }
    
    public void onInflate(Activity paramActivity, Bundle paramBundle1, Bundle paramBundle2)
    {
      throw new UnsupportedOperationException("onInflate not allowed on StreetViewPanoramaViewDelegate");
    }
    
    public void onLowMemory()
    {
      try
      {
        this.zzaCD.onLowMemory();
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
        this.zzaCD.onPause();
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
        this.zzaCD.onResume();
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
        this.zzaCD.onSaveInstanceState(paramBundle);
        return;
      }
      catch (RemoteException paramBundle)
      {
        throw new RuntimeRemoteException(paramBundle);
      }
    }
    
    public void onStart() {}
    
    public void onStop() {}
    
    public IStreetViewPanoramaViewDelegate zzvB()
    {
      return this.zzaCD;
    }
  }
  
  static class zzb
    extends zza<StreetViewPanoramaView.zza>
  {
    private final Context mContext;
    protected zzf<StreetViewPanoramaView.zza> zzaBZ;
    private final StreetViewPanoramaOptions zzaCG;
    private final ViewGroup zzaCg;
    private final List<OnStreetViewPanoramaReadyCallback> zzaCu = new ArrayList();
    
    zzb(ViewGroup paramViewGroup, Context paramContext, StreetViewPanoramaOptions paramStreetViewPanoramaOptions)
    {
      this.zzaCg = paramViewGroup;
      this.mContext = paramContext;
      this.zzaCG = paramStreetViewPanoramaOptions;
    }
    
    public void getStreetViewPanoramaAsync(OnStreetViewPanoramaReadyCallback paramOnStreetViewPanoramaReadyCallback)
    {
      if (zzqj() != null)
      {
        ((StreetViewPanoramaView.zza)zzqj()).getStreetViewPanoramaAsync(paramOnStreetViewPanoramaReadyCallback);
        return;
      }
      this.zzaCu.add(paramOnStreetViewPanoramaReadyCallback);
    }
    
    protected void zza(zzf<StreetViewPanoramaView.zza> paramzzf)
    {
      this.zzaBZ = paramzzf;
      zzvu();
    }
    
    public void zzvu()
    {
      if ((this.zzaBZ != null) && (zzqj() == null)) {}
      try
      {
        Object localObject = zzy.zzay(this.mContext).zza(zze.zzw(this.mContext), this.zzaCG);
        this.zzaBZ.zza(new StreetViewPanoramaView.zza(this.zzaCg, (IStreetViewPanoramaViewDelegate)localObject));
        localObject = this.zzaCu.iterator();
        while (((Iterator)localObject).hasNext())
        {
          OnStreetViewPanoramaReadyCallback localOnStreetViewPanoramaReadyCallback = (OnStreetViewPanoramaReadyCallback)((Iterator)localObject).next();
          ((StreetViewPanoramaView.zza)zzqj()).getStreetViewPanoramaAsync(localOnStreetViewPanoramaReadyCallback);
        }
        return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
        this.zzaCu.clear();
        return;
      }
      catch (GooglePlayServicesNotAvailableException localGooglePlayServicesNotAvailableException) {}
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/maps/StreetViewPanoramaView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */