package com.ad4screen.sdk.service.modules.member;

import android.content.Context;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.tasks.c;
import com.ad4screen.sdk.systems.b;
import com.ad4screen.sdk.systems.d;
import com.ad4screen.sdk.systems.d.b;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class f
  extends c
{
  private final String c = "com.ad4screen.sdk.service.modules.member.UnlinkMembersTask";
  private final String d = "content";
  private final Context e;
  private String f;
  private String[] g;
  
  public f(String[] paramArrayOfString, Context paramContext)
  {
    super(paramContext);
    this.e = paramContext;
    this.g = paramArrayOfString;
  }
  
  protected void a(String paramString)
  {
    try
    {
      paramString = new JSONObject(paramString);
      if (!paramString.isNull("unlinkMembersResponse"))
      {
        paramString = paramString.getJSONObject("unlinkMembersResponse");
        if (paramString.getInt("returnCode") == 0)
        {
          Log.debug("MemberManager|Unlink Success " + this.f);
          d.a(this.e).e(d.b.l);
          return;
        }
        Log.error("MemberManager|Unlink Failure with error " + paramString.getString("returnCode") + " : " + paramString.getString("returnLabel"));
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
    Object localObject = b.a(this.e);
    if (((b)localObject).f == null)
    {
      Log.warn("MemberManager|No sharedId, skipping tracking");
      return false;
    }
    if (!d.a(this.e).c(d.b.l))
    {
      Log.debug("Service interruption on UnlinkMembersTask");
      return false;
    }
    try
    {
      JSONObject localJSONObject1 = new JSONObject();
      JSONObject localJSONObject2 = new JSONObject();
      localJSONObject2.put("partnerId", ((b)localObject).d);
      localJSONObject2.put("deviceId", ((b)localObject).f);
      localObject = new JSONArray();
      int i = 0;
      while (i < this.g.length)
      {
        ((JSONArray)localObject).put(this.g[i]);
        i += 1;
      }
      localJSONObject2.put("members", localObject);
      localJSONObject1.put("unlinkMembers", localJSONObject2);
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
    Object localObject = (f)paramc;
    try
    {
      paramc = new JSONObject(d());
      localObject = new JSONObject(((f)localObject).d()).getJSONArray("members");
      JSONArray localJSONArray = paramc.getJSONArray("members");
      int i = 0;
      while (i < ((JSONArray)localObject).length())
      {
        localJSONArray.put(((JSONArray)localObject).get(i));
        i += 1;
      }
      this.f = paramc.toString();
      return this;
    }
    catch (JSONException paramc)
    {
      Log.internal("Failed to merge " + c(), paramc);
      return this;
    }
    catch (NullPointerException paramc)
    {
      Log.internal("Failed to merge " + c(), paramc);
    }
    return this;
  }
  
  protected String c()
  {
    return d.b.l.toString();
  }
  
  public c d(String paramString)
    throws JSONException
  {
    super.d(paramString);
    paramString = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.service.modules.member.UnlinkMembersTask");
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
    return d.a(this.e).a(d.b.l);
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.member.UnlinkMembersTask";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = super.toJSON();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("content", this.f);
    localJSONObject1.put("com.ad4screen.sdk.service.modules.member.UnlinkMembersTask", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/member/f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */