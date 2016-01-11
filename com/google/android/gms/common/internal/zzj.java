package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public final class zzj
  implements Handler.Callback
{
  private final Handler mHandler;
  private final zza zzaaC;
  private final ArrayList<GoogleApiClient.ConnectionCallbacks> zzaaD = new ArrayList();
  final ArrayList<GoogleApiClient.ConnectionCallbacks> zzaaE = new ArrayList();
  private final ArrayList<GoogleApiClient.OnConnectionFailedListener> zzaaF = new ArrayList();
  private volatile boolean zzaaG = false;
  private final AtomicInteger zzaaH = new AtomicInteger(0);
  private boolean zzaaI = false;
  private final Object zzqt = new Object();
  
  public zzj(Looper paramLooper, zza paramzza)
  {
    this.zzaaC = paramzza;
    this.mHandler = new Handler(paramLooper, this);
  }
  
  public boolean handleMessage(Message arg1)
  {
    if (???.what == 1)
    {
      GoogleApiClient.ConnectionCallbacks localConnectionCallbacks = (GoogleApiClient.ConnectionCallbacks)???.obj;
      synchronized (this.zzqt)
      {
        if ((this.zzaaG) && (this.zzaaC.isConnected()) && (this.zzaaD.contains(localConnectionCallbacks))) {
          localConnectionCallbacks.onConnected(this.zzaaC.zzlM());
        }
        return true;
      }
    }
    Log.wtf("GmsClientEvents", "Don't know how to handle this message.");
    return false;
  }
  
  public boolean isConnectionCallbacksRegistered(GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks)
  {
    zzu.zzu(paramConnectionCallbacks);
    synchronized (this.zzqt)
    {
      boolean bool = this.zzaaD.contains(paramConnectionCallbacks);
      return bool;
    }
  }
  
  public boolean isConnectionFailedListenerRegistered(GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    zzu.zzu(paramOnConnectionFailedListener);
    synchronized (this.zzqt)
    {
      boolean bool = this.zzaaF.contains(paramOnConnectionFailedListener);
      return bool;
    }
  }
  
  public void registerConnectionCallbacks(GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks)
  {
    zzu.zzu(paramConnectionCallbacks);
    synchronized (this.zzqt)
    {
      if (this.zzaaD.contains(paramConnectionCallbacks))
      {
        Log.w("GmsClientEvents", "registerConnectionCallbacks(): listener " + paramConnectionCallbacks + " is already registered");
        if (this.zzaaC.isConnected()) {
          this.mHandler.sendMessage(this.mHandler.obtainMessage(1, paramConnectionCallbacks));
        }
        return;
      }
      this.zzaaD.add(paramConnectionCallbacks);
    }
  }
  
  public void registerConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    zzu.zzu(paramOnConnectionFailedListener);
    synchronized (this.zzqt)
    {
      if (this.zzaaF.contains(paramOnConnectionFailedListener))
      {
        Log.w("GmsClientEvents", "registerConnectionFailedListener(): listener " + paramOnConnectionFailedListener + " is already registered");
        return;
      }
      this.zzaaF.add(paramOnConnectionFailedListener);
    }
  }
  
  public void unregisterConnectionCallbacks(GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks)
  {
    zzu.zzu(paramConnectionCallbacks);
    synchronized (this.zzqt)
    {
      if (!this.zzaaD.remove(paramConnectionCallbacks)) {
        Log.w("GmsClientEvents", "unregisterConnectionCallbacks(): listener " + paramConnectionCallbacks + " not found");
      }
      while (!this.zzaaI) {
        return;
      }
      this.zzaaE.add(paramConnectionCallbacks);
    }
  }
  
  public void unregisterConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    zzu.zzu(paramOnConnectionFailedListener);
    synchronized (this.zzqt)
    {
      if (!this.zzaaF.remove(paramOnConnectionFailedListener)) {
        Log.w("GmsClientEvents", "unregisterConnectionFailedListener(): listener " + paramOnConnectionFailedListener + " not found");
      }
      return;
    }
  }
  
  public void zzbu(int paramInt)
  {
    this.mHandler.removeMessages(1);
    synchronized (this.zzqt)
    {
      this.zzaaI = true;
      Object localObject2 = new ArrayList(this.zzaaD);
      int i = this.zzaaH.get();
      localObject2 = ((ArrayList)localObject2).iterator();
      GoogleApiClient.ConnectionCallbacks localConnectionCallbacks;
      do
      {
        if (((Iterator)localObject2).hasNext())
        {
          localConnectionCallbacks = (GoogleApiClient.ConnectionCallbacks)((Iterator)localObject2).next();
          if ((this.zzaaG) && (this.zzaaH.get() == i)) {}
        }
        else
        {
          this.zzaaE.clear();
          this.zzaaI = false;
          return;
        }
      } while (!this.zzaaD.contains(localConnectionCallbacks));
      localConnectionCallbacks.onConnectionSuspended(paramInt);
    }
  }
  
  public void zzg(Bundle paramBundle)
  {
    boolean bool2 = true;
    for (;;)
    {
      synchronized (this.zzqt)
      {
        if (!this.zzaaI)
        {
          bool1 = true;
          zzu.zzU(bool1);
          this.mHandler.removeMessages(1);
          this.zzaaI = true;
          if (this.zzaaE.size() != 0) {
            break label185;
          }
          bool1 = bool2;
          zzu.zzU(bool1);
          Object localObject2 = new ArrayList(this.zzaaD);
          int i = this.zzaaH.get();
          localObject2 = ((ArrayList)localObject2).iterator();
          GoogleApiClient.ConnectionCallbacks localConnectionCallbacks;
          if (((Iterator)localObject2).hasNext())
          {
            localConnectionCallbacks = (GoogleApiClient.ConnectionCallbacks)((Iterator)localObject2).next();
            if ((this.zzaaG) && (this.zzaaC.isConnected()) && (this.zzaaH.get() == i)) {}
          }
          else
          {
            this.zzaaE.clear();
            this.zzaaI = false;
            return;
          }
          if (this.zzaaE.contains(localConnectionCallbacks)) {
            continue;
          }
          localConnectionCallbacks.onConnected(paramBundle);
        }
      }
      boolean bool1 = false;
      continue;
      label185:
      bool1 = false;
    }
  }
  
  public void zzh(ConnectionResult paramConnectionResult)
  {
    this.mHandler.removeMessages(1);
    synchronized (this.zzqt)
    {
      Object localObject2 = new ArrayList(this.zzaaF);
      int i = this.zzaaH.get();
      localObject2 = ((ArrayList)localObject2).iterator();
      while (((Iterator)localObject2).hasNext())
      {
        GoogleApiClient.OnConnectionFailedListener localOnConnectionFailedListener = (GoogleApiClient.OnConnectionFailedListener)((Iterator)localObject2).next();
        if ((!this.zzaaG) || (this.zzaaH.get() != i)) {
          return;
        }
        if (this.zzaaF.contains(localOnConnectionFailedListener)) {
          localOnConnectionFailedListener.onConnectionFailed(paramConnectionResult);
        }
      }
    }
  }
  
  public void zznT()
  {
    this.zzaaG = false;
    this.zzaaH.incrementAndGet();
  }
  
  public void zznU()
  {
    this.zzaaG = true;
  }
  
  public static abstract interface zza
  {
    public abstract boolean isConnected();
    
    public abstract Bundle zzlM();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/google/android/gms/common/internal/zzj.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */