package com.appsee;

import android.graphics.Rect;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class td
{
  public td(ed paramed, boolean paramBoolean, short[] paramArrayOfShort, long paramLong1, long paramLong2, Rect paramRect, int paramInt)
  {
    this.G = paramed;
    this.b = paramBoolean;
    this.K = paramLong1;
    this.m = paramLong2;
    this.k = paramRect;
    this.d = paramInt;
    if ((paramArrayOfShort != null) && (paramArrayOfShort.length > 0))
    {
      this.i = new ArrayList(paramArrayOfShort.length);
      int n = paramArrayOfShort.length;
      paramInt = 0;
      for (int j = 0; paramInt < n; j = paramInt)
      {
        short s = paramArrayOfShort[j];
        paramed = this.i;
        paramInt = j + 1;
        paramed.add(Short.valueOf(s));
      }
    }
  }
  
  public boolean a()
  {
    return this.b;
  }
  
  public int i()
  {
    return this.d;
  }
  
  public long i()
  {
    return this.m;
  }
  
  public Rect i()
  {
    return this.k;
  }
  
  public ed i()
  {
    return this.G;
  }
  
  public List<Short> i()
  {
    return this.i;
  }
  
  public void i(int paramInt)
  {
    this.d = paramInt;
  }
  
  public void i(long paramLong)
  {
    this.m = paramLong;
  }
  
  public void i(Rect paramRect)
  {
    this.k = paramRect;
  }
  
  public void i(ed paramed)
  {
    this.G = paramed;
  }
  
  public void i(List<Short> paramList)
  {
    this.i = paramList;
  }
  
  public void i(boolean paramBoolean)
  {
    this.b = paramBoolean;
  }
  
  public boolean i()
  {
    return (this.G == ed.i) || (this.G == ed.C);
  }
  
  public long l()
  {
    return this.K;
  }
  
  public JSONObject l()
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put(AppseeBackgroundUploader.i("J"), this.G.ordinal());
    String str = AppseeBackgroundUploader.i("L");
    if (this.b) {}
    for (int j = 1;; j = 0)
    {
      localJSONObject.put(str, j);
      localJSONObject.put(AppseeBackgroundUploader.i("M"), this.K);
      localJSONObject.put(AppseeBackgroundUploader.i("["), this.m);
      if ((this.i != null) && (!this.i.isEmpty())) {
        localJSONObject.put(AppseeBackgroundUploader.i("Y"), new JSONArray(this.i));
      }
      localJSONObject.put(AppseeBackgroundUploader.i("\\"), i());
      return localJSONObject;
    }
  }
  
  public void l(long paramLong)
  {
    this.K = paramLong;
  }
  
  public boolean l()
  {
    return (this.G == ed.G) || (this.G == ed.m) || (this.G == ed.k) || (this.G == ed.b);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/td.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */