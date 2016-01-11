package com.google.android.gms.auth.api.credentials.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzi;

public final class CredentialsClientImpl
  extends zzi<ICredentialsService>
{
  public static final String ACTION_START_SERVICE = "com.google.android.gms.auth.api.credentials.service.START";
  
  public CredentialsClientImpl(Context paramContext, Looper paramLooper, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    super(paramContext, paramLooper, 68, paramConnectionCallbacks, paramOnConnectionFailedListener);
  }
  
  protected ICredentialsService createServiceInterface(IBinder paramIBinder)
  {
    return ICredentialsService.zza.zzaq(paramIBinder);
  }
  
  protected String getServiceDescriptor()
  {
    return "com.google.android.gms.auth.api.credentials.internal.ICredentialsService";
  }
  
  protected String getStartServiceAction()
  {
    return "com.google.android.gms.auth.api.credentials.service.START";
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/auth/api/credentials/internal/CredentialsClientImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */