package com.google.android.gms.auth.api.credentials.internal;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialRequest;
import com.google.android.gms.auth.api.credentials.CredentialRequestResult;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zza.zzb;

public final class zzc
  implements CredentialsApi
{
  public PendingResult<Status> delete(GoogleApiClient paramGoogleApiClient, final Credential paramCredential)
  {
    paramGoogleApiClient.zzb(new zzd(paramGoogleApiClient)
    {
      protected void zza(Context paramAnonymousContext, ICredentialsService paramAnonymousICredentialsService)
        throws RemoteException
      {
        paramAnonymousICredentialsService.performCredentialsDeleteOperation(new zzc.zza(this), new DeleteRequest(paramCredential));
      }
      
      protected Status zzb(Status paramAnonymousStatus)
      {
        return paramAnonymousStatus;
      }
    });
  }
  
  public PendingResult<Status> disableAutoSignIn(GoogleApiClient paramGoogleApiClient)
  {
    paramGoogleApiClient.zzb(new zzd(paramGoogleApiClient)
    {
      protected void zza(Context paramAnonymousContext, ICredentialsService paramAnonymousICredentialsService)
        throws RemoteException
      {
        paramAnonymousICredentialsService.performDisableAutoSignInOperation(new zzc.zza(this));
      }
      
      protected Status zzb(Status paramAnonymousStatus)
      {
        return paramAnonymousStatus;
      }
    });
  }
  
  public PendingResult<CredentialRequestResult> request(GoogleApiClient paramGoogleApiClient, final CredentialRequest paramCredentialRequest)
  {
    paramGoogleApiClient.zza(new zzd(paramGoogleApiClient)
    {
      protected void zza(Context paramAnonymousContext, ICredentialsService paramAnonymousICredentialsService)
        throws RemoteException
      {
        paramAnonymousICredentialsService.performCredentialsRequestOperation(new zza()
        {
          public void onCredentialResult(Status paramAnonymous2Status, Credential paramAnonymous2Credential)
          {
            zzc.1.this.setResult(new zzb(paramAnonymous2Status, paramAnonymous2Credential));
          }
        }, paramCredentialRequest);
      }
      
      protected CredentialRequestResult zzk(Status paramAnonymousStatus)
      {
        return zzb.zzj(paramAnonymousStatus);
      }
    });
  }
  
  public PendingResult<Status> save(GoogleApiClient paramGoogleApiClient, final Credential paramCredential)
  {
    paramGoogleApiClient.zzb(new zzd(paramGoogleApiClient)
    {
      protected void zza(Context paramAnonymousContext, ICredentialsService paramAnonymousICredentialsService)
        throws RemoteException
      {
        paramAnonymousICredentialsService.performCredentialsSaveOperation(new zzc.zza(this), new SaveRequest(paramCredential));
      }
      
      protected Status zzb(Status paramAnonymousStatus)
      {
        return paramAnonymousStatus;
      }
    });
  }
  
  private static class zza
    extends zza
  {
    private zza.zzb<Status> zzPg;
    
    zza(zza.zzb<Status> paramzzb)
    {
      this.zzPg = paramzzb;
    }
    
    public void onStatusResult(Status paramStatus)
    {
      this.zzPg.zzm(paramStatus);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/auth/api/credentials/internal/zzc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */