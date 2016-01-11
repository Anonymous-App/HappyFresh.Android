package com.appsee;

import android.graphics.Rect;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class bl
{
  public bl(Rect paramRect, long paramLong)
  {
    this.k = paramLong;
    this.G = paramRect;
  }
  
  public long i()
  {
    return this.k;
  }
  
  public Rect i()
  {
    return this.G;
  }
  
  public JSONObject i()
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put(AppseeBackgroundUploader.i("B"), this.k);
    JSONArray localJSONArray = new JSONArray();
    localJSONArray.put(this.G.left);
    localJSONArray.put(this.G.top);
    localJSONArray.put(this.G.width());
    localJSONArray.put(this.G.height());
    localJSONObject.put(AppseeBackgroundUploader.i("P"), localJSONArray);
    return localJSONObject;
  }
  
  public void i(long paramLong)
  {
    this.k = paramLong;
  }
  
  public void i(Rect paramRect)
  {
    this.G = paramRect;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/bl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */