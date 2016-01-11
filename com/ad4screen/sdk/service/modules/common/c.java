package com.ad4screen.sdk.service.modules.common;

import android.content.Context;
import android.net.Uri;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.cache.a;
import com.ad4screen.sdk.common.e;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.systems.b;
import com.ad4screen.sdk.systems.d;
import com.ad4screen.sdk.systems.d.b;
import java.util.Calendar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class c
  extends com.ad4screen.sdk.common.tasks.c
{
  private final String c = "com.ad4screen.sdk.service.modules.common.DownloadWebservicesURLTask";
  private final int d = 10;
  private final Context e;
  private int f = 1;
  
  public c(Context paramContext)
  {
    super(paramContext);
    this.e = paramContext;
  }
  
  protected void a(String paramString)
  {
    d.a(this.e).e(d.b.a);
    for (;;)
    {
      int i;
      try
      {
        paramString = new JSONObject(paramString).getJSONArray("data");
        i = 0;
        if (i < paramString.length())
        {
          JSONObject localJSONObject = paramString.getJSONObject(i);
          d.b localb = d.b.a(localJSONObject.getString("name"));
          if (localb != null)
          {
            d.a(this.e).a(localb, localJSONObject.getString("url"));
            Log.internal("DownloadWebservicesURLTask|" + localb.toString() + " url updated");
          }
        }
        else
        {
          b.a(this.e).b(Calendar.getInstance().getTime());
          Log.internal("DownloadWebservicesURLTask|Routes updated");
          a.a(this.e).f();
          return;
        }
      }
      catch (JSONException paramString)
      {
        Log.internal("DownloadWebservicesURLTask|Webservices URLs Parsing error!", paramString);
        return;
      }
      i += 1;
    }
  }
  
  protected void a(Throwable paramThrowable) {}
  
  protected boolean a()
  {
    if (!d.a(this.e).c(d.b.a))
    {
      Log.debug("Service interruption on DownloadWebservicesURLTask");
      return false;
    }
    a(4);
    return true;
  }
  
  protected boolean a(int paramInt, String paramString)
  {
    if (this.f < 10)
    {
      Log.internal("DownloadWebservicesURLTask|Call to server " + this.f + " failed");
      this.f += 1;
      Log.internal("DownloadWebservicesURLTask|Now calling server " + this.f);
      a.a(this.e).a(this);
      return false;
    }
    Log.error("DownloadWebservicesURLTask|Can't download webservices url");
    return super.a(paramInt, paramString);
  }
  
  public com.ad4screen.sdk.common.tasks.c b(com.ad4screen.sdk.common.tasks.c paramc)
  {
    return paramc;
  }
  
  protected String c()
  {
    return d.b.a.toString();
  }
  
  protected String d()
  {
    return null;
  }
  
  protected String e()
  {
    return h.a(this.e, d.a(this.e).a(d.b.a), false, new e[] { new e("SERVER", String.valueOf(this.f)), new e("version", Uri.encode("A3.2.1")) });
  }
  
  public String getClassKey()
  {
    return "com.ad4screen.sdk.service.modules.common.DownloadWebservicesURLTask";
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/common/c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */