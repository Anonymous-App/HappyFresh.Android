package com.appsee;

import android.app.Activity;
import android.app.Instrumentation;

class wd
  extends Instrumentation
{
  wd(sd paramsd) {}
  
  public void callActivityOnDestroy(Activity paramActivity)
  {
    i(paramActivity);
    super.callActivityOnDestroy(paramActivity);
  }
  
  public void callActivityOnPause(Activity paramActivity)
  {
    ol.l(new ad(this));
    super.callActivityOnPause(paramActivity);
  }
  
  public void callActivityOnResume(Activity paramActivity)
  {
    ol.l(new gd(this, paramActivity));
    super.callActivityOnResume(paramActivity);
  }
  
  public void callActivityOnStop(Activity paramActivity)
  {
    i(paramActivity);
    super.callActivityOnStop(paramActivity);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/wd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */