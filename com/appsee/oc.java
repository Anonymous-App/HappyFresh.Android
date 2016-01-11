package com.appsee;

import org.json.JSONException;
import org.json.JSONObject;

class oc
{
  ki k;
  
  public oc(long paramLong, ki paramki)
  {
    this.G = paramLong;
    this.k = paramki;
  }
  
  public long i()
  {
    return this.G;
  }
  
  public ki i()
  {
    return this.k;
  }
  
  public JSONObject i()
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put(AppseeBackgroundUploader.i("M"), i());
    localJSONObject.put(AppseeBackgroundUploader.i("V"), this.k.ordinal());
    return localJSONObject;
  }
  
  public void i(long paramLong)
  {
    this.G = paramLong;
  }
  
  public void i(ki paramki)
  {
    this.k = paramki;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/oc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */