package com.ad4screen.sdk.service.modules.tracking;

import android.content.Context;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.e;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.systems.b;
import com.ad4screen.sdk.systems.d;
import com.ad4screen.sdk.systems.d.b;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

public class c
  extends com.ad4screen.sdk.common.tasks.c
{
  String[] c;
  String d;
  String e;
  private final String f = "com.ad4screen.sdk.service.modules.tracking.FacebookProfileTrackingTask";
  private final String g = "content";
  private final Context h;
  private String i;
  
  public c(Context paramContext, String paramString1, String paramString2, String[] paramArrayOfString)
  {
    super(paramContext);
    this.h = paramContext;
    this.c = paramArrayOfString;
    this.d = paramString2;
    this.e = paramString1;
  }
  
  protected void a(String paramString)
  {
    Log.debug("Facebook|Send profile Success : " + paramString);
    d.a(this.h).e(d.b.D);
  }
  
  protected void a(Throwable paramThrowable)
  {
    Log.internal("Ad4Screen|Facebook Profile Tracking Service error!", paramThrowable);
  }
  
  protected boolean a()
  {
    int j = 0;
    Object localObject = b.a(this.h);
    if (!d.a(this.h).c(d.b.D))
    {
      Log.debug("Service interruption on FacebookProfileTrackingTask");
      return false;
    }
    if (((b)localObject).f == null)
    {
      Log.warn("Facebook|SharedId is undefined, cannot send profile");
      return false;
    }
    if (this.e == null)
    {
      Log.warn("Facebook|App Id is undefined, cannot send profile");
      return false;
    }
    c("application/x-www-form-urlencoded;charset=utf-8");
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new e("fb_appid", this.e));
    localArrayList.add(new e("sharedid", ((b)localObject).f));
    localArrayList.add(new e("partnerid", ((b)localObject).d));
    localArrayList.add(new e("fb_token", this.d));
    localObject = this.c;
    int k = localObject.length;
    while (j < k)
    {
      localArrayList.add(new e("fb_permissions[]", localObject[j]));
      j += 1;
    }
    this.i = h.a((e[])localArrayList.toArray(new e[localArrayList.size()]));
    return true;
  }
  
  public com.ad4screen.sdk.common.tasks.c b(com.ad4screen.sdk.common.tasks.c paramc)
  {
    return paramc;
  }
  
  protected String c()
  {
    return d.b.D.toString();
  }
  
  public com.ad4screen.sdk.common.tasks.c d(String paramString)
    throws JSONException
  {
    super.d(paramString);
    paramString = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.service.modules.tracking.FacebookProfileTrackingTask");
    if (!paramString.isNull("content")) {
      this.i = paramString.getString("content");
    }
    return this;
  }
  
  protected String d()
  {
    return this.i;
  }
  
  protected String e()
  {
    return d.a(this.h).a(d.b.D);
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.tracking.FacebookProfileTrackingTask";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = super.toJSON();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("content", this.i);
    localJSONObject1.put("com.ad4screen.sdk.service.modules.tracking.FacebookProfileTrackingTask", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/tracking/c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */