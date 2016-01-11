package com.google.android.gms.auth.api.credentials;

import com.google.android.gms.common.api.Result;

public abstract interface CredentialRequestResult
  extends Result
{
  public abstract Credential getCredential();
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/auth/api/credentials/CredentialRequestResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */