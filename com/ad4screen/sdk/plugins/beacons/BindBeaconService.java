package com.ad4screen.sdk.plugins.beacons;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.ad4screen.sdk.A4S.Callback;
import com.ad4screen.sdk.Log;
import java.util.Iterator;
import java.util.List;

public class BindBeaconService
{
  private BindBeaconProvider<IBeaconService> mBindProvider;
  private Context mContext;
  private IBeaconService mService;
  
  public BindBeaconService(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  private void bindToService(final A4S.Callback<IBeaconService> paramCallback)
  {
    Log.internal("BindBeaconService|Finding service...");
    this.mBindProvider = new BindBeaconProvider(this.mContext, "com.ad4screen.sdk.intent.action.QUERY", "com.ad4screen.sdk.intent.category.BEACON_NOTIFICATIONS", IBeaconService.class, new BeaconProviderCallback()
    {
      private IBeaconService firstMatchingVersionService(List<IBeaconService> paramAnonymousList)
      {
        paramAnonymousList = paramAnonymousList.iterator();
        while (paramAnonymousList.hasNext())
        {
          IBeaconService localIBeaconService = (IBeaconService)paramAnonymousList.next();
          try
          {
            long l = localIBeaconService.getVersion();
            if (1L == l) {
              return localIBeaconService;
            }
          }
          catch (RemoteException localRemoteException)
          {
            Log.internal("BindBeaconService|Remote exception while binding to service", localRemoteException);
          }
        }
        Log.internal("BindBeaconService|No service found with version 1");
        return null;
      }
      
      public void onProvidersBinded(List<IBeaconService> paramAnonymousList)
      {
        BindBeaconService.access$002(BindBeaconService.this, firstMatchingVersionService(paramAnonymousList));
        if (BindBeaconService.this.mService == null) {}
        while (paramCallback == null) {
          try
          {
            BindBeaconService.this.mContext.bindService(new Intent(BindBeaconService.this.mContext, Class.forName("com.ad4screen.sdk.A4SBeaconService")), new ServiceConnection()
            {
              public void onServiceConnected(ComponentName paramAnonymous2ComponentName, IBinder paramAnonymous2IBinder)
              {
                BindBeaconService.access$002(BindBeaconService.this, IBeaconService.Stub.asInterface(paramAnonymous2IBinder));
                if (BindBeaconService.1.this.val$callback != null) {
                  BindBeaconService.1.this.val$callback.onResult(BindBeaconService.this.mService);
                }
              }
              
              public void onServiceDisconnected(ComponentName paramAnonymous2ComponentName)
              {
                BindBeaconService.access$002(BindBeaconService.this, null);
              }
            }, 1);
            return;
          }
          catch (ClassNotFoundException paramAnonymousList)
          {
            Log.error("BindBeaconService|Can't create com.ad4screen.sdk.A4SBeaconService", paramAnonymousList);
            return;
          }
        }
        paramCallback.onResult(BindBeaconService.this.mService);
      }
    });
    this.mBindProvider.connectToFirstRunningService();
  }
  
  public IBeaconService getService(A4S.Callback<IBeaconService> paramCallback)
  {
    if (this.mService == null) {
      bindToService(paramCallback);
    }
    for (;;)
    {
      return this.mService;
      try
      {
        Log.internal("BindBeaconService|Service running with v" + this.mService.getVersion());
        if (paramCallback != null) {
          paramCallback.onResult(this.mService);
        }
      }
      catch (RemoteException localRemoteException)
      {
        Log.error("BindBeaconService|Error while getting service", localRemoteException);
        bindToService(paramCallback);
      }
    }
  }
  
  public void unbindService()
  {
    if (this.mBindProvider != null) {
      this.mBindProvider.closeConnections();
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/plugins/beacons/BindBeaconService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */