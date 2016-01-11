package com.ad4screen.sdk.service.modules.inbox;

import android.content.Context;
import android.os.RemoteException;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.tasks.c;
import com.ad4screen.sdk.g;
import com.ad4screen.sdk.inbox.Message;
import com.ad4screen.sdk.systems.b;
import com.ad4screen.sdk.systems.d;
import com.ad4screen.sdk.systems.d.b;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class e
  extends c
{
  private final String c = "com.ad4screen.sdk.service.modules.inbox.UpdateMessagesTask";
  private final String d = "content";
  private final Context e;
  private String f;
  private Message[] g;
  private g h;
  
  public e(Message[] paramArrayOfMessage, Context paramContext, g paramg)
  {
    super(paramContext);
    this.e = paramContext;
    this.g = paramArrayOfMessage;
    this.h = paramg;
  }
  
  protected void a(String paramString)
  {
    int i = 0;
    for (;;)
    {
      try
      {
        paramString = new JSONObject(paramString);
        if (paramString.isNull("Response")) {
          break;
        }
        paramString = paramString.getJSONObject("Response");
        if (paramString.getInt("returnCode") == 0)
        {
          paramString = new StringBuilder();
          if (i < this.g.length)
          {
            if (this.g[i].o)
            {
              paramString.append(this.g[i].a + " ");
              this.g[i].o = false;
            }
          }
          else
          {
            Log.debug("Inbox|Successfully updated inbox { Updated Messages : [ " + paramString.toString() + "]}");
            d.a(this.e).e(d.b.p);
            this.h.a(this.g);
          }
        }
        else
        {
          Log.error("Inbox|Send update Failure with error : " + paramString.getString("returnLabel"));
          this.h.a();
          return;
        }
      }
      catch (JSONException paramString)
      {
        Log.error("Inbox|Can't parse server response", paramString);
        return;
      }
      catch (RemoteException paramString)
      {
        Log.error("Inbox|No callback to trigger at the end of updateMessages method", paramString);
        return;
      }
      i += 1;
    }
  }
  
  protected void a(Throwable paramThrowable)
  {
    Log.error("Inbox|Messages update Failure : No connection");
  }
  
  protected boolean a()
  {
    c("application/json;charset=utf-8");
    a(4);
    Object localObject = b.a(this.e);
    if (((b)localObject).f == null)
    {
      Log.warn("Inbox|No sharedId, skipping tracking");
      return false;
    }
    if (!d.a(this.e).c(d.b.p))
    {
      Log.debug("Service interruption on UpdateMessagesTask");
      return false;
    }
    for (;;)
    {
      int i;
      try
      {
        JSONObject localJSONObject1 = new JSONObject();
        localJSONObject1.put("partnerId", ((b)localObject).d);
        localJSONObject1.put("sharedId", ((b)localObject).f);
        localObject = new JSONArray();
        i = 0;
        if (i < this.g.length)
        {
          if (!this.g[i].o) {
            break label228;
          }
          JSONObject localJSONObject2 = new JSONObject();
          localJSONObject2.put("id", this.g[i].a);
          localJSONObject2.put("read", this.g[i].l);
          localJSONObject2.put("archived", this.g[i].m);
          ((JSONArray)localObject).put(localJSONObject2);
          break label228;
        }
        if (((JSONArray)localObject).length() == 0)
        {
          Log.debug("Inbox|No Update to send");
          return false;
        }
      }
      catch (JSONException localJSONException)
      {
        Log.error("Inbox|Could not build message to send to Ad4Screen", localJSONException);
        return false;
      }
      localJSONException.put("messages", localObject);
      this.f = localJSONException.toString();
      return true;
      label228:
      i += 1;
    }
  }
  
  public c b(c paramc)
  {
    return paramc;
  }
  
  protected String c()
  {
    return d.b.p.toString();
  }
  
  public c d(String paramString)
    throws JSONException
  {
    super.d(paramString);
    paramString = new JSONObject(paramString).getJSONObject("com.ad4screen.sdk.service.modules.inbox.UpdateMessagesTask");
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
    return d.a(this.e).a(d.b.p);
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.inbox.UpdateMessagesTask";
  }
  
  public JSONObject toJSON()
    throws JSONException
  {
    JSONObject localJSONObject1 = super.toJSON();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject2.put("content", this.f);
    localJSONObject1.put("com.ad4screen.sdk.service.modules.inbox.UpdateMessagesTask", localJSONObject2);
    return localJSONObject1;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/inbox/e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */