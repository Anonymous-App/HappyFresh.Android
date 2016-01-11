package com.ad4screen.sdk.service.modules.common;

import android.content.Context;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.e;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.tasks.c;
import org.json.JSONException;
import org.json.JSONObject;

public class g
  extends c
{
  Context c;
  String d;
  e[] e;
  private final String f = "com.ad4screen.sdk.service.modules.common.TrackUrlTask";
  private final String g = "url";
  
  public g(Context paramContext, String paramString, e... paramVarArgs)
  {
    super(paramContext);
    this.c = paramContext;
    this.d = paramString;
    this.e = paramVarArgs;
  }
  
  protected void a(String paramString)
  {
    Log.debug("Tracking succeed : " + e() + " with response : " + paramString);
  }
  
  protected void a(Throwable paramThrowable)
  {
    Log.debug("Tracking failed : " + e(), paramThrowable);
  }
  
  protected boolean a()
  {
    this.d = h.a(this.c, this.d, false, this.e);
    return true;
  }
  
  public c b(c paramc)
  {
    return paramc;
  }
  
  protected String c()
  {
    return e();
  }
  
  public c d(String paramString)
    throws JSONException
  {
    super.d(paramString);
    paramString = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.service.modules.common.TrackUrlTask");
    if (!paramString.isNull("url")) {
      this.d = paramString.getString("url");
    }
    return this;
  }
  
  protected String d()
  {
    return null;
  }
  
  protected String e()
  {
    return this.d;
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.common.TrackUrlTask";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = super.toJSON();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("url", this.d);
    localJSONObject1.put("com.ad4screen.sdk.service.modules.common.TrackUrlTask", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/common/g.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */