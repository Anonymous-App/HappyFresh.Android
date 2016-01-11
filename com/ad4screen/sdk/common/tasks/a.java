package com.ad4screen.sdk.common.tasks;

import android.content.Context;
import android.net.Uri;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.e;
import com.ad4screen.sdk.service.modules.tracking.i;
import com.ad4screen.sdk.service.modules.tracking.j;
import com.ad4screen.sdk.systems.f;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class a
  extends c
{
  private final String c = "com.ad4screen.sdk.common.tasks.BulkManager";
  private Context d;
  private ArrayList<c> e = new ArrayList();
  private String f;
  private boolean g;
  
  public a(Context paramContext)
  {
    super(paramContext);
    this.d = paramContext;
    a(0);
    h();
  }
  
  public void a(c paramc)
  {
    if ((paramc instanceof i))
    {
      this.g = true;
      j localj = new j(this.d);
      if (localj.a())
      {
        this.e.add(localj);
        this.e.add(paramc);
        paramc = new com.ad4screen.sdk.service.modules.common.b(this.d);
        if (!paramc.a()) {
          break label84;
        }
        this.e.add(paramc);
      }
    }
    for (;;)
    {
      return;
      Log.debug("BulkManager|TrackingTask can't be launched right now because of service interruption on VersionTrackingTask.");
      return;
      label84:
      Log.debug("BulkManager|ConfigurationTrackingTask can't be launched right now because of service interruption.");
      return;
      this.e.add(paramc);
      if ((paramc instanceof com.ad4screen.sdk.service.modules.location.c))
      {
        int i = 0;
        while (i < this.e.size())
        {
          if (((this.e.get(i) instanceof com.ad4screen.sdk.service.modules.geofencing.d)) || ((this.e.get(i) instanceof com.ad4screen.sdk.service.modules.geofencing.c)))
          {
            paramc = (c)this.e.get(i);
            this.e.remove(i);
            this.e.add(paramc);
            this.g = true;
          }
          i += 1;
        }
      }
    }
  }
  
  protected void a(String paramString)
  {
    for (;;)
    {
      int i;
      c localc;
      try
      {
        JSONArray localJSONArray = new JSONObject(paramString).getJSONArray("_bulk");
        i = 0;
        if (i >= this.e.size()) {
          break label269;
        }
        localc = (c)this.e.get(i);
        int j;
        try
        {
          JSONObject localJSONObject = localJSONArray.getJSONObject(i);
          if (!localJSONObject.isNull("code"))
          {
            paramString = localJSONObject.toString();
            j = localJSONObject.getInt("code");
            if (!localJSONObject.isNull("body")) {
              paramString = localJSONObject.getJSONObject("body").toString();
            }
            if (j == 200)
            {
              localc.a(paramString);
              f.a().a(new d.b(localc, localc.c()));
            }
          }
          else
          {
            if (localJSONObject.isNull("links")) {
              break label277;
            }
            paramString = localJSONObject.getJSONObject("links").getString("next");
            com.ad4screen.sdk.service.modules.common.h.a(this.d, paramString, new e[0]);
          }
        }
        catch (JSONException paramString)
        {
          localc.a(new ConnectException("Could not reach Accengage servers"));
          f.a().a(new d.a(localc, localc.c()));
        }
        if (localc.a(j, paramString)) {
          continue;
        }
      }
      catch (JSONException paramString)
      {
        a(new JSONException("BulkManager failed to read response from server").initCause(paramString));
        return;
      }
      localc.a(new ConnectException("Could not reach Accengage servers"));
      f.a().a(new d.a(localc, localc.c()));
      continue;
      label269:
      this.e.clear();
      return;
      label277:
      i += 1;
    }
  }
  
  protected void a(Throwable paramThrowable)
  {
    Iterator localIterator = this.e.iterator();
    while (localIterator.hasNext())
    {
      c localc = (c)localIterator.next();
      localc.a(new ConnectException("BulkManager request failed").initCause(paramThrowable));
      f.a().a(new d.a(localc, localc.c()));
    }
    this.e.clear();
  }
  
  protected boolean a()
  {
    return true;
  }
  
  protected boolean a(int paramInt, String paramString)
  {
    if (paramInt == 404)
    {
      Log.internal("BulkManager|404 error on _bulk request. Has this _bulk been wrongly sent in GET?");
      return true;
    }
    return super.a(paramInt, paramString);
  }
  
  public c b(c paramc)
  {
    return paramc;
  }
  
  public boolean b()
  {
    return this.e.size() == 0;
  }
  
  protected String c()
  {
    return com.ad4screen.sdk.systems.d.b.f.toString();
  }
  
  protected String d()
  {
    return this.f;
  }
  
  protected String e()
  {
    return com.ad4screen.sdk.systems.d.a(this.d).a(com.ad4screen.sdk.systems.d.b.f);
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.common.tasks.BulkManager";
  }
  
  public void run()
  {
    do
    {
      JSONObject localJSONObject1;
      JSONArray localJSONArray;
      for (;;)
      {
        try
        {
          com.ad4screen.sdk.systems.b localb = com.ad4screen.sdk.systems.b.a(this.d);
          localJSONObject1 = new JSONObject();
          localJSONObject1.put("sync", this.g);
          localJSONArray = new JSONArray();
          Iterator localIterator = this.e.iterator();
          if (!localIterator.hasNext()) {
            break;
          }
          c localc = (c)localIterator.next();
          JSONObject localJSONObject2 = new JSONObject();
          localJSONObject2.put("url", com.ad4screen.sdk.common.h.a(this.d, localc.e(), new e[] { new e("partnerId", Uri.encode(localb.d)), new e("sharedId", Uri.encode(localb.f)) }));
          if ((localc.d() != null) && (localc.d().length() > 0))
          {
            localJSONObject2.put("method", "POST");
            localJSONObject2.put("body", new JSONObject(localc.d()));
            localJSONArray.put(localJSONObject2);
          }
          else
          {
            localJSONObject2.put("method", "GET");
          }
        }
        catch (JSONException localJSONException)
        {
          a(new JSONException("BulkManager failed to construct valid request").initCause(localJSONException));
          return;
        }
      }
      localJSONObject1.put("_bulk", localJSONArray);
      this.f = localJSONObject1.toString();
    } while (!a());
    f();
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/common/tasks/a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */