package com.parse;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.ReceiverCallNotAllowedException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

class ConnectivityNotifier
  extends BroadcastReceiver
{
  private static final String TAG = "com.parse.ConnectivityNotifier";
  private static final ConnectivityNotifier singleton = new ConnectivityNotifier();
  private boolean hasRegisteredReceiver = false;
  private Set<ConnectivityListener> listeners = new HashSet();
  private final Object lock = new Object();
  
  public static ConnectivityNotifier getNotifier(Context paramContext)
  {
    singleton.tryToRegisterForNetworkStatusNotifications(paramContext);
    return singleton;
  }
  
  public static boolean isConnected(Context paramContext)
  {
    paramContext = (ConnectivityManager)paramContext.getSystemService("connectivity");
    if (paramContext == null) {}
    do
    {
      return false;
      paramContext = paramContext.getActiveNetworkInfo();
    } while ((paramContext == null) || (!paramContext.isConnected()));
    return true;
  }
  
  private boolean tryToRegisterForNetworkStatusNotifications(Context paramContext)
  {
    synchronized (this.lock)
    {
      if (this.hasRegisteredReceiver) {
        return true;
      }
      if (paramContext == null) {
        return false;
      }
    }
    return false;
  }
  
  public void addListener(ConnectivityListener paramConnectivityListener)
  {
    synchronized (this.lock)
    {
      this.listeners.add(paramConnectivityListener);
      return;
    }
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    synchronized (this.lock)
    {
      ArrayList localArrayList = new ArrayList(this.listeners);
      ??? = localArrayList.iterator();
      if (((Iterator)???).hasNext()) {
        ((ConnectivityListener)((Iterator)???).next()).networkConnectivityStatusChanged(paramContext, paramIntent);
      }
    }
  }
  
  public void removeListener(ConnectivityListener paramConnectivityListener)
  {
    synchronized (this.lock)
    {
      this.listeners.remove(paramConnectivityListener);
      return;
    }
  }
  
  public static abstract interface ConnectivityListener
  {
    public abstract void networkConnectivityStatusChanged(Context paramContext, Intent paramIntent);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ConnectivityNotifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */