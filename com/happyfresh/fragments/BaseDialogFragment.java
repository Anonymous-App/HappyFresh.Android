package com.happyfresh.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import com.appsee.Appsee;
import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder;
import com.google.android.gms.analytics.Tracker;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.customs.ICartScreenViewBuilder;
import com.happyfresh.utils.LogUtils;

public class BaseDialogFragment
  extends DialogFragment
{
  protected ICartApplication mICartApplication;
  
  public ICartApplication getApplication()
  {
    if (this.mICartApplication != null) {
      return this.mICartApplication;
    }
    return ICartApplication.get(this);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mICartApplication = ICartApplication.get(this);
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


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/fragments/BaseDialogFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */