package com.happyfresh.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.happyfresh.fragments.ChooseLocationFragment;

public class ChooseLocationActivity
  extends LocationActivity
{
  private static final String TAG = ChooseLocationActivity.class.getSimpleName();
  
  protected Fragment createFragment()
  {
    return new ChooseLocationFragment();
  }
  
  public void onConnectionSuspended(int paramInt) {}
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setToolbarTitle(null);
    requestLocationPermission();
  }
  
  protected void setContentView()
  {
    setContentView(2130903069);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/activities/ChooseLocationActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */