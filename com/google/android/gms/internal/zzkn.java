package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zza.zzb;

public final class zzkn
  implements zzkm
{
  public PendingResult<Status> zzc(GoogleApiClient paramGoogleApiClient)
  {
    paramGoogleApiClient.zzb(new zzko.zza(paramGoogleApiClient)
    {
      protected void zza(zzkp paramAnonymouszzkp)
        throws RemoteException
      {
        ((zzkr)paramAnonymouszzkp.zznM()).zza(new zzkn.zza(this));
      }
    });
  }
  
  private static class zza
    extends zzkk
  {
    private final zza.zzb<Status> zzOs;
    
    public zza(zza.zzb<Status> paramzzb)
    {
      this.zzOs = paramzzb;
    }
    
    public void zzbB(int paramInt)
      throws RemoteException
    {
      this.zzOs.zzm(new Status(paramInt));
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzkn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */