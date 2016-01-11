package com.appsee;

import android.util.Log;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class to
{
  public to(int paramInt)
  {
    i(paramInt, null, null, false);
  }
  
  public to(Throwable paramThrowable)
  {
    i(paramThrowable.getClass().getName(), paramThrowable.getMessage(), paramThrowable, true);
  }
  
  public String i()
  {
    return this.b;
  }
  
  public Throwable i()
  {
    return this.i;
  }
  
  public JSONObject i()
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put(AppseeBackgroundUploader.i("\037W P"), this.b);
    localJSONObject.put(AppseeBackgroundUploader.i("rt\0078O7P"), this.k);
    Object localObject2 = AppseeBackgroundUploader.i("|(V7Ge\0351U\033X.I)m?L9Mx\004?G?[");
    if (this.i == null) {}
    for (Object localObject1 = null;; localObject1 = Log.getStackTraceString(this.i))
    {
      localJSONObject.put((String)localObject2, localObject1);
      localObject1 = new JSONArray();
      localObject2 = this.G.iterator();
      while (((Iterator)localObject2).hasNext()) {
        ((JSONArray)localObject1).put(((uf)((Iterator)localObject2).next()).i());
      }
    }
    localJSONObject.put(AppseeBackgroundUploader.i("ky\006.O4F"), localObject1);
    return localJSONObject;
  }
  
  public void i(String paramString)
  {
    this.k = paramString;
  }
  
  public void i(Throwable paramThrowable)
  {
    this.i = paramThrowable;
  }
  
  public String l()
  {
    return this.k;
  }
  
  public void l(String paramString)
  {
    this.b = paramString;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/to.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */