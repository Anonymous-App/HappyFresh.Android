package com.ad4screen.sdk.service.modules.inapp.parsing;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.service.modules.inapp.model.events.a;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class c
{
  private com.ad4screen.sdk.service.modules.inapp.model.events.regular.c a(JSONObject paramJSONObject)
    throws JSONException
  {
    return new com.ad4screen.sdk.service.modules.inapp.model.events.regular.c(Long.valueOf(paramJSONObject.getLong("code")), paramJSONObject.getString("value"));
  }
  
  private com.ad4screen.sdk.service.modules.inapp.model.events.composite.c b(JSONArray paramJSONArray)
    throws JSONException
  {
    if ((paramJSONArray == null) || (paramJSONArray.length() == 0)) {
      return null;
    }
    return new com.ad4screen.sdk.service.modules.inapp.model.events.composite.c(a(paramJSONArray));
  }
  
  private com.ad4screen.sdk.service.modules.inapp.model.events.regular.b b(JSONObject paramJSONObject)
    throws JSONException
  {
    long l = paramJSONObject.getLong("code");
    String str = null;
    if (!paramJSONObject.isNull("value")) {
      str = paramJSONObject.getString("value");
    }
    return new com.ad4screen.sdk.service.modules.inapp.model.events.regular.b(Long.valueOf(l), str);
  }
  
  private a c(JSONObject paramJSONObject)
    throws JSONException
  {
    return c(paramJSONObject.getJSONArray("events"));
  }
  
  private com.ad4screen.sdk.service.modules.inapp.model.events.composite.b c(JSONArray paramJSONArray)
    throws JSONException
  {
    if ((paramJSONArray == null) || (paramJSONArray.length() == 0)) {
      return null;
    }
    return new com.ad4screen.sdk.service.modules.inapp.model.events.composite.b(a(paramJSONArray));
  }
  
  private a d(JSONObject paramJSONObject)
    throws JSONException
  {
    return b(paramJSONObject.getJSONArray("events"));
  }
  
  public List<a> a(JSONArray paramJSONArray)
    throws JSONException
  {
    if ((paramJSONArray == null) || (paramJSONArray.length() == 0)) {
      return null;
    }
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    for (;;)
    {
      if (i < paramJSONArray.length())
      {
        Object localObject = paramJSONArray.getJSONObject(i);
        String str = ((JSONObject)localObject).getString("type");
        try
        {
          if ("regex".equalsIgnoreCase(str)) {
            localObject = a((JSONObject)localObject);
          }
          while (localObject != null)
          {
            localArrayList.add(localObject);
            break;
            if ("concrete".equalsIgnoreCase(str))
            {
              localObject = b((JSONObject)localObject);
            }
            else if ("all".equalsIgnoreCase(str))
            {
              localObject = c((JSONObject)localObject);
            }
            else if ("any".equalsIgnoreCase(str))
            {
              localObject = d((JSONObject)localObject);
              continue;
              return localArrayList;
            }
          }
        }
        catch (JSONException localJSONException)
        {
          Log.internal("InAppEventParser|Impossible to parse Event", localJSONException);
        }
      }
      i += 1;
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/parsing/c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */