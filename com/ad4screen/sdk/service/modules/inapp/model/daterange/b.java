package com.ad4screen.sdk.service.modules.inapp.model.daterange;

import com.ad4screen.sdk.common.persistence.c;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

public class b
  implements c<b>, com.ad4screen.sdk.common.persistence.d
{
  private Date a;
  private Date b;
  private d c;
  private boolean d;
  
  public b a(String paramString)
    throws JSONException
  {
    paramString = new JSONObject(paramString).getJSONObject(getClassKey());
    if (!paramString.isNull("startDate")) {
      this.a = new Date(paramString.getLong("startDate"));
    }
    if (!paramString.isNull("endDate")) {
      this.b = new Date(paramString.getLong("endDate"));
    }
    if (!paramString.isNull("isLocal")) {
      this.d = paramString.getBoolean("isLocal");
    }
    if (!paramString.isNull("recurrence"))
    {
      paramString = paramString.getJSONObject("recurrence");
      this.c = new d().a(paramString.toString());
    }
    return this;
  }
  
  public Date a()
  {
    return this.a;
  }
  
  public void a(d paramd)
  {
    this.c = paramd;
  }
  
  public void a(Date paramDate)
  {
    this.a = paramDate;
  }
  
  public void a(boolean paramBoolean)
  {
    this.d = paramBoolean;
  }
  
  public Date b()
  {
    return this.b;
  }
  
  public void b(Date paramDate)
  {
    this.b = paramDate;
  }
  
  public d c()
  {
    return this.c;
  }
  
  public boolean d()
  {
    return this.d;
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.inapp.model.DateRange";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = new JSONObject();
    JSONObject localJSONObject2 = new JSONObject();
    if (this.a != null) {
      localJSONObject2.put("startDate", this.a.getTime());
    }
    if (this.b != null) {
      localJSONObject2.put("endDate", this.b.getTime());
    }
    localJSONObject2.put("isLocal", this.d);
    if (this.c != null) {
      localJSONObject2.put("recurrence", this.c.toJSON());
    }
    localJSONObject1.put(getClassKey(), localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/model/daterange/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */