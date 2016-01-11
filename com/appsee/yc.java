package com.appsee;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

class yc
{
  public static yc i()
  {
    try
    {
      if (k == null) {
        k = new yc();
      }
      yc localyc = k;
      return localyc;
    }
    finally {}
  }
  
  public mo i(String paramString)
  {
    try
    {
      mo localmo2 = (mo)this.G.get(paramString);
      mo localmo1 = localmo2;
      if (localmo2 == null)
      {
        localmo1 = new mo(paramString);
        this.G.put(paramString, localmo1);
      }
      return localmo1;
    }
    finally {}
  }
  
  public JSONObject i()
    throws JSONException
  {
    try
    {
      JSONObject localJSONObject1 = new JSONObject();
      Iterator localIterator = this.G.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        int i = ((mo)localEntry.getValue()).i();
        if (i >= 0) {
          localJSONObject1.put((String)localEntry.getKey(), i);
        }
      }
    }
    finally {}
    return localJSONObject2;
  }
  
  public void i()
  {
    try
    {
      this.G.clear();
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/appsee/yc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */