package com.facebook;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import com.facebook.internal.Validate;

public abstract class AccessTokenTracker
{
  private final LocalBroadcastManager broadcastManager;
  private boolean isTracking = false;
  private final BroadcastReceiver receiver;
  
  public AccessTokenTracker()
  {
    Validate.sdkInitialized();
    this.receiver = new CurrentAccessTokenBroadcastReceiver(null);
    this.broadcastManager = LocalBroadcastManager.getInstance(FacebookSdk.getApplicationContext());
    startTracking();
  }
  
  private void addBroadcastReceiver()
  {
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.facebook.sdk.ACTION_CURRENT_ACCESS_TOKEN_CHANGED");
    this.broadcastManager.registerReceiver(this.receiver, localIntentFilter);
  }
  
  public boolean isTracking()
  {
    return this.isTracking;
  }
  
  protected abstract void onCurrentAccessTokenChanged(AccessToken paramAccessToken1, AccessToken paramAccessToken2);
  
  public void startTracking()
  {
    if (this.isTracking) {
      return;
    }
    addBroadcastReceiver();
    this.isTracking = true;
  }
  
  public void stopTracking()
  {
    if (!this.isTracking) {
      return;
    }
    this.broadcastManager.unregisterReceiver(this.receiver);
    this.isTracking = false;
  }
  
  private class CurrentAccessTokenBroadcastReceiver
    extends BroadcastReceiver
  {
    private CurrentAccessTokenBroadcastReceiver() {}
    
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      if ("com.facebook.sdk.ACTION_CURRENT_ACCESS_TOKEN_CHANGED".equals(paramIntent.getAction()))
      {
        paramContext = (AccessToken)paramIntent.getParcelableExtra("com.facebook.sdk.EXTRA_OLD_ACCESS_TOKEN");
        paramIntent = (AccessToken)paramIntent.getParcelableExtra("com.facebook.sdk.EXTRA_NEW_ACCESS_TOKEN");
        AccessTokenTracker.this.onCurrentAccessTokenChanged(paramContext, paramIntent);
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/facebook/AccessTokenTracker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */