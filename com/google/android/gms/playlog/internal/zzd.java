package com.google.android.gms.playlog.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.internal.zzoz.zza;

public class zzd
  implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{
  private zzf zzaGD;
  private final zzoz.zza zzaGN;
  private boolean zzaGO;
  
  public zzd(zzoz.zza paramzza)
  {
    this.zzaGN = paramzza;
    this.zzaGD = null;
    this.zzaGO = true;
  }
  
  public void onConnected(Bundle paramBundle)
  {
    this.zzaGD.zzak(false);
    if ((this.zzaGO) && (this.zzaGN != null)) {
      this.zzaGN.zzxl();
    }
    this.zzaGO = false;
  }
  
  public void onConnectionFailed(ConnectionResult paramConnectionResult)
  {
    this.zzaGD.zzak(true);
    if ((this.zzaGO) && (this.zzaGN != null))
    {
      if (!paramConnectionResult.hasResolution()) {
        break label48;
      }
      this.zzaGN.zzf(paramConnectionResult.getResolution());
    }
    for (;;)
    {
      this.zzaGO = false;
      return;
      label48:
      this.zzaGN.zzxm();
    }
  }
  
  public void onConnectionSuspended(int paramInt)
  {
    this.zzaGD.zzak(true);
  }
  
  public void zza(zzf paramzzf)
  {
    this.zzaGD = paramzzf;
  }
  
  public void zzaj(boolean paramBoolean)
  {
    this.zzaGO = paramBoolean;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/playlog/internal/zzd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */