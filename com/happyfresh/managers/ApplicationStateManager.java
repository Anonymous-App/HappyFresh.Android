package com.happyfresh.managers;

import android.os.Handler;
import com.happyfresh.callbacks.ICartCallback;
import com.happyfresh.common.ICartApplication;
import com.happyfresh.listeners.ApplicationStateChangedListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ApplicationStateManager
{
  private static final String TAG = ApplicationStateManager.class.getSimpleName();
  private List<ApplicationStateChangedListener> appStateChangedListeners = new ArrayList();
  private int mActivityCount;
  private BackgroundWatcherTask mBackgroundWatcherTask;
  private ICartApplication mICartApplication;
  private boolean mOnBackground = true;
  
  public ApplicationStateManager(ICartApplication paramICartApplication)
  {
    this.mICartApplication = paramICartApplication;
  }
  
  private void enterBackground()
  {
    Iterator localIterator = this.appStateChangedListeners.iterator();
    while (localIterator.hasNext())
    {
      ApplicationStateChangedListener localApplicationStateChangedListener = (ApplicationStateChangedListener)localIterator.next();
      if (localApplicationStateChangedListener != null) {
        localApplicationStateChangedListener.onEnterBackground();
      }
    }
  }
  
  private void enterForeground()
  {
    Iterator localIterator = this.appStateChangedListeners.iterator();
    while (localIterator.hasNext())
    {
      ApplicationStateChangedListener localApplicationStateChangedListener = (ApplicationStateChangedListener)localIterator.next();
      if (localApplicationStateChangedListener != null) {
        localApplicationStateChangedListener.onEnterForeground();
      }
    }
    this.mICartApplication.getAndroidAdsId(new ICartCallback(getClass().getSimpleName())
    {
      public void onSuccess(String paramAnonymousString)
      {
        String str1 = ApplicationStateManager.this.mICartApplication.getSharedPreferencesManager().getStockLocationCity();
        String str2 = ApplicationStateManager.this.mICartApplication.getSharedPreferencesManager().getStockLocationZipcode();
        ApplicationStateManager.this.mICartApplication.sendUserData(paramAnonymousString, str1, null, str2, false);
      }
    });
  }
  
  public boolean addApplicationStateChangedListeners(ApplicationStateChangedListener paramApplicationStateChangedListener)
  {
    return this.appStateChangedListeners.add(paramApplicationStateChangedListener);
  }
  
  public int getActivityCount()
  {
    return this.mActivityCount;
  }
  
  public boolean isOnBackground()
  {
    return this.mOnBackground;
  }
  
  public void onActivityPause()
  {
    this.mActivityCount -= 1;
    this.mBackgroundWatcherTask = new BackgroundWatcherTask(null);
    new Handler(this.mICartApplication.getMainLooper()).postDelayed(this.mBackgroundWatcherTask, 500L);
  }
  
  public void onActivityResume()
  {
    if (this.mOnBackground)
    {
      this.mOnBackground = false;
      enterForeground();
    }
    if (this.mBackgroundWatcherTask != null)
    {
      this.mBackgroundWatcherTask.invalid = true;
      this.mBackgroundWatcherTask = null;
    }
    this.mActivityCount += 1;
  }
  
  public boolean removeApplicationStateChangedListeners(ApplicationStateChangedListener paramApplicationStateChangedListener)
  {
    return this.appStateChangedListeners.remove(paramApplicationStateChangedListener);
  }
  
  private class BackgroundWatcherTask
    implements Runnable
  {
    public boolean invalid;
    
    private BackgroundWatcherTask() {}
    
    public void run()
    {
      if (this.invalid) {}
      while (ApplicationStateManager.this.mActivityCount > 0) {
        return;
      }
      ApplicationStateManager.access$302(ApplicationStateManager.this, true);
      ApplicationStateManager.this.enterBackground();
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/happyfresh/managers/ApplicationStateManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */