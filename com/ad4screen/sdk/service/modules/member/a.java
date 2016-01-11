package com.ad4screen.sdk.service.modules.member;

import android.content.Context;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.h.a;
import com.ad4screen.sdk.common.tasks.c;
import com.ad4screen.sdk.systems.d;
import com.ad4screen.sdk.systems.d.b;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

public class a
  extends c
{
  private final String c = "com.ad4screen.sdk.service.modules.member.LinkMemberTask";
  private final String d = "content";
  private final Context e;
  private String f;
  private com.ad4screen.sdk.service.modules.member.model.b g;
  
  public a(com.ad4screen.sdk.service.modules.member.model.b paramb, Context paramContext)
  {
    super(paramContext);
    this.e = paramContext;
    this.g = paramb;
  }
  
  protected void a(String paramString)
  {
    try
    {
      paramString = new JSONObject(paramString);
      if (!paramString.isNull("linkMemberResponse"))
      {
        paramString = paramString.getJSONObject("linkMemberResponse");
        if (paramString.getInt("returnCode") == 0)
        {
          Log.debug("MemberManager|Login Success " + this.f);
          d.a(this.e).e(d.b.k);
          return;
        }
        Log.error("MemberManager|Login Failure with error " + paramString.getString("returnCode") + " : " + paramString.getString("returnLabel"));
        return;
      }
    }
    catch (JSONException paramString)
    {
      Log.error("MemberManager|Can't parse server response", paramString);
    }
  }
  
  protected void a(Throwable paramThrowable) {}
  
  protected boolean a()
  {
    c("application/json;charset=utf-8");
    b(4);
    Object localObject = com.ad4screen.sdk.systems.b.a(this.e);
    if (((com.ad4screen.sdk.systems.b)localObject).f == null)
    {
      Log.warn("MemberManager|No sharedId, skipping tracking");
      return false;
    }
    if (!d.a(this.e).c(d.b.k))
    {
      Log.debug("Service interruption on LinkMemberTask");
      return false;
    }
    try
    {
      JSONObject localJSONObject1 = new JSONObject();
      JSONObject localJSONObject2 = new JSONObject();
      localJSONObject2.put("partnerId", ((com.ad4screen.sdk.systems.b)localObject).d);
      localJSONObject2.put("deviceId", ((com.ad4screen.sdk.systems.b)localObject).f);
      localObject = new JSONObject();
      ((JSONObject)localObject).put("id", this.g.a);
      ((JSONObject)localObject).put("lastConnection", h.a(new Date(this.g.c), h.a.a));
      ((JSONObject)localObject).put("connectionCount", this.g.b);
      localJSONObject2.put("member", localObject);
      localJSONObject1.put("linkMember", localJSONObject2);
      this.f = localJSONObject1.toString();
      return true;
    }
    catch (JSONException localJSONException)
    {
      Log.error("MemberManager|Could not build message to send to Ad4Screen", localJSONException);
    }
    return false;
  }
  
  public c b(c paramc)
  {
    return paramc;
  }
  
  protected String c()
  {
    return this.f;
  }
  
  public c d(String paramString)
    throws JSONException
  {
    super.d(paramString);
    paramString = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.service.modules.member.LinkMemberTask");
    if (!paramString.isNull("content")) {
      this.f = paramString.getString("content");
    }
    return this;
  }
  
  protected String d()
  {
    return this.f;
  }
  
  protected String e()
  {
    return d.a(this.e).a(d.b.k);
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.member.LinkMemberTask";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = super.toJSON();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("content", this.f);
    localJSONObject1.put("com.ad4screen.sdk.service.modules.member.LinkMemberTask", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/member/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */