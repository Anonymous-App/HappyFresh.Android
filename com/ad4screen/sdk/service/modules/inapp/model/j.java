package com.ad4screen.sdk.service.modules.inapp.model;

import com.ad4screen.sdk.common.persistence.d;
import com.ad4screen.sdk.common.persistence.e;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class j
  implements com.ad4screen.sdk.common.persistence.c<j>, d
{
  private List<com.ad4screen.sdk.service.modules.inapp.model.events.a> a;
  private List<k> b;
  private List<com.ad4screen.sdk.service.modules.inapp.model.daterange.b> c;
  private List<com.ad4screen.sdk.service.modules.inapp.model.states.a> d;
  private List<f> e;
  private List<c> f;
  private List<b> g;
  
  public j a(String paramString)
    throws JSONException
  {
    e locale = new e();
    paramString = new JSONObject(paramString).getJSONObject(getClassKey());
    this.a = new ArrayList();
    Object localObject1;
    Object localObject2;
    if (!paramString.isNull("events"))
    {
      localObject1 = ((ArrayList)locale.a(paramString.getJSONObject("events").toString(), new ArrayList())).iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (com.ad4screen.sdk.service.modules.inapp.model.events.a)locale.a(((JSONObject)((Iterator)localObject1).next()).toString(), new com.ad4screen.sdk.service.modules.inapp.model.events.a());
        this.a.add(localObject2);
      }
    }
    this.b = new ArrayList();
    if (!paramString.isNull("views"))
    {
      localObject1 = ((ArrayList)locale.a(paramString.getJSONObject("views").toString(), new ArrayList())).iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (k)locale.a(((JSONObject)((Iterator)localObject1).next()).toString(), new k());
        this.b.add(localObject2);
      }
    }
    this.d = new ArrayList();
    if (!paramString.isNull("states"))
    {
      localObject1 = ((ArrayList)locale.a(paramString.getJSONObject("states").toString(), new ArrayList())).iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (com.ad4screen.sdk.service.modules.inapp.model.states.a)locale.a(((JSONObject)((Iterator)localObject1).next()).toString(), new com.ad4screen.sdk.service.modules.inapp.model.states.a());
        this.d.add(localObject2);
      }
    }
    this.c = new ArrayList();
    if (!paramString.isNull("dateRanges"))
    {
      localObject1 = ((ArrayList)locale.a(paramString.getJSONObject("dateRanges").toString(), new ArrayList())).iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (com.ad4screen.sdk.service.modules.inapp.model.daterange.b)locale.a(((JSONObject)((Iterator)localObject1).next()).toString(), new com.ad4screen.sdk.service.modules.inapp.model.daterange.b());
        this.c.add(localObject2);
      }
    }
    this.e = new ArrayList();
    if (!paramString.isNull("locations"))
    {
      localObject1 = ((ArrayList)locale.a(paramString.getJSONObject("locations").toString(), new ArrayList())).iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (f)locale.a(((JSONObject)((Iterator)localObject1).next()).toString(), new f());
        this.e.add(localObject2);
      }
    }
    this.f = new ArrayList();
    if (!paramString.isNull("geofences"))
    {
      localObject1 = ((ArrayList)locale.a(paramString.getJSONObject("geofences").toString(), new ArrayList())).iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (c)locale.a(((JSONObject)((Iterator)localObject1).next()).toString(), new c());
        this.f.add(localObject2);
      }
    }
    this.g = new ArrayList();
    if (!paramString.isNull("beacons"))
    {
      paramString = ((ArrayList)locale.a(paramString.getJSONObject("beacons").toString(), new ArrayList())).iterator();
      while (paramString.hasNext())
      {
        localObject1 = (b)locale.a(((JSONObject)paramString.next()).toString(), new b());
        this.g.add(localObject1);
      }
    }
    return this;
  }
  
  public List<f> a()
  {
    return this.e;
  }
  
  public void a(List<f> paramList)
  {
    this.e = paramList;
  }
  
  public List<com.ad4screen.sdk.service.modules.inapp.model.daterange.b> b()
  {
    return this.c;
  }
  
  public void b(List<com.ad4screen.sdk.service.modules.inapp.model.daterange.b> paramList)
  {
    this.c = paramList;
  }
  
  public List<k> c()
  {
    return this.b;
  }
  
  public void c(List<k> paramList)
  {
    this.b = paramList;
  }
  
  public List<com.ad4screen.sdk.service.modules.inapp.model.states.a> d()
  {
    return this.d;
  }
  
  public void d(List<com.ad4screen.sdk.service.modules.inapp.model.states.a> paramList)
  {
    this.d = paramList;
  }
  
  public List<com.ad4screen.sdk.service.modules.inapp.model.events.a> e()
  {
    return this.a;
  }
  
  public void e(List<com.ad4screen.sdk.service.modules.inapp.model.events.a> paramList)
  {
    this.a = paramList;
  }
  
  public List<c> f()
  {
    return this.f;
  }
  
  public void f(List<c> paramList)
  {
    this.f = paramList;
  }
  
  public List<b> g()
  {
    return this.g;
  }
  
  public void g(List<b> paramList)
  {
    this.g = paramList;
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.inapp.model.TriggerRule";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    e locale = new e();
    JSONObject localJSONObject1 = new JSONObject();
    JSONObject localJSONObject2 = new JSONObject();
    if (this.a != null) {
      localJSONObject2.put("events", locale.a(this.a));
    }
    if (this.b != null) {
      localJSONObject2.put("views", locale.a(this.b));
    }
    if (this.d != null) {
      localJSONObject2.put("states", locale.a(this.d));
    }
    if (this.c != null) {
      localJSONObject2.put("dateRanges", locale.a(this.c));
    }
    if (this.e != null) {
      localJSONObject2.put("locations", locale.a(this.e));
    }
    if (this.f != null) {
      localJSONObject2.put("geofences", locale.a(this.f));
    }
    if (this.g != null) {
      localJSONObject2.put("beacons", locale.a(this.g));
    }
    localJSONObject1.put(getClassKey(), localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/model/j.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */