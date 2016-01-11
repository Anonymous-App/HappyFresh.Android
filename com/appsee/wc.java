package com.appsee;

import org.json.JSONException;
import org.json.JSONObject;

class wc
{
  public wc(String paramString, long paramLong, boolean paramBoolean, vc paramvc)
  {
    this.k = paramString;
    this.b = paramLong;
    this.i = paramBoolean;
    this.G = paramvc;
  }
  
  public long i()
  {
    return this.b;
  }
  
  public vc i()
  {
    return this.G;
  }
  
  public String i()
  {
    return this.k;
  }
  
  public JSONObject i()
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put(AppseeBackgroundUploader.i("W"), this.k);
    localJSONObject.put(AppseeBackgroundUploader.i("X"), this.i);
    localJSONObject.put(AppseeBackgroundUploader.i("I"), this.G.ordinal());
    localJSONObject.put(AppseeBackgroundUploader.i("J"), this.b);
    return localJSONObject;
  }
  
  public void i(long paramLong)
  {
    this.b = paramLong;
  }
  
  public void i(vc paramvc)
  {
    this.G = paramvc;
  }
  
  public void i(String paramString)
  {
    this.k = paramString;
  }
  
  public void i(boolean paramBoolean)
  {
    this.i = paramBoolean;
  }
  
  public boolean i()
  {
    return this.i;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/wc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */