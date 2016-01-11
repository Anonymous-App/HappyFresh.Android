package com.appsee;

import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class zc
{
  public zc(String paramString1, String paramString2, List<String> paramList)
    throws Exception
  {
    this.k = paramString1;
    this.b = paramString2;
    this.G = paramList;
    if (this.k == null) {
      this.k = "";
    }
    if (this.b == null) {
      this.b = "";
    }
    i();
  }
  
  public String a()
  {
    return this.i;
  }
  
  public void a(String paramString)
  {
    this.i = paramString;
  }
  
  public String i()
  {
    return this.k;
  }
  
  public List<String> i()
  {
    return this.G;
  }
  
  public JSONObject i()
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    JSONArray localJSONArray = new JSONArray();
    if (this.G != null)
    {
      Iterator localIterator = this.G.iterator();
      while (localIterator.hasNext()) {
        localJSONArray.put((String)localIterator.next());
      }
    }
    localJSONObject.put(AppseeBackgroundUploader.i("J"), this.i);
    localJSONObject.put(AppseeBackgroundUploader.i("M"), this.k);
    localJSONObject.put(AppseeBackgroundUploader.i("A"), this.b);
    localJSONObject.put(AppseeBackgroundUploader.i("V"), localJSONArray);
    return localJSONObject;
  }
  
  public void i(String paramString)
  {
    this.k = paramString;
  }
  
  public void i(List<String> paramList)
  {
    this.G = paramList;
  }
  
  public String l()
  {
    return this.b;
  }
  
  public void l(String paramString)
  {
    this.b = paramString;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/zc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */