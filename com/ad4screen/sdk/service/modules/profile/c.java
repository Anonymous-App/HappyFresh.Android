package com.ad4screen.sdk.service.modules.profile;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.e;
import com.ad4screen.sdk.common.g;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.service.modules.tracking.model.a.a;
import com.ad4screen.sdk.systems.b;
import com.ad4screen.sdk.systems.d;
import com.ad4screen.sdk.systems.d.b;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class c
  extends com.ad4screen.sdk.common.tasks.c
{
  private final String c = "com.ad4screen.sdk.service.modules.profile.UpdateMemberInfoTask";
  private final String d = "content";
  private final String e = "activeMember";
  private final Context f;
  private Bundle g;
  private String h;
  private String i;
  
  public c(Context paramContext, Bundle paramBundle)
  {
    super(paramContext);
    this.f = paramContext;
    this.g = paramBundle;
  }
  
  protected void a(String paramString)
  {
    Log.debug("UpdateMemberInfoTask|Profile is successfully updated");
    d.a(this.f).e(d.b.d);
  }
  
  protected void a(Throwable paramThrowable)
  {
    Log.error("UpdateMemberInfoTask|Profile update failed");
  }
  
  protected boolean a()
  {
    Object localObject1 = b.a(this.f);
    Object localObject2 = com.ad4screen.sdk.service.modules.member.c.a(this.f);
    if (((b)localObject1).f == null)
    {
      Log.warn("UpdateMemberInfoTask|No sharedId, skipping user info update");
      return false;
    }
    if (!((com.ad4screen.sdk.service.modules.member.c)localObject2).c())
    {
      Log.warn("UpdateMemberInfoTask|No member logged in. Please use login method before updating member information");
      return false;
    }
    if (!d.a(this.f).c(d.b.d))
    {
      Log.debug("Service interruption on UpdateMemberInfoTask");
      return false;
    }
    h();
    i();
    this.i = ((com.ad4screen.sdk.service.modules.member.c)localObject2).b();
    try
    {
      localObject1 = new JSONObject();
      localObject2 = this.g.keySet().iterator();
      while (((Iterator)localObject2).hasNext())
      {
        String str = (String)((Iterator)localObject2).next();
        ((JSONObject)localObject1).put(str, this.g.get(str).toString());
      }
      Log.debug("UpdateMemberInfoTask", localException);
    }
    catch (Exception localException)
    {
      Log.error("UpdateMemberInfoTask|Could not build message to send to server", localException);
      return false;
    }
    this.h = localException.toString();
    return true;
  }
  
  protected boolean a(int paramInt, String paramString)
  {
    if ((paramInt == 500) && (paramString != null))
    {
      Log.error("UpdateMemberInfoTask|Request succeeded but parameters are invalid, server returned :" + paramString);
      try
      {
        Iterator localIterator = new com.ad4screen.sdk.service.modules.tracking.model.a().a(paramString).a().iterator();
        while (localIterator.hasNext())
        {
          a.a locala = (a.a)localIterator.next();
          if (locala.b().toLowerCase(Locale.US).contains("unknown fields"))
          {
            Log.error("UpdatMemberInfoTask|Some fields does not exists : " + locala.b());
            return true;
          }
        }
      }
      catch (JSONException localJSONException)
      {
        Log.internal("UpdateMemberInfoTask|Error Parsing failed : " + localJSONException.getMessage(), localJSONException);
      }
    }
    return super.a(paramInt, paramString);
  }
  
  public com.ad4screen.sdk.common.tasks.c b(com.ad4screen.sdk.common.tasks.c paramc)
  {
    paramc = (c)paramc;
    try
    {
      paramc = new JSONObject(paramc.d());
      JSONObject localJSONObject = new JSONObject(d());
      JSONArray localJSONArray = paramc.names();
      int j = 0;
      while (j < localJSONArray.length())
      {
        String str = localJSONArray.getString(j);
        localJSONObject.put(str, String.valueOf(paramc.get(str)));
        j += 1;
      }
      this.h = localJSONObject.toString();
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
    return d.b.d.toString() + "/" + g.e().a() + "/" + (int)(Math.random() * 10000.0D);
  }
  
  public com.ad4screen.sdk.common.tasks.c d(String paramString)
    throws JSONException
  {
    super.d(paramString);
    paramString = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.service.modules.profile.UpdateMemberInfoTask");
    if (!paramString.isNull("content")) {
      this.h = paramString.getString("content");
    }
    if (!paramString.isNull("activeMember")) {
      this.i = paramString.getString("activeMember");
    }
    return this;
  }
  
  protected String d()
  {
    return this.h;
  }
  
  protected String e()
  {
    return h.a(this.f, d.a(this.f).a(d.b.d), false, new e[] { new e("memberId", Uri.encode(this.i)) });
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.profile.UpdateMemberInfoTask";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = super.toJSON();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("content", this.h);
    localJSONObject2.put("activeMember", this.i);
    localJSONObject1.put("com.ad4screen.sdk.service.modules.profile.UpdateMemberInfoTask", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/profile/c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */