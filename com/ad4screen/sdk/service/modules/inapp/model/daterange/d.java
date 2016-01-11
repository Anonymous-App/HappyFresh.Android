package com.ad4screen.sdk.service.modules.inapp.model.daterange;

import com.ad4screen.sdk.common.persistence.c;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class d
  implements c<d>, com.ad4screen.sdk.common.persistence.d
{
  private g a = g.a;
  private int b = 1;
  private List<Integer> c = new ArrayList();
  private HashMap<e, Integer> d = new HashMap();
  private List<Integer> e = new ArrayList();
  private List<Integer> f = new ArrayList();
  private f g = f.a;
  private long h;
  
  public d a(String paramString)
    throws JSONException
  {
    int j = 0;
    JSONObject localJSONObject1 = new JSONObject(paramString).getJSONObject(getClassKey());
    if (!localJSONObject1.isNull("frequency")) {
      this.a = g.valueOf(localJSONObject1.getString("frequency"));
    }
    if (!localJSONObject1.isNull("interval")) {
      this.b = localJSONObject1.getInt("interval");
    }
    int i;
    if (!localJSONObject1.isNull("byMonthDay"))
    {
      this.c = new ArrayList();
      paramString = localJSONObject1.getJSONArray("byMonthDay");
      i = 0;
      while (i < paramString.length())
      {
        this.c.add(Integer.valueOf(paramString.getInt(i)));
        i += 1;
      }
    }
    if (!localJSONObject1.isNull("byDay"))
    {
      this.d = new HashMap();
      JSONArray localJSONArray = localJSONObject1.getJSONArray("byDay");
      i = 0;
      while (i < localJSONArray.length())
      {
        JSONObject localJSONObject2 = localJSONArray.getJSONObject(i);
        e locale = e.valueOf(localJSONObject2.getString("recurrenceDay"));
        paramString = null;
        if (!localJSONObject2.isNull("recurrencePrefix")) {
          paramString = Integer.valueOf(localJSONObject2.getInt("recurrencePrefix"));
        }
        this.d.put(locale, paramString);
        i += 1;
      }
    }
    if (!localJSONObject1.isNull("byHour"))
    {
      this.e = new ArrayList();
      paramString = localJSONObject1.getJSONArray("byHour");
      i = 0;
      while (i < paramString.length())
      {
        this.e.add(Integer.valueOf(paramString.getInt(i)));
        i += 1;
      }
    }
    if (!localJSONObject1.isNull("byMinute"))
    {
      this.f = new ArrayList();
      paramString = localJSONObject1.getJSONArray("byMinute");
      i = j;
      while (i < paramString.length())
      {
        this.f.add(Integer.valueOf(paramString.getInt(i)));
        i += 1;
      }
    }
    if (!localJSONObject1.isNull("durationType")) {
      this.g = f.valueOf(localJSONObject1.getString("durationType"));
    }
    if (!localJSONObject1.isNull("durationValue")) {
      this.h = localJSONObject1.getLong("durationValue");
    }
    return this;
  }
  
  public g a()
  {
    return this.a;
  }
  
  public void a(int paramInt)
  {
    this.b = paramInt;
  }
  
  public void a(long paramLong)
  {
    this.h = paramLong;
  }
  
  public void a(f paramf)
  {
    this.g = paramf;
  }
  
  public void a(g paramg)
  {
    this.a = paramg;
  }
  
  public void a(HashMap<e, Integer> paramHashMap)
  {
    this.d = paramHashMap;
  }
  
  public void a(List<Integer> paramList)
  {
    this.c = paramList;
  }
  
  public int b()
  {
    return this.b;
  }
  
  public void b(List<Integer> paramList)
  {
    this.e = paramList;
  }
  
  public List<Integer> c()
  {
    return this.c;
  }
  
  public void c(List<Integer> paramList)
  {
    this.f = paramList;
  }
  
  public HashMap<e, Integer> d()
  {
    return this.d;
  }
  
  public List<Integer> e()
  {
    return this.e;
  }
  
  public List<Integer> f()
  {
    return this.f;
  }
  
  public long g()
  {
    return this.g.a() * this.h;
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.inapp.model.daterange.Recurrence";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = new JSONObject();
    JSONObject localJSONObject2 = new JSONObject();
    if (this.a != null) {
      localJSONObject2.put("frequency", this.a.toString());
    }
    localJSONObject2.put("interval", this.b);
    JSONArray localJSONArray;
    Iterator localIterator;
    if (this.c != null)
    {
      localJSONArray = new JSONArray();
      localIterator = this.c.iterator();
      while (localIterator.hasNext()) {
        localJSONArray.put((Integer)localIterator.next());
      }
      localJSONObject2.put("byMonthDay", localJSONArray);
    }
    if (this.d != null)
    {
      localJSONArray = new JSONArray();
      localIterator = this.d.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        JSONObject localJSONObject3 = new JSONObject();
        localJSONObject3.put("recurrenceDay", ((e)localEntry.getKey()).toString());
        localJSONObject3.put("recurrencePrefix", localEntry.getValue());
        localJSONArray.put(localJSONObject3);
      }
      localJSONObject2.put("byDay", localJSONArray);
    }
    if (this.e != null)
    {
      localJSONArray = new JSONArray();
      localIterator = this.e.iterator();
      while (localIterator.hasNext()) {
        localJSONArray.put((Integer)localIterator.next());
      }
      localJSONObject2.put("byHour", localJSONArray);
    }
    if (this.f != null)
    {
      localJSONArray = new JSONArray();
      localIterator = this.f.iterator();
      while (localIterator.hasNext()) {
        localJSONArray.put((Integer)localIterator.next());
      }
      localJSONObject2.put("byMinute", localJSONArray);
    }
    if (this.g != null) {
      localJSONObject2.put("durationType", this.g.toString());
    }
    localJSONObject2.put("durationValue", this.h);
    localJSONObject1.put(getClassKey(), localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/model/daterange/d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */