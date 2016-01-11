package com.parse;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

class ParseJSONUtils
{
  public static JSONObject create(JSONObject paramJSONObject, Collection<String> paramCollection)
  {
    JSONObject localJSONObject = new JSONObject();
    Iterator localIterator = paramJSONObject.keys();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (!paramCollection.contains(str)) {
        try
        {
          localJSONObject.put(str, paramJSONObject.opt(str));
        }
        catch (JSONException paramJSONObject)
        {
          throw new RuntimeException(paramJSONObject);
        }
      }
    }
    return localJSONObject;
  }
  
  public static int getInt(JSONObject paramJSONObject, List<String> paramList)
    throws JSONException
  {
    Iterator localIterator = paramList.iterator();
    for (;;)
    {
      String str;
      if (localIterator.hasNext()) {
        str = (String)localIterator.next();
      }
      try
      {
        int i = paramJSONObject.getInt(str);
        return i;
      }
      catch (JSONException localJSONException) {}
      throw new JSONException("No value for " + paramList);
    }
  }
  
  public static Iterable<String> keys(JSONObject paramJSONObject)
  {
    new Iterable()
    {
      public Iterator<String> iterator()
      {
        return this.val$finalObject.keys();
      }
    };
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/parse/ParseJSONUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */