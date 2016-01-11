package com.appsee;

import org.json.JSONException;
import org.json.JSONObject;

class uf
{
  public uf(to paramto, long paramLong, String paramString1, boolean paramBoolean, String paramString2)
  {
    this.i = paramLong;
    this.m = paramString1;
    this.k = paramBoolean;
    this.b = paramString2;
  }
  
  public long i()
  {
    return this.i;
  }
  
  public String i()
  {
    return this.b;
  }
  
  public JSONObject i()
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put(AppseeBackgroundUploader.i("U"), i());
    localJSONObject.put(AppseeBackgroundUploader.i("R"), l());
    localJSONObject.put(AppseeBackgroundUploader.i("_"), i());
    localJSONObject.put(AppseeBackgroundUploader.i("-X"), i());
    return localJSONObject;
  }
  
  public boolean i()
  {
    return this.k;
  }
  
  public String l()
  {
    return this.m;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/uf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */