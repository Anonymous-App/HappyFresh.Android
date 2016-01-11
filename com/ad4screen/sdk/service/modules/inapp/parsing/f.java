package com.ad4screen.sdk.service.modules.inapp.parsing;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.service.modules.inapp.model.h;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class f
{
  private com.ad4screen.sdk.service.modules.inapp.model.states.c a(JSONObject paramJSONObject)
  {
    return new com.ad4screen.sdk.service.modules.inapp.model.states.c(h.b("name", paramJSONObject), h.b("value", paramJSONObject));
  }
  
  private void a(com.ad4screen.sdk.service.modules.inapp.model.states.composites.c paramc, JSONArray paramJSONArray)
    throws JSONException
  {
    int i = 0;
    while (i < paramJSONArray.length())
    {
      com.ad4screen.sdk.service.modules.inapp.model.states.a locala = c(paramJSONArray.getJSONObject(i));
      paramc.a.add(locala);
      i += 1;
    }
  }
  
  private com.ad4screen.sdk.service.modules.inapp.model.states.b b(JSONObject paramJSONObject)
  {
    Object localObject = h.b("name", paramJSONObject);
    paramJSONObject = h.b("value", paramJSONObject);
    try
    {
      localObject = new com.ad4screen.sdk.service.modules.inapp.model.states.b((String)localObject, paramJSONObject);
      return (com.ad4screen.sdk.service.modules.inapp.model.states.b)localObject;
    }
    catch (Exception localException)
    {
      Log.error("InApp|Failed to create Pattern from regex while parsing configuration : " + paramJSONObject);
    }
    return null;
  }
  
  private com.ad4screen.sdk.service.modules.inapp.model.states.composites.b b(JSONArray paramJSONArray)
    throws JSONException
  {
    com.ad4screen.sdk.service.modules.inapp.model.states.composites.b localb = new com.ad4screen.sdk.service.modules.inapp.model.states.composites.b();
    a(localb, paramJSONArray);
    return localb;
  }
  
  private com.ad4screen.sdk.service.modules.inapp.model.states.a c(JSONObject paramJSONObject)
    throws JSONException
  {
    String str = h.b("type", paramJSONObject);
    if ("any".equals(str)) {
      return b(paramJSONObject.getJSONArray("value"));
    }
    if ("all".equals(str)) {
      return c(paramJSONObject.getJSONArray("value"));
    }
    if ("concrete".equals(str)) {
      return a(paramJSONObject);
    }
    if ("regex".equals(str)) {
      return b(paramJSONObject);
    }
    return null;
  }
  
  private com.ad4screen.sdk.service.modules.inapp.model.states.composites.a c(JSONArray paramJSONArray)
    throws JSONException
  {
    com.ad4screen.sdk.service.modules.inapp.model.states.composites.a locala = new com.ad4screen.sdk.service.modules.inapp.model.states.composites.a();
    a(locala, paramJSONArray);
    return locala;
  }
  
  public List<com.ad4screen.sdk.service.modules.inapp.model.states.a> a(JSONArray paramJSONArray)
    throws JSONException
  {
    ArrayList localArrayList = new ArrayList();
    if (paramJSONArray != null)
    {
      int i = 0;
      while (i < paramJSONArray.length())
      {
        localArrayList.add(c(paramJSONArray.getJSONObject(i)));
        i += 1;
      }
    }
    return localArrayList;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/parsing/f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */