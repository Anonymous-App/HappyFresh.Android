package com.adjust.sdk;

import android.net.Uri;

public class AdjustInstance
{
  private ActivityHandler activityHandler;
  private String referrer;
  private long referrerClickTime;
  
  private boolean checkActivityHandler()
  {
    if (this.activityHandler == null)
    {
      getLogger().error("Please initialize Adjust by calling 'onCreate' before", new Object[0]);
      return false;
    }
    return true;
  }
  
  private static ILogger getLogger()
  {
    return AdjustFactory.getLogger();
  }
  
  public void appWillOpenUrl(Uri paramUri)
  {
    if (!checkActivityHandler()) {
      return;
    }
    long l = System.currentTimeMillis();
    this.activityHandler.readOpenUrl(paramUri, l);
  }
  
  public boolean isEnabled()
  {
    if (!checkActivityHandler()) {
      return false;
    }
    return this.activityHandler.isEnabled();
  }
  
  public void onCreate(AdjustConfig paramAdjustConfig)
  {
    if (this.activityHandler != null)
    {
      getLogger().error("Adjust already initialized", new Object[0]);
      return;
    }
    paramAdjustConfig.referrer = this.referrer;
    paramAdjustConfig.referrerClickTime = this.referrerClickTime;
    this.activityHandler = ActivityHandler.getInstance(paramAdjustConfig);
  }
  
  public void onPause()
  {
    if (!checkActivityHandler()) {
      return;
    }
    this.activityHandler.trackSubsessionEnd();
  }
  
  public void onResume()
  {
    if (!checkActivityHandler()) {
      return;
    }
    this.activityHandler.trackSubsessionStart();
  }
  
  public void sendReferrer(String paramString)
  {
    long l = System.currentTimeMillis();
    if (this.activityHandler == null)
    {
      this.referrer = paramString;
      this.referrerClickTime = l;
      return;
    }
    this.activityHandler.sendReferrer(paramString, l);
  }
  
  public void setEnabled(boolean paramBoolean)
  {
    if (!checkActivityHandler()) {
      return;
    }
    this.activityHandler.setEnabled(paramBoolean);
  }
  
  public void setOfflineMode(boolean paramBoolean)
  {
    if (!checkActivityHandler()) {
      return;
    }
    this.activityHandler.setOfflineMode(paramBoolean);
  }
  
  public void trackEvent(AdjustEvent paramAdjustEvent)
  {
    if (!checkActivityHandler()) {
      return;
    }
    this.activityHandler.trackEvent(paramAdjustEvent);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/adjust/sdk/AdjustInstance.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */