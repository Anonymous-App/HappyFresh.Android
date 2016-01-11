package com.appsee;

import android.graphics.Rect;
import org.json.JSONException;
import org.json.JSONObject;

class ie
{
  public ie(xm paramxm, String paramString1, String paramString2, long paramLong, Rect paramRect)
  {
    this.K = paramxm;
    this.d = paramString1;
    this.m = paramString2;
    this.b = paramLong;
    this.i = paramRect;
  }
  
  public void a(String paramString)
  {
    this.m = paramString;
  }
  
  public long i()
  {
    return this.b;
  }
  
  public xm i()
  {
    return this.K;
  }
  
  public String i()
  {
    return this.m;
  }
  
  public void i(long paramLong)
  {
    this.b = paramLong;
  }
  
  public void i(xm paramxm)
  {
    this.K = paramxm;
  }
  
  public void i(Boolean paramBoolean)
  {
    this.G = paramBoolean.booleanValue();
  }
  
  public void i(String paramString)
  {
    this.d = paramString;
  }
  
  public String l()
  {
    return this.d;
  }
  
  public JSONObject l()
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put(AppseeBackgroundUploader.i("K"), this.K.ordinal());
    localJSONObject.put(AppseeBackgroundUploader.i("L"), this.b);
    localJSONObject.put(AppseeBackgroundUploader.i("V"), this.m);
    localJSONObject.put(AppseeBackgroundUploader.i("O"), this.d);
    String str;
    if (this.K == xm.C) {
      if (!lb.i(this.k))
      {
        localJSONObject.put(AppseeBackgroundUploader.i("3["), this.k);
        str = AppseeBackgroundUploader.i("3J");
        if (!this.G) {
          break label139;
        }
      }
    }
    label139:
    for (int j = 1;; j = 0)
    {
      localJSONObject.put(str, j);
      localJSONObject.put(AppseeBackgroundUploader.i("]"), i());
      return localJSONObject;
    }
  }
  
  public void l(String paramString)
  {
    this.k = paramString;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/ie.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */