package com.appsee;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

class tn
{
  public tn(String paramString, long paramLong, Map<String, Object> paramMap)
  {
    this.i = paramString;
    this.k = paramLong;
    this.G = paramMap;
  }
  
  public long i()
  {
    return this.k;
  }
  
  public String i()
  {
    return this.i;
  }
  
  public Map<String, Object> i()
  {
    return this.G;
  }
  
  public JSONObject i()
    throws JSONException
  {
    JSONObject localJSONObject1 = new JSONObject();
    localJSONObject1.put(AppseeBackgroundUploader.i("Z"), i());
    localJSONObject1.put(AppseeBackgroundUploader.i("@"), i());
    if ((this.G != null) && (!this.G.isEmpty()))
    {
      JSONObject localJSONObject2 = new JSONObject();
      Iterator localIterator = this.G.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        localJSONObject2.put((String)localEntry.getKey(), localEntry.getValue());
      }
      localJSONObject1.put(AppseeBackgroundUploader.i("D"), localJSONObject2);
    }
    return localJSONObject1;
  }
  
  public void i(long paramLong)
  {
    this.k = paramLong;
  }
  
  public void i(String paramString)
  {
    this.i = paramString;
  }
  
  public void i(Map<String, Object> paramMap)
  {
    this.G = paramMap;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/tn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */