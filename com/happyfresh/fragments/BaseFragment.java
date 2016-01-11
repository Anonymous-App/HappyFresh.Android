package com.happyfresh.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.appsee.Appsee;
import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder;
import com.google.android.gms.analytics.Tracker;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.customs.ICartScreenViewBuilder;
import com.happyfresh.utils.LogUtils;

public abstract class BaseFragment
  extends Fragment
{
  protected Context mContext;
  protected ICartApplication mICartApplication;
  
  public ICartApplication getApplication()
  {
    if (this.mICartApplication != null) {
      return this.mICartApplication;
    }
    return ICartApplication.get(this);
  }
  
  protected abstract String getScreenName();
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mICartApplication = ICartApplication.get(this);
    this.mContext = this.mICartApplication.getApplicationContext();
  }
  
  public void onResume()
  {
    super.onResume();
    sendTracker();
  }
  
  protected void sendTracker()
  {
    sendTracker(getScreenName());
  }
  
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
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/BaseFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */