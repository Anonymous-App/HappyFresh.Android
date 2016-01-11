package com.ad4screen.sdk.service.modules.inapp.model;

import android.annotation.SuppressLint;
import com.ad4screen.sdk.common.persistence.c;
import com.ad4screen.sdk.common.persistence.e;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class g
  implements c<g>, com.ad4screen.sdk.common.persistence.d
{
  private e a = new e();
  private com.ad4screen.sdk.model.displayformats.d b;
  private int c;
  private int d;
  private int e;
  private int f;
  private long g;
  private long h;
  private long i;
  private HashMap<Long, Integer> j = new HashMap();
  private HashMap<Long, Integer> k = new HashMap();
  
  @SuppressLint({"UseSparseArrays"})
  private HashMap<Long, Integer> b(String paramString)
    throws JSONException
  {
    new HashMap();
    HashMap localHashMap = new HashMap();
    paramString = (HashMap)this.a.a(paramString, new HashMap());
    if (paramString != null)
    {
      paramString = paramString.entrySet().iterator();
      while (paramString.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)paramString.next();
        localHashMap.put(Long.valueOf(((Integer)localEntry.getKey()).intValue()), Integer.valueOf(((Integer)localEntry.getValue()).intValue()));
      }
    }
    return localHashMap;
  }
  
  public com.ad4screen.sdk.model.displayformats.d a()
  {
    return this.b;
  }
  
  public g a(String paramString)
    throws JSONException
  {
    paramString = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.service.modules.inapp.model.Message");
    this.c = paramString.getInt("displayCount");
    this.d = paramString.getInt("sessionDisplayCount");
    this.e = paramString.getInt("clickCount");
    this.f = paramString.getInt("sessionClickCount");
    this.g = paramString.getLong("lastRulesValid");
    this.h = paramString.getLong("sessionLastRulesValid");
    this.i = paramString.getLong("lastDisplayTime");
    if (!paramString.isNull("format")) {
      this.b = ((com.ad4screen.sdk.model.displayformats.d)this.a.a(paramString.getString("format"), new com.ad4screen.sdk.model.displayformats.d()));
    }
    if (!paramString.isNull("eventsTriggerCount")) {
      this.j = b(paramString.getString("eventsTriggerCount"));
    }
    if (!paramString.isNull("eventsTriggerExclusionsCount")) {
      this.k = b(paramString.getString("eventsTriggerExclusionsCount"));
    }
    return this;
  }
  
  public void a(int paramInt)
  {
    this.c = paramInt;
  }
  
  public void a(long paramLong)
  {
    this.g = paramLong;
  }
  
  public void a(com.ad4screen.sdk.model.displayformats.d paramd)
  {
    this.b = paramd;
  }
  
  public int b()
  {
    return this.c;
  }
  
  public void b(int paramInt)
  {
    this.d = paramInt;
  }
  
  public void b(long paramLong)
  {
    this.h = paramLong;
  }
  
  public int c()
  {
    return this.d;
  }
  
  public void c(int paramInt)
  {
    this.e = paramInt;
  }
  
  public void c(long paramLong)
  {
    this.i = paramLong;
  }
  
  public int d()
  {
    return this.e;
  }
  
  public void d(int paramInt)
  {
    this.f = paramInt;
  }
  
  public int e()
  {
    return this.f;
  }
  
  public long f()
  {
    return this.g;
  }
  
  public long g()
  {
    return this.h;
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.inapp.model.Message";
  }
  
  public long h()
  {
    return this.i;
  }
  
  public HashMap<Long, Integer> i()
  {
    return this.j;
  }
  
  public HashMap<Long, Integer> j()
  {
    return this.k;
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = new JSONObject();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("format", this.a.a(this.b));
    localJSONObject2.put("lastDisplayTime", this.i);
    localJSONObject2.put("displayCount", this.c);
    localJSONObject2.put("sessionDisplayCount", this.d);
    localJSONObject2.put("clickCount", this.e);
    localJSONObject2.put("sessionClickCount", this.f);
    localJSONObject2.put("lastRulesValid", this.g);
    localJSONObject2.put("sessionLastRulesValid", this.h);
    localJSONObject2.put("eventsTriggerCount", this.a.a(this.j));
    localJSONObject2.put("eventsTriggerExclusionsCount", this.a.a(this.k));
    localJSONObject1.put("com.ad4screen.sdk.service.modules.inapp.model.Message", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/model/g.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */