package com.ad4screen.sdk.common.persistence.helpers;

import com.ad4screen.sdk.common.persistence.e;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

public final class a
{
  public static boolean a(JSONObject paramJSONObject, String paramString)
    throws JSONException
  {
    Object localObject = paramJSONObject.getString(paramString);
    localObject = (HashMap)new e().a((String)localObject, new HashMap());
    paramJSONObject.remove(paramString);
    ConcurrentHashMap localConcurrentHashMap = new ConcurrentHashMap();
    localConcurrentHashMap.putAll((Map)localObject);
    paramJSONObject.put(paramString, new e().a(localConcurrentHashMap));
    return true;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/persistence/helpers/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */