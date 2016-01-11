package com.google.android.gms.auth.api.credentials.internal;

import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialRequestResult;
import com.google.android.gms.common.api.Status;

public final class zzb
  implements CredentialRequestResult
{
  private final Status zzOt;
  private final Credential zzPb;
  
  public zzb(Status paramStatus, Credential paramCredential)
  {
    this.zzOt = paramStatus;
    this.zzPb = paramCredential;
  }
  
  public static zzb zzj(Status paramStatus)
  {
    return new zzb(paramStatus, null);
  }
  
  public Credential getCredential()
  {
    return this.zzPb;
  }
  
  public Status getStatus()
  {
    return this.zzOt;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/auth/api/credentials/internal/zzb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */