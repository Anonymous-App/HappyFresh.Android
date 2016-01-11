package com.happyfresh.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.ad4screen.sdk.A4S;
import com.adjust.sdk.Adjust;
import com.appsee.Appsee;
import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder;
import com.google.android.gms.analytics.Tracker;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.customs.ICartScreenViewBuilder;
import com.happyfresh.managers.ApplicationStateManager;
import com.happyfresh.managers.SharedPreferencesManager;
import com.happyfresh.models.EditingCartInProgress;
import com.happyfresh.models.Order;
import com.happyfresh.models.User;
import com.happyfresh.utils.LogUtils;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import java.util.List;
import java.util.Locale;

public abstract class BaseActivity
  extends AppCompatActivity
  implements ActivityCompat.OnRequestPermissionsResultCallback
{
  protected static boolean PERMISSION_REQUESTED = false;
  protected static final int PERMISSION_REQUEST_LOCATION = 1;
  protected ICartApplication mICartApplication;
  private ICartCallback mRequestPermissionCallback;
  Toolbar mToolbar;
  
  public void changeLanguage(Locale paramLocale)
  {
    if (paramLocale == null) {
      return;
    }
    Resources localResources = this.mICartApplication.getResources();
    DisplayMetrics localDisplayMetrics = localResources.getDisplayMetrics();
    Configuration localConfiguration = localResources.getConfiguration();
    localConfiguration.locale = paramLocale;
    localResources.updateConfiguration(localConfiguration, localDisplayMetrics);
  }
  
  protected abstract Fragment createFragment();
  
  public void disableUpNavigation()
  {
    ActionBar localActionBar = getSupportActionBar();
    if (localActionBar != null)
    {
      localActionBar.setDefaultDisplayHomeAsUpEnabled(false);
      localActionBar.setDisplayHomeAsUpEnabled(false);
      invalidateOptionsMenu();
    }
  }
  
  protected String getFragmentTag()
  {
    return null;
  }
  
  protected void initCartMenuItem(final MenuItem paramMenuItem)
  {
    if (paramMenuItem == null) {}
    View localView;
    do
    {
      return;
      localView = paramMenuItem.getActionView();
    } while (localView == null);
    TextView localTextView = (TextView)localView.findViewById(2131559071);
    int i = 0;
    Order localOrder = this.mICartApplication.currentOrder;
    if (localOrder != null) {
      i = localOrder.lineItems.size();
    }
    i = i + this.mICartApplication.getEditingCartInProgress().getSizeAddToCartInProgress() - this.mICartApplication.getEditingCartInProgress().getSizeRemoveFromCartInProgress();
    if (i == 0) {
      localTextView.setVisibility(8);
    }
    for (;;)
    {
      ((ImageView)localView.findViewById(2131559070)).setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          BaseActivity.this.onOptionsItemSelected(paramMenuItem);
        }
      });
      return;
      localTextView.setText(String.valueOf(i));
      localTextView.setVisibility(0);
    }
  }
  
  public boolean isLocationPermissionGranted()
  {
    return (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) && (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView();
    setRequestedOrientation(1);
    this.mToolbar = ((Toolbar)findViewById(2131558514));
    Fragment localFragment;
    String str;
    if (paramBundle == null)
    {
      paramBundle = getSupportFragmentManager();
      if (paramBundle.findFragmentById(2131558513) == null)
      {
        localFragment = createFragment();
        str = getFragmentTag();
        if (str != null) {
          break label189;
        }
        paramBundle.beginTransaction().add(2131558513, localFragment).commit();
      }
    }
    for (;;)
    {
      setSupportActionBar(this.mToolbar);
      this.mICartApplication = ICartApplication.get(this);
      this.mICartApplication.setCurrentUser();
      this.mICartApplication.setCurrentUserAddress();
      this.mICartApplication.setCurrentOrder();
      this.mICartApplication.setCurrentSubDistrict();
      Appsee.start("d6a767c5b33d4167801fa3d88c997aa2");
      if (this.mICartApplication.getCurrentUser() != null) {
        Appsee.setUserId(this.mICartApplication.getCurrentUser().email);
      }
      if (!TextUtils.isEmpty(this.mICartApplication.getSharedPreferencesManager().getStockLocationName())) {
        Appsee.setLocationDescription(this.mICartApplication.getSharedPreferencesManager().getStockLocationName());
      }
      changeLanguage(this.mICartApplication.getSharedPreferencesManager().getLocale());
      return;
      label189:
      paramBundle.beginTransaction().add(2131558513, localFragment, str).commit();
      continue;
      restoreFragment(getSupportFragmentManager().findFragmentById(2131558513));
    }
  }
  
  protected void onDestroy()
  {
    this.mICartApplication.getMixpanel().flush();
    super.onDestroy();
  }
  
  protected void onNewIntent(Intent paramIntent)
  {
    super.onNewIntent(paramIntent);
    setIntent(paramIntent);
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default: 
      return super.onOptionsItemSelected(paramMenuItem);
    }
    onBackPressed();
    return true;
  }
  
  protected void onPause()
  {
    super.onPause();
    Adjust.onPause();
    this.mICartApplication.getAccengage().stopActivity(this);
    this.mICartApplication.getApplicationStateManager().onActivityPause();
  }
  
  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfInt)
  {
    switch (paramInt)
    {
    }
    do
    {
      do
      {
        return;
        if ((paramArrayOfInt.length <= 0) || (paramArrayOfInt[0] != 0)) {
          break;
        }
      } while (this.mRequestPermissionCallback == null);
      this.mRequestPermissionCallback.onSuccess(null);
      return;
    } while (this.mRequestPermissionCallback == null);
    this.mRequestPermissionCallback.onFailure(null);
  }
  
  protected void onResume()
  {
    super.onResume();
    Adjust.onResume();
    setAccengageInAppDisplayLocked();
    this.mICartApplication.getAccengage().startActivity(this);
    this.mICartApplication.getApplicationStateManager().onActivityResume();
  }
  
  public void requestLocationPermission()
  {
    requestLocationPermission(null, false);
  }
  
  public void requestLocationPermission(ICartCallback paramICartCallback, boolean paramBoolean)
  {
    if ((!isLocationPermissionGranted()) && ((!PERMISSION_REQUESTED) || (paramBoolean)))
    {
      this.mRequestPermissionCallback = paramICartCallback;
      ActivityCompat.requestPermissions(this, new String[] { "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION" }, 1);
      if (!paramBoolean) {
        PERMISSION_REQUESTED = true;
      }
    }
  }
  
  protected void restoreFragment(Fragment paramFragment) {}
  
  protected void sendTracker(String paramString)
  {
    if ((this.mICartApplication != null) && (paramString != null))
    {
      LogUtils.LOG("tracker >> " + paramString);
      Tracker localTracker = this.mICartApplication.getTracker();
      localTracker.setScreenName(paramString);
      localTracker.send(ICartScreenViewBuilder.build(this.mICartApplication).build());
      Appsee.startScreen(paramString);
    }
  }
  
  protected void setAccengageInAppDisplayLocked()
  {
    this.mICartApplication.setAccengageInAppDisplayLocked(false);
  }
  
  protected void setContentView()
  {
    setContentView(2130903067);
  }
  
  protected void setToolbarTitle(String paramString)
  {
    ActionBar localActionBar = getSupportActionBar();
    if (localActionBar != null) {
      localActionBar.setTitle(paramString);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/activities/BaseActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */