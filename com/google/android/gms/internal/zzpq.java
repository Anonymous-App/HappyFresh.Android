package com.google.android.gms.internal;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.ClientKey;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zze;
import com.google.android.gms.signin.internal.zzg;
import com.google.android.gms.signin.internal.zzh;
import java.util.concurrent.Executors;

public final class zzpq
{
  public static final Api<zzpt> API;
  public static final Api.ClientKey<zzh> zzNX = new Api.ClientKey();
  public static final Api.zza<zzh, zzpt> zzNY;
  static final Api.zza<zzh, Api.ApiOptions.NoOptions> zzaJO;
  public static final zzpr zzaJP = new zzg();
  public static final Api<Api.ApiOptions.NoOptions> zzada;
  public static final Api.ClientKey<zzh> zzajz = new Api.ClientKey();
  
  static
  {
    zzNY = new Api.zza()
    {
      public int getPriority()
      {
        return Integer.MAX_VALUE;
      }
      
      public zzh zza(Context paramAnonymousContext, Looper paramAnonymousLooper, zze paramAnonymouszze, zzpt paramAnonymouszzpt, GoogleApiClient.ConnectionCallbacks paramAnonymousConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramAnonymousOnConnectionFailedListener)
      {
        if (paramAnonymouszzpt == null) {
          paramAnonymouszzpt = zzpt.zzaJQ;
        }
        for (;;)
        {
          return new zzh(paramAnonymousContext, paramAnonymousLooper, true, paramAnonymouszze, paramAnonymouszzpt, paramAnonymousConnectionCallbacks, paramAnonymousOnConnectionFailedListener, Executors.newSingleThreadExecutor());
        }
      }
    };
    zzaJO = new Api.zza()
    {
      public int getPriority()
      {
        return Integer.MAX_VALUE;
      }
      
      public zzh zzv(Context paramAnonymousContext, Looper paramAnonymousLooper, zze paramAnonymouszze, Api.ApiOptions.NoOptions paramAnonymousNoOptions, GoogleApiClient.ConnectionCallbacks paramAnonymousConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramAnonymousOnConnectionFailedListener)
      {
        return new zzh(paramAnonymousContext, paramAnonymousLooper, false, paramAnonymouszze, zzpt.zzaJQ, paramAnonymousConnectionCallbacks, paramAnonymousOnConnectionFailedListener, Executors.newSingleThreadExecutor());
      }
    };
    API = new Api("SignIn.API", zzNY, zzNX, new Scope[0]);
    zzada = new Api("SignIn.INTERNAL_API", zzaJO, zzajz, new Scope[0]);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/internal/zzpq.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */