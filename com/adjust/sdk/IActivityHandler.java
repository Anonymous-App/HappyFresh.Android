package com.adjust.sdk;

import android.net.Uri;
import org.json.JSONObject;

public abstract interface IActivityHandler
{
  public abstract void finishedTrackingActivity(JSONObject paramJSONObject);
  
  public abstract ActivityPackage getAttributionPackage();
  
  public abstract void init(AdjustConfig paramAdjustConfig);
  
  public abstract boolean isEnabled();
  
  public abstract void readOpenUrl(Uri paramUri, long paramLong);
  
  public abstract void sendReferrer(String paramString, long paramLong);
  
  public abstract void setAskingAttribution(boolean paramBoolean);
  
  public abstract void setEnabled(boolean paramBoolean);
  
  public abstract void setOfflineMode(boolean paramBoolean);
  
  public abstract void trackEvent(AdjustEvent paramAdjustEvent);
  
  public abstract void trackSubsessionEnd();
  
  public abstract void trackSubsessionStart();
  
  public abstract boolean tryUpdateAttribution(AdjustAttribution paramAdjustAttribution);
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/adjust/sdk/IActivityHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */