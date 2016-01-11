package com.ad4screen.sdk.service.modules.inapp;

import com.ad4screen.sdk.common.persistence.d;
import com.ad4screen.sdk.common.persistence.e;
import com.ad4screen.sdk.service.modules.inapp.model.a;
import com.ad4screen.sdk.systems.h;
import com.ad4screen.sdk.systems.j;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class i
  implements com.ad4screen.sdk.common.persistence.c<i>, d
{
  private static HashMap<String, a> r = new HashMap();
  private final long a = 600000L;
  private final String b = "userDisplayLocked";
  private final String c = "displayedInApp";
  private final String d = "currentActivityClassPath";
  private final String e = "currentActivityName";
  private final String f = "currentActivityInstance";
  private final String g = "sessionEvents";
  private final String h = "sessionStates";
  private final String i = "sessionBeacons";
  private e j = new e();
  private boolean k;
  private ArrayList<String> l = new ArrayList();
  private String m;
  private String n;
  private String o;
  private List<Long> p = new ArrayList();
  private HashMap<String, com.ad4screen.sdk.service.modules.inapp.model.states.c> q = new HashMap();
  
  public static i a(h paramh)
  {
    return paramh.e().a();
  }
  
  private void b(HashMap<String, a> paramHashMap)
  {
    Iterator localIterator = paramHashMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (System.currentTimeMillis() - ((a)paramHashMap.get(str)).d > 600000L) {
        r.remove(str);
      }
    }
  }
  
  public i a(String paramString)
    throws JSONException
  {
    int i2 = 0;
    paramString = new JSONObject(paramString);
    this.k = paramString.getBoolean("userDisplayLocked");
    Object localObject;
    int i1;
    if (!paramString.isNull("displayedInApp"))
    {
      localObject = paramString.getJSONArray("displayedInApp");
      i1 = 0;
      while (i1 < ((JSONArray)localObject).length())
      {
        this.l.add(((JSONArray)localObject).getString(i1));
        i1 += 1;
      }
    }
    if (!paramString.isNull("currentActivityClassPath")) {
      this.m = paramString.getString("currentActivityClassPath");
    }
    if (!paramString.isNull("currentActivityName")) {
      this.n = paramString.getString("currentActivityName");
    }
    if (!paramString.isNull("currentActivityInstance")) {
      this.o = paramString.getString("currentActivityInstance");
    }
    if (!paramString.isNull("sessionEvents"))
    {
      localObject = paramString.getJSONArray("sessionEvents");
      i1 = 0;
      while (i1 < ((JSONArray)localObject).length())
      {
        this.p.add(Long.valueOf(((JSONArray)localObject).getLong(i1)));
        i1 += 1;
      }
    }
    if (!paramString.isNull("sessionStates"))
    {
      localObject = paramString.getJSONArray("sessionStates");
      i1 = 0;
      while (i1 < ((JSONArray)localObject).length())
      {
        JSONObject localJSONObject = ((JSONArray)localObject).getJSONObject(i1);
        this.q.put(localJSONObject.getString("key"), this.j.a(localJSONObject.getString("state"), new com.ad4screen.sdk.service.modules.inapp.model.states.c()));
        i1 += 1;
      }
    }
    if (!paramString.isNull("sessionBeacons"))
    {
      paramString = paramString.getJSONArray("sessionBeacons");
      i1 = i2;
      while (i1 < paramString.length())
      {
        localObject = paramString.getJSONObject(i1);
        r.put(((JSONObject)localObject).getString("key"), this.j.a(((JSONObject)localObject).getString("beacon"), new a()));
        i1 += 1;
      }
    }
    return this;
  }
  
  public HashMap<String, a> a()
  {
    b(new HashMap(r));
    return r;
  }
  
  public void a(a parama)
  {
    if (parama == null) {
      return;
    }
    r.put(parama.a, parama);
  }
  
  public void a(String paramString, com.ad4screen.sdk.service.modules.inapp.model.states.c paramc)
  {
    this.q.put(paramString, paramc);
  }
  
  public void a(HashMap<String, a> paramHashMap)
  {
    r = new HashMap(paramHashMap);
    b(paramHashMap);
  }
  
  public void a(boolean paramBoolean)
  {
    this.k = paramBoolean;
  }
  
  public void b(h paramh)
  {
    paramh.e().a(this);
  }
  
  public void b(String paramString)
  {
    this.m = paramString;
  }
  
  public boolean b()
  {
    return this.k;
  }
  
  public i c(h paramh)
  {
    paramh.e().a(new i());
    return paramh.e().a();
  }
  
  public ArrayList<String> c()
  {
    return this.l;
  }
  
  public void c(String paramString)
  {
    this.n = paramString;
  }
  
  public String d()
  {
    return this.m;
  }
  
  public void d(String paramString)
  {
    this.o = paramString;
  }
  
  public com.ad4screen.sdk.service.modules.inapp.model.states.c e(String paramString)
  {
    return (com.ad4screen.sdk.service.modules.inapp.model.states.c)this.q.get(paramString);
  }
  
  public String e()
  {
    return this.n;
  }
  
  public String f()
  {
    return this.o;
  }
  
  public void f(String paramString)
  {
    this.q.remove(paramString);
  }
  
  public List<Long> g()
  {
    return this.p;
  }
  
  public String getClassKey()
  {
    return "InApp.SessionData";
  }
  
  public HashMap<String, com.ad4screen.sdk.service.modules.inapp.model.states.c> h()
  {
    return this.q;
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = new JSONObject();
    localJSONObject1.put("userDisplayLocked", this.k);
    localJSONObject1.put("currentActivityClassPath", this.m);
    localJSONObject1.put("currentActivityName", this.n);
    localJSONObject1.put("currentActivityInstance", this.o);
    JSONArray localJSONArray = new JSONArray();
    Iterator localIterator = this.l.iterator();
    while (localIterator.hasNext()) {
      localJSONArray.put((String)localIterator.next());
    }
    localJSONObject1.put("displayedInApp", localJSONArray);
    localJSONArray = new JSONArray();
    localIterator = this.p.iterator();
    while (localIterator.hasNext()) {
      localJSONArray.put((Long)localIterator.next());
    }
    localJSONObject1.put("sessionEvents", localJSONArray);
    localJSONArray = new JSONArray();
    localIterator = this.q.keySet().iterator();
    String str;
    JSONObject localJSONObject2;
    while (localIterator.hasNext())
    {
      str = (String)localIterator.next();
      localJSONObject2 = new JSONObject();
      localJSONObject2.put("key", str);
      localJSONObject2.put("state", this.j.a(this.q.get(str)));
      localJSONArray.put(localJSONObject2);
    }
    localJSONObject1.put("sessionStates", localJSONArray);
    localJSONArray = new JSONArray();
    localIterator = r.keySet().iterator();
    while (localIterator.hasNext())
    {
      str = (String)localIterator.next();
      localJSONObject2 = new JSONObject();
      localJSONObject2.put("key", str);
      localJSONObject2.put("beacon", this.j.a(r.get(str)));
      localJSONArray.put(localJSONObject2);
    }
    localJSONObject1.put("sessionBeacons", localJSONArray);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/i.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */