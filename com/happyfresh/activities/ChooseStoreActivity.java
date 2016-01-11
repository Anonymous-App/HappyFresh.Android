package com.happyfresh.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.fragments.ChooseStoreFragment;
import com.happyfresh.managers.SharedPreferencesManager;
import java.util.ArrayList;

public class ChooseStoreActivity
  extends BaseActivity
{
  private static final String TAG = ChooseStoreActivity.class.getSimpleName();
  private ClosingChooseStoreActivityReceiver mClosingChooseStoreActivityReceiver;
  
  protected Fragment createFragment()
  {
    long l = getIntent().getLongExtra("ICartConstant.KEYS.EXTRAS.SUB_DISTRICT_ID", 0L);
    Object localObject = getIntent().getStringExtra("ICartConstant.KEYS.EXTRAS.ZIP_CODE");
    double d1 = getIntent().getDoubleExtra("ICartConstant.KEYS.EXTRAS.LONGITUDE", 0.0D);
    double d2 = getIntent().getDoubleExtra("ICartConstant.KEYS.EXTRAS.LATITUDE", 0.0D);
    String str1 = getIntent().getStringExtra("ICartConstant.KEYS.EXTRAS.SUB_LOCALITY");
    String str2 = getIntent().getStringExtra("ICartConstant.KEYS.EXTRAS.CITY_NAME");
    ArrayList localArrayList = getIntent().getParcelableArrayListExtra("ICartConstant.KEYS.EXTRAS.STOCK_LOCATIONS");
    Bundle localBundle = new Bundle();
    localBundle.putLong("ICartConstant.KEYS.EXTRAS.SUB_DISTRICT_ID", Long.valueOf(l).longValue());
    localBundle.putString("ICartConstant.KEYS.EXTRAS.ZIP_CODE", (String)localObject);
    localBundle.putDouble("ICartConstant.KEYS.EXTRAS.LONGITUDE", Double.valueOf(d1).doubleValue());
    localBundle.putDouble("ICartConstant.KEYS.EXTRAS.LATITUDE", Double.valueOf(d2).doubleValue());
    localBundle.putString("ICartConstant.KEYS.EXTRAS.SUB_LOCALITY", str1);
    localBundle.putString("ICartConstant.KEYS.EXTRAS.CITY_NAME", str2);
    localBundle.putParcelableArrayList("ICartConstant.KEYS.EXTRAS.STOCK_LOCATIONS", localArrayList);
    localObject = new ChooseStoreFragment();
    ((Fragment)localObject).setArguments(localBundle);
    return (Fragment)localObject;
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    paramBundle = this.mICartApplication.getSharedPreferencesManager().getSubDistrictName();
    ActionBar localActionBar = getSupportActionBar();
    if (localActionBar != null)
    {
      localActionBar.setDefaultDisplayHomeAsUpEnabled(true);
      localActionBar.setDisplayHomeAsUpEnabled(true);
      localActionBar.setTitle(paramBundle);
    }
  }
  
  protected void onPause()
  {
    super.onPause();
    unregisterReceiver(this.mClosingChooseStoreActivityReceiver);
  }
  
  protected void onResume()
  {
    super.onResume();
    IntentFilter localIntentFilter = new IntentFilter("ChooseStoreActivity.ACTION_CLOSE");
    this.mClosingChooseStoreActivityReceiver = new ClosingChooseStoreActivityReceiver();
    registerReceiver(this.mClosingChooseStoreActivityReceiver, localIntentFilter);
  }
  
  class ClosingChooseStoreActivityReceiver
    extends BroadcastReceiver
  {
    ClosingChooseStoreActivityReceiver() {}
    
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      if (paramIntent.getAction().equals("ChooseStoreActivity.ACTION_CLOSE")) {
        ChooseStoreActivity.this.finish();
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/activities/ChooseStoreActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */