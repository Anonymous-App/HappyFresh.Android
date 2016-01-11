package com.ad4screen.sdk.service.modules.inapp;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.h.a;
import com.ad4screen.sdk.model.displayformats.e.a;
import com.ad4screen.sdk.model.displayformats.g.a;
import com.ad4screen.sdk.service.modules.inapp.model.i;
import com.ad4screen.sdk.service.modules.inapp.model.i.a;
import com.ad4screen.sdk.service.modules.inapp.model.j;
import com.ad4screen.sdk.service.modules.inapp.parsing.f;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class d
{
  public com.ad4screen.sdk.service.modules.inapp.model.e a;
  private Context b;
  
  public d(Context paramContext)
  {
    this.b = paramContext;
  }
  
  private void a(HashMap<String, String> paramHashMap, JSONArray paramJSONArray)
  {
    if (paramHashMap == null) {}
    for (;;)
    {
      return;
      int i = 0;
      while (i < paramJSONArray.length())
      {
        try
        {
          Object localObject = paramJSONArray.getJSONObject(i);
          String str = com.ad4screen.sdk.service.modules.inapp.model.h.b("value", (JSONObject)localObject);
          localObject = com.ad4screen.sdk.service.modules.inapp.model.h.b("key", (JSONObject)localObject);
          if ((str != null) && (localObject != null)) {
            paramHashMap.put(localObject, str);
          }
        }
        catch (JSONException localJSONException)
        {
          for (;;)
          {
            Log.error("InApp|Error parsing customs params", localJSONException);
          }
        }
        i += 1;
      }
    }
  }
  
  private j b(JSONObject paramJSONObject)
    throws JSONException
  {
    j localj = new j();
    if (!paramJSONObject.isNull("views")) {
      localj.c(new com.ad4screen.sdk.service.modules.inapp.parsing.g().a(paramJSONObject.getJSONArray("views")));
    }
    if (!paramJSONObject.isNull("events")) {
      localj.e(new com.ad4screen.sdk.service.modules.inapp.parsing.c().a(paramJSONObject.getJSONArray("events")));
    }
    if (!paramJSONObject.isNull("states")) {
      localj.d(new f().a(paramJSONObject.getJSONArray("states")));
    }
    if (!paramJSONObject.isNull("locations")) {
      localj.a(new com.ad4screen.sdk.service.modules.inapp.parsing.e().a(paramJSONObject.getJSONArray("locations")));
    }
    if (!paramJSONObject.isNull("dateRanges")) {
      localj.b(new com.ad4screen.sdk.service.modules.inapp.parsing.b().a(paramJSONObject.getJSONArray("dateRanges")));
    }
    if (!paramJSONObject.isNull("geofences")) {
      localj.f(new com.ad4screen.sdk.service.modules.inapp.parsing.d().a(paramJSONObject.getJSONArray("geofences")));
    }
    if (!paramJSONObject.isNull("beacons")) {
      localj.g(new com.ad4screen.sdk.service.modules.inapp.parsing.a().a(paramJSONObject.getJSONArray("beacons")));
    }
    return localj;
  }
  
  private Long c(JSONObject paramJSONObject)
  {
    paramJSONObject = com.ad4screen.sdk.service.modules.inapp.model.h.d("timer", paramJSONObject);
    if (paramJSONObject != null) {
      return Long.valueOf(paramJSONObject.intValue() * 1000);
    }
    return null;
  }
  
  private Long d(JSONObject paramJSONObject)
  {
    paramJSONObject = com.ad4screen.sdk.service.modules.inapp.model.h.d("sessionTimer", paramJSONObject);
    if (paramJSONObject != null) {
      return Long.valueOf(paramJSONObject.intValue() * 1000);
    }
    return null;
  }
  
  private com.ad4screen.sdk.model.displayformats.d e(JSONObject paramJSONObject)
  {
    if (paramJSONObject.isNull("id")) {
      return null;
    }
    try
    {
      com.ad4screen.sdk.service.modules.alarm.model.b localb = new com.ad4screen.sdk.service.modules.alarm.model.b();
      localb.i = com.ad4screen.sdk.service.modules.inapp.model.h.b("id", paramJSONObject);
      paramJSONObject = paramJSONObject.getJSONObject("data");
      if (!paramJSONObject.isNull("alarms"))
      {
        paramJSONObject = paramJSONObject.getJSONArray("alarms");
        localb.a = new String[paramJSONObject.length()];
        int i = 0;
        while (i < paramJSONObject.length())
        {
          localb.a[i] = paramJSONObject.getJSONObject(i).getString("id");
          i += 1;
        }
      }
      return localb;
    }
    catch (JSONException paramJSONObject)
    {
      Log.error("InApp|Error parsing cancelAlarm format", paramJSONObject);
    }
    return null;
  }
  
  private com.ad4screen.sdk.service.modules.alarm.model.c f(JSONObject paramJSONObject)
  {
    long l1 = 1000L;
    if (paramJSONObject.isNull("id")) {
      return null;
    }
    for (;;)
    {
      com.ad4screen.sdk.service.modules.alarm.model.c localc;
      Bundle localBundle;
      Object localObject2;
      long l2;
      try
      {
        localc = new com.ad4screen.sdk.service.modules.alarm.model.c();
        localc.i = com.ad4screen.sdk.service.modules.inapp.model.h.b("id", paramJSONObject);
        localObject1 = paramJSONObject.getJSONObject("data");
        localc.k = com.ad4screen.sdk.service.modules.inapp.model.h.b("clickTrackingUrl", (JSONObject)localObject1);
        localc.j = com.ad4screen.sdk.service.modules.inapp.model.h.b("displayTrackingUrl", (JSONObject)localObject1);
        localc.c(com.ad4screen.sdk.service.modules.inapp.model.h.b("cancelTrackingUrl", (JSONObject)localObject1));
        localc.a(com.ad4screen.sdk.service.modules.inapp.model.h.c("allowUpdate", (JSONObject)localObject1).booleanValue());
        localBundle = new Bundle();
        localBundle.putString("isAlarm", "true");
        Object localObject3;
        if (!((JSONObject)localObject1).isNull("pushPayload"))
        {
          localObject2 = ((JSONObject)localObject1).getJSONArray("pushPayload");
          int i = 0;
          if (i < ((JSONArray)localObject2).length())
          {
            localObject3 = ((JSONArray)localObject2).getJSONObject(i);
            localBundle.putString(((JSONObject)localObject3).getString("name"), ((JSONObject)localObject3).getString("value"));
            i += 1;
            continue;
          }
        }
        localBundle.putString("a4sid", com.ad4screen.sdk.service.modules.inapp.model.h.b("id", paramJSONObject));
        localBundle.putString("a4sforeground", com.ad4screen.sdk.service.modules.inapp.model.h.b("allowForegroundDisplay", (JSONObject)localObject1));
        if (!((JSONObject)localObject1).isNull("at"))
        {
          localc.b(com.ad4screen.sdk.common.h.a(((JSONObject)localObject1).getString("at"), h.a.b));
          localc.a(10000L);
        }
        if (!((JSONObject)localObject1).isNull("in"))
        {
          l2 = ((JSONObject)localObject1).getInt("in") * 1000;
          if (l2 >= 1000L) {
            break label468;
          }
          localc.a(l1);
        }
        if (!((JSONObject)localObject1).isNull("displayCustomParams"))
        {
          paramJSONObject = new HashMap();
          a(paramJSONObject, ((JSONObject)localObject1).getJSONArray("displayCustomParams"));
          localObject2 = paramJSONObject.keySet().iterator();
          if (((Iterator)localObject2).hasNext())
          {
            localObject3 = (String)((Iterator)localObject2).next();
            localBundle.putString((String)localObject3, (String)paramJSONObject.get(localObject3));
            continue;
          }
        }
        if (((JSONObject)localObject1).isNull("clickCustomParams")) {
          break label458;
        }
      }
      catch (JSONException paramJSONObject)
      {
        Log.error("InApp|Error parsing setAlarm format", paramJSONObject);
        return null;
      }
      paramJSONObject = new HashMap();
      a(paramJSONObject, ((JSONObject)localObject1).getJSONArray("clickCustomParams"));
      Object localObject1 = paramJSONObject.keySet().iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (String)((Iterator)localObject1).next();
        localBundle.putString((String)localObject2, (String)paramJSONObject.get(localObject2));
      }
      label458:
      localc.a(localBundle);
      return localc;
      label468:
      l1 = l2;
    }
  }
  
  private com.ad4screen.sdk.model.displayformats.a g(JSONObject paramJSONObject)
  {
    if (paramJSONObject.isNull("id")) {
      paramJSONObject = null;
    }
    com.ad4screen.sdk.model.displayformats.a locala;
    for (;;)
    {
      return paramJSONObject;
      locala = new com.ad4screen.sdk.model.displayformats.a();
      try
      {
        locala.i = com.ad4screen.sdk.service.modules.inapp.model.h.b("id", paramJSONObject);
        Object localObject = paramJSONObject.getJSONObject("data");
        locala.a = com.ad4screen.sdk.service.modules.inapp.model.h.d("autoclose", (JSONObject)localObject);
        locala.c.c = com.ad4screen.sdk.service.modules.inapp.model.h.b("url", (JSONObject)localObject);
        locala.c.a = com.ad4screen.sdk.service.modules.inapp.model.h.b("title", (JSONObject)localObject);
        locala.c.b = com.ad4screen.sdk.service.modules.inapp.model.h.b("body", (JSONObject)localObject);
        locala.c.d = com.ad4screen.sdk.service.modules.inapp.model.h.b("template", (JSONObject)localObject);
        locala.c.e = com.ad4screen.sdk.service.modules.inapp.model.h.b("inAnimation", (JSONObject)localObject);
        locala.c.f = com.ad4screen.sdk.service.modules.inapp.model.h.b("outAnimation", (JSONObject)localObject);
        locala.k = com.ad4screen.sdk.service.modules.inapp.model.h.b("clickTrackingUrl", (JSONObject)localObject);
        locala.j = com.ad4screen.sdk.service.modules.inapp.model.h.b("displayTrackingUrl", (JSONObject)localObject);
        locala.l = com.ad4screen.sdk.service.modules.inapp.model.h.b("closeTrackingUrl", (JSONObject)localObject);
        locala.b = com.ad4screen.sdk.service.modules.inapp.model.h.c("interstitial", (JSONObject)localObject).booleanValue();
        if (!((JSONObject)localObject).isNull("canUseOverlay")) {
          locala.g = com.ad4screen.sdk.service.modules.inapp.model.h.c("canUseOverlay", (JSONObject)localObject).booleanValue();
        }
        if (!((JSONObject)localObject).isNull("displayCustomParams")) {
          a(locala.e, ((JSONObject)localObject).getJSONArray("displayCustomParams"));
        }
        if (!((JSONObject)localObject).isNull("clickCustomParams")) {
          a(locala.f, ((JSONObject)localObject).getJSONArray("clickCustomParams"));
        }
        paramJSONObject = locala;
        if (!((JSONObject)localObject).isNull("landingPage"))
        {
          paramJSONObject = ((JSONObject)localObject).getJSONObject("landingPage");
          localObject = new com.ad4screen.sdk.model.displayformats.e();
          ((com.ad4screen.sdk.model.displayformats.e)localObject).i = (locala.i + '#' + "target");
          ((com.ad4screen.sdk.model.displayformats.e)localObject).a.c = com.ad4screen.sdk.service.modules.inapp.model.h.b("url", paramJSONObject);
          ((com.ad4screen.sdk.model.displayformats.e)localObject).a.a = com.ad4screen.sdk.service.modules.inapp.model.h.b("title", paramJSONObject);
          ((com.ad4screen.sdk.model.displayformats.e)localObject).a.b = com.ad4screen.sdk.service.modules.inapp.model.h.b("body", paramJSONObject);
          ((com.ad4screen.sdk.model.displayformats.e)localObject).a.d = com.ad4screen.sdk.service.modules.inapp.model.h.b("template", paramJSONObject);
          if (com.ad4screen.sdk.service.modules.inapp.model.h.c("openWithSafari", paramJSONObject).booleanValue()) {
            ((com.ad4screen.sdk.model.displayformats.e)localObject).b = e.a.b;
          }
          locala.d = ((com.ad4screen.sdk.model.displayformats.d)localObject);
          return locala;
        }
      }
      catch (JSONException paramJSONObject)
      {
        Log.error("InApp|Error parsing banner format", paramJSONObject);
      }
    }
    return locala;
  }
  
  private com.ad4screen.sdk.model.displayformats.g h(JSONObject paramJSONObject)
  {
    if (paramJSONObject.isNull("id")) {
      paramJSONObject = null;
    }
    com.ad4screen.sdk.model.displayformats.g localg;
    for (;;)
    {
      return paramJSONObject;
      localg = new com.ad4screen.sdk.model.displayformats.g();
      try
      {
        localg.i = com.ad4screen.sdk.service.modules.inapp.model.h.b("id", paramJSONObject);
        Object localObject = paramJSONObject.getJSONObject("data");
        localg.a = com.ad4screen.sdk.service.modules.inapp.model.h.b("title", (JSONObject)localObject);
        localg.b = com.ad4screen.sdk.service.modules.inapp.model.h.b("body", (JSONObject)localObject);
        localg.j = com.ad4screen.sdk.service.modules.inapp.model.h.b("displayTrackingUrl", (JSONObject)localObject);
        localg.k = com.ad4screen.sdk.service.modules.inapp.model.h.b("clickTrackingUrl", (JSONObject)localObject);
        if (!((JSONObject)localObject).isNull("displayCustomParams")) {
          a(localg.d, ((JSONObject)localObject).getJSONArray("displayCustomParams"));
        }
        paramJSONObject = localg;
        if (!((JSONObject)localObject).isNull("buttons"))
        {
          paramJSONObject = new ArrayList();
          localObject = ((JSONObject)localObject).getJSONArray("buttons");
          int i = 0;
          while (i < ((JSONArray)localObject).length())
          {
            JSONObject localJSONObject = ((JSONArray)localObject).getJSONObject(i);
            paramJSONObject.add(a(localg.i, String.valueOf(i), localJSONObject));
            i += 1;
          }
          localg.c = new g.a[paramJSONObject.size()];
          paramJSONObject.toArray(localg.c);
          return localg;
        }
      }
      catch (JSONException paramJSONObject)
      {
        Log.error("InApp|Error parsing popup format", paramJSONObject);
      }
    }
    return localg;
  }
  
  private com.ad4screen.sdk.model.displayformats.e i(JSONObject paramJSONObject)
  {
    if (paramJSONObject.isNull("id")) {}
    for (;;)
    {
      return null;
      com.ad4screen.sdk.model.displayformats.e locale = new com.ad4screen.sdk.model.displayformats.e();
      try
      {
        locale.i = com.ad4screen.sdk.service.modules.inapp.model.h.b("id", paramJSONObject);
        locale.b = e.a.b;
        paramJSONObject = com.ad4screen.sdk.service.modules.inapp.model.h.b("url", paramJSONObject.getJSONObject("data"));
        if (paramJSONObject != null) {
          locale.a.c = paramJSONObject;
        }
      }
      catch (JSONException paramJSONObject)
      {
        for (;;)
        {
          Log.error("InApp|Error parsing system action format", paramJSONObject);
        }
      }
    }
    return locale;
  }
  
  private com.ad4screen.sdk.model.displayformats.c j(JSONObject paramJSONObject)
  {
    if (paramJSONObject.isNull("id")) {}
    for (;;)
    {
      return null;
      com.ad4screen.sdk.model.displayformats.c localc = new com.ad4screen.sdk.model.displayformats.c();
      try
      {
        localc.i = com.ad4screen.sdk.service.modules.inapp.model.h.b("id", paramJSONObject);
        localc.a = com.ad4screen.sdk.service.modules.inapp.model.h.b("body", paramJSONObject.getJSONObject("data"));
        paramJSONObject = localc.a;
        if (paramJSONObject == null) {}
      }
      catch (JSONException paramJSONObject)
      {
        for (;;)
        {
          Log.error("InApp|Error parsing file format", paramJSONObject);
        }
      }
    }
    return localc;
  }
  
  private com.ad4screen.sdk.service.modules.inapp.model.g k(JSONObject paramJSONObject)
  {
    Object localObject1 = com.ad4screen.sdk.service.modules.inapp.model.h.b("type", paramJSONObject);
    if ("banner".equalsIgnoreCase((String)localObject1)) {
      localObject1 = g(paramJSONObject);
    }
    for (;;)
    {
      if (localObject1 == null)
      {
        return null;
        if ("popmessage".equalsIgnoreCase((String)localObject1)) {
          try
          {
            localObject1 = paramJSONObject.getJSONObject("data");
            if ((!((JSONObject)localObject1).isNull("action")) && ("system".equalsIgnoreCase(((JSONObject)localObject1).getString("action"))))
            {
              localc = j(paramJSONObject);
              localObject1 = localc;
              if (localc != null) {
                continue;
              }
              localObject1 = h(paramJSONObject);
            }
          }
          catch (JSONException localJSONException)
          {
            for (;;)
            {
              Log.error("InApp|Failed to parse file format : ", localJSONException);
              com.ad4screen.sdk.model.displayformats.c localc = null;
            }
          }
        }
        if ("browser".equalsIgnoreCase(localJSONException))
        {
          localObject2 = i(paramJSONObject);
          continue;
        }
        if ("setAlarm".equalsIgnoreCase((String)localObject2))
        {
          localObject2 = f(paramJSONObject);
          continue;
        }
        if ("cancelAlarm".equalsIgnoreCase((String)localObject2)) {
          localObject2 = e(paramJSONObject);
        }
      }
      else
      {
        paramJSONObject = new com.ad4screen.sdk.service.modules.inapp.model.g();
        paramJSONObject.a((com.ad4screen.sdk.model.displayformats.d)localObject2);
        return paramJSONObject;
      }
      Object localObject2 = null;
    }
  }
  
  public g.a a(String paramString1, String paramString2, JSONObject paramJSONObject)
    throws JSONException
  {
    g.a locala = new g.a();
    locala.a = com.ad4screen.sdk.service.modules.inapp.model.h.b("id", paramJSONObject);
    Object localObject = com.ad4screen.sdk.service.modules.inapp.model.h.b("action", paramJSONObject);
    if ("browser".equalsIgnoreCase((String)localObject))
    {
      localObject = new com.ad4screen.sdk.model.displayformats.e();
      ((com.ad4screen.sdk.model.displayformats.e)localObject).i = (paramString1 + '#' + paramString2);
      ((com.ad4screen.sdk.model.displayformats.e)localObject).b = e.a.b;
      ((com.ad4screen.sdk.model.displayformats.e)localObject).a.c = com.ad4screen.sdk.service.modules.inapp.model.h.b("url", paramJSONObject);
      locala.d = ((com.ad4screen.sdk.model.displayformats.d)localObject);
    }
    for (;;)
    {
      locala.b = com.ad4screen.sdk.service.modules.inapp.model.h.b("title", paramJSONObject);
      if (!paramJSONObject.isNull("icon")) {
        locala.c = this.b.getResources().getIdentifier(paramJSONObject.getString("icon"), "drawable", this.b.getPackageName());
      }
      if (!paramJSONObject.isNull("clickCustomParams")) {
        a(locala.e, paramJSONObject.getJSONArray("clickCustomParams"));
      }
      return locala;
      if ("urlExec".equalsIgnoreCase((String)localObject))
      {
        localObject = new com.ad4screen.sdk.model.displayformats.h();
        ((com.ad4screen.sdk.model.displayformats.h)localObject).i = (paramString1 + "#" + paramString2);
        ((com.ad4screen.sdk.model.displayformats.h)localObject).a = com.ad4screen.sdk.service.modules.inapp.model.h.b("url", paramJSONObject);
        locala.d = ((com.ad4screen.sdk.model.displayformats.d)localObject);
      }
      else if ("webView".equalsIgnoreCase((String)localObject))
      {
        localObject = new com.ad4screen.sdk.model.displayformats.e();
        ((com.ad4screen.sdk.model.displayformats.e)localObject).i = (paramString1 + "#" + paramString2);
        ((com.ad4screen.sdk.model.displayformats.e)localObject).a.d = "com_ad4screen_sdk_template_interstitial";
        ((com.ad4screen.sdk.model.displayformats.e)localObject).a.c = com.ad4screen.sdk.service.modules.inapp.model.h.b("url", paramJSONObject);
        locala.d = ((com.ad4screen.sdk.model.displayformats.d)localObject);
      }
    }
  }
  
  protected void a(LinkedList<i> paramLinkedList, JSONObject paramJSONObject, String paramString)
    throws JSONException
  {
    i locali = new i();
    locali.b(paramString);
    Integer localInteger;
    int i;
    if (!paramJSONObject.isNull("basics"))
    {
      paramString = paramJSONObject.getJSONObject("basics");
      locali.a(com.ad4screen.sdk.service.modules.inapp.model.h.c("displayOnlyOnceByEvent", paramString).booleanValue());
      locali.a(com.ad4screen.sdk.common.h.a(com.ad4screen.sdk.service.modules.inapp.model.h.b("startDate", paramString), h.a.b));
      locali.b(com.ad4screen.sdk.common.h.a(com.ad4screen.sdk.service.modules.inapp.model.h.b("endDate", paramString), h.a.b));
      locali.a(com.ad4screen.sdk.service.modules.inapp.model.h.d("capping", paramString));
      localInteger = com.ad4screen.sdk.service.modules.inapp.model.h.d("priority", paramString);
      if (localInteger != null) {
        break label266;
      }
      i = 0;
      locali.a(i);
      locali.b(com.ad4screen.sdk.service.modules.inapp.model.h.d("globalClickCapping", paramString));
      locali.c(com.ad4screen.sdk.service.modules.inapp.model.h.d("sessionClickCapping", paramString));
      locali.d(com.ad4screen.sdk.service.modules.inapp.model.h.d("delayCapping", paramString));
      locali.a(c(paramString));
      locali.b(d(paramString));
      paramString = com.ad4screen.sdk.service.modules.inapp.model.h.b("networkRestriction", paramString);
      if (paramString != null)
      {
        if (!"3g".equalsIgnoreCase(paramString)) {
          break label276;
        }
        locali.a(i.a.b);
      }
    }
    for (;;)
    {
      if (!paramJSONObject.isNull("inclusions")) {
        locali.a(b(paramJSONObject.getJSONObject("inclusions")));
      }
      if (!paramJSONObject.isNull("exclusions")) {
        locali.b(b(paramJSONObject.getJSONObject("exclusions")));
      }
      paramLinkedList.add(locali);
      return;
      label266:
      i = localInteger.intValue();
      break;
      label276:
      if ("wifi".equalsIgnoreCase(paramString)) {
        locali.a(i.a.c);
      }
    }
  }
  
  public void a(JSONObject paramJSONObject)
  {
    this.a = new com.ad4screen.sdk.service.modules.inapp.model.e();
    Object localObject = new LinkedList();
    LinkedList localLinkedList = new LinkedList();
    try
    {
      paramJSONObject = paramJSONObject.getJSONArray("notifications");
      int i = 0;
      while (i < paramJSONObject.length())
      {
        JSONObject localJSONObject = paramJSONObject.getJSONObject(i);
        com.ad4screen.sdk.service.modules.inapp.model.g localg = k(localJSONObject);
        if (localg != null)
        {
          localLinkedList.add(localg);
          if (!localJSONObject.isNull("rules")) {
            a((LinkedList)localObject, localJSONObject.getJSONObject("rules"), localg.a().i);
          }
        }
        i += 1;
      }
      return;
    }
    catch (JSONException paramJSONObject)
    {
      Log.error("InApp|Failed to parse configuration : ", paramJSONObject);
      this.a.a = new i[((LinkedList)localObject).size()];
      ((LinkedList)localObject).toArray(this.a.a);
      paramJSONObject = localLinkedList.iterator();
      while (paramJSONObject.hasNext())
      {
        localObject = (com.ad4screen.sdk.service.modules.inapp.model.g)paramJSONObject.next();
        this.a.b.put(((com.ad4screen.sdk.service.modules.inapp.model.g)localObject).a().i, localObject);
      }
    }
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */