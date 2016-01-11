package com.ad4screen.sdk.common.tasks;

import android.content.Context;
import com.ad4screen.sdk.common.g;
import com.ad4screen.sdk.common.persistence.c;
import com.ad4screen.sdk.common.persistence.d;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class b
  implements c<b>, d, Runnable
{
  public long a = g.e().a();
  public Context b;
  
  public b(Context paramContext)
  {
    this.b = paramContext;
  }
  
  public b a(String paramString)
    throws JSONException
  {
    paramString = new JSONObject(paramString);
    if (!paramString.isNull("creationTimestamp")) {
      this.a = paramString.getLong("creationTimestamp");
    }
    return this;
  }
  
  public abstract boolean a();
  
  public final void run()
  {
    if (!a()) {
      com.ad4screen.sdk.common.cache.a.a(this.b).a(this);
    }
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = new JSONObject();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject1.put("type", getClassKey());
    localJSONObject1.put("creationTimestamp", this.a);
    localJSONObject1.put("com.ad4screen.sdk.common.tasks.ExternalTask", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/tasks/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */