package com.appsee;

import org.json.JSONException;
import org.json.JSONObject;

class db
{
  public db(short paramShort1, short paramShort2, short paramShort3, long paramLong, bb parambb, boolean paramBoolean)
  {
    this.i = paramShort1;
    this.K = paramShort2;
    this.k = paramShort3;
    this.G = paramLong;
    this.m = parambb;
    this.b = paramBoolean;
  }
  
  public short a()
  {
    return this.i;
  }
  
  public void a(short paramShort)
  {
    this.k = paramShort;
  }
  
  public long i()
  {
    return this.G;
  }
  
  public bb i()
  {
    return this.m;
  }
  
  public JSONObject i()
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put(AppseeBackgroundUploader.i("_"), this.i);
    localJSONObject.put(AppseeBackgroundUploader.i("]"), this.m.ordinal());
    localJSONObject.put(AppseeBackgroundUploader.i("@"), this.K);
    localJSONObject.put(AppseeBackgroundUploader.i("A"), this.k);
    localJSONObject.put(AppseeBackgroundUploader.i("L"), this.G);
    if (this.b) {
      localJSONObject.put(AppseeBackgroundUploader.i("S"), 1);
    }
    return localJSONObject;
  }
  
  public short i()
  {
    return this.k;
  }
  
  public void i(long paramLong)
  {
    this.G = paramLong;
  }
  
  public void i(bb parambb)
  {
    this.m = parambb;
  }
  
  public void i(short paramShort)
  {
    this.i = paramShort;
  }
  
  public void i(boolean paramBoolean)
  {
    this.b = paramBoolean;
  }
  
  public boolean i()
  {
    return this.b;
  }
  
  public short l()
  {
    return this.K;
  }
  
  public void l(short paramShort)
  {
    this.K = paramShort;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/db.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */