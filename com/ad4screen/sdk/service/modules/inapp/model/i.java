package com.ad4screen.sdk.service.modules.inapp.model;

import com.ad4screen.sdk.common.persistence.c;
import com.ad4screen.sdk.common.persistence.d;
import com.ad4screen.sdk.common.persistence.e;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

public class i
  implements c<i>, d
{
  private String a;
  private Date b;
  private Date c;
  private Integer d;
  private Integer e;
  private Integer f;
  private Integer g;
  private a h = a.a;
  private int i;
  private boolean j;
  private Long k;
  private Long l;
  private j m = new j();
  private j n = new j();
  
  public i a(String paramString)
    throws JSONException
  {
    e locale = new e();
    paramString = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.service.modules.inapp.model.Rule");
    if (!paramString.isNull("id")) {
      this.a = paramString.getString("id");
    }
    if (!paramString.isNull("displayOnlyOnceByEvent")) {
      this.j = paramString.getBoolean("displayOnlyOnceByEvent");
    }
    if (!paramString.isNull("startDate")) {
      this.b = new Date(paramString.getLong("startDate"));
    }
    if (!paramString.isNull("endDate")) {
      this.c = new Date(paramString.getLong("endDate"));
    }
    if (!paramString.isNull("capping")) {
      this.d = Integer.valueOf(paramString.getInt("capping"));
    }
    if (!paramString.isNull("clickCapping")) {
      this.e = Integer.valueOf(paramString.getInt("clickCapping"));
    }
    if (!paramString.isNull("sessionClickCapping")) {
      this.f = Integer.valueOf(paramString.getInt("sessionClickCapping"));
    }
    if (!paramString.isNull("delay")) {
      this.g = Integer.valueOf(paramString.getInt("delay"));
    }
    if (!paramString.isNull("networkRestriction")) {
      this.h = a.valueOf(paramString.getString("networkRestriction"));
    }
    if (!paramString.isNull("priority")) {
      this.i = paramString.getInt("priority");
    }
    if (!paramString.isNull("timer")) {
      this.k = Long.valueOf(paramString.getLong("timer"));
    }
    if (!paramString.isNull("sessionTimer")) {
      this.l = Long.valueOf(paramString.getLong("sessionTimer"));
    }
    if (!paramString.isNull("inclusions")) {
      this.m = ((j)locale.a(paramString.getJSONObject("inclusions").toString(), new j()));
    }
    if (!paramString.isNull("exclusions")) {
      this.n = ((j)locale.a(paramString.getJSONObject("exclusions").toString(), new j()));
    }
    return this;
  }
  
  public String a()
  {
    return this.a;
  }
  
  public void a(int paramInt)
  {
    this.i = paramInt;
  }
  
  public void a(a parama)
  {
    this.h = parama;
  }
  
  public void a(j paramj)
  {
    if (paramj == null)
    {
      this.m = new j();
      return;
    }
    this.m = paramj;
  }
  
  public void a(Integer paramInteger)
  {
    this.d = paramInteger;
  }
  
  public void a(Long paramLong)
  {
    this.k = paramLong;
  }
  
  public void a(Date paramDate)
  {
    this.b = paramDate;
  }
  
  public void a(boolean paramBoolean)
  {
    this.j = paramBoolean;
  }
  
  public Date b()
  {
    return this.b;
  }
  
  public void b(j paramj)
  {
    if (paramj == null)
    {
      this.n = new j();
      return;
    }
    this.n = paramj;
  }
  
  public void b(Integer paramInteger)
  {
    this.e = paramInteger;
  }
  
  public void b(Long paramLong)
  {
    this.l = paramLong;
  }
  
  public void b(String paramString)
  {
    this.a = paramString;
  }
  
  public void b(Date paramDate)
  {
    this.c = paramDate;
  }
  
  public Date c()
  {
    return this.c;
  }
  
  public void c(Integer paramInteger)
  {
    this.f = paramInteger;
  }
  
  public Integer d()
  {
    return this.d;
  }
  
  public void d(Integer paramInteger)
  {
    this.g = paramInteger;
  }
  
  public Integer e()
  {
    return this.e;
  }
  
  public Integer f()
  {
    return this.f;
  }
  
  public Integer g()
  {
    return this.g;
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.inapp.model.Rule";
  }
  
  public a h()
  {
    return this.h;
  }
  
  public int i()
  {
    return this.i;
  }
  
  public boolean j()
  {
    return this.j;
  }
  
  public Long k()
  {
    return this.k;
  }
  
  public Long l()
  {
    return this.l;
  }
  
  public j m()
  {
    return this.m;
  }
  
  public j n()
  {
    return this.n;
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    e locale = new e();
    JSONObject localJSONObject1 = new JSONObject();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("id", this.a);
    if (this.b != null) {
      localJSONObject2.put("startDate", this.b.getTime());
    }
    if (this.c != null) {
      localJSONObject2.put("endDate", this.c.getTime());
    }
    localJSONObject2.put("capping", this.d);
    localJSONObject2.put("clickCapping", this.e);
    localJSONObject2.put("sessionClickCapping", this.f);
    localJSONObject2.put("delay", this.g);
    localJSONObject2.put("networkRestriction", this.h.toString());
    localJSONObject2.put("priority", this.i);
    localJSONObject2.put("displayOnlyOnceByEvent", this.j);
    localJSONObject2.put("timer", this.k);
    localJSONObject2.put("sessionTimer", this.l);
    localJSONObject2.put("inclusions", locale.a(this.m));
    localJSONObject2.put("exclusions", locale.a(this.n));
    localJSONObject1.put("com.ad4screen.sdk.service.modules.inapp.model.Rule", localJSONObject2);
    return localJSONObject1;
  }
  
  public static enum a
  {
    private a() {}
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/model/i.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */