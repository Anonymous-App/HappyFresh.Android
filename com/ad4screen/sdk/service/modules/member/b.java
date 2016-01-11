package com.ad4screen.sdk.service.modules.member;

import android.content.Context;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.tasks.c;
import com.ad4screen.sdk.systems.d;
import com.ad4screen.sdk.systems.d.b;
import org.json.JSONException;
import org.json.JSONObject;

public class b
  extends c
{
  private final String c = "com.ad4screen.sdk.service.modules.member.LoadMembersTask";
  private final String d = "content";
  private final a e;
  private final Context f;
  private String g;
  
  public b(Context paramContext, a parama)
  {
    super(paramContext);
    this.f = paramContext;
    this.e = parama;
  }
  
  protected void a(String paramString)
  {
    try
    {
      Log.internal("MemberManager|Members start parsing");
      paramString = new JSONObject(paramString);
      e locale = new e();
      locale.a(paramString);
      if (locale.a == null)
      {
        Log.error("MemberManager|Members parsing failed");
        if (this.e != null) {
          this.e.a();
        }
      }
      else
      {
        Log.internal("MemberManager|Members parsing success");
        d.a(this.f).e(d.b.m);
        if (this.e != null)
        {
          this.e.a(locale.a);
          return;
        }
      }
    }
    catch (JSONException paramString)
    {
      Log.internal("MemberManager|Response JSON Parsing error!", paramString);
      if (this.e != null) {
        this.e.a();
      }
    }
  }
  
  protected void a(Throwable paramThrowable)
  {
    if (this.e != null) {
      this.e.a();
    }
  }
  
  protected boolean a()
  {
    c("application/json;charset=utf-8");
    a(4);
    com.ad4screen.sdk.systems.b localb = com.ad4screen.sdk.systems.b.a(this.f);
    if (localb.f == null)
    {
      Log.warn("MemberManager|No sharedId, skipping reception of Linked Members");
      if (this.e != null) {
        this.e.a();
      }
    }
    do
    {
      return false;
      if (!d.a(this.f).c(d.b.m))
      {
        Log.debug("Service interruption on LoadMembersTask");
        return false;
      }
      try
      {
        JSONObject localJSONObject1 = new JSONObject();
        JSONObject localJSONObject2 = new JSONObject();
        localJSONObject1.put("partnerId", localb.d);
        localJSONObject1.put("deviceId", localb.f);
        localJSONObject2.put("listMembers", localJSONObject1);
        this.g = localJSONObject2.toString();
        return true;
      }
      catch (JSONException localJSONException)
      {
        Log.error("MemberManager|Could not build message to send to Ad4Screen", localJSONException);
      }
    } while (this.e == null);
    this.e.a();
    return false;
  }
  
  public c b(c paramc)
  {
    return paramc;
  }
  
  protected String c()
  {
    return d.b.m.toString();
  }
  
  public c d(String paramString)
    throws JSONException
  {
    super.d(paramString);
    paramString = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.service.modules.member.LoadMembersTask");
    if (!paramString.isNull("content")) {
      this.g = paramString.getString("content");
    }
    return this;
  }
  
  protected String d()
  {
    return this.g;
  }
  
  protected String e()
  {
    return d.a(this.f).a(d.b.m);
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.member.LoadMembersTask";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = super.toJSON();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("content", this.g);
    localJSONObject1.put("com.ad4screen.sdk.service.modules.member.LoadMembersTask", localJSONObject2);
    return localJSONObject1;
  }
  
  public static abstract interface a
  {
    public abstract void a();
    
    public abstract void a(String[] paramArrayOfString);
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/member/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */