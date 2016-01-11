package com.google.android.gms.auth.api;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
import com.google.android.gms.auth.api.credentials.internal.CredentialsClientImpl;
import com.google.android.gms.auth.api.credentials.internal.zzc;
import com.google.android.gms.auth.api.proxy.zza;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.ApiOptions.Optional;
import com.google.android.gms.common.api.Api.ClientKey;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zze;
import com.google.android.gms.internal.zzje;
import com.google.android.gms.internal.zzjf;
import com.google.android.gms.internal.zzjg;
import com.google.android.gms.internal.zzjj;
import com.google.android.gms.internal.zzjm;
import com.google.android.gms.internal.zzjn;
import com.google.android.gms.internal.zzjp;
import com.google.android.gms.internal.zzjq;

public final class Auth
{
  public static final Api.ClientKey<CredentialsClientImpl> CLIENT_KEY_CREDENTIALS_API;
  public static final Api<Api.ApiOptions.NoOptions> CREDENTIALS_API;
  public static final CredentialsApi CredentialsApi = new zzc();
  public static final Api.ClientKey<zzjj> zzOE = new Api.ClientKey();
  public static final Api.ClientKey<zzjg> zzOF;
  public static final Api.ClientKey<zzjq> zzOG;
  private static final Api.zza<zzjj, zza> zzOH;
  private static final Api.zza<CredentialsClientImpl, Api.ApiOptions.NoOptions> zzOI;
  private static final Api.zza<zzjg, Api.ApiOptions.NoOptions> zzOJ;
  private static final Api.zza<zzjq, Api.ApiOptions.NoOptions> zzOK;
  public static final Api<zza> zzOL;
  public static final Api<Api.ApiOptions.NoOptions> zzOM;
  public static final Api<Api.ApiOptions.NoOptions> zzON;
  public static final zza zzOO;
  public static final zzje zzOP = new zzjf();
  public static final zzjn zzOQ = new zzjp();
  
  static
  {
    CLIENT_KEY_CREDENTIALS_API = new Api.ClientKey();
    zzOF = new Api.ClientKey();
    zzOG = new Api.ClientKey();
    zzOH = new Api.zza()
    {
      public int getPriority()
      {
        return Integer.MAX_VALUE;
      }
      
      public zzjj zza(Context paramAnonymousContext, Looper paramAnonymousLooper, zze paramAnonymouszze, Auth.zza paramAnonymouszza, GoogleApiClient.ConnectionCallbacks paramAnonymousConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramAnonymousOnConnectionFailedListener)
      {
        return new zzjj(paramAnonymousContext, paramAnonymousLooper, paramAnonymouszze, paramAnonymouszza, paramAnonymousConnectionCallbacks, paramAnonymousOnConnectionFailedListener);
      }
    };
    zzOI = new Api.zza()
    {
      public int getPriority()
      {
        return Integer.MAX_VALUE;
      }
      
      public CredentialsClientImpl zzd(Context paramAnonymousContext, Looper paramAnonymousLooper, zze paramAnonymouszze, Api.ApiOptions.NoOptions paramAnonymousNoOptions, GoogleApiClient.ConnectionCallbacks paramAnonymousConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramAnonymousOnConnectionFailedListener)
      {
        return new CredentialsClientImpl(paramAnonymousContext, paramAnonymousLooper, paramAnonymousConnectionCallbacks, paramAnonymousOnConnectionFailedListener);
      }
    };
    zzOJ = new Api.zza()
    {
      public int getPriority()
      {
        return Integer.MAX_VALUE;
      }
      
      public zzjg zze(Context paramAnonymousContext, Looper paramAnonymousLooper, zze paramAnonymouszze, Api.ApiOptions.NoOptions paramAnonymousNoOptions, GoogleApiClient.ConnectionCallbacks paramAnonymousConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramAnonymousOnConnectionFailedListener)
      {
        return new zzjg(paramAnonymousContext, paramAnonymousLooper, paramAnonymouszze, paramAnonymousConnectionCallbacks, paramAnonymousOnConnectionFailedListener);
      }
    };
    zzOK = new Api.zza()
    {
      public int getPriority()
      {
        return Integer.MAX_VALUE;
      }
      
      public zzjq zzf(Context paramAnonymousContext, Looper paramAnonymousLooper, zze paramAnonymouszze, Api.ApiOptions.NoOptions paramAnonymousNoOptions, GoogleApiClient.ConnectionCallbacks paramAnonymousConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramAnonymousOnConnectionFailedListener)
      {
        return new zzjq(paramAnonymousContext, paramAnonymousLooper, paramAnonymousConnectionCallbacks, paramAnonymousOnConnectionFailedListener);
      }
    };
    zzOL = new Api("Auth.PROXY_API", zzOH, zzOE, new Scope[0]);
    CREDENTIALS_API = new Api("Auth.CREDENTIALS_API", zzOI, CLIENT_KEY_CREDENTIALS_API, new Scope[0]);
    zzOM = new Api("Auth.SIGN_IN_API", zzOK, zzOG, new Scope[0]);
    zzON = new Api("Auth.ACCOUNT_STATUS_API", zzOJ, zzOF, new Scope[0]);
    zzOO = new zzjm();
  }
  
  public static final class zza
    implements Api.ApiOptions.Optional
  {
    private final Bundle zzOR;
    
    public Bundle zzkY()
    {
      return new Bundle(this.zzOR);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/auth/api/Auth.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */