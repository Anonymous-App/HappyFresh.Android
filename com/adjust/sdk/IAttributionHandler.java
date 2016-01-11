package com.adjust.sdk;

import org.json.JSONObject;

public abstract interface IAttributionHandler
{
  public abstract void checkAttribution(JSONObject paramJSONObject);
  
  public abstract void getAttribution();
  
  public abstract void init(IActivityHandler paramIActivityHandler, ActivityPackage paramActivityPackage, boolean paramBoolean);
  
  public abstract void pauseSending();
  
  public abstract void resumeSending();
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/adjust/sdk/IAttributionHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */