package com.ad4screen.sdk.service.modules.push;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import com.ad4screen.sdk.model.displayformats.d;
import com.ad4screen.sdk.model.displayformats.e;
import com.ad4screen.sdk.model.displayformats.e.a;
import com.ad4screen.sdk.model.displayformats.h;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class c
{
  private String a;
  private String b;
  private String c;
  private boolean d = true;
  private HashMap<String, String> e;
  private d f;
  
  public static c a(String paramString, JSONObject paramJSONObject)
    throws JSONException
  {
    c localc = new c();
    Object localObject1 = paramJSONObject.getString("id");
    Object localObject2 = paramJSONObject.getString("action");
    String str = paramJSONObject.getString("url");
    localc.b(paramJSONObject.getString("title"));
    localc.a((String)localObject1);
    if ("browser".equalsIgnoreCase((String)localObject2))
    {
      localObject2 = new e();
      ((e)localObject2).i = (paramString + '#' + (String)localObject1);
      ((e)localObject2).a.c = str;
      ((e)localObject2).b = e.a.b;
      localc.a((d)localObject2);
    }
    for (;;)
    {
      if (!paramJSONObject.isNull("icon")) {
        localc.c(paramJSONObject.getString("icon"));
      }
      if (paramJSONObject.isNull("ccp")) {
        break label356;
      }
      paramString = new HashMap();
      localObject1 = paramJSONObject.getJSONObject("ccp");
      int i = 0;
      while (i < ((JSONObject)localObject1).names().length())
      {
        str = ((JSONObject)localObject1).names().getString(i);
        paramString.put(str, ((JSONObject)localObject1).getString(str));
        i += 1;
      }
      if ("webView".equalsIgnoreCase((String)localObject2))
      {
        localObject2 = new e();
        ((e)localObject2).i = (paramString + '#' + (String)localObject1);
        ((e)localObject2).a.c = str;
        ((e)localObject2).a.d = "com_ad4screen_sdk_template_interstitial";
        localc.a((d)localObject2);
      }
      else if ("urlExec".equalsIgnoreCase((String)localObject2))
      {
        localObject2 = new h();
        ((h)localObject2).i = (paramString + "#" + (String)localObject1);
        ((h)localObject2).a = str;
        localc.a((d)localObject2);
      }
    }
    localc.a(paramString);
    label356:
    if (!paramJSONObject.isNull("destructive"))
    {
      localc.a(paramJSONObject.getBoolean("destructive"));
      return localc;
    }
    localc.a(true);
    return localc;
  }
  
  public int a(Context paramContext)
  {
    if (TextUtils.isEmpty(this.c)) {
      return -1;
    }
    return paramContext.getResources().getIdentifier(this.c, "drawable", paramContext.getPackageName());
  }
  
  public String a()
  {
    return this.b;
  }
  
  public void a(d paramd)
  {
    this.f = paramd;
  }
  
  public void a(String paramString)
  {
    this.a = paramString;
  }
  
  public void a(HashMap<String, String> paramHashMap)
  {
    this.e = paramHashMap;
  }
  
  public void a(boolean paramBoolean)
  {
    this.d = paramBoolean;
  }
  
  public HashMap<String, String> b()
  {
    if (this.e == null) {
      this.e = new HashMap();
    }
    return this.e;
  }
  
  public void b(String paramString)
  {
    this.b = paramString;
  }
  
  public d c()
  {
    return this.f;
  }
  
  public void c(String paramString)
  {
    this.c = paramString;
  }
  
  public boolean d()
  {
    return this.d;
  }
}


/* Location:              /Users/michael/Downloads/dex2jar-2.0/HappyFresh.jar!/com/ad4screen/sdk/service/modules/push/c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */