package com.google.android.gms.common.api;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.ResolveAccountResponse;
import com.google.android.gms.common.internal.zzj;
import com.google.android.gms.common.internal.zzq.zza;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.internal.zzps;
import com.google.android.gms.internal.zzpt;
import com.google.android.gms.signin.internal.AuthAccountResult;
import com.google.android.gms.signin.internal.zzb;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.locks.Lock;

public class zze
  implements zzh
{
  private final Context mContext;
  private final Api.zza<? extends zzps, zzpt> zzWE;
  private final zzg zzWK;
  private final Lock zzWL;
  private ConnectionResult zzWM;
  private int zzWN;
  private int zzWO = 0;
  private boolean zzWP = false;
  private int zzWQ;
  private final Bundle zzWR = new Bundle();
  private final Set<Api.ClientKey> zzWS = new HashSet();
  private zzps zzWT;
  private int zzWU;
  private boolean zzWV;
  private boolean zzWW;
  private IAccountAccessor zzWX;
  private boolean zzWY;
  private boolean zzWZ;
  private final com.google.android.gms.common.internal.zze zzXa;
  private final Map<Api<?>, Integer> zzXb;
  
  public zze(zzg paramzzg, com.google.android.gms.common.internal.zze paramzze, Map<Api<?>, Integer> paramMap, Api.zza<? extends zzps, zzpt> paramzza, Lock paramLock, Context paramContext)
  {
    this.zzWK = paramzzg;
    this.zzXa = paramzze;
    this.zzXb = paramMap;
    this.zzWE = paramzza;
    this.zzWL = paramLock;
    this.mContext = paramContext;
  }
  
  private void zzT(boolean paramBoolean)
  {
    if (this.zzWT != null)
    {
      if (this.zzWT.isConnected())
      {
        if (paramBoolean) {
          this.zzWT.zzxY();
        }
        this.zzWT.disconnect();
      }
      this.zzWX = null;
    }
  }
  
  private void zza(ConnectionResult paramConnectionResult)
  {
    this.zzWL.lock();
    for (;;)
    {
      try
      {
        boolean bool = zzaW(2);
        if (!bool) {
          return;
        }
        if (paramConnectionResult.isSuccess())
        {
          zzmG();
          return;
        }
        if (zzc(paramConnectionResult))
        {
          zzmI();
          zzmG();
        }
        else
        {
          zzd(paramConnectionResult);
        }
      }
      finally
      {
        this.zzWL.unlock();
      }
    }
  }
  
  private void zza(ResolveAccountResponse paramResolveAccountResponse)
  {
    ConnectionResult localConnectionResult = paramResolveAccountResponse.zzoa();
    this.zzWL.lock();
    for (;;)
    {
      try
      {
        boolean bool = zzaW(0);
        if (!bool) {
          return;
        }
        if (localConnectionResult.isSuccess())
        {
          this.zzWX = paramResolveAccountResponse.zznZ();
          this.zzWW = true;
          this.zzWY = paramResolveAccountResponse.zzob();
          this.zzWZ = paramResolveAccountResponse.zzoc();
          zzmE();
          return;
        }
        if (zzc(localConnectionResult))
        {
          zzmI();
          if (this.zzWQ == 0) {
            zzmG();
          }
        }
        else
        {
          zzd(localConnectionResult);
        }
      }
      finally
      {
        this.zzWL.unlock();
      }
    }
  }
  
  private boolean zza(int paramInt1, int paramInt2, ConnectionResult paramConnectionResult)
  {
    if ((paramInt2 == 1) && (!zzb(paramConnectionResult))) {}
    while ((this.zzWM != null) && (paramInt1 >= this.zzWN)) {
      return false;
    }
    return true;
  }
  
  private boolean zzaW(int paramInt)
  {
    if (this.zzWO != paramInt)
    {
      Log.wtf("GoogleApiClientConnecting", "GoogleApiClient connecting is in step " + zzaX(this.zzWO) + " but received callback for step " + zzaX(paramInt));
      zzd(new ConnectionResult(8, null));
      return false;
    }
    return true;
  }
  
  private String zzaX(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return "UNKNOWN";
    case 0: 
      return "STEP_GETTING_SERVICE_BINDINGS";
    case 1: 
      return "STEP_VALIDATING_ACCOUNT";
    case 2: 
      return "STEP_AUTHENTICATING";
    }
    return "STEP_GETTING_REMOTE_SERVICE";
  }
  
  private void zzb(ConnectionResult paramConnectionResult, Api<?> paramApi, int paramInt)
  {
    if (paramInt != 2)
    {
      int i = paramApi.zzmp().getPriority();
      if (zza(i, paramInt, paramConnectionResult))
      {
        this.zzWM = paramConnectionResult;
        this.zzWN = i;
      }
    }
    this.zzWK.zzXv.put(paramApi.zzms(), paramConnectionResult);
  }
  
  private static boolean zzb(ConnectionResult paramConnectionResult)
  {
    if (paramConnectionResult.hasResolution()) {}
    while (GooglePlayServicesUtil.zzaT(paramConnectionResult.getErrorCode()) != null) {
      return true;
    }
    return false;
  }
  
  private boolean zzc(ConnectionResult paramConnectionResult)
  {
    return (this.zzWU == 2) || ((this.zzWU == 1) && (!paramConnectionResult.hasResolution()));
  }
  
  private void zzd(ConnectionResult paramConnectionResult)
  {
    boolean bool = false;
    this.zzWP = false;
    this.zzWK.zzXw.clear();
    this.zzWM = paramConnectionResult;
    if (!paramConnectionResult.hasResolution()) {
      bool = true;
    }
    zzT(bool);
    zzaV(3);
    if ((!this.zzWK.zzmO()) || (!GooglePlayServicesUtil.zze(this.mContext, paramConnectionResult.getErrorCode())))
    {
      this.zzWK.zzmR();
      this.zzWK.zzXn.zzh(paramConnectionResult);
    }
    this.zzWK.zzXn.zznT();
  }
  
  private boolean zzmC()
  {
    this.zzWQ -= 1;
    if (this.zzWQ > 0) {
      return false;
    }
    if (this.zzWQ < 0)
    {
      Log.wtf("GoogleApiClientConnecting", "GoogleApiClient received too many callbacks for the given step. Clients may be in an unexpected state; GoogleApiClient will now disconnect.");
      zzd(new ConnectionResult(8, null));
      return false;
    }
    if (this.zzWM != null)
    {
      zzd(this.zzWM);
      return false;
    }
    return true;
  }
  
  private void zzmD()
  {
    if (this.zzWV)
    {
      zzmE();
      return;
    }
    zzmG();
  }
  
  private void zzmE()
  {
    if ((this.zzWW) && (this.zzWQ == 0))
    {
      this.zzWO = 1;
      this.zzWQ = this.zzWK.zzXu.size();
      Iterator localIterator = this.zzWK.zzXu.keySet().iterator();
      while (localIterator.hasNext())
      {
        Api.ClientKey localClientKey = (Api.ClientKey)localIterator.next();
        if (this.zzWK.zzXv.containsKey(localClientKey))
        {
          if (zzmC()) {
            zzmF();
          }
        }
        else {
          ((Api.Client)this.zzWK.zzXu.get(localClientKey)).validateAccount(this.zzWX);
        }
      }
    }
  }
  
  private void zzmF()
  {
    this.zzWO = 2;
    this.zzWK.zzXw = zzmJ();
    this.zzWT.zza(this.zzWX, this.zzWK.zzXw, new zza(this));
  }
  
  private void zzmG()
  {
    Set localSet = this.zzWK.zzXw;
    if (localSet.isEmpty()) {
      localSet = zzmJ();
    }
    for (;;)
    {
      this.zzWO = 3;
      this.zzWQ = this.zzWK.zzXu.size();
      Iterator localIterator = this.zzWK.zzXu.keySet().iterator();
      while (localIterator.hasNext())
      {
        Api.ClientKey localClientKey = (Api.ClientKey)localIterator.next();
        if (this.zzWK.zzXv.containsKey(localClientKey))
        {
          if (zzmC()) {
            zzmH();
          }
        }
        else {
          ((Api.Client)this.zzWK.zzXu.get(localClientKey)).getRemoteService(this.zzWX, localSet);
        }
      }
      return;
    }
  }
  
  private void zzmH()
  {
    this.zzWK.zzmN();
    if (this.zzWT != null)
    {
      if (this.zzWY) {
        this.zzWT.zza(this.zzWX, this.zzWZ);
      }
      zzT(false);
    }
    Object localObject = this.zzWK.zzXv.keySet().iterator();
    while (((Iterator)localObject).hasNext())
    {
      Api.ClientKey localClientKey = (Api.ClientKey)((Iterator)localObject).next();
      ((Api.Client)this.zzWK.zzXu.get(localClientKey)).disconnect();
    }
    if (this.zzWP)
    {
      this.zzWP = false;
      zzaV(-1);
      return;
    }
    if (this.zzWR.isEmpty()) {}
    for (localObject = null;; localObject = this.zzWR)
    {
      this.zzWK.zzXn.zzg((Bundle)localObject);
      return;
    }
  }
  
  private void zzmI()
  {
    this.zzWV = false;
    this.zzWK.zzXw.clear();
    Iterator localIterator = this.zzWS.iterator();
    while (localIterator.hasNext())
    {
      Api.ClientKey localClientKey = (Api.ClientKey)localIterator.next();
      if (!this.zzWK.zzXv.containsKey(localClientKey)) {
        this.zzWK.zzXv.put(localClientKey, new ConnectionResult(17, null));
      }
    }
  }
  
  private Set<Scope> zzmJ()
  {
    HashSet localHashSet = new HashSet(this.zzXa.zznv());
    Map localMap = this.zzXa.zznx();
    Iterator localIterator = localMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      Api localApi = (Api)localIterator.next();
      if (!this.zzWK.zzXv.containsKey(localApi.zzms())) {
        localHashSet.addAll(((com.google.android.gms.common.internal.zze.zza)localMap.get(localApi)).zzWJ);
      }
    }
    return localHashSet;
  }
  
  public void begin()
  {
    this.zzWK.zzXn.zznU();
    this.zzWK.zzXv.clear();
    this.zzWP = false;
    this.zzWV = false;
    this.zzWM = null;
    this.zzWO = 0;
    this.zzWU = 2;
    this.zzWW = false;
    this.zzWY = false;
    int i = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.mContext);
    final Object localObject1;
    if (i != 0)
    {
      localObject1 = new ConnectionResult(i, null);
      this.zzWK.zzXs.post(new Runnable()
      {
        public void run()
        {
          zze.zza(zze.this).lock();
          try
          {
            zze.zza(zze.this, localObject1);
            return;
          }
          finally
          {
            zze.zza(zze.this).unlock();
          }
        }
      });
    }
    for (;;)
    {
      return;
      localObject1 = new HashMap();
      Object localObject2 = this.zzXb.keySet().iterator();
      i = 0;
      Object localObject3;
      if (((Iterator)localObject2).hasNext())
      {
        localObject3 = (Api)((Iterator)localObject2).next();
        Api.Client localClient = (Api.Client)this.zzWK.zzXu.get(((Api)localObject3).zzms());
        int k = ((Integer)this.zzXb.get(localObject3)).intValue();
        if (((Api)localObject3).zzmp().getPriority() == 1) {}
        for (int j = 1;; j = 0)
        {
          if (localClient.requiresSignIn())
          {
            this.zzWV = true;
            if (k < this.zzWU) {
              this.zzWU = k;
            }
            if (k != 0) {
              this.zzWS.add(((Api)localObject3).zzms());
            }
          }
          ((Map)localObject1).put(localClient, new zzc(this, (Api)localObject3, k));
          i = j | i;
          break;
        }
      }
      if (i != 0) {
        this.zzWV = false;
      }
      if (this.zzWV)
      {
        this.zzXa.zza(Integer.valueOf(this.zzWK.getSessionId()));
        localObject2 = new zzd(null);
        this.zzWT = ((zzps)this.zzWE.zza(this.mContext, this.zzWK.getLooper(), this.zzXa, this.zzXa.zznB(), (GoogleApiClient.ConnectionCallbacks)localObject2, (GoogleApiClient.OnConnectionFailedListener)localObject2));
        this.zzWT.connect();
      }
      this.zzWQ = this.zzWK.zzXu.size();
      localObject2 = this.zzWK.zzXu.values().iterator();
      while (((Iterator)localObject2).hasNext())
      {
        localObject3 = (Api.Client)((Iterator)localObject2).next();
        ((Api.Client)localObject3).connect((GoogleApiClient.ConnectionProgressReportCallbacks)((Map)localObject1).get(localObject3));
      }
    }
  }
  
  public void connect()
  {
    this.zzWP = false;
  }
  
  public String getName()
  {
    return "CONNECTING";
  }
  
  public void onConnected(Bundle paramBundle)
  {
    if (!zzaW(3)) {}
    do
    {
      return;
      if (paramBundle != null) {
        this.zzWR.putAll(paramBundle);
      }
    } while (!zzmC());
    zzmH();
  }
  
  public void onConnectionSuspended(int paramInt)
  {
    zzd(new ConnectionResult(8, null));
  }
  
  public <A extends Api.Client, R extends Result, T extends zza.zza<R, A>> T zza(T paramT)
  {
    this.zzWK.zzXo.add(paramT);
    return paramT;
  }
  
  public void zza(ConnectionResult paramConnectionResult, Api<?> paramApi, int paramInt)
  {
    if (!zzaW(3)) {}
    do
    {
      return;
      zzb(paramConnectionResult, paramApi, paramInt);
    } while (!zzmC());
    zzmH();
  }
  
  public void zzaV(int paramInt)
  {
    if (paramInt == -1)
    {
      Iterator localIterator = this.zzWK.zzXo.iterator();
      while (localIterator.hasNext())
      {
        zzg.zze localzze = (zzg.zze)localIterator.next();
        if (localzze.zzmv() != 1)
        {
          localzze.cancel();
          localIterator.remove();
        }
      }
      this.zzWK.zzmK();
      if ((this.zzWM == null) && (!this.zzWK.zzXo.isEmpty()))
      {
        this.zzWP = true;
        return;
      }
      this.zzWK.zzXv.clear();
      this.zzWM = null;
      zzT(true);
    }
    this.zzWK.zze(this.zzWM);
  }
  
  public <A extends Api.Client, T extends zza.zza<? extends Result, A>> T zzb(T paramT)
  {
    throw new IllegalStateException("GoogleApiClient is not connected yet.");
  }
  
  private static class zza
    extends zzb
  {
    private final WeakReference<zze> zzXe;
    
    zza(zze paramzze)
    {
      this.zzXe = new WeakReference(paramzze);
    }
    
    public void zza(final ConnectionResult paramConnectionResult, final AuthAccountResult paramAuthAccountResult)
    {
      paramAuthAccountResult = (zze)this.zzXe.get();
      if (paramAuthAccountResult == null) {
        return;
      }
      zze.zzb(paramAuthAccountResult).zzXs.post(new Runnable()
      {
        public void run()
        {
          zze.zzc(paramAuthAccountResult, paramConnectionResult);
        }
      });
    }
  }
  
  private static class zzb
    extends zzq.zza
  {
    private final WeakReference<zze> zzXe;
    
    zzb(zze paramzze)
    {
      this.zzXe = new WeakReference(paramzze);
    }
    
    public void zzb(final ResolveAccountResponse paramResolveAccountResponse)
    {
      final zze localzze = (zze)this.zzXe.get();
      if (localzze == null) {
        return;
      }
      zze.zzb(localzze).zzXs.post(new Runnable()
      {
        public void run()
        {
          zze.zza(localzze, paramResolveAccountResponse);
        }
      });
    }
  }
  
  private static class zzc
    implements GoogleApiClient.ConnectionProgressReportCallbacks
  {
    private final WeakReference<zze> zzXe;
    private final Api<?> zzXk;
    private final int zzXl;
    
    public zzc(zze paramzze, Api<?> paramApi, int paramInt)
    {
      this.zzXe = new WeakReference(paramzze);
      this.zzXk = paramApi;
      this.zzXl = paramInt;
    }
    
    public void onReportAccountValidation(ConnectionResult paramConnectionResult)
    {
      boolean bool = true;
      zze localzze = (zze)this.zzXe.get();
      if (localzze == null) {
        return;
      }
      if (Looper.myLooper() == zze.zzb(localzze).getLooper()) {}
      for (;;)
      {
        zzu.zza(bool, "onReportAccountValidation must be called on the GoogleApiClient handler thread");
        zze.zza(localzze).lock();
        try
        {
          bool = zze.zza(localzze, 1);
          if (!bool)
          {
            return;
            bool = false;
            continue;
          }
          if (!paramConnectionResult.isSuccess()) {
            zze.zza(localzze, paramConnectionResult, this.zzXk, this.zzXl);
          }
          if (zze.zzf(localzze)) {
            zze.zzh(localzze);
          }
          return;
        }
        finally
        {
          zze.zza(localzze).unlock();
        }
      }
    }
    
    public void onReportServiceBinding(ConnectionResult paramConnectionResult)
    {
      boolean bool = false;
      zze localzze = (zze)this.zzXe.get();
      if (localzze == null) {
        return;
      }
      if (Looper.myLooper() == zze.zzb(localzze).getLooper()) {
        bool = true;
      }
      zzu.zza(bool, "onReportServiceBinding must be called on the GoogleApiClient handler thread");
      zze.zza(localzze).lock();
      try
      {
        bool = zze.zza(localzze, 0);
        if (!bool) {
          return;
        }
        if (!paramConnectionResult.isSuccess()) {
          zze.zza(localzze, paramConnectionResult, this.zzXk, this.zzXl);
        }
        if (zze.zzf(localzze)) {
          zze.zzg(localzze);
        }
        return;
      }
      finally
      {
        zze.zza(localzze).unlock();
      }
    }
  }
  
  private class zzd
    implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
  {
    private zzd() {}
    
    public void onConnected(Bundle paramBundle)
    {
      zze.zzc(zze.this).zza(new zze.zzb(zze.this));
    }
    
    /* Error */
    public void onConnectionFailed(ConnectionResult paramConnectionResult)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 17	com/google/android/gms/common/api/zze$zzd:zzXd	Lcom/google/android/gms/common/api/zze;
      //   4: invokestatic 44	com/google/android/gms/common/api/zze:zza	(Lcom/google/android/gms/common/api/zze;)Ljava/util/concurrent/locks/Lock;
      //   7: invokeinterface 49 1 0
      //   12: aload_0
      //   13: getfield 17	com/google/android/gms/common/api/zze$zzd:zzXd	Lcom/google/android/gms/common/api/zze;
      //   16: aload_1
      //   17: invokestatic 53	com/google/android/gms/common/api/zze:zzb	(Lcom/google/android/gms/common/api/zze;Lcom/google/android/gms/common/ConnectionResult;)Z
      //   20: ifeq +30 -> 50
      //   23: aload_0
      //   24: getfield 17	com/google/android/gms/common/api/zze$zzd:zzXd	Lcom/google/android/gms/common/api/zze;
      //   27: invokestatic 55	com/google/android/gms/common/api/zze:zzd	(Lcom/google/android/gms/common/api/zze;)V
      //   30: aload_0
      //   31: getfield 17	com/google/android/gms/common/api/zze$zzd:zzXd	Lcom/google/android/gms/common/api/zze;
      //   34: invokestatic 58	com/google/android/gms/common/api/zze:zze	(Lcom/google/android/gms/common/api/zze;)V
      //   37: aload_0
      //   38: getfield 17	com/google/android/gms/common/api/zze$zzd:zzXd	Lcom/google/android/gms/common/api/zze;
      //   41: invokestatic 44	com/google/android/gms/common/api/zze:zza	(Lcom/google/android/gms/common/api/zze;)Ljava/util/concurrent/locks/Lock;
      //   44: invokeinterface 61 1 0
      //   49: return
      //   50: aload_0
      //   51: getfield 17	com/google/android/gms/common/api/zze$zzd:zzXd	Lcom/google/android/gms/common/api/zze;
      //   54: aload_1
      //   55: invokestatic 64	com/google/android/gms/common/api/zze:zza	(Lcom/google/android/gms/common/api/zze;Lcom/google/android/gms/common/ConnectionResult;)V
      //   58: goto -21 -> 37
      //   61: astore_1
      //   62: aload_0
      //   63: getfield 17	com/google/android/gms/common/api/zze$zzd:zzXd	Lcom/google/android/gms/common/api/zze;
      //   66: invokestatic 44	com/google/android/gms/common/api/zze:zza	(Lcom/google/android/gms/common/api/zze;)Ljava/util/concurrent/locks/Lock;
      //   69: invokeinterface 61 1 0
      //   74: aload_1
      //   75: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	76	0	this	zzd
      //   0	76	1	paramConnectionResult	ConnectionResult
      // Exception table:
      //   from	to	target	type
      //   12	37	61	finally
      //   50	58	61	finally
    }
    
    public void onConnectionSuspended(int paramInt) {}
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/api/zze.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */