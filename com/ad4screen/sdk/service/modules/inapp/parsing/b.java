package com.ad4screen.sdk.service.modules.inapp.parsing;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.h.a;
import com.ad4screen.sdk.service.modules.inapp.model.daterange.d;
import com.ad4screen.sdk.service.modules.inapp.model.daterange.e;
import com.ad4screen.sdk.service.modules.inapp.model.daterange.f;
import com.ad4screen.sdk.service.modules.inapp.model.daterange.g;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class b
{
  private d a(JSONObject paramJSONObject)
    throws JSONException
  {
    d locald = new d();
    Object localObject = paramJSONObject.getJSONObject("recurrence");
    paramJSONObject = ((JSONObject)localObject).getString("frequency");
    if (paramJSONObject != null) {}
    HashMap localHashMap;
    for (;;)
    {
      try
      {
        locald.a(g.valueOf(paramJSONObject.trim().toUpperCase(Locale.US)));
        if (((JSONObject)localObject).has("interval"))
        {
          i = ((JSONObject)localObject).optInt("interval", -1);
          if (i > 0) {
            locald.a(i);
          }
        }
        else
        {
          if (((JSONObject)localObject).has("byMonthDay")) {
            locald.a(a(((JSONObject)localObject).getString("byMonthDay"), ","));
          }
          if (!((JSONObject)localObject).has("byDay")) {
            break label298;
          }
          paramJSONObject = ((JSONObject)localObject).getString("byDay");
          if (paramJSONObject == null) {
            break label298;
          }
          Pattern localPattern = Pattern.compile("^([-+]?[0-9]+)?([A-Z]{2})$");
          String[] arrayOfString = paramJSONObject.split(",");
          localHashMap = new HashMap();
          i = 0;
          if (i >= arrayOfString.length) {
            break label292;
          }
          paramJSONObject = localPattern.matcher(arrayOfString[i].trim());
          if ((paramJSONObject.find()) && (paramJSONObject.groupCount() == 2))
          {
            str1 = paramJSONObject.group(1);
            str2 = paramJSONObject.group(2);
            paramJSONObject = null;
            if (str1 == null) {}
          }
        }
      }
      catch (IllegalArgumentException paramJSONObject)
      {
        int i;
        String str1;
        String str2;
        throw new JSONException("Wrong enum from server : " + paramJSONObject.getMessage());
      }
      try
      {
        paramJSONObject = Integer.valueOf(Integer.parseInt(str1));
        localHashMap.put(e.valueOf(str2.trim().toUpperCase(Locale.US)), paramJSONObject);
      }
      catch (IllegalArgumentException paramJSONObject)
      {
        Log.internal("Impossible to parse day", paramJSONObject);
        continue;
      }
      i += 1;
    }
    throw new JSONException("Wrong interval, interval must be greater than 0");
    label292:
    locald.a(localHashMap);
    label298:
    if (((JSONObject)localObject).has("byHour")) {
      locald.b(a(((JSONObject)localObject).getString("byHour"), ","));
    }
    if (((JSONObject)localObject).has("byMinute")) {
      locald.c(a(((JSONObject)localObject).getString("byMinute"), ","));
    }
    paramJSONObject = ((JSONObject)localObject).getJSONObject("duration");
    localObject = paramJSONObject.getString("type");
    try
    {
      locald.a(f.valueOf(((String)localObject).trim().toUpperCase(Locale.US)));
      locald.a(paramJSONObject.getLong("duration"));
      return locald;
    }
    catch (IllegalArgumentException paramJSONObject)
    {
      throw new JSONException(paramJSONObject.getMessage());
    }
  }
  
  private List<Integer> a(String paramString1, String paramString2)
  {
    ArrayList localArrayList = null;
    if (paramString1 != null)
    {
      paramString1 = paramString1.split(paramString2);
      localArrayList = new ArrayList();
      int i = 0;
      for (;;)
      {
        if (i < paramString1.length) {
          try
          {
            localArrayList.add(Integer.valueOf(Integer.parseInt(paramString1[i].trim())));
            i += 1;
          }
          catch (NumberFormatException paramString2)
          {
            for (;;)
            {
              Log.internal("Impossible to parse number", paramString2);
            }
          }
        }
      }
    }
    return localArrayList;
  }
  
  public List<com.ad4screen.sdk.service.modules.inapp.model.daterange.b> a(JSONArray paramJSONArray)
  {
    int i = 0;
    ArrayList localArrayList = new ArrayList();
    if (paramJSONArray != null) {
      while (i < paramJSONArray.length())
      {
        try
        {
          JSONObject localJSONObject = paramJSONArray.getJSONObject(i);
          com.ad4screen.sdk.service.modules.inapp.model.daterange.b localb = new com.ad4screen.sdk.service.modules.inapp.model.daterange.b();
          String str = localJSONObject.optString("dateBeginning", null);
          if (str != null) {
            localb.a(h.a(str, h.a.b));
          }
          str = localJSONObject.optString("dateEnding", null);
          if (str != null) {
            localb.b(h.a(str, h.a.b));
          }
          localb.a(localJSONObject.optBoolean("isLocal", false));
          if (localJSONObject.has("recurrence")) {
            localb.a(a(localJSONObject));
          }
          localArrayList.add(localb);
        }
        catch (JSONException localJSONException)
        {
          for (;;)
          {
            Log.internal("Error during parsing of rule " + i + " : " + localJSONException.getMessage());
          }
        }
        i += 1;
      }
    }
    return localArrayList;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/parsing/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */