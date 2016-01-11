package com.adjust.sdk;

import android.content.Context;
import org.json.JSONObject;

public abstract interface IPackageHandler
{
  public abstract void addPackage(ActivityPackage paramActivityPackage);
  
  public abstract void closeFirstPackage();
  
  public abstract void finishedTrackingActivity(JSONObject paramJSONObject);
  
  public abstract String getFailureMessage();
  
  public abstract void init(IActivityHandler paramIActivityHandler, Context paramContext, boolean paramBoolean);
  
  public abstract void pauseSending();
  
  public abstract void resumeSending();
  
  public abstract void sendClickPackage(ActivityPackage paramActivityPackage);
  
  public abstract void sendFirstPackage();
  
  public abstract void sendNextPackage();
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/adjust/sdk/IPackageHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */