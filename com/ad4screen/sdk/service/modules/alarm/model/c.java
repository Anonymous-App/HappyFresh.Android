package com.ad4screen.sdk.service.modules.alarm.model;

import android.os.Bundle;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.h.a;
import com.ad4screen.sdk.common.persistence.e;
import com.ad4screen.sdk.model.displayformats.d;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

public class c
  extends d
{
  private final String a = "com.ad4screen.sdk.model.displayformats.SetAlarm";
  private final String b = "pushPayload";
  private final String c = "date";
  private String d = "nextDisplayDate";
  private final String e = "cancelTrackingUrl";
  private final String f = "allowUpdate";
  private final String g = "updateTime";
  private Bundle n = new Bundle();
  private Date o;
  private Date p;
  private String q;
  private long r;
  private boolean s;
  
  public Bundle a()
  {
    return this.n;
  }
  
  public c a(String paramString)
    throws JSONException
  {
    paramString = new JSONObject(paramString);
    if (!paramString.isNull("pushPayload"))
    {
      JSONObject localJSONObject = paramString.getJSONObject("pushPayload");
      this.n = ((Bundle)this.m.a(localJSONObject.toString(), new Bundle()));
    }
    if (!paramString.isNull("date")) {
      this.p = h.a(paramString.getString("date"), h.a.b);
    }
    if (!paramString.isNull(this.d)) {
      this.o = h.a(paramString.getString(this.d), h.a.b);
    }
    if (!paramString.isNull("cancelTrackingUrl")) {
      this.q = paramString.getString("cancelTrackingUrl");
    }
    if (!paramString.isNull("allowUpdate")) {
      this.s = paramString.getBoolean("allowUpdate");
    }
    if (!paramString.isNull("updateTime")) {
      this.r = paramString.getLong("updateTime");
    }
    return this;
  }
  
  public void a(long paramLong)
  {
    this.r = paramLong;
  }
  
  public void a(Bundle paramBundle)
  {
    this.n = paramBundle;
  }
  
  public void a(Date paramDate)
  {
    this.o = paramDate;
  }
  
  public void a(boolean paramBoolean)
  {
    this.s = paramBoolean;
  }
  
  public Date b()
  {
    return this.o;
  }
  
  public void b(Date paramDate)
  {
    this.p = paramDate;
  }
  
  public Date c()
  {
    return this.p;
  }
  
  public void c(String paramString)
  {
    this.q = paramString;
  }
  
  public long d()
  {
    return this.r;
  }
  
  public boolean e()
  {
    return this.s;
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.model.displayformats.SetAlarm";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject = super.toJSON();
    localJSONObject.put("type", "com.ad4screen.sdk.model.displayformats.SetAlarm");
    if (this.n != null) {
      localJSONObject.put("pushPayload", this.m.a(this.n));
    }
    if (this.p != null) {
      localJSONObject.put("date", h.a(this.p, h.a.b));
    }
    if (this.o != null) {
      localJSONObject.put(this.d, h.a(this.o, h.a.b));
    }
    if (this.q != null) {
      localJSONObject.put("cancelTrackingUrl", this.q);
    }
    localJSONObject.put("allowUpdate", this.s);
    localJSONObject.put("updateTime", this.r);
    return localJSONObject;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/alarm/model/c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */