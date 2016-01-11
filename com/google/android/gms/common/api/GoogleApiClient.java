package com.google.android.gms.common.api;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zze;
import com.google.android.gms.common.internal.zze.zza;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.internal.zzpq;
import com.google.android.gms.internal.zzps;
import com.google.android.gms.internal.zzpt;
import com.google.android.gms.internal.zzpt.zza;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public abstract interface GoogleApiClient
{
  public abstract ConnectionResult blockingConnect();
  
  public abstract ConnectionResult blockingConnect(long paramLong, TimeUnit paramTimeUnit);
  
  public abstract PendingResult<Status> clearDefaultAccountAndReconnect();
  
  public abstract void connect();
  
  public abstract void disconnect();
  
  public abstract void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString);
  
  public abstract ConnectionResult getConnectionResult(Api<?> paramApi);
  
  public abstract Context getContext();
  
  public abstract Looper getLooper();
  
  public abstract int getSessionId();
  
  public abstract boolean hasConnectedApi(Api<?> paramApi);
  
  public abstract boolean isConnected();
  
  public abstract boolean isConnecting();
  
  public abstract boolean isConnectionCallbacksRegistered(ConnectionCallbacks paramConnectionCallbacks);
  
  public abstract boolean isConnectionFailedListenerRegistered(OnConnectionFailedListener paramOnConnectionFailedListener);
  
  public abstract void reconnect();
  
  public abstract void registerConnectionCallbacks(ConnectionCallbacks paramConnectionCallbacks);
  
  public abstract void registerConnectionFailedListener(OnConnectionFailedListener paramOnConnectionFailedListener);
  
  public abstract void stopAutoManage(FragmentActivity paramFragmentActivity);
  
  public abstract void unregisterConnectionCallbacks(ConnectionCallbacks paramConnectionCallbacks);
  
  public abstract void unregisterConnectionFailedListener(OnConnectionFailedListener paramOnConnectionFailedListener);
  
  public abstract <C extends Api.Client> C zza(Api.ClientKey<C> paramClientKey);
  
  public abstract <A extends Api.Client, R extends Result, T extends zza.zza<R, A>> T zza(T paramT);
  
  public abstract boolean zza(Api<?> paramApi);
  
  public abstract boolean zza(Scope paramScope);
  
  public abstract <A extends Api.Client, T extends zza.zza<? extends Result, A>> T zzb(T paramT);
  
  public abstract <L> zzi<L> zzo(L paramL);
  
  public static final class Builder
  {
    private final Context mContext;
    private Account zzMY;
    private String zzOd;
    private String zzOe;
    private FragmentActivity zzWA;
    private int zzWB = -1;
    private int zzWC = -1;
    private GoogleApiClient.OnConnectionFailedListener zzWD;
    private Api.zza<? extends zzps, zzpt> zzWE;
    private final Set<GoogleApiClient.ConnectionCallbacks> zzWF = new HashSet();
    private final Set<GoogleApiClient.OnConnectionFailedListener> zzWG = new HashSet();
    private zzpt.zza zzWH = new zzpt.zza();
    private Looper zzWt;
    private final Set<Scope> zzWv = new HashSet();
    private int zzWw;
    private View zzWx;
    private final Map<Api<?>, zze.zza> zzWy = new HashMap();
    private final Map<Api<?>, Api.ApiOptions> zzWz = new HashMap();
    
    public Builder(Context paramContext)
    {
      this.mContext = paramContext;
      this.zzWt = paramContext.getMainLooper();
      this.zzOe = paramContext.getPackageName();
      this.zzOd = paramContext.getClass().getName();
      this.zzWE = zzpq.zzNY;
    }
    
    public Builder(Context paramContext, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
    {
      this(paramContext);
      zzu.zzb(paramConnectionCallbacks, "Must provide a connected listener");
      this.zzWF.add(paramConnectionCallbacks);
      zzu.zzb(paramOnConnectionFailedListener, "Must provide a connection failed listener");
      this.zzWG.add(paramOnConnectionFailedListener);
    }
    
    private void zza(Api<?> paramApi, int paramInt, Scope... paramVarArgs)
    {
      boolean bool = true;
      int i = 0;
      if (paramInt == 1) {}
      HashSet localHashSet;
      for (;;)
      {
        localHashSet = new HashSet(paramApi.zzmr());
        int j = paramVarArgs.length;
        paramInt = i;
        while (paramInt < j)
        {
          localHashSet.add(paramVarArgs[paramInt]);
          paramInt += 1;
        }
        if (paramInt != 2) {
          break;
        }
        bool = false;
      }
      throw new IllegalArgumentException("Invalid resolution mode: '" + paramInt + "', use a constant from GoogleApiClient.ResolutionMode");
      this.zzWy.put(paramApi, new zze.zza(localHashSet, bool));
    }
    
    private GoogleApiClient zzmy()
    {
      zzm localzzm = zzm.zza(this.zzWA);
      zzg localzzg = new zzg(this.mContext.getApplicationContext(), this.zzWt, zzmx(), this.zzWE, this.zzWz, this.zzWF, this.zzWG, this.zzWB, -1);
      localzzm.zza(this.zzWB, localzzg, this.zzWD);
      return localzzg;
    }
    
    private GoogleApiClient zzmz()
    {
      zzn localzzn = zzn.zzb(this.zzWA);
      GoogleApiClient localGoogleApiClient = localzzn.zzbc(this.zzWC);
      Object localObject = localGoogleApiClient;
      if (localGoogleApiClient == null) {
        localObject = new zzg(this.mContext.getApplicationContext(), this.zzWt, zzmx(), this.zzWE, this.zzWz, this.zzWF, this.zzWG, -1, this.zzWC);
      }
      localzzn.zza(this.zzWC, (GoogleApiClient)localObject, this.zzWD);
      return (GoogleApiClient)localObject;
    }
    
    public Builder addApi(Api<? extends Api.ApiOptions.NotRequiredOptions> paramApi)
    {
      this.zzWz.put(paramApi, null);
      this.zzWv.addAll(paramApi.zzmr());
      return this;
    }
    
    public <O extends Api.ApiOptions.HasOptions> Builder addApi(Api<O> paramApi, O paramO)
    {
      zzu.zzb(paramO, "Null options are not permitted for this Api");
      this.zzWz.put(paramApi, paramO);
      this.zzWv.addAll(paramApi.zzmr());
      return this;
    }
    
    public <O extends Api.ApiOptions.HasOptions> Builder addApiIfAvailable(Api<O> paramApi, O paramO, Scope... paramVarArgs)
    {
      zzu.zzb(paramO, "Null options are not permitted for this Api");
      this.zzWz.put(paramApi, paramO);
      zza(paramApi, 1, paramVarArgs);
      return this;
    }
    
    public Builder addApiIfAvailable(Api<? extends Api.ApiOptions.NotRequiredOptions> paramApi, Scope... paramVarArgs)
    {
      this.zzWz.put(paramApi, null);
      zza(paramApi, 1, paramVarArgs);
      return this;
    }
    
    public Builder addConnectionCallbacks(GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks)
    {
      this.zzWF.add(paramConnectionCallbacks);
      return this;
    }
    
    public Builder addOnConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
    {
      this.zzWG.add(paramOnConnectionFailedListener);
      return this;
    }
    
    public Builder addScope(Scope paramScope)
    {
      this.zzWv.add(paramScope);
      return this;
    }
    
    public GoogleApiClient build()
    {
      if (!this.zzWz.isEmpty()) {}
      for (boolean bool = true;; bool = false)
      {
        zzu.zzb(bool, "must call addApi() to add at least one API");
        if (this.zzWB < 0) {
          break;
        }
        return zzmy();
      }
      if (this.zzWC >= 0) {
        return zzmz();
      }
      return new zzg(this.mContext, this.zzWt, zzmx(), this.zzWE, this.zzWz, this.zzWF, this.zzWG, -1, -1);
    }
    
    public Builder enableAutoManage(FragmentActivity paramFragmentActivity, int paramInt, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
    {
      if (paramInt >= 0) {}
      for (boolean bool = true;; bool = false)
      {
        zzu.zzb(bool, "clientId must be non-negative");
        this.zzWB = paramInt;
        this.zzWA = ((FragmentActivity)zzu.zzb(paramFragmentActivity, "Null activity is not permitted."));
        this.zzWD = paramOnConnectionFailedListener;
        return this;
      }
    }
    
    public Builder requestServerAuthCode(String paramString, GoogleApiClient.ServerAuthCodeCallbacks paramServerAuthCodeCallbacks)
    {
      this.zzWH.zza(paramString, paramServerAuthCodeCallbacks);
      return this;
    }
    
    public Builder setAccountName(String paramString)
    {
      if (paramString == null) {}
      for (paramString = null;; paramString = new Account(paramString, "com.google"))
      {
        this.zzMY = paramString;
        return this;
      }
    }
    
    public Builder setGravityForPopups(int paramInt)
    {
      this.zzWw = paramInt;
      return this;
    }
    
    public Builder setHandler(Handler paramHandler)
    {
      zzu.zzb(paramHandler, "Handler must not be null");
      this.zzWt = paramHandler.getLooper();
      return this;
    }
    
    public Builder setViewForPopups(View paramView)
    {
      this.zzWx = paramView;
      return this;
    }
    
    public Builder useDefaultAccount()
    {
      return setAccountName("<<default account>>");
    }
    
    public zze zzmx()
    {
      return new zze(this.zzMY, this.zzWv, this.zzWy, this.zzWw, this.zzWx, this.zzOe, this.zzOd, this.zzWH.zzyc());
    }
  }
  
  public static abstract interface ConnectionCallbacks
  {
    public static final int CAUSE_NETWORK_LOST = 2;
    public static final int CAUSE_SERVICE_DISCONNECTED = 1;
    
    public abstract void onConnected(Bundle paramBundle);
    
    public abstract void onConnectionSuspended(int paramInt);
  }
  
  public static abstract interface ConnectionProgressReportCallbacks
  {
    public abstract void onReportAccountValidation(ConnectionResult paramConnectionResult);
    
    public abstract void onReportServiceBinding(ConnectionResult paramConnectionResult);
  }
  
  public static abstract interface OnConnectionFailedListener
  {
    public abstract void onConnectionFailed(ConnectionResult paramConnectionResult);
  }
  
  public static abstract interface ServerAuthCodeCallbacks
  {
    public abstract CheckResult onCheckServerAuthorization(String paramString, Set<Scope> paramSet);
    
    public abstract boolean onUploadServerAuthCode(String paramString1, String paramString2);
    
    public static class CheckResult
    {
      private boolean zzWI;
      private Set<Scope> zzWJ;
      
      private CheckResult(boolean paramBoolean, Set<Scope> paramSet)
      {
        this.zzWI = paramBoolean;
        this.zzWJ = paramSet;
      }
      
      public static CheckResult newAuthNotRequiredResult()
      {
        return new CheckResult(false, null);
      }
      
      public static CheckResult newAuthRequiredResult(Set<Scope> paramSet)
      {
        if ((paramSet != null) && (!paramSet.isEmpty())) {}
        for (boolean bool = true;; bool = false)
        {
          zzu.zzb(bool, "A non-empty scope set is required if further auth is needed.");
          return new CheckResult(true, paramSet);
        }
      }
      
      public boolean zzmA()
      {
        return this.zzWI;
      }
      
      public Set<Scope> zzmB()
      {
        return this.zzWJ;
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/api/GoogleApiClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */