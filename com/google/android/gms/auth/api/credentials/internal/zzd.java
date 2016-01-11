package com.google.android.gms.auth.api.credentials.internal;

import android.content.Context;
import android.os.DeadObjectException;
import android.os.RemoteException;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.zza.zza;

abstract class zzd<R extends Result>
  extends zza.zza<R, CredentialsClientImpl>
{
  zzd(GoogleApiClient paramGoogleApiClient)
  {
    super(Auth.CLIENT_KEY_CREDENTIALS_API, paramGoogleApiClient);
  }
  
  protected abstract void zza(Context paramContext, ICredentialsService paramICredentialsService)
    throws DeadObjectException, RemoteException;
  
  protected final void zza(CredentialsClientImpl paramCredentialsClientImpl)
    throws DeadObjectException, RemoteException
  {
    zza(paramCredentialsClientImpl.getContext(), (ICredentialsService)paramCredentialsClientImpl.zznM());
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/auth/api/credentials/internal/zzd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */