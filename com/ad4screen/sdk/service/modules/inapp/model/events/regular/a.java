package com.ad4screen.sdk.service.modules.inapp.model.events.regular;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class a
  extends com.ad4screen.sdk.service.modules.inapp.model.events.a
{
  protected Long a;
  protected String b;
  
  protected a() {}
  
  protected a(Long paramLong, String paramString)
  {
    this.a = paramLong;
    this.b = paramString;
  }
  
  public Long a()
  {
    return this.a;
  }
  
  public a b(String paramString)
    throws JSONException
  {
    paramString = new JSONObject(paramString).getJSONObject(getClassKey());
    this.a = Long.valueOf(paramString.getLong("code"));
    if (!paramString.isNull("value")) {
      this.b = paramString.getString("value");
    }
    return this;
  }
  
  public String b()
  {
    return this.b;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    do
    {
      do
      {
        return true;
        if (paramObject == null) {
          return false;
        }
        if (getClass() != paramObject.getClass()) {
          return false;
        }
        paramObject = (a)paramObject;
        if (this.a == null)
        {
          if (((a)paramObject).a != null) {
            return false;
          }
        }
        else if (!this.a.equals(((a)paramObject).a)) {
          return false;
        }
        if (this.b != null) {
          break;
        }
      } while (((a)paramObject).b == null);
      return false;
    } while (this.b.equals(((a)paramObject).b));
    return false;
  }
  
  public int hashCode()
  {
    int j = 0;
    int i;
    if (this.a == null)
    {
      i = 0;
      if (this.b != null) {
        break label39;
      }
    }
    for (;;)
    {
      return (i + 31) * 31 + j;
      i = this.a.hashCode();
      break;
      label39:
      j = this.b.hashCode();
    }
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = new JSONObject();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("code", this.a);
    localJSONObject2.put("value", this.b);
    localJSONObject1.put(getClassKey(), localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inapp/model/events/regular/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */