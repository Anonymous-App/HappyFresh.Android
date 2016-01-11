package com.appsee;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;

@TargetApi(14)
class nd
  implements Application.ActivityLifecycleCallbacks
{
  public void onActivityCreated(Activity paramActivity, Bundle paramBundle) {}
  
  public void onActivityDestroyed(Activity paramActivity)
  {
    ol.l(new md(this, paramActivity));
  }
  
  public void onActivityPaused(Activity paramActivity)
  {
    ol.l(new qd(this));
  }
  
  public void onActivityResumed(Activity paramActivity)
  {
    ol.l(new kd(this, paramActivity));
  }
  
  public void onActivitySaveInstanceState(Activity paramActivity, Bundle paramBundle) {}
  
  public void onActivityStarted(Activity paramActivity) {}
  
  public void onActivityStopped(Activity paramActivity)
  {
    ol.l(new dd(this, paramActivity));
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/nd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */