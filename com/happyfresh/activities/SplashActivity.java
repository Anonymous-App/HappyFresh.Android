package com.happyfresh.activities;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.fragments.SplashFragment;
import com.happyfresh.listeners.LocationRetrievedListener;
import com.happyfresh.utils.AccengageTrackerUtils;
import com.happyfresh.utils.AdjustUtils;
import com.happyfresh.utils.LogUtils;
import com.happyfresh.utils.MixpanelTrackerUtils;

public class SplashActivity
  extends LocationActivity
{
  protected Fragment createFragment()
  {
    SplashFragment localSplashFragment = new SplashFragment();
    Uri localUri = getIntent().getData();
    if (localUri != null)
    {
      Bundle localBundle = new Bundle();
      localBundle.putString("ICartConstant.EXTRAS.DEEP_LINKS.TYPE", localUri.getQueryParameter("type"));
      localBundle.putString("ICartConstant.EXTRAS.DEEP_LINKS.STOCK_LOCATION_ID", localUri.getQueryParameter("stock_location_id"));
      localBundle.putString("ICartConstant.EXTRAS.DEEP_LINKS.SUPPLIER_ID", localUri.getQueryParameter("supplier_id"));
      localSplashFragment.setArguments(localBundle);
    }
    return localSplashFragment;
  }
  
  public void onConnectionSuspended(int paramInt) {}
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setToolbarTitle(null);
    this.mICartApplication.trackCrashlytics();
    AdjustUtils.trackOpenApp();
    MixpanelTrackerUtils.trackOpenApp(this.mICartApplication);
    AccengageTrackerUtils.trackOpenApp(this.mICartApplication);
    this.mICartApplication.sendEvent("ui_action", "Opened App", null);
    requestLocation(new LocationRetrievedListener()
    {
      public void onLocationNotRetrieved()
      {
        LogUtils.LOG("Cannot get location");
      }
      
      public void onLocationRetrieved(Location paramAnonymousLocation)
      {
        LogUtils.LOG("Location updated");
      }
    });
  }
  
  protected void setAccengageInAppDisplayLocked()
  {
    this.mICartApplication.setAccengageInAppDisplayLocked(true);
  }
  
  protected void setContentView()
  {
    setContentView(2130903069);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/activities/SplashActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */