package com.ad4screen.sdk.plugins.beacons;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.IBinder;
import android.os.IInterface;
import com.ad4screen.sdk.Log;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class BindBeaconProvider<T extends IInterface>
{
  private static final String TAG = "BindBeaconService";
  private final BeaconProviderCallback<T> mCallback;
  private final Context mContext;
  private final Intent mIntent;
  private int mMax;
  private final Class<T> mParameterType;
  private List<T> mProviders = new ArrayList();
  private List<IBeaconServiceConnection<T>> mProvidersConnections = new ArrayList();
  
  public BindBeaconProvider(Context paramContext, String paramString1, String paramString2, Class<T> paramClass, BeaconProviderCallback<T> paramBeaconProviderCallback)
  {
    this.mContext = paramContext;
    this.mIntent = new Intent(paramString1);
    if (paramString2 != null) {
      this.mIntent.addCategory(paramString2);
    }
    this.mParameterType = paramClass;
    this.mCallback = paramBeaconProviderCallback;
  }
  
  private List<ResolveInfo> getServicesForIntent(Intent paramIntent)
  {
    return this.mContext.getPackageManager().queryIntentServices(paramIntent, 0);
  }
  
  private boolean isServiceRunning(String paramString1, String paramString2)
  {
    Iterator localIterator = ((ActivityManager)this.mContext.getSystemService("activity")).getRunningServices(Integer.MAX_VALUE).iterator();
    while (localIterator.hasNext())
    {
      ActivityManager.RunningServiceInfo localRunningServiceInfo = (ActivityManager.RunningServiceInfo)localIterator.next();
      if ((paramString1.equals(localRunningServiceInfo.service.getClassName())) && (paramString2.equals(localRunningServiceInfo.service.getPackageName())))
      {
        Log.info("BindBeaconService|" + paramString1 + " running");
        return true;
      }
    }
    Log.info("BindBeaconService|" + paramString1 + " not running");
    return false;
  }
  
  private void onFinishToBind()
  {
    Log.info("BindBeaconService|onFinishToBind with " + this.mProvidersConnections.size() + " items");
    Iterator localIterator = this.mProvidersConnections.iterator();
    while (localIterator.hasNext())
    {
      IInterface localIInterface = (IInterface)((IBeaconServiceConnection)localIterator.next()).getProvider();
      if (localIInterface != null) {
        this.mProviders.add(localIInterface);
      }
    }
    if (this.mCallback != null) {
      this.mCallback.onProvidersBinded(this.mProviders);
    }
  }
  
  private void onProviderDisconnected(T paramT)
  {
    this.mProviders.remove(paramT);
  }
  
  public void closeConnections()
  {
    Log.info("BindBeaconService|Clear " + this.mProvidersConnections.size() + " binded interface");
    Iterator localIterator = this.mProvidersConnections.iterator();
    while (localIterator.hasNext())
    {
      IBeaconServiceConnection localIBeaconServiceConnection = (IBeaconServiceConnection)localIterator.next();
      this.mContext.unbindService(localIBeaconServiceConnection);
    }
    this.mProvidersConnections.clear();
    this.mProviders.clear();
    this.mMax = 0;
  }
  
  public void connectToFirstRunningService()
  {
    List localList = Collections.synchronizedList(new ArrayList());
    Iterator localIterator = getServicesForIntent(this.mIntent).iterator();
    while (localIterator.hasNext())
    {
      ResolveInfo localResolveInfo = (ResolveInfo)localIterator.next();
      Object localObject = localResolveInfo.serviceInfo;
      if ((localObject != null) && (isServiceRunning(((ServiceInfo)localObject).name, ((ServiceInfo)localObject).packageName)))
      {
        Log.info("BindBeaconService|Service found " + localResolveInfo);
        Intent localIntent = new Intent(this.mIntent);
        localIntent.setClassName(((ServiceInfo)localObject).packageName, ((ServiceInfo)localObject).name);
        localObject = new BeaconServiceConnection(localList, null);
        try
        {
          Log.info("BindBeaconService|Binding...");
          if (!this.mContext.bindService(localIntent, (ServiceConnection)localObject, 1)) {
            break label233;
          }
          localList.add(localObject);
          Log.info("BindBeaconService|Bind OK !");
        }
        catch (SecurityException localSecurityException)
        {
          Log.warn("BindBeaconService|Impossible to bind to '" + localResolveInfo.serviceInfo.name + "' in app '" + localResolveInfo.serviceInfo.applicationInfo.packageName + "'");
        }
        continue;
        label233:
        Log.error("BindBeaconService|Impossible to bind to service " + localResolveInfo);
      }
    }
    this.mMax = localList.size();
    if ((this.mMax == 0) && (this.mCallback != null)) {
      this.mCallback.onProvidersBinded(this.mProviders);
    }
    this.mProvidersConnections = localList;
  }
  
  public List<T> getProviders()
  {
    return this.mProviders;
  }
  
  private final class BeaconServiceConnection
    implements IBeaconServiceConnection<T>
  {
    private T mProvider;
    private List<IBeaconServiceConnection<T>> mProvidersConnection;
    
    private BeaconServiceConnection()
    {
      List localList;
      this.mProvidersConnection = localList;
    }
    
    public T getProvider()
    {
      return this.mProvider;
    }
    
    public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
    {
      try
      {
        Log.debug("BindBeaconService|onServiceConnected " + paramComponentName);
        this.mProvider = ((IInterface)Class.forName(BindBeaconProvider.this.mParameterType.getName() + "$Stub").getMethod("asInterface", new Class[] { IBinder.class }).invoke(null, new Object[] { paramIBinder }));
        BindBeaconProvider.access$210(BindBeaconProvider.this);
        if (BindBeaconProvider.this.mMax == 0) {
          BindBeaconProvider.this.onFinishToBind();
        }
        return;
      }
      catch (Exception paramComponentName)
      {
        for (;;)
        {
          Log.error("BindBeaconService|Ignore provider. Invalid type " + BindBeaconProvider.this.mParameterType.getName() + " in " + getClass(), paramComponentName);
        }
      }
    }
    
    public void onServiceDisconnected(ComponentName paramComponentName)
    {
      Log.debug("BindBeaconService|onServiceDisconnected");
      this.mProvidersConnection.remove(this);
      BindBeaconProvider.this.onProviderDisconnected(this.mProvider);
      this.mProvider = null;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/plugins/beacons/BindBeaconProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */